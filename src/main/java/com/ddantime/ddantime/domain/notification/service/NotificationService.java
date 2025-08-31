package com.ddantime.ddantime.domain.notification.service;

import com.ddantime.ddantime.common.firebase.FcmService;
import com.ddantime.ddantime.domain.notification.dto.NotificationMessage;
import com.ddantime.ddantime.domain.setting.notification.repository.NotificationPromiseTimeRepository;
import com.ddantime.ddantime.domain.setting.notification.repository.NotificationSettingRepository;
import com.ddantime.ddantime.domain.user.entity.UserActivityMeta;
import com.ddantime.ddantime.domain.user.repository.DeviceInfoRepository;
import com.ddantime.ddantime.domain.user.repository.UserActivityMetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserActivityMetaRepository userActivityMetaRepository;
    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationPromiseTimeRepository notificationPromiseTimeRepository;
    private final DeviceInfoRepository deviceInfoRepository;
    private final FcmService fcmService;

    private final Random random = new Random();

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // 3일 이상 접속 X
    private static final List<NotificationMessage> COMEBACK_NO_ACCESS_MESSAGES = List.of(
            new NotificationMessage("그동안 어떻게 지내셨어요?", "딴타임이 조금 궁금해졌어요. 나만의 작은 쉼, 다시 기록해볼래요?", "오랜만이에요! 잊고 있던 순간을 기록해보세요.✨"),
            new NotificationMessage("딴타임이 그리워하고 있어요ㅠㅠ", "쉬엄쉬엄도 괜찮아요. 지금 생각나는 그 순간, 툭 남겨보는 건 어때요?", "다시 만나 반가워요! 딴타임을 남겨보세요. \uD83E\uDD17"),
            new NotificationMessage("딴타임이 궁금하긴 한데요…", "기록할 게 없어도 괜찮아요. 그저 잘 지내고 있길 바라요.", "반가워요! 새로운 딴타임 순간을 기록으로 남겨볼까요?✍\uFE0F")
    );

    // 3일 내 접속 O
    private static final List<NotificationMessage> COMEBACK_ACCESS_MESSAGES = List.of(
            new NotificationMessage("오늘도 딴타임과 함께해요!", "딴타임에 자주 와주셔서 고마워요. 오늘은 한 줄 기록해 볼까요?", "나만의 딴타임, 잊기 전에 기록해보세요.\uD83D\uDCA1"),
            new NotificationMessage("딴타임과 조금씩 친해지고 있어요 🙂", "딴타임과 함께하는 시간이 늘어난 요즘, 기록으로 작은 습관을 만들어봐요!", "작은 순간도 좋아요. 가볍게 기록해봐요.\uD83C\uDF31"),
            new NotificationMessage("기록은 천천히, 딴타임은 늘 여기 있어요.", "요즘 딴타임과 함께해 주셔서 감사해요! 기억에 남는 순간을 지금 남겨보세요!", "오늘의 딴타임을 기록으로 남겨보세요!✍\uFE0F")
    );

    private static final List<NotificationMessage> PROMISE_MESSAGES = List.of(
            new NotificationMessage("약속한 딴타임 시간입니다!", "그 어떤 것도 딴타임이 될 수 있어요! 지금 생각나는 작은 순간, 딴타임에 남겨볼까요?", "약속한 시간이에요! 기록할 준비, 되셨나요?"),
            new NotificationMessage("딴타임 약속, 지금이 그때!", "지금 잠깐 숨 돌릴 타이밍이에요. 떠오르는 그 순간, 가볍게 기록해볼까요?", "띵동! 기록할 시간이라고 알려드려요. \uD83D\uDD14"),
            new NotificationMessage("딴타임 하기로 한 시간이에요!", "오늘 하루는 어떠셨나요? 나만의 딴타임이 있다면 한번 기록해두는 것도 좋아요!", "떠오르는 순간들을 기록으로 남길 시간이에요!")
    );

    private NotificationMessage getRandomMessage(List<NotificationMessage> msgData) {
        return msgData.get(random.nextInt(msgData.size()));
    }

    public void sendComeBackNotifications() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(5);

        // 대상 조회
        List<UserActivityMeta> targets = userActivityMetaRepository.findComeBackTargets(threshold);

        log.info("돌아와요 발송 대상 수: {}", targets.size());

        if(!targets.isEmpty()){
            targets.forEach(meta -> log.info("대상 userId={} lastRecordDate={} lastAccessDate={}",
                    meta.getUser().getId(),
                    meta.getLastRecordDate(),
                    meta.getLastAccessDate()));


            // 실행당 그룹별로 1개만 뽑기
            NotificationMessage accessedMsg = getRandomMessage(COMEBACK_ACCESS_MESSAGES);
            NotificationMessage notAccessedMsg = getRandomMessage(COMEBACK_NO_ACCESS_MESSAGES);

            int success = 0, fail = 0;

            Map<String, String> payload = new HashMap<>(Map.of(
                    "environment", activeProfile,
                    "type", "comeback"
            ));

            for (UserActivityMeta meta : targets) {
                String fcmToken = meta.getUser().getDeviceInfo().getFcmToken();
                if (fcmToken == null || fcmToken.isBlank()) {
                    log.warn("FCM 토큰 없음 userId={}", meta.getUser().getId());
                    continue;
                }

                try {
                    String title = notAccessedMsg.title();
                    String body = notAccessedMsg.body();
                    String foregroundMessage = notAccessedMsg.foregroundMessage();

                    LocalDateTime lastAccess = meta.getLastAccessDate();
                    if (lastAccess != null && !lastAccess.isBefore(LocalDateTime.now().minusDays(3))) {
                        // 최근 3일 안에 접속한 사용자
                        title = accessedMsg.title();
                        body = accessedMsg.body();
                        foregroundMessage = accessedMsg.foregroundMessage();
                    }

                    payload.put("foregroundMessage", foregroundMessage);
                    fcmService.sendMessage(fcmToken, title, body, payload);
                    success++;
                } catch (Exception e) {
                    fail++;
                    log.error("FCM 발송 실패 userId={} token={}", meta.getUser().getId(), fcmToken, e);
                }
            }
            log.info("✅ 돌아와요 알림 발송 완료: 성공 {}건, 실패 {}건", success, fail);
        }

    }

    public void sendPromiseNotifications() {
        // XXX. 스케줄러 비용이슈로 우선 30분 단위로 수정
        // 요청 시각을 slot 단위로 변환 (30분 단위)
        int minuteOfDay = LocalTime.now().getHour() * 60 + LocalTime.now().getMinute();
        int slot = (minuteOfDay / 30) * 30;  // 30분 단위로 내림

        log.info("⏰ scheduledTime={}",  toHHMM(slot));

        // 알림 켠 유저 중 slot에 해당하는 대상자 조회
        List<UUID> userIds = notificationPromiseTimeRepository.findUserIdsForMinute((short) slot);

        // slot 기반 유저
        sendPromiseGroup(userIds, slot, false);

        // 현재 20:00 이면, 설정 시간이 없는 사용자도 조회
        if (slot == 1200) {
            List<UUID> userIdsWithoutTime  = notificationSettingRepository.findUserIdsWithoutPromiseTimes();
            sendPromiseGroup(userIdsWithoutTime, slot, true);
        }
    }

    private String toHHMM(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    private void sendPromiseGroup(List<UUID> userIds, int slot, boolean withoutTime) {
        if (userIds.isEmpty()) return;

        List<String> tokens = deviceInfoRepository.findFcmTokensByUserIds(userIds);

        log.info("약속해요 발송 대상 tokens 수: {} ({} 사용자)",
                tokens.size(),
                withoutTime ? "default" : "custom");

        int success = 0, fail = 0;
        NotificationMessage notificationMessage = getRandomMessage(PROMISE_MESSAGES);

        String title = notificationMessage.title();
        String body = notificationMessage.body();
        String foregroundMessage = notificationMessage.foregroundMessage();

        Map<String, String> payload = new HashMap<>(Map.of(
                "environment", activeProfile,
                "type", "promise",
                "foregroundMessage", foregroundMessage,
                "scheduledTime", toHHMM(slot),
                "timeType", withoutTime ? "default" : "custom"
        ));

        for (String token : tokens) {
            try {
                fcmService.sendMessage(token, title, body, payload);
                success++;
            } catch (Exception e) {
                fail++;
                log.error("FCM 발송 실패 token={}", token, e);
            }
        }

        log.info("✅ 약속해요 알림 발송 완료: 성공 {}건, 실패 {}건 ({} 사용자)",
                success, fail, withoutTime ? "default" : "custom");
    }

}