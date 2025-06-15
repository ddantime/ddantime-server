#chmod +x build.sh
# !/bin/bash

PROJECT_ID="ddantime"
IMAGE="asia-northeast3-docker.pkg.dev/$PROJECT_ID/ddantime-dev/ddantime-dev:latest"

echo "📦 Docker build (for linux/amd64) 시작..."
docker buildx build \
  --platform=linux/amd64 \
  -f Dockerfile.dev \
  -t $IMAGE \
  --push .

echo "✅ Docker image 빌드 및 GCP Artifact Registry 푸시 완료!"
