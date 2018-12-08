#!/bin/bash
docker build -t lancio/billing ./
docker save -o billing.tar lancio/billing:latest