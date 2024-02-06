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
import ssl
import sys
import zipfile

import httpx
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

    ssl._create_default_https_context = ssl._create_unverified_context

    distribution_local_name = os.path.join(
        dest, "dqo-distribution-%s-bin.zip" % dqo_version
    )

    os.makedirs(dest, exist_ok=True)

    if os.path.exists(distribution_local_name):
        os.remove(distribution_local_name)

    download_to_file(dqo_tag, dqo_version, distribution_local_name)

    with zipfile.ZipFile(distribution_local_name, "r") as zip_ref:
        zip_ref.extractall(dest)

    if os.path.exists(distribution_local_name):
        os.remove(distribution_local_name)


def http_client() -> httpx.Client:
    return httpx.Client(
        http2=True,
        timeout=httpx.Timeout(5.0, connect=10.0, read=6.0),
        follow_redirects=True,
        headers={
            "Accept-Encoding": "identity",
            "Accept-Language": "en-US,en;q=0.9,pl;q=0.8",
            "Cache-Control": "no-cache, no-transform",
            "Pragma": "no-cache",
            "Sec-Ch-Ua": '"Not_A Brand";v="8", "Chromium";v="120", "Google Chrome";v="120"',
            "Sec-Ch-Ua-Mobile": "?0",
            "Sec-Ch-Ua-Platform": '"Windows"',
            "Sec-Fetch-Dest": "document",
            "Sec-Fetch-Mode": "navigate",
            "Sec-Fetch-Site": "cross-site",
            "Sec-Fetch-User": "?1",
            "Upgrade-Insecure-Requests": "1",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/120.0.0.0 Safari/537.36",
        },
    )


def download_to_file(dqo_tag: str, dqo_version: str, distribution_local_name: str):
    github_url = (
        "https://github.com/dqops/dqo/releases/download/%s/dqo-distribution-%s-bin.zip"
        % (dqo_tag, dqo_version)
    )

    with http_client() as client:
        if download_to_file_try(
            client, github_url, distribution_local_name, dqo_version
        ):
            return

        print("Download from %s failed, attempting another mirror." % github_url)

        if download_to_file_fallback(client, dqo_version, distribution_local_name):
            return

    print(
        "Download failed. Check your internet connection and firewall settings.",
        file=sys.stderr,
    )
    sys.exit(-1)


def download_to_file_fallback(
    client: httpx.Client, dqo_version: str, distribution_local_name: str
):
    # Set any cookies sent by the resource server.
    bucket_url = "https://dqops.com/releases/dqo-distribution-%s-bin.zip" % dqo_version
    head_url = bucket_url
    client.head(head_url)

    return download_to_file_try(
        client, bucket_url, distribution_local_name, dqo_version
    )


def download_to_file_try(client, source_url, path, dqo_version):
    try:
        trying_message = "Trying to download DQOps version %s from %s" % (
            dqo_version,
            source_url,
        )
        print(trying_message)

        return download_to_file_once(client, source_url, path)
    except Exception as e:
        print(
            "%s: Download interrupted, reason: %s" % (e.__class__.__name__, e),
            file=sys.stderr,
        )

    return False


def download_to_file_once(client, source_url, path, chunk_size=1024 * 1024):
    with open(path, mode="wb") as dest:
        new_bytes_so_far = 0
        while True:
            bytes_so_far = new_bytes_so_far
            start_byte = bytes_so_far if bytes_so_far > 0 else None
            new_bytes_so_far, total_size = download_to_file_partial(
                client, source_url, dest, chunk_size=chunk_size, start_byte=start_byte
            )

            if new_bytes_so_far == bytes_so_far:
                print("No progress since last download attempt, abort.")
                return False
            elif new_bytes_so_far == total_size:
                return True


def download_to_file_partial(
    client, source_url, opened_dest, chunk_size=1024 * 1024, start_byte: int = None
):
    if start_byte and start_byte != 0:
        bytes_so_far = start_byte
        headers = {"Range": f"bytes={start_byte}-"}
    else:
        bytes_so_far = 0
        headers = None

    try:
        with client.stream("GET", source_url, headers=headers) as response_stream:
            total_size = int(response_stream.headers.get("Content-Length").strip()) + (
                start_byte if start_byte else 0
            )

            if (
                start_byte
                and start_byte != 0
                and "Content-Range" not in response_stream.headers
            ):
                # Partial download not allowed, shouldn't attempt further retries.
                return bytes_so_far, total_size

            for chunk in response_stream.iter_raw(chunk_size=chunk_size):
                if not chunk:
                    return bytes_so_far, total_size

                chunk_bytes = len(chunk)
                opened_dest.write(chunk)
                bytes_so_far += chunk_bytes

                print(
                    "Downloaded %d of %d bytes (%0.2f%%)"
                    % (
                        bytes_so_far,
                        total_size,
                        round(float(bytes_so_far) / total_size * 100, 2),
                    )
                )

    except httpx.TimeoutException:
        pass

    return bytes_so_far, total_size


def install_dqo_home_if_missing(dqo_home):
    if os.path.exists(dqo_home) and os.path.exists(os.path.join(dqo_home, "README.md")):
        return

    install_dqo(dqo_home, GITHUB_RELEASE, VERSION)


def install_jre_if_missing(java_install_dir):
    if os.path.exists(java_install_dir) and len(os.listdir(java_install_dir)) > 0:
        return

    ssl._create_default_https_context = ssl._create_unverified_context

    print("Installing Java JRE %s at %s" % (JAVA_VERSION, java_install_dir))
    jdk.install(JAVA_VERSION, jre=True, path=java_install_dir)
