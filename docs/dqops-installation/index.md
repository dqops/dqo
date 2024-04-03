# DQOps installation options
This guide lists all options of installing DQOps Data Quality Operations Center, using pip, docker, or compiling the code directly.

## Install to play and learn

<div class="grid cards grid-columns-150-pct" markdown>

-   :material-language-python:{ .lg .middle } __Install as a Python package__

    ---

    If you have Python >=3.8 installed, installing the `dqops` pip package is the easiest way to start.
    Running DQOps as a pip package is meant for the non-production usage.
    
    ```
    python -m pip install --user dqops
    ```
    
    Before running this command, please read the rest of the [installing from pip](install-dqops-using-pip.md) manual.

    <br/>
    [:octicons-arrow-right-24: Learn how to install as a Python package](install-dqops-using-pip.md)


</div>


## Install for production use

<div class="grid cards grid-columns-150-pct" markdown>

-   :material-docker:{ .lg .middle } __Run as a Docker container__

    ---

    You can also start DQOps as a Docker container that is published on [Docker Hub](https://hub.docker.com/r/dqops/dqo).

    ```
    docker pull dqops/dqo
    docker run -v [path to local DQOps user home folder]:/dqo/userhome \
           -it -p 8888:8888 dqops/dqo [--dqo.cloud.api-key=here-your-DQOps-Cloud-API-key]
    ```

    All required parameters for starting DQOps in Docker are described in the [running DQOps in Docker](run-dqops-as-docker-container.md) manual.

    <br/>
    [:octicons-arrow-right-24: Learn how to run DQOps in Docker](run-dqops-as-docker-container.md)


-   :material-folder-zip:{ .lg .middle } __Download a release package__

    ---

    DQOps releases are published in the *dqops\dqo* ([https://github.com/dqops/dqo/releases](https://github.com/dqops/dqo/releases)) releases archive on GitHub.

    If you need to configure DQOps instance for your needs, running DQOps even on bare metal, follow the
    [installing DQOps from release package](install-dqops-from-release-package.md) manual.

    You will need Python >=3.8 and Java >= 17 installed to start DQOps.

    <br/>
    [:octicons-arrow-right-24: Learn how to download a release package](install-dqops-from-release-package.md)


-   :material-github:{ .lg .middle } __Compile DQOps from GitHub__

    ---

    If you wish to contribute to DQOps, check out the GitHub repository and compile the code.

    ```
    git clone https://github.com/dqops/dqo.git
    ```

    Follow the [install from GitHub](install-dqops-from-github.md) manual to compile and start DQOps. If you have Java JDK 17
    or newer on the PATH, you can just start the `dqo.cmd` or `./dqo` script after check out. DQOps will compile itself
    before the first start.

    <br/>
    [:octicons-arrow-right-24: Learn how to compile DQOps from GitHub](install-dqops-from-github.md)

</div>


## What's more
- Follow the [getting started guide](../getting-started/index.md) to understand how to run DQOps for the first time.
- Read the [DQOps concepts guide](../dqo-concepts/index.md) to learn how to configure data quality checks in DQOps.
