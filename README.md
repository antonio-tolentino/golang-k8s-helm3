# README


Jenkins pipeline for Kubernetes with helm 3.

This pipeline was designed with focus on gitflow strategy to promote code between environments.

Pipeline features:
* All workload is executed in containers on Kubernetes
* Email notification in case of failure or success.
* Slack notification in case of failure or success.
* SonarQube for code analysis.
* Authorization deploy on hml and prd branches.
* Kaniko to build and push images.
* Templates Helm 3 to deploy on Kubernetes.


