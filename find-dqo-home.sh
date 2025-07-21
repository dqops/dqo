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
    export DQO_JAVA_OPTS="-XX:MaxRAMPercentage=60.0"
  else
    export DQO_JAVA_OPTS="-XX:MaxRAMPercentage=60.0"
  fi
fi

# Figure out where java is.
export DQO_RUNNER=java
if [ -n "$JAVA_HOME" ]; then
  export DQO_RUNNER="$JAVA_HOME/bin/java"
else
  printenv DQO_RUNNER >> /dev/null

  returnedValue=$?
  if [ $returnedValue -ne 0 ]; then
      echo Java not found and JAVA_HOME environment variable is not set.
      echo Install Java 17 or newer and set JAVA_HOME to point to the Java installation directory.
      exit $returnedValue
  fi
fi
