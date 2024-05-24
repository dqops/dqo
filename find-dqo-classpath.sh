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

if [ -e $(dirname $0)/dqops/pom.xml ]; then
    "$JAVA_HOME/bin/javac" --version > /dev/null 2> /dev/null

    returnedValue=$?
    if [ $returnedValue -ne 0 ]; then
        echo Java JDK is not installed. Make sure that your JAVA_HOME points to a JDK 11 or never installation. Java JRE is not enough to compile DQOps
        exit $?
    fi

    if [ ! -e $(dirname $0)/dqops/target/dqo-dqops-$DQO_VERSION.jar ]; then
        if [ -d $(dirname $0)/dqops ]; then
            . "$(dirname $0)/mvnw.sh" package -DskipTests -Pbuild-with-jdk-11 -Prun-npm -f "$(dirname $0)/pom.xml"

            returnedValue=$?
            if [ $returnedValue -ne 0 ]; then
                echo DQO failed to compile
                exit $?
            fi
        else
            echo DQO jar not found
            exit 1
        fi
    fi

    if [ -n "$DQO_PREPEND_CLASSES" ]; then
        export DQO_LAUNCH_CLASSPATH="$DQO_PREPEND_CLASSES:$(dirname $0)/dqops/target/dqo-dqops-$DQO_VERSION.jar:$(dirname $0)/lib/target/output/dqo-lib-$DQO_VERSION/jars/*"
    else
        export DQO_LAUNCH_CLASSPATH="$(dirname $0)/dqops/target/dqo-dqops-$DQO_VERSION.jar:$(dirname $0)/lib/target/output/dqo-lib-$DQO_VERSION/jars/*"
    fi
else

    if [ ! -d $DQO_HOME/jars ]; then
        echo DQO_HOME does not have a $DQO_HOME/jars folder
        exit 1
    fi

    if [ -n "$DQO_PREPEND_CLASSES" ]; then
        export DQO_LAUNCH_CLASSPATH="$DQO_PREPEND_CLASSES:$DQO_HOME/jars/*"
    else
        export DQO_LAUNCH_CLASSPATH="$DQO_HOME/jars/*"
    fi
fi

if [ -z $HADOOP_HOME ]; then
    export HADOOP_HOME=$DQO_HOME
fi

if [ -z $AZURE_ENABLE_HTTP_CLIENT_SHARING ]; then
    export AZURE_ENABLE_HTTP_CLIENT_SHARING=true
fi

