#!/bin/bash

# 프로젝트 설정
PROJECT_ID="ddantime"
REGION="asia-northeast3"
SERVICE_NAME="ddantime-dev"
IMAGE="asia-northeast3-docker.pkg.dev/$PROJECT_ID/ddantime-dev/ddantime-dev:latest"
ENV_FILE=".env.dev"
TEMP_YAML="env.gcp.yaml"
INSTANCE_CONNECTION_NAME="ddantime:asia-northeast3:ddantime-sql"

echo "📦 .env.dev → env.gcp.yaml 변환 중..."
awk -F= '{
  key=$1;
  sub(/^[^=]*=/, "", $0);  # 첫 번째 = 이전 제거하고 나머지 전체를 value로 취급
  print key ": " $0;
}' "$ENV_FILE" > "$TEMP_YAML"

echo "🚀 Cloud Run 배포 시작..."
gcloud run deploy $SERVICE_NAME \
  --image=$IMAGE \
  --platform=managed \
  --region=$REGION \
  --allow-unauthenticated \
  --env-vars-file=$TEMP_YAML \
  --add-cloudsql-instances=$INSTANCE_CONNECTION_NAME

echo "🧹 임시 파일 정리 중..."
rm -f $TEMP_YAML

echo "✅ 배포 완료!"
