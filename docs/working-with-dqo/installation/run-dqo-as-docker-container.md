# Run DQOps in Docker

DQOps can be run as a Docker container in a server mode or in Shell mode. You can also build a custom DQOps container image.

> Running DQOps as docker container is a preferred method for starting in a long-running production mode.

## Prerequisites

To run DQOps as a Docker container you need

- Docker running locally. Follow the instructions to [download and install Docker](https://docs.docker.com/get-docker/).
- DQOps Cloud account and a DQOps Cloud API Key. If you want to use all DQOps features, such as storing data quality
    definitions and results in the cloud or data quality dashboards. [Create a new DQOps Cloud account here](https://cloud.dqops.com/registration).
- A `DQOps User Home` folder is created locally which will be mounted to your container. Volumes are the preferred mechanism for persisting data generated
    by and used by Docker containers. The `DQOps User Home` folder will locally store data such as sensor readouts, checkout results, and data source configurations.
    [You can learn more about data storage here](../../dqo-concepts/data-storage/data-storage.md).

## Start DQOps in Shell mode

To start DQOps in a Shell mode follow the steps below.

1. Download the DQOps image from DockerHub by running the following command in a terminal:

    ```
    docker pull dqops/dqo
    ```

2. Create an empty folder where you want to create your `DQOps User Home`. `DQOps User Home` is a folder where
   DQOps will store the metadata of imported data sources, the configuration of enabled data quality checks, and the
   data quality results.

3. Run DQOps Docker image

    ```
    docker run -v [enter the path to your local DQOps user home folder]:/dqo/userhome -it -p 8888:8888 dqops/dqo [--dqo.cloud.api-key=here-our-DQOps-Cloud-API-key]
    ```

   - The `-v` flag mounts your locally created `DQOps User Home` folder into the container. 
     You need to provide the path to your local `DQOps User Home` folder
   - The `-i` flag keeps STDIN open even if not attached.
   - The `-t` flag allocates a pseudo-TTY.
   - The `-p` flag creates a mapping between the host’s port 8888 to the container’s port 8888. Without the port mapping, you would not be able to access the application.
   - The `--dqo.cloud.api-key` argument specifies the API Key of your [DQOps Cloud registration](https://cloud.dqops.com/registration).
     When the DQOps Cloud API Key is not specified and you are starting DQOps using an empty `DQOps User Home` folder,
     DQOps will not be able to open the browser. Please copy the url to the [DQO Cloud Login](https://cloud.dqops.com/) that is shown
     to a browser and create or login to your DQOps Cloud account.

   If you want to use the current folder as your `DQOps User Home`, you can bind this folder to the `/dqo/userhome` mount point in
   the DQOps docker image. Please keep in mind that the `DQOps User Home` folder should be empty (to initialize it on startup)
   or it should be already a valid `DQOps User Home` folder.

    ```
    docker run -v .:/dqo/userhome -it -p 8888:8888 dqops/dqo
    ```

4. After a few seconds you can use the DQOps terminal or open the graphical interface by 
   opening [http://localhost:8888](http://localhost:8888) in a web browser. 


## Start DQOps in server mode

To start DQO in server mode follow the steps below.

1. Download the DQO image from DockerHub by running the following command in a terminal:

    ```
    docker pull dqops/dqo
    ```

2. Run the DQOps Docker image
    ```
    docker run -v [enter the path to your local userhome folder]:/dqo/userhome -d -p 8888:8888 dqops/dqo [--dqo.cloud.api-key=here-our-DQOps-Cloud-API-key] run
    ```

   - The `-v` flag mounts your locally created `DQOps User Home` folder into the container.
     You need to provide the path to your local `DQOps User Home` folder
   - The `-p` flag creates a mapping between the host’s port 8888 to the container’s port 8888. Without the port mapping, you would not be able to access the application.
   - The `--dqo.cloud.api-key` argument specifies the API Key of your [DQOps Cloud account](https://cloud.dqops.com/registration).
   - The `run` command at the end will run the [run CLI command](../../command-line-interface/run.md) command and activate a server mode
     without the DQOps Shell.

3. After a few seconds open your web browser to **http://localhost:8888**. You should see the DQOps user interface.



## Build a custom DQOps container image

1. Create an empty folder. 

2. Open a terminal, navigate to the created directory and clone the DQOps repository from [GitHub](https://github.com/dqops/dqo).
    
    ```
    git clone https://github.com/dqops/dqo.git
    ```

3. Modify the DQOps Docker file `Dockerfile` located in the main directory.

4. Run the following command to build a DQO container image using a Dockerfile:

    ```
    docker build -t your_dqo_image_name .
    ```
   
    The `-t` parameter specifies the name for the container image, in this case "your_dqo_image_name".


