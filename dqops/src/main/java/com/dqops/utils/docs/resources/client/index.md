# REST API and Python client

A running DQOps instance can be interacted with through the use of a RESTful API.


## Overview

You can integrate the DQOps REST API with Python code using `dqops` package.

*Check out the [guide](#python-client-guide) underneath. It will walk you through the process of installing and interacting with DQOps through the Python client.*


## Connecting to DQOps

For the purposes of this guide, we'll be using the synchronous interface that the authenticated Python client offers.



## Alternative connection methods

A running DQOps instance is by default shipped alongside a REST API server.
Here's a list of proposed ways of accessing the DQOps REST API:

1. [**curl**](#curl) - Direct HTTP method calls.
2. [**Python sync client**](#python-sync-client) - Unauthenticated synchronous Python client.
3. [**Python auth sync client**](#python-auth-sync-client) - Authenticated synchronous Python client.
4. [**Python async client**](#python-async-client) - Unauthenticated asynchronous Python client.
5. [**Python auth async client**](#python-auth-async-client) - Authenticated asynchronous Python client.

*You can find examples on how to execute each DQOps REST API operation in the [operations' documentation](./operations/index.md).*

<!-- INCLUDE CLIENT_CONNECTING -->

### **curl**
### **Python sync client**
### **Python auth sync client**
### **Python async client**
### **Python auth async client**

## Python client guide

This guide will showcase the capabilities of DQOps REST API client when integrated into your Python applications.
Using an authenticated synchronous client we'll execute some key operations. Find out about other methods of connecting to DQOps [here](#connecting-to-dqops).

Before heading on to the guide, you'll need to have completed the [DQOps installation using PIP](../working-with-dqo/installation/install-dqo-using-pip.md).

Authenticated client, [API Key]()

<!-- INCLUDE CLIENT_GUIDE -->

## What's more

Are you looking to address a specific issue? Head down here for full reference documentation over DQOps REST API operations.

<!-- INCLUDE CLIENT_INDEX -->
