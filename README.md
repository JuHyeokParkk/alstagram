## 고려할 사항
- JWT를 사용했으나 보안적인 issue를 고려하게 되어 완전한 stateless 특성을 지니고 있지는 않음
  - 사용자 토큰에 대한 저장 및 접근의 경우 redis 사용 고려
  - 세션 클러스터링을 적용해 확장에 용이한 설계

- Refresh Token의 재사용 감지를 위한 Refresh Token Rotation 적용

- 비밀번호 불일치 횟수 제한, 일정 횟수 초과 시 계정 잠금 -   brute-force attack 방지
- 메일 본인 인증을 통한 가입 및 계정 잠금 해제
- CSRF, XSS 대응
- JWT의 보안적 문제에 대해 일정 부분 보완이 가능한 PASETO 사용 고려
- Remember me 기능
- 휴면 회원 및 복귀한 휴면 회원 처리
- 로그인 종단 간 SSL 설정
- BFF Pattern + Reference Token 적용

## 화면 예시


|로그인 화면|회원가입 화면|
|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/77018024/204141536-bc6e8a46-657d-4ad8-a2b7-0ca0cec032a0.png" width="100%" height="350">|<img src="https://user-images.githubusercontent.com/77018024/204142810-c6b289f6-6c74-4736-93ba-ef7e7e2a21d4.png" width="100%" height="350">|

|메일 인증 화면|피드 화면|
|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/77018024/204142953-e3326f77-28fd-40cf-b677-14b3e86217ed.png" width="100%" height="350">|<img src="https://user-images.githubusercontent.com/77018024/204142174-8ba8c37e-1167-4321-a860-895b5d14d5e5.png" width="100%" height="350">|

