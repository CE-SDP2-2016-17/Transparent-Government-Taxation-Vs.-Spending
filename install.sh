#!/bin/bash
yum install httpd
service status httpd > updates_httpd.txt
