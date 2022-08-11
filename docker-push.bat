for /f "delims=" %%a in ('git rev-parse --short HEAD') do @set commit_sha=%%a

docker push us.gcr.io/dqo-ai-testing/dqo:%commit_sha%
docker push us.gcr.io/dqo-ai-testing/dqo:latest
