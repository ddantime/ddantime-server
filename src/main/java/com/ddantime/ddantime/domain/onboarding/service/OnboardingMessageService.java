package com.ddantime.ddantime.domain.onboarding.service;

import com.ddantime.ddantime.common.exception.CustomException;
import com.ddantime.ddantime.common.exception.ErrorCode;
import com.ddantime.ddantime.domain.onboarding.dto.OnboardingMessageDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OnboardingMessageService {

    private final Map<String, OnboardingMessageDto> messageMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * JSON 질문 값으로 응답 메시지 Map으로 만들기
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        loadMessages().forEach(msg ->
                messageMap.put(makeKey(msg.getReason(), msg.getTiming(), msg.getDayState()), msg)
        );
    }

    public OnboardingMessageDto getMessage(String reason, String timing, String dayState) {
        String key = makeKey(reason, timing, dayState);
        OnboardingMessageDto message = messageMap.get(key);

        if (message == null || message.getMessage() == null || message.getMessage().isEmpty()) {
            throw new CustomException(ErrorCode.ONBOARDING_MESSAGE_NOT_FOUND);
        }

        return message;
    }

    private List<OnboardingMessageDto> loadMessages() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("onboarding_messages.json");
        if (inputStream == null) throw new FileNotFoundException("onboarding_messages.json not found");

        return objectMapper.readValue(inputStream, new TypeReference<>() {});
    }

    private String makeKey(String reason, String timing, String dayState) {
        return String.join("|", reason.trim(), timing.trim(), dayState.trim());
    }
}
