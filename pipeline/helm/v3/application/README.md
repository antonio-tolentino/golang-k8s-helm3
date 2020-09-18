# Helm Instructions #
This document describes what need to be changed before apply helm templates.

## Chart ##
Verify "chart.yaml" and replace tags bellow if necessary:
* name
* version
* appVersion

## Values default ##
Verify "values.yaml", replace or ajust every resource necessary to your application.

## Kubernetes Livenessprobe e Readinessprobe ##
Verify "deployment.yaml" tags "path" and "port" and other tags if necessary.


## Command to apply helm ##



