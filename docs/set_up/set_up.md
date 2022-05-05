#Set up

Let's see how to set up the application to run it successfully. 
Firstly, you should download app. You can do it by pip or by control version system git. The link to the repository where you can find at github : (link to project).  
# Download

## With pip
Dqo.ai is currently available in [PyPi test repository](https://test.pypi.org/) as `dqoai`.
The only what you have to do is open your terminal and type pip dqoai. The application should download within a few minutes. 

## With git
Application is available on [GitHub](https://github.com/dqoai).
Copy https link to the project and in terminal type: git clone (link to project).

## Install Java JDK

To run app successfully you should download and install Java JDK in version 16 or higher. We recommend using https://adoptopenjdk.net 

## Set up environment variable JAVA_HOME

To run application it's essential config environment variable JAVA_HOME at your system. Depending on platform that you use the process of setting up environment variable differs.

You can verify if environment variable JAVA_HOME is configured correctly.
###MacOS/Linux
In a terminal run `echo $JAVA_HOME`. You should see a path to folder `home` which is placed in java installation folder. The correct `JAVA_HOME` paths should be like that `/dev/Library/Java/JavaVirtualMachines/temurin-17.0.2/Contents/Home`

###Windows
In a terminal run `echo %JAVA_HOME%`. You should see a path to folder `home` which is placed in java installation folder. The correct `JAVA_HOME` paths should be like that `path to home`.

#Run application
Go to the folder where Dqo.ai is downloaded. Depending on platform, you run application using shell or cmd script.
###MacOS/Linux
To run application, in a terminal type `./dqo`. 

###Windows
To run application, in a terminal type `dqo`.

