#!/bin/bash
#
# Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
# This file is licensed under the Business Source License 1.1,
# which can be found in the root directory of this repository.
#
# Change Date: This file will be licensed under the Apache License, Version 2.0,
# four (4) years from its last modification date.
#

if [ -z "$DQO_JAVA_OPTS" ]; then
  export DQO_JAVA_OPTS="-XX:MaxRAMPercentage=80.0"
fi

_term() {
  kill -TERM $child
  wait $child
}

trap _term SIGTERM
trap _term SIGINT

exec 3<&0 java $DQO_JAVA_OPTS --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED -cp /dqo/app:/dqo/app/lib/*:/dqo/home/jars/*:/dqo/userhome/jars/*:/dqo/userhome/jars/:/dqo/userhome/jars/paid/* com.dqops.cli.CliApplication $* <&3 &

child=$!
wait $child
exit $?
