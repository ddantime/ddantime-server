# 딴타임 (ddantime)

> 자기 이해와 시간 관리를 돕는 '딴짓 기록' 서비스의 백엔드 서버

딴타임은 사용자가 하루 동안의 '딴짓'을 기록하고,
이를 분석하여 자신의 시간 사용 패턴을 이해하고 더 나은 습관을 만들 수 있도록 돕는 서비스입니다.

이 저장소는 딴타임 서비스의 백엔드 API를 제공하며,
인증, 기록 관리, 통계, 알림 등 핵심 기능을 담당합니다.

---

# 🚀 프로젝트를 만든 이유

많은 시간 관리 서비스는 "계획"에 집중하지만,
실제로 시간을 어떻게 사용했는지 기록하고 돌아보는 기능은 부족하다고 느꼈습니다.

딴타임은 사용자가 의도하지 않았던 시간 사용(딴짓)을 기록하고,
이를 통해 자신의 행동 패턴을 객관적으로 이해할 수 있도록 만드는 것을 목표로 시작되었습니다.

---

# 🎯 해결하려는 문제

- 시간을 어디에 소비하는지 기억하기 어렵다.
- 반복되는 비효율적인 습관을 파악하기 어렵다.
- 단순한 TODO 앱만으로는 행동 패턴을 개선하기 어렵다.

딴타임은 사용자의 기록을 기반으로 통계와 피드백을 제공하여
스스로 행동을 돌아보고 개선할 수 있도록 돕습니다.

---

# ✨ 주요 기능

- 딴짓 기록 관리
- 사용자 인증
- 통계 데이터 제공
- Firebase Cloud Messaging 기반 푸시 알림
- 스케줄링을 통한 자동 알림
- Swagger(OpenAPI) API 문서 제공

---

# 🏗 기술 스택

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL
- Firebase Cloud Messaging (FCM)
- Google Cloud Run
- Google Cloud Scheduler
- SpringDoc OpenAPI

---

# ▶️ 실행 방법

```bash
git clone https://github.com/ddantime/ddantime-server.git

cd ddantime-server
```

application.yml을 설정한 후

```bash
./gradlew bootRun
```

또는

```bash
./gradlew test
```

---

# 📈 앞으로의 개선 계획

- 테스트 코드 확대
- API 안정성 개선
- 사용자 통계 기능 고도화
- AI 기반 행동 분석 기능 연구
- CI/CD 자동화 강화
- 성능 모니터링 및 로그 분석 개선

---

# 🤝 유지관리 방향

이 프로젝트는 지속적으로 유지·개선되는 오픈소스 프로젝트를 목표로 합니다.

앞으로도 기능 개선, 버그 수정, 문서화, 테스트 코드 보강을 꾸준히 진행하며,
시간 관리와 자기 이해를 돕는 서비스로 발전시킬 계획입니다.

Issue와 Pull Request를 통한 기여를 언제든 환영합니다.


![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.4.5-brightgreen)
![License](https://img.shields.io/badge/license-MIT-green)
