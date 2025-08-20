#chmod +x build.sh
# !/bin/bash
PROJECT_ID="ddantime"
REGION="asia-northeast3"
REPO="ddantime-dev"
IMAGE_NAME="ddantime-dev"
IMAGE="asia-northeast3-docker.pkg.dev/${PROJECT_ID}/${REPO}/${IMAGE_NAME}:latest"

echo "📦 Docker buildx (linux/amd64) 시작..."
docker buildx build \
  --platform linux/amd64 \
  -f Dockerfile.dev \
  -t "${IMAGE}" \
  --push .

echo "✅ 이미지 푸시 완료 → ${IMAGE}"
