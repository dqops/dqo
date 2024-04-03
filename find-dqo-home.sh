#!/bin/bash
#
# Copyright © 2021-2024 DQOps (support@dqops.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Only attempt to find DQO_HOME if it is not set
if [ -z "$DQO_HOME" ]; then
  export DQO_HOME=$(dirname "$0")/..
fi

if [ -z "$DQO_USER_HOME" ]; then
  export DQO_USER_HOME=.
fi

if [ ! -d "$DQO_USER_HOME" ]; then
  mkdir $DQO_USER_HOME
fi

if [ -z "$DQO_JAVA_OPTS" ]; then
  if [[ "$OSTYPE" == "darwin"* ]]; then
    export DQO_JAVA_OPTS=-XX:MaxRAMPercentage=60.0 -Djavax.net.ssl.trustStoreType=KeychainStore
  else
    export DQO_JAVA_OPTS=-XX:MaxRAMPercentage=60.0
  fi
fi

# Figure out where java is.
export DQO_RUNNER=java
if [ -n "$JAVA_HOME" ]; then
  export DQO_RUNNER=$JAVA_HOME/bin/java
else
  printenv DQO_RUNNER >> /dev/null

  returnedValue=$?
  if [ $returnedValue -ne 0 ]; then
      echo Java not found and JAVA_HOME environment variable is not set.
      echo Install Java 17 or newer and set JAVA_HOME to point to the Java installation directory.
      exit $returnedValue
  fi

fi
