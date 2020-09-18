 
helm upgrade --install --debug --dry-run \
books-develop \
--create-namespace=true \
--namespace=test \
--set image.repository=gcr.io/arquitetura-207620/books-develop \
--set image.tag=91e38b1 \
pipeline/helm/application



## nginx ##
helm upgrade --install --debug --dry-run \
books-develop \
--create-namespace=true \
--namespace=test \
--set image.repository=gcr.io/arquitetura-207620/books-develop \
--set image.tag=91e38b1 \
--set ingress.enabled=true \
--set ingress.hosts[0].host="books-develop.domain.com.br" \
--set ingress.hosts[0].paths[0]="/" \
--set ingress.hosts[0].paths[1]="/api/v1" \
--set ingress.hosts[1].host="books-develop.test" \
--set ingress.hosts[1].paths[0]="/" \
pipeline/helm/application



## helm develop yaml file ##
 
helm upgrade --install --debug --dry-run \
books-develop \
--create-namespace=true \
--namespace=test \
--set image.repository=gcr.io/arquitetura-207620/books-develop \
--set image.tag=91e38b1 \
-f pipeline/helm/application/values-develop.yaml \
pipeline/helm/application