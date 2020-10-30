# Kaniko Container Registry Credentials

## Google container Registry (GCR)
In GCP console create a Service account, add Storage Admin role and generate json key file.

## Create Kubernetes Secret with Json credentials
Rename json service account file to "jenkins-gcp-credentials.json" and apply command below:

```bash
kubectl create secret generic kaniko-gcp-credentials-secret --from-file=./jenkins-gcp-credentials.json
```

## Elastic Container Registry (ECR) and Docker Hub
Create config.json with the credentials for Docker Hub or ECR and apply command bellow:

```bash
kubectl create configmap docker-config --from-file=config.json
```



## References
https://github.com/GoogleContainerTools/kaniko