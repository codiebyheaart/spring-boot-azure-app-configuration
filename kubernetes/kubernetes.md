# 1.1 Create secret in AKS
kubectl create secret generic appconfig-secret \
  --from-literal=APP_CONFIG_CONNECTION_STRING="Endpoint=https://<your_store>.azconfig.io;Id=<ID>;Secret=<SECRET>"

  kubectl get secret appconfig-secret -o yaml

kubectl get secret appconfig-secret \
  -o jsonpath="{.data.APP_CONFIG_CONNECTION_STRING}" | base64 -d

# 2. Azure Container Registry (ACR)
2.1 Login to ACR
az acr login -n nindia


az aks update \
  --resource-group pankaj-aks-group \
  --name pankaj-aks \
  --attach-acr nindia


az acr repository list -n nindia -o table


## Apply Deployment 

kubectl apply -f deployment.yaml

kubectl get pods

# optional ---kubectl delete pod -l app=spring-config-app

# Apply LoadBalancer Service
kubectl apply -f service.yaml

kubectl get svc spring-config-service
http://<EXTERNAL-IP>/

# Verify App Configuration Integration
[Verify App Configuration Integration](http://<EXTERNAL-IP>/config

{
  "source": "Azure App Configuration",
  "name1": "your-value",
  "name2": "your-value"
}

## Useful AKS Commands
kubectl get nodes
kubectl get deployments
kubectl logs <pod-name>
kubectl rollout restart deployment spring-config-app

## Clean Up Resources (Optional)
kubectl delete -f deployment.yaml
kubectl delete -f service.yaml
Delete AKS:

az aks delete -g pankaj-aks-group -n pankaj-aks


## Quick notes
# Set context variables (run this first)
export RESOURCE_GROUP="my-aks-rg"
export ACR_NAME="myacr"
export APP_CONFIG_NAME="myappconfig"
export AKS_NAME="my-aks-cluster"
export LOCATION="eastus"

# Get cluster credentials
az aks get-credentials --resource-group $RESOURCE_GROUP --name $AKS_NAME

# Common kubectl commands
kubectl get all                                    # View all resources
kubectl get pods                                   # View pods
kubectl get services                               # View services
kubectl logs -f -l app=spring-app                  # Follow logs
kubectl describe pod <pod-name>                    # Pod details
kubectl exec -it <pod-name> -- sh                  # Shell into pod
kubectl rollout restart deployment spring-app      # Restart deployment
kubectl scale deployment spring-app --replicas=3   # Scale
kubectl delete pod <pod-name>                      # Delete pod (will recreate)

# Common Azure CLI commands
az aks list -o table                               # List clusters
az acr list -o table                               # List registries
az appconfig kv list --name $APP_CONFIG_NAME       # List config keys
az aks show --name $AKS_NAME --resource-group $RESOURCE_GROUP  # Cluster details









)








