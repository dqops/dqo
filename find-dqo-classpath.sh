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

if [ -e $(dirname $0)/dqops/pom.xml ]; then
    "$JAVA_HOME/bin/javac" --version > /dev/null 2> /dev/null

    returnedValue=$?
    if [ $returnedValue -ne 0 ]; then
        echo Java JDK is not installed. Make sure that your JAVA_HOME points to a JDK 11 or never installation. Java JRE is not enough to compile DQOps
        exit $?
    fi

    if [ ! -e $(dirname $0)/dqops/target/dqo-dqops-$DQO_VERSION.jar ]; then
        if [ -d $(dirname $0)/dqops ]; then
            . "$(dirname $0)/mvnw.sh" package -DskipTests -Pbuild-with-jdk-11 -Pdisable-duckdb-extensions-download -Prun-npm -f "$(dirname $0)/pom.xml"

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
        export DQO_LAUNCH_CLASSPATH="$DQO_PREPEND_CLASSES:$DQO_USER_HOME/jars/paid/*:$DQO_USER_HOME%/jars/*:$DQO_USER_HOME%/jars/:$(dirname $0)/dqops/target/dependencies/*:$(dirname $0)/dqops/target/dqo-dqops-$DQO_VERSION.jar.original"
    else
        export DQO_LAUNCH_CLASSPATH="$DQO_USER_HOME/jars/paid/*:$DQO_USER_HOME%/jars/*:$DQO_USER_HOME%/jars/:$(dirname $0)/dqops/target/dependencies/*:$(dirname $0)/dqops/target/dqo-dqops-$DQO_VERSION.jar.original"
    fi
else

    if [ ! -d $DQO_HOME/jars ]; then
        echo DQO_HOME does not have a $DQO_HOME/jars folder
        exit 1
    fi

    if [ -n "$DQO_PREPEND_CLASSES" ]; then
        export DQO_LAUNCH_CLASSPATH="$DQO_PREPEND_CLASSES:$DQO_USER_HOME/jars/paid/*:$DQO_USER_HOME%/jars/*:$DQO_USER_HOME%/jars/:$DQO_HOME/jars/*"
    else
        export DQO_LAUNCH_CLASSPATH="$DQO_USER_HOME/jars/paid/*:$DQO_USER_HOME%/jars/*:$DQO_USER_HOME%/jars/:$DQO_HOME/jars/*:$DQO_USER_HOME%/jars/*"
    fi
fi

if [ -z $HADOOP_HOME ]; then
    export HADOOP_HOME=$DQO_HOME
fi

if [ -z $AZURE_ENABLE_HTTP_CLIENT_SHARING ]; then
    export AZURE_ENABLE_HTTP_CLIENT_SHARING=true
fi

