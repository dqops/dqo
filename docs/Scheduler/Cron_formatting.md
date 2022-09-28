Cron strings are formatted in crontab format. This is a space-separated list of numbers for when the schedule will execute:

Each value may be a number, a fraction, or an asterisk `*` .

For example:

A cron string of `0 0 * * *` will execute at midnight every day.

A cron string of `*/5 * * * *` will execute every 5 minutes.

Please refer to a full reference on cron formatting [here](https://www.ibm.com/docs/en/db2oc?topic=task-unix-cron-format)

