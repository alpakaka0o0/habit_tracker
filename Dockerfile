# 1단계: 빌드 스테이지
FROM gradle:8.4-jdk17 AS builder
WORKDIR /app

# 필요한 것들 복사
COPY . .

# 테스트 생략하고 빌드
RUN gradle clean build

# 2단계: 실행 스테이지
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]