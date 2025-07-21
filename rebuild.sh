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

pushd $(dirname $0)

./mvnw.sh clean -f pom.xml
./mvnw.sh package -DskipTests -Pdisable-duckdb-extensions-download -Pbuild-with-jdk-11 -Prun-npm

popd
