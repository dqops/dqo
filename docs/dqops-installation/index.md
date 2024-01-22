# DQOps installation options
This guide lists all options of installing DQOps Data Quality Operations Center, using pip, docker, or compiling the code directly.


## [Install as a Python package](install-dqops-using-pip.md)
   
If you have Python >=3.8 installed, installing the `dqops` pip package is the easiest way to start.
Running DQOps as a pip package is meant for the non-production usage.

```
python -m pip install --user dqops
```

Before running this command, please read the rest of the [installing from pip](install-dqops-using-pip.md) manual.


## [Run as a Docker container](run-dqops-as-docker-container.md)

You can also start DQOps as a Docker container that is published on Docker Hub.

```
docker pull dqops/dqo
docker run -v [enter the path to your local DQOps user home folder]:/dqo/userhome -it -p 8888:8888 dqops/dqo [--dqo.cloud.api-key=here-our-DQOps-Cloud-API-key]
```

All required parameters for starting DQOps in Docker are described in the [running DQOps in Docker](run-dqops-as-docker-container.md) manual.


## [Download a release package](install-dqops-from-release-package.md)

DQOps releases are published in the *dqops\dqo* [https://github.com/dqops/dqo/releases](https://github.com/dqops/dqo/releases) releases archive on GitHub.
If you need to configure DQOps instance for your needs, running DQOps even on bare metal, follow the
[installing DQOps from release package](install-dqops-from-release-package.md) manual.

You will need Python >=3.8 and Java >= 17 installed to start DQOps.


## [Compile DQOps from GitHub](install-dqops-from-github.md)

If you wish to contribute to DQOps, check out the GitHub repository and compile the code.

```
git clone https://github.com/dqops/dqo.git
```

Follow the [install from GitHub](install-dqops-from-github.md) manual to compile and start DQOps. If you have Java JDK 17
or newer on the PATH, you can just start the `dqo.cmd` or `./dqo` script after check out. DQOps will compile itself
before the first start.


## What's more
- Follow the [getting started guide](../getting-started/index.md) to understand how to run DQOps for the first time.
- Read the [DQOps concepts guide](../dqo-concepts/index.md) to learn how to configure data quality checks in DQOps.
