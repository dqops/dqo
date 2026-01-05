#
# Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
# This file is licensed under the Business Source License 1.1,
# which can be found in the root directory of this repository.
#
# Change Date: This file will be licensed under the Apache License, Version 2.0,
# four (4) years from its last modification date.

# WARNING: the next two lines with the version numbers (VERSION =, PIP_VERSION =) should not be modified manually. They are changed by a maven profile at compile time.
VERSION = "1.13.1"
PIP_VERSION = "1.13.1"
GITHUB_RELEASE = "v" + VERSION + ""
JAVA_VERSION = "17"


def get_dqo_version():
    return VERSION, PIP_VERSION, GITHUB_RELEASE, JAVA_VERSION
