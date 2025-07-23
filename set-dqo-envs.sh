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

export DQO_HOME=$(dirname $0)/home
export DQO_USER_HOME=$(dirname $0)/userhome

if [ ! -d $DQO_USER_HOME ]
then
    mkdir $DQO_USER_HOME
fi
