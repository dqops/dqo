#!/bin/sh
#
# Copyright © 2021 DQO.ai (support@dqo.ai)
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

source $(dirname $0)/set-dqo-envs.sh

# Only attempt to find DQO_HOME if it is not set.

export FIND_DQO_HOME_PYTHON_SCRIPT=$(dirname $0)/find_dqo_home.sh

export DQO_VERSION=0.1.0

if [[ -f $(dirname $0)/dqoai/pom.xml ]]; then

     export DQO_LIBS=$(dirname $0)/lib/target/output/dqo-lib-"$DQO_VERSION"/jars
     export DQO_JAR=$(dirname $0)/dqoai/target/dqo-dqoai-"$DQO_VERSION".jar
    echo "Compiling DQO.ai"
    sh $(dirname $0)/mvnw.sh package -DskipTests -Pbuild-with-jdk-11 -f $(dirname $0)/pom.xml
    export DQO_LAUNCH_CLASSPATH=$DQO_JAR
else
    if [[ -f $(dirname $0)/jars ]]; then
        export DQO_JARS_DIR=$DQO_HOME/jars
    else
        echo "DQO_HOME does not have a %DQO_HOME%/jars folder"
        exit 0
    fi
    export DQO_LAUNCH_CLASSPATH=$DQO_JAR;$DQO_LIBS/*
fi

# Add the launcher build dir to the classpath if requested.
if [ ! -z "${DQO_PREPEND_CLASSES}" ]; then
  export DQO_LAUNCH_CLASSPATH=$DQO_PREPEND_CLASSES;$DQO_LAUNCH_CLASSPATH
fi


if [ -z "$JAVA_HOME" ]; then
  if [ -x "/usr/libexec/java_home" ]; then
    export JAVA_HOME="`/usr/libexec/java_home`"
  else
    export JAVA_HOME="/Library/Java/Home"
  fi
fi

if [ -z "$JAVA_HOME" ] ; then
  if [ -r /etc/gentoo-release ] ; then
    JAVA_HOME=`java-config --jre-home`
  fi
fi

# Figure out where java is.
if [ ! -z "${JAVA_HOME}" ]; then
  export DQO_RUNNER=$JAVA_HOME/bin/java
else
    echo "Java not found and JAVA_HOME environment variable is not set."
    echo "Install Java 11 or newer and set JAVA_HOME to point to the Java installation directory."
fi

