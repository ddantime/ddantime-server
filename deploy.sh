#!/bin/bash

# 프로젝트 설정
PROJECT_ID="ddantime"
REGION="asia-northeast3"
SERVICE_NAME="ddantime-dev"
REPO="ddantime-dev"
IMAGE_NAME="ddantime-dev"
IMAGE="asia-northeast3-docker.pkg.dev/${PROJECT_ID}/${REPO}/${IMAGE_NAME}:latest"

INSTANCE_CONNECTION_NAME="${PROJECT_ID}:${REGION}:ddantime"

RUN_SA="ddantime-run-dev@${PROJECT_ID}.iam.gserviceaccount.com"
FIREBASE_PROJECT_ID="ddantime-ygjh"

gcloud run deploy "${SERVICE_NAME}" \
  --project "${PROJECT_ID}" \
  --region "${REGION}" \
  --image "${IMAGE}" \
  --platform managed \
  --allow-unauthenticated \
  --service-account "${RUN_SA}" \
  --add-cloudsql-instances "${INSTANCE_CONNECTION_NAME}" \
  --set-secrets DB_USERNAME=db-username:latest \
  --set-secrets DB_PASSWORD=db-password:latest \
  --set-secrets=CRYPTO_DEK=crypto-dek:latest \
  --set-env-vars SPRING_PROFILES_ACTIVE=dev,SWAGGER_SERVER_URL="https://ddantime-dev-647554719138.asia-northeast3.run.app/",DB_URL="jdbc:postgresql:///ddantime-dev?cloudSqlInstance=${INSTANCE_CONNECTION_NAME}&socketFactory=com.google.cloud.sql.postgres.SocketFactory",FIREBASE_PROJECT_ID="${FIREBASE_PROJECT_ID}",JAVA_TOOL_OPTIONS="-Duser.timezone=Asia/Seoul" \
  --timeout 300

echo "✅ 배포 완료!"