# Wait for job operator

This operator is designed to release the computation from the airflow when waiting for a job to be completed.

Instead of running an airflow task of an hour, the operator allows a long-running task to be completed with timeout at first.
Then the wait for job operator is used to track a status of the long-running task over a specific time.
The wait for job operator is based on the jobId of tracked task. 
As the jobId consists unchanged forever over the application lifetime, pulling the status of specific job by its id is always available.


## Preparation

First you need to establish whether the task you run can finish within the specified timeout time which is 120s as a default.







