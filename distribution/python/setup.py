#!/usr/bin/env python3

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

from glob import glob
import os
import sys
import zipfile
import jdk
from setuptools import setup
from setuptools import find_packages
import importlib.util
from setuptools.command.install import install
from shutil import rmtree

# ignore those, they are filled by importing dqops/version.py
VERSION = "filled by dqops/version.py"
PIP_VERSION = "filled by dqops/version.py"
GITHUB_RELEASE = "filled by dqops/version.py"
JAVA_VERSION = "filled by dqops/version.py"


HOME_PATH = "dqops/home"
JRE_PATH = "dqops/jre"
DQO_REPO_HOME = os.path.abspath("../../")

try:
    exec(open('dqops/version.py').read())
except IOError:
    print("Failed to load DQOps version file for packaging. You must be in DQOps distribution/python dir.",
          file=sys.stderr)
    sys.exit(-1)

try:
    download_module_spec = importlib.util.spec_from_file_location("install", "dqops/install.py")
    download_module = importlib.util.module_from_spec(download_module_spec)
    download_module_spec.loader.exec_module(download_module)
except IOError:
    print("Failed to load the download module (dqops/install.py) which had to be packaged together.",
          file=sys.stderr)
    sys.exit(-1)

# Provide guidance about how to use setup.py
incorrect_invocation_message = """
If you are installing DQOps from https://github.com/dqops/dqo source, you must first build DQOps.

    To build DQOps with maven, go to the https://github.com/dqops/dqo root folder and run run:
      ./mvnw -DskipTests clean package"""

# Figure out where the distribution file is present
DISTRIBUTION_PATH = os.path.join(DQO_REPO_HOME, "distribution/target/dqo-distribution-{0}-bin.zip".format(VERSION))

in_dqo = os.path.isfile(os.path.join(DQO_REPO_HOME, "pom.xml")) and \
         os.path.isfile(os.path.join(DQO_REPO_HOME, "dqops/src/main/java/com/dqops/cli/CliApplication.java"))


class InstallCommand(install):
    def run(self):
        install.run(self)

        # download and install DQO
        if not in_dqo:
            download_module.install_dqo(HOME_PATH, GITHUB_RELEASE, VERSION)

        os.makedirs(JRE_PATH, exist_ok=True)

        # install a local JRE
        print("Installing Java JRE %s at %s" % (JAVA_VERSION, JRE_PATH))
        jdk.install(JAVA_VERSION, jre=True, path=JRE_PATH)

try:
    if in_dqo:
        # Create a folder to expand the distribution
        try:
            os.mkdir(HOME_PATH)
        except:
            print("Cannot create {0}".format(HOME_PATH), file=sys.stderr)
            sys.exit(-1)

        if not os.path.isfile(DISTRIBUTION_PATH) and not os.path.exists(HOME_PATH):
            print(incorrect_invocation_message, file=sys.stderr)
            sys.exit(-1)

        with zipfile.ZipFile(DISTRIBUTION_PATH, "r") as zip_ref:
            zip_ref.extractall(HOME_PATH)

    with open('README.md') as f:
        long_description = f.read()

#    home_dirs = [root[len("dqops/"):].replace('\\', '/') + "/*" for (root, dirs, files) in os.walk(HOME_PATH)]

    setup(
        name='dqops',
        version=PIP_VERSION,
        description='DQOps Data Quality Operations Center',
        long_description=long_description,
        long_description_content_type="text/markdown",
        author='DQOps Developers',
        author_email='support@dqops.com',
        url='https://github.com/dqops/dqo/tree/master/distribution/python',
        packages=find_packages(),
        include_package_data=True,
        package_data={
            'dqops': ['py.typed']
        },
        license='http://www.apache.org/licenses/LICENSE-2.0',
        install_requires=['install-jdk>=1.1.0',
                          'httpx>=0.20.0,<0.26.0',
                          'h2>=4.0.0',
                          'attrs>=21.3.0',
                          'python-dateutil>=2.8.2',
                          'numpy>=1.24.3',
                          'MarkupSafe>=2.1.1',
                          'Jinja2>=3.0.3',
                          'six>=1.16.0',
                          'pandas>=2.0.1',
                          'scipy>=1.10.1'],
        python_requires='>=3.8',
        classifiers=[
            'Development Status :: 5 - Production/Stable',
            'License :: OSI Approved :: Apache Software License',
            'Programming Language :: Python :: 3.8',
            'Programming Language :: Python :: 3.9',
            'Programming Language :: Python :: 3.10',
            'Programming Language :: Python :: 3.11',
            'Programming Language :: Python :: 3.12',
            'Environment :: Console',
            'Framework :: Apache Airflow :: Provider',
            'Intended Audience :: Developers',
            'Operating System :: Microsoft :: Windows',
            'Operating System :: MacOS',
            'Operating System :: POSIX :: Linux',
            'Programming Language :: Java',
            'Programming Language :: SQL',
            'Topic :: Database',
            'Topic :: Scientific/Engineering :: Artificial Intelligence',
            'Topic :: Scientific/Engineering :: Information Analysis',
            'Topic :: Security',
            'Topic :: Software Development :: Quality Assurance',
            'Topic :: Software Development :: Testing',
            'Topic :: Software Development :: Version Control :: Git',
            'Programming Language :: Python :: Implementation :: CPython',
            'Programming Language :: Python :: Implementation :: PyPy'],
        # cmdclass={
        #     'install': InstallCommand,
        # },
        entry_points={
            'console_scripts': ['dqo=dqops.startdqo:main'],
        },
    )
finally:
    if in_dqo:
        rmtree(HOME_PATH)
