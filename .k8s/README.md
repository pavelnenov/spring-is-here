# How to deploy the application to Kubernetes

1. Make sure docker desktop is running and Kubernetes feature enabled
2. Make sure kubectl is installed and configured to use docker desktop https://kubernetes.io/docs/tasks/tools/install-kubectl-macos/
3. Build the docker images

3.1 Make sure to go to [api-gateway build.gradle](../api-gateway/build.gradle) and [authentication-client build.gradle](../authentication-client/build.gradle) and change the image name to your own docker hub account. (replace "pavelnenov" with your registry name)

Build and push to registry:

```bash
./gradlew jib
````

Or build locally:
```bash
./gradlew jibDockerBuild
```

3. Create a Secret object for the database password (password = "sekret")

In order to create the secret, first you need to encode it to base64
```bash
echo -n 'sekret' | base64
```
Then update this value in [mysql-secret-password.yaml](mysql-secret-password.yaml)
```yaml
data:
  password: c2VrcmV0 // the result from the encode command
```

And create the secret
```bash
kubectl apply -f mysql-secret-password.yaml
```

4. Deploy the database

```bash
kubectl apply -f db-deployment.yaml
```
Verify the db is deployed
```bash
kubectl get deployments
kubectl get pods
```

5. Deploy the api-gateway

```bash
kubectl apply -f api-gateway-deployment.yaml
```

Verify the api-gateway is deployed
```bash
kubectl get deployments
kubectl get pods
```

6. Deploy the authentication-client

```bash
kubectl apply -f auth-client-deployment.yaml
```

Verify the authentication-client is deployed
```bash
kubectl get deployments
kubectl get pods
```

7. Deploy a Service authentication-service

```bash
kubectl apply -f auth-client-service.yaml
```

8. Deploy the load balancer service
Now in order to be able to interact with the kubernetes deployment externally, we need to create a service of type LoadBalancer

```bash
kubectl deploy -f load-balancer-service.yaml
```

Verify the service is deployed
```bash
kubectl get services
```


## Cheat sheet

### Create secret for the password
```bash
echo -n 'sekret' | base64
```

```bash
kubectl create secret generic mysql-pass --from-literal=password=sekret
```

```bash
kubectl get secret mysql-pass -o jsonpath="{.data.password}"
```


expose service
```bash
kubectl expose deployment mysql --type=LoadBalancer --name=mysql-service
```

```bash
kubectl run nginx --image=nginx:latest --port=80
```

scale deployment
```bash
kubectl scale deployment authentication-client-deployment --replicas=5
```
or
```bash
kubectl patch deployment authentication-client-deployment -p '{"spec":{"replicas":5}}'
```

Create endpoint to break the app