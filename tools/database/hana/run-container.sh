# Running the script you accept the SAP license
sudo docker run -p 39013:39013 -p 39017:39017 -p 39041-39045:39041-39045 -p 1128-1129:1128-1129 -p 59013-59014:59013-59014 -h hxehost -v /data/hxe:/hana/mounts \
--ulimit nofile=1048576:1048576 \
--sysctl kernel.shmmax=1073741824 \
--sysctl net.ipv4.ip_local_port_range='40000 60999' \
--sysctl kernel.shmall=8388608 \
--name hxexsa1 saplabs/hanaexpress:latest \
--agree-to-sap-license \
--passwords-url file:///hana/mounts/account.json