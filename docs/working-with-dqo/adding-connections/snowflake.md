# Adding connections

All the connections require two things specified:

- connection name,
- provider.

After running command
```
connection add
```
DQO will automatically ask you to provide a connection's name and select a provider. You can specify them in the
command itself

```
connection add -n={ here goes your connection name} -t={ here goes your provider name}
```

Depending on a provider, different information is required. In the following paragraphs we will describe how to add 
connection to each of the existing providers.
A required information is provided with [properties]() or with environment variables.

## BigQuery

In this section we describe how to add a BigQuery connection. Of course in order to access GCP services, you have to 
have a GCP account along with existing project and billing account. The second thing is working
[Google Cloud CLI](https://cloud.google.com/sdk/docs/install).

### List of properties
- `bigquery-source-project-id` - your GCP project ID
- `bigquery-billing-project-id` - your GCP billing project ID
- `bigquery-quota-project-id` - GCP quota (billing) project ID
- `bigquery-authentication-mode` - a method of authentication, choose one of three options
    - `google_application_credentials` - credentials obtained by (the preferred method)
    - `json_key_content`
    - `json_key_path`
    
So to be able to add BigQuery connection with application credentials in a headless mode, the command looks like this

```
connection add -n=<name> -t=bigquery -P=bigquery-source-project-id=<project_ID> -P=bigquery-billing-project-id=<billing_ID> -P=bigquery-quota-project-id=<quota_ID> -P=bigquery-authentication-mode=google_application_credentials -hl
```
## Snowflake

### List of properties
- `snowflake-account` - your Snowflake account name
- `snowflake-warehouse` - your GCP billing project ID