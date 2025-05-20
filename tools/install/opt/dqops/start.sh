#!/bin/bash

# change the version if a specific version is desired
docker_image_version=latest

# set the API Key
dqo_cloud_api_key=

# set the URL
dqo_ui_url=http://`hostname`

docker pull dqops/dqo:$docker_image_version

if [ $? -ne 0 ]; then
  echo Cannot download the docker image, version $docker_image_version, using the latest version
  docker_image_version=latest
  docker pull dqops/dqo:$docker_image_version
fi

docker run \
       --name dqops \
       -d \
       -p 80:8888 \
       -v /opt/dqops/userhome:/dqo/home \
       --network=host \
       -e DQO_USER_INITIALIZE_USER_HOME=true \
       -e DQO_INSTANCE_RETURN_BASE_URL=$dqo_ui_url \
       -e DQO_LOGGING_MAX_HISTORY=30 \
       -e DQO_LOGGING_TOTAL_SIZE_CAP=1gb \
       -e DQO_CLOUD_API_KEY=$dqo_cloud_api_key \
       dqops/dqo:$docker_image_version \
       --silent \
       run
