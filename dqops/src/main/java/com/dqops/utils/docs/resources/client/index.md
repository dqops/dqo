# REST API and Python client

A running DQOps instance can be interacted with through the use of a RESTful API.


## Overview

You can integrate the DQOps REST API with Python code using `dqops` package.

*Check out the [guide](#python-client-guide) underneath. It will walk you through several common use cases for the DQOps Python client.*


## Connecting to DQOps

For the purposes of this guide, we'll be using the synchronous interface offered by the authenticated Python client.
It waits for the response from the REST API before execution of the program will resume.

It is recommended to only allow authenticated users to access the service, especially for exposed REST APIs that can be accessed by different users.
For this purpose, the authenticated Python client for DQOps requires an API Key to be passed.
The client puts the API Key as a `Bearer` token in the `Authorization` header for each HTTP request sent with it.


## Getting the personal API Key

You can easily get your personal, user-unique API Key through the DQOps Web UI.

1. Open your DQOps instance's Web UI in your browser. *If you're trying to access a local DQOps instance, that would be [http://localhost:8888/home](http://localhost:8888/home).*
2. Click on your portrait in the top-right corner.
3. Click on the "Generate API Key" button.

![User portrait menu](../images/api-key.png "User portrait menu"){ loading=lazy } &nbsp;&nbsp;&nbsp;&nbsp; ![Generate API Key](../images/generated-api-key.png "Generate API Key"){ loading=lazy }


You can copy the generated API Key and use it in your Python project.
When running the examples below, use your API Key in place of the placeholder `s4mp13_4u7h_70k3n`.

## Alternative connecting methods

A running DQOps instance is by default shipped alongside a REST API server.
Here's a list of proposed ways of accessing the DQOps REST API:

1. [**curl**](#curl) - Direct HTTP method calls.
2. [**Python sync client**](#python-sync-client) - Unauthenticated synchronous Python client.
3. [**Python auth sync client**](#python-auth-sync-client) - Authenticated synchronous Python client.
4. [**Python async client**](#python-async-client) - Unauthenticated asynchronous Python client.
5. [**Python auth async client**](#python-auth-async-client) - Authenticated asynchronous Python client.

*You can find examples on how to execute each DQOps REST API operation using each of these methods in the [operations' documentation](./operations/index.md).*

<!-- INCLUDE CLIENT_CONNECTING -->

## Python client guide

This guide will showcase the capabilities of DQOps REST API client when integrated into your Python applications.
Using an authenticated synchronous client we'll execute some key operations. Find out about other methods of connecting to DQOps [here](#alternative-connection-methods).

Before heading on to the guide, you'll need to have completed the [DQOps installation using PIP](../dqops-installation/install-dqops-using-pip.md).
On top of that, connection to a DQOps instance with the authenticated client requires a valid [API Key](#getting-the-personal-api-key).

<!-- INCLUDE CLIENT_GUIDE -->

## What's more

Are you looking to address a specific issue? Head down here for full reference documentation over DQOps REST API operations.

<!-- INCLUDE CLIENT_INDEX -->
