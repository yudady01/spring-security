#!/usr/bin/env bash

mysql -uroot -proot tommy < "/docker-entrypoint-initdb.d/000-create-databases.sql"
mysql -uroot -proot tommy < "/docker-entrypoint-initdb.d/001-create-tables.sql"
