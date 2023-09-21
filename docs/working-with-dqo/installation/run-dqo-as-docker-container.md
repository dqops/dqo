# Run DQO as a Docker container

DQO can be run as a Docker container in a server mode or in Shell mode. You can also build a custom DQO container image.   

## Prerequisites

To run DQO as a Docker container you need

- Docker running locally. Follow the instructions to [download and install Docker](https://docs.docker.com/get-docker/).
- DQO Cloud account and unique identification code (API Key). If you want to use DQO features, such as storing data quality
    definitions and results in the cloud or data quality dashboards. [Create a new DQO Cloud account here](https://cloud.dqops.com/registration).
- A "userhome" folder is created locally which will be mounted to your container. Volumes are the preferred mechanism for persisting data generated
    by and used by Docker containers. The "userhome" folder will locally store data such as sensor readouts, checkout results, and data source configurations.
    [You can learn more about data storage here](../../dqo-concepts/data-storage/data-storage.md).

## Start DQO in server mode

To start DQO in server mode follow the steps below.

1. Download the DQO image from DockerHub by running the following command in a terminal: 

    ```
    docker pull dqops/dqo
    ```

2. Run the DQO Docker image 
    ```
    docker run -v [enter the path to your local userhome folder]:/dqo/userhome -p 8888:8888 dqops/dqo --dqo.cloud.api-key=[enter your API Key] run
    ```

    - The `-v` flag mounts your locally created "userhome" folder into the container. You need to provide the path to your local userhome folder
    - The `-p` flag creates a mapping between the host’s port 8888 to the container’s port 8888. Without the port mapping, you would not be able to access the application.
    - The `--dqo.cloud.api-key` argument specifies the API Key of your [DQO Cloud account](https://cloud.dqops.com/registration).

3. After a few seconds open your web browser to **http://localhost:8888**. You should see the graphical interface of the DQO.


## Start DQO in Shell mode

To start DQO in a Shell mode follow the steps below.

1. Download the DQO image from DockerHub by running the following command in a terminal:

    ```
    docker pull dqops/dqo
    ```

2. Run DQO Docker image

    ```
    `docker run -v [enter the path to your local userhome folder]:/dqo/userhome -it -p 8888:8888 dqops/dqo --dqo.cloud.api-key=[enter your API Key]`
    ```

   - The `-v` flag mounts your locally created "userhome" folder into the container. You need to provide the path to your local userhome folder
   - The `-i` flag keeps STDIN open even if not attached.
   - The `-t` flag allocates a pseudo-TTY.
   - The `-p` flag creates a mapping between the host’s port 8888 to the container’s port 8888. Without the port mapping, you would not be able to access the application.
   - The `--dqo.cloud.api-key` argument specifies the API Key of your [DQO Cloud account](https://cloud.dqops.com/registration).

3. After a few seconds you can use the DQO terminal or open the graphical interface by pasting [http://localhost:8888](http://localhost:8888) into a web browser. 

## Build a custom DQO container image

1. Create an empty catalogue. 
2. Open a terminal, navigate to the created directory and clone the DQO repository from [GitHub](https://github.com/dqops/dqo).
    
    ```
    git clone https://github.com/dqops/dqo.git
    ```

3. Modify the DQO Docker file `Dockerfile` located in the main directory.

4. Run the following command to build a DQO container image using a Dockerfile:

    ```
    docker build -t your_dqo_image_name .
    ```
   
    The `-t` parameter specifies the name for the container image, in this case "your_dqo_image_name".


