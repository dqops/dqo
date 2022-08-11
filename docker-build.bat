for /f "delims=" %%a in ('git rev-parse --short HEAD') do @set commit_sha=%%a


docker build --build-arg DQO_VERSION=0.1.0 ^
-t us.gcr.io/dqo-ai-testing/dqo:latest ^
-t us.gcr.io/dqo-ai-testing/dqo:%commit_sha% ^
-t dqo .\
