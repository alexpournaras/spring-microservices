# Spring Boot Microservices

This repository is about developing a microservices-based app. More specifically:

1. Use the MVC pattern to design and develop a RESTful microservice that receives text through an HTTP request, redacts it and sends it back in pdf format as a response. The microservice should be discoverable through a registration service (e.g. Netflix’s Eureka service). The redaction should handle at least names, email addresses, and street addresses.

2. Use the MVC pattern to design and develop a web server that obtains input from a UI, generates an appropriate HTTP request containing the text, and sends it over to the redaction microservice. It then receives and saves the returned pdf.

## Docker Hub Image Registry
```
cd pdf
docker build -t apournaras/pdf .
docker run -p 3333:3333 apournaras/pdf
docker push apournaras/pdf
```

## Google Cloud Image Registry
```
cd pdf
docker build -t gcr.io/caramel-banner-388810/pdf .
docker run -p 3333:3333 gcr.io/caramel-banner-388810/pdf
docker push gcr.io/caramel-banner-388810/pdf
```

## Docker Compose
```
docker compose up -d
docker compose down
```

## Kompose Converter
```
cd kubernetes
kompose convert -f ..\docker-compose.yml
```

## Kubernetes
```
Add this type in each service.yml
type: LoadBalancer

cd kubernetes
kubectl apply -f .
```

## Google Cloud
```
gcloud auth configure-docker
gcloud config set project PROJECT_ID
gcloud config set compute/zone compute-zone
gcloud container clusters create cluster-name
```