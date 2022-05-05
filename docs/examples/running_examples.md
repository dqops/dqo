Here we describe all the necessary things to run the examples

## Adding connection
### GCP
You have to download and install [Google Cloud SDK](https://cloud.google.com/sdk/docs/install).
After installing Google Cloud CLI, log in to your GCP account (you can start one for free), by running:

```commandline
gcloud auth application-default login
```

After setting up your GCP account, create your GCP project. That will be your GCP billing project
used to run SQL sensors on the public datasets provided by Google.

The examples are using a name of your GCP billing project, received as an environment variable GCP_PROJECT.
You have to set and export this variable before starting DQO shell.


=== "Windows"
```
set GCP_PROJECT={here is your GCP billing project}
```

=== "Linux"
```bash
export GCP_PROJECT={here is your GCP billing project}
```

=== "MacOS"
```bash
export GCP_PROJECT={here is your GCP billing project}
```
The rest of the process is described in each example. Go ahead and start the next tutorial.