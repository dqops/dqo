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
#

import os
import sys
import urllib.request
import zipfile

import jdk

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


def install_dqo(dest: str, dqo_tag: str, dqo_version: str):
    """
    Downloads and installs DQOps distribution locally.
    :param dest: Destination folder to download and extract the zip package.
    :param dqo_tag: GitHub release tag.
    :param dqo_version:  DQOps version.
    """

    github_url = (
        "https://github.com/dqops/dqo/releases/download/%s/dqo-distribution-%s-bin.zip"
        % (dqo_tag, dqo_version)
    )
    distribution_local_name = os.path.join(
        dest, "dqo-distribution-%s-bin.zip" % dqo_version
    )
    print("Trying to download DQOps version %s from %s" % (dqo_version, github_url))

    os.makedirs(dest, exist_ok=True)

    if os.path.exists(distribution_local_name):
        os.remove(distribution_local_name)

    download_to_file(urllib.request.urlopen(github_url), distribution_local_name)
    with zipfile.ZipFile(distribution_local_name, "r") as zip_ref:
        zip_ref.extractall(dest)

    if os.path.exists(distribution_local_name):
        os.remove(distribution_local_name)


def download_to_file(response, path, chunk_size=1024 * 1024):
    total_size = int(response.info().get("Content-Length").strip())
    bytes_so_far = 0

    with open(path, mode="wb") as dest:
        while True:
            chunk = response.read(chunk_size)
            bytes_so_far += len(chunk)
            if not chunk:
                break
            dest.write(chunk)
            print(
                "Downloaded %d of %d bytes (%0.2f%%)"
                % (
                    bytes_so_far,
                    total_size,
                    round(float(bytes_so_far) / total_size * 100, 2),
                )
            )


def install_dqo_home_if_missing(dqo_home):
    if os.path.exists(dqo_home) and os.path.exists(os.path.join(dqo_home, "README.md")):
        return

    install_dqo(dqo_home, GITHUB_RELEASE, VERSION)


def install_jre_if_missing(java_install_dir):
    if os.path.exists(java_install_dir) and len(os.listdir(java_install_dir)) > 0:
        return

    print("Installing Java JRE %s at %s" % (JAVA_VERSION, java_install_dir))
    jdk.install(JAVA_VERSION, jre=True, path=java_install_dir)
