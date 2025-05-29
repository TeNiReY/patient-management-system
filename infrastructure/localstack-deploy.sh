#!/bin/bash

set -e #Stops the script if any command fails

aws --endpoint-url=http://localhost:4566 cloudformation deploy \
    --stack-name patient-management \
    --template-file "./cdk.out/localstack.template.json"

aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancer \
    --query "LoadBalancer[0].DNSName" --output text