#!/bin/bash

# Stop the service
systemctl stop dqops.service

# Delete the service
rm /etc/systemd/system/dqops.service

# Prepare the user home folder
mkdir "`dirname $(realpath "$0")`/userhome"

# Create and install the service
ln -s "`dirname $(realpath "$0")`/dqops.service" /etc/systemd/system/dqops.service

# Enable autostart
systemctl enable dqops.service
systemctl start dqops.service
systemctl status dqops.service
