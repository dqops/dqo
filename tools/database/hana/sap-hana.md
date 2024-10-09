This file is a SAP HANA setup for the development purpose 
which allows to create a persistent container.

Windows requires the WSL. Works fine with Ubuntu 22 under WSL.

## Environment configuration

Run WSL 
> wsl

Edit the file
> nano /etc/sysctl.conf

add lines
```
fs.file-max=20000000
fs.aio-max-nr=262144
vm.memory_failure_early_kill=1
vm.max_map_count=135217728
net.ipv4.ip_local_port_range=40000 60999
```

Create an account file with permissions
> mkdir /data
> 
> mkdir /data/hxe
> 
> touch /data/hxe/account.json
> 
> sudo chmod 600 /data/hxe/account.json
>
> sudo chown 12000:79 /data/hxe/account.json
>
> chmod -R 777 /data/hxe
> 
> nano /data/hxe/account.json

and paste
```
{
"master_password" : "HXEHana1"
}
```

## Docker lifecycle

Run the HANA docker. Remember that you accept the license by running the container from the given script.
>sh run-container.sh

Remove the docker
> docker rm hxexsa1

## DBeaver login config to the container

```
host: localhost
port: 39017
instance number: 90
username: SYSTEM
password: HXEHana1
```
