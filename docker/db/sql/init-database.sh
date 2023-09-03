#!/usr/bin/env bash
#wait for the MySQL Server to come up
#sleep 90s

mysql -u docker -pdocker todo < "/docker-entrypoint-initdb.d/init.sql"