# Run DQOps in Docker
This guide shows how to pull [DQOps docker image from Docker Hub](https://hub.docker.com/r/dqops/dqo), and how to pass the right parameters to the container to start it in a production mode.

## Overview

DQOps can be run as a Docker container in a server mode or in Shell mode. You can also build a custom DQOps container image.

!!! note

    Running DQOps as docker container is a preferred method for starting in a long-running production mode.


## Prerequisites

To run DQOps as a Docker container you need

-   Docker running locally. Follow the instructions to [download and install Docker](https://docs.docker.com/get-docker/).

-   DQOps Cloud account and a DQOps Cloud API Key. If you want to use all DQOps features, such as storing data quality
    definitions and results in the cloud or data quality dashboards. [Create a new DQOps Cloud account here](https://cloud.dqops.com/registration).

-   A `DQOps User Home` folder is created locally which will be mounted to your container. Volumes are the preferred mechanism for persisting data generated
    by and used by Docker containers. The [`DQOps User Home`](../dqo-concepts/dqops-user-home-folder.md) folder
    stores local data such as sensor readouts, data quality check results, and the data source configuration.



## Start DQOps in DQOps interactive shell mode

To start DQOps in a [Shell mode](../dqo-concepts/command-line-interface.md) follow the steps below.

1.  Download the [DQOps image from Docker Hub](https://hub.docker.com/r/dqops/dqo) by running the following command in a terminal:

    ```
    docker pull dqops/dqo
    ```

2.  Create an empty folder where you want to create your [`DQOps User Home`](../dqo-concepts/dqops-user-home-folder.md).
    [`DQOps User Home`](../dqo-concepts/dqops-user-home-folder.md) is a folder where
    DQOps will store the metadata of imported data sources, the configuration of activated data quality checks, and the
    data quality results.

3.  Run DQOps Docker image

    ```
    docker run -v [path to local DQOps user home folder]:/dqo/userhome -it -p 8888:8888 dqops/dqo [--dqo.cloud.api-key=here-your-DQOps-Cloud-API-key]
    ```

    - The `-v` flag mounts your locally created [`DQOps User Home`](../dqo-concepts/dqops-user-home-folder.md) folder into the container. 
      You need to provide the path to your local [`DQOps User Home`](../dqo-concepts/dqops-user-home-folder.md) folder
    - The `-i` flag keeps STDIN open even if not attached.
    - The `-t` flag allocates a pseudo-TTY.
    - The `-p` flag creates a mapping between the host’s port 8888 to the container’s port 8888. Without the port mapping, you would not be able to access the application.
    - The `--dqo.cloud.api-key` argument specifies the API Key of your [DQOps Cloud registration](https://cloud.dqops.com/registration).
      When the DQOps Cloud API Key is not specified and you are starting DQOps using an empty [`DQOps User Home`](../dqo-concepts/dqops-user-home-folder.md) folder,
      DQOps will not be able to open the browser. Please copy the url to the [DQOps Cloud Login](https://cloud.dqops.com/) that is shown
      to a browser and create or login to your DQOps Cloud account.

    If you want to use the current folder as your `DQOps User Home`, you can bind this folder to the `/dqo/userhome` mount point in
    the DQOps docker image. Please keep in mind that the `DQOps User Home` folder should be empty (to initialize it on startup)
    or it should be already a valid `DQOps User Home` folder.
    Read the [DQOps user home folder concept](../dqo-concepts/dqops-user-home-folder.md) to learn more.

    ```
    docker run -v .:/dqo/userhome -it -p 8888:8888 dqops/dqo
    ```

4.  After a few seconds you can use the DQOps terminal or open the user interface by 
    opening [http://localhost:8888](http://localhost:8888) in a web browser. 


## Start DQOps in server mode

To start DQOps in a server mode follow the steps below.

1. Download the `dqops/dqo` image from DockerHub by running the following command in a terminal:

    ```
    docker pull dqops/dqo
    ```

2. Run the DQOps Docker image

    ```
    docker run -v [enter the path to your local userhome folder]:/dqo/userhome -d -m=4g -p 8888:8888 dqops/dqo [--dqo.cloud.api-key=here-our-DQOps-Cloud-API-key] run
    ```

   - The `-v` flag mounts your locally created `DQOps User Home` folder into the container.
     You need to provide the path to your local `DQOps User Home` folder
   - The `-p` flag creates a mapping between the host’s port 8888 to the container’s port 8888. Without the port mapping, you would not be able to access the application.
   - The `-d` flag turns on a daemon mode
   - The `-m` parameter configures the memory size for the container. We are advising to allocate at least 2 GB of memory for the DQOps
     container, which is configured by `-m=2g`. DQOps container runs one Java JVM process and several small Python processes (two per core)
     that are running the rules. DQOps runtime allocates 80% of the container memory for the JVM heap. The memory is used for caching
     YAML and parquet files in memory. The memory size can be changed by passing the `DQO_JAVA_OPTS`
     environment variable to the container using the following docker run parameter: `-e DQO_JAVA_OPTS=-XX:MaxRAMPercentage=60.0`
   - The `--dqo.cloud.api-key` argument specifies the API Key of your [DQOps Cloud account](https://cloud.dqops.com/registration).
   - The `run` command at the end will run the [run CLI command](../command-line-interface/run.md) command and activate a server mode
     without the DQOps Shell.

3. After a few seconds open your web browser to **http://localhost:8888/**. You should see the DQOps user interface.

    ![DQOps user interface](https://dqops.com/docs/images/getting-started/dqops-user-interface.png){ loading=lazy; width="1200px" }


## Build a custom DQOps container image

1. Create an empty folder. 

2. Open a terminal, navigate to the created directory and clone the DQOps repository from [GitHub](https://github.com/dqops/dqo).
    
    ```
    git clone https://github.com/dqops/dqo.git
    ```

3. Modify the DQOps Docker file `Dockerfile` located in the main directory.

4. Run the following command to build a DQOps container image using a Dockerfile:

    ```
    docker build -t your_dqo_image_name .
    ```
   
    The `-t` parameter specifies the name for the container image, in this case "your_dqo_image_name".


