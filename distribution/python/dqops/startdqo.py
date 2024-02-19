#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import os
import subprocess
import sys

from dqops import install

# ignore those, they are filled by importing version.py
VERSION = "filled by dqops/version.py"
PIP_VERSION = "filled by dqops/version.py"
GITHUB_RELEASE = "filled by dqops/version.py"
JAVA_VERSION = "filled by dqops/version.py"

try:
    exec(
        open(
            os.path.join(os.path.dirname(os.path.realpath(__file__)), "version.py")
        ).read()
    )
except IOError:
    print("Failed to load DQOps version file.", file=sys.stderr)
    sys.exit(-1)


def main():
    module_dir = os.path.dirname(os.path.realpath(__file__))

    dqo_home = os.environ.get("DQO_HOME")
    if dqo_home is None:
        dqo_home = os.path.join(os.path.join(module_dir, "home"), VERSION)
        install.install_dqo_home_if_missing(dqo_home)

    # TODO: detect if the user has Java 17 or newer on the JAVA_HOME, we can use it
    java_install_dir = os.path.join(module_dir, "jre" + JAVA_VERSION)
    install.install_jre_if_missing(java_install_dir)
    java_home = os.path.join(java_install_dir, os.listdir(java_install_dir)[0])

    if os.path.exists(os.path.join(java_home, "Contents/Home")):
        java_home = os.path.join(java_home, "Contents/Home")  # support MacOS correctly

    os_platform = sys.platform.lower()[0:3]
    dqo_envs = os.environ.copy()
    dqo_envs["DQO_HOME"] = dqo_home
    dqo_envs["JAVA_HOME"] = java_home
    dqo_envs["DQO_PYTHON_INTERPRETER"] = sys.executable
    dqo_envs["DQO_PYTHON_USE_HOST_PYTHON"] = "true"

    if os_platform == "win":
        # Windows
        subprocess.call(
            [os.path.join(dqo_home, "bin/dqo.cmd")] + sys.argv[1:], env=dqo_envs
        )
    elif os_platform == "lin" or os_platform == "dar":
        # Linux (lin) or MacOS X (dar)
        dqo_path = os.path.join(dqo_home, "bin/dqo")
        if os.access(dqo_path, os.X_OK):
            subprocess.call([dqo_path] + sys.argv[1:], env=dqo_envs)
        else:
            subprocess.call(["/bin/bash", dqo_path] + sys.argv[1:], env=dqo_envs)
    else:
        print("Operating system {0} unsupported".format(sys.platform), file=sys.stderr)
        sys.exit(-1)


if __name__ == "__main__":
    main()
