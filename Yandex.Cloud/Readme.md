# Yandex.Cloud project

## Resource management
<details>
  <summary><h3>Start/Stop resources</h3></summary>
  
#### To start resources
```
yc managed-kubernetes cluster start kube-infra
yc managed-kubernetes cluster start kube-prod
yc application-load-balancer load-balancer list 
yc managed-postgresql cluster start postgres-prod
# with the command above, you will get the id of the load balancers
# for each load balancer, run the following command
yc application-load-balancer load-balancer start %LOAD_BALANCERS_ID%
```
#### To stop resources
```
yc managed-kubernetes cluster stop kube-infra
yc managed-kubernetes cluster stop kube-prod
yc application-load-balancer load-balancer list 
# with the command above, you will get the id of the load balancers
# for each load balancer, run the following command
yc application-load-balancer load-balancer stop %LOAD_BALANCERS_ID%
yc managed-postgresql cluster stop postgres-prod
```
</details>
<details>
  <summary><h3>Change the context of k8s</h3></summary>

#### Examples of commands:
```
kubectl config get-contexts 
kubectl config set-contexts 
kubectl config use-context yc-kube-infra
```
</details>
<details>
  <summary><h3>Key change in SOPS+AGE</h3></summary>

#### Examples:
```
# for infra
export SOPS_AGE_KEY_FILE=$(pwd)/key_infra.txt
export SOPS_AGE_RECIPIENTS=%AGE_KEY%

# for todoapp
export SOPS_AGE_KEY_FILE=$(pwd)/key.txt
export SOPS_AGE_RECIPIENTS=%AGE_KEY%
```
</details>

## GitLab repo and sctructure
[Link](https://gitlab.com/yc-projects) to the project group in gitlab
#### Structure
Not ready yet...

## Step 1. GitOps model

<details>
  <summary><h3>Long version</h3></summary>

  #### Configuring the CLI profile for Yandex.Cloud
  ```
  yc init
  ```

  Let's create a security group that will be needed for both GitLab and Kubernetes.
  ```
  yc vpc security-group create --name yc-security-group --network-name default \
--rule 'direction=ingress,port=443,protocol=tcp,v4-cidrs=0.0.0.0/0' \
--rule 'direction=ingress,port=80,protocol=tcp,v4-cidrs=0.0.0.0/0' \
--rule 'direction=ingress,from-port=0,to-port=65535,protocol=any,predefined=self_security_group' \
--rule 'direction=ingress,from-port=0,to-port=65535,protocol=any,v4-cidrs=[10.96.0.0/16,10.112.0.0/16]' \
--rule 'direction=ingress,from-port=0,to-port=65535,protocol=tcp,v4-cidrs=[198.18.235.0/24,198.18.248.0/24]' \
--rule 'direction=egress,from-port=0,to-port=65535,protocol=any,v4-cidrs=0.0.0.0/0' \
--rule 'direction=ingress,protocol=icmp,v4-cidrs=[10.0.0.0/8,192.168.0.0/16,172.16.0.0/12]' 
  ```

  Save its ID in the environment variable  
  ```
  export SG_ID=$(yc vpc security-group get --name yc-security-group | head -1 | awk '{print $2}')
  ```

  GitLab is created from the web interface. Select the Managed Service for GitLab item. 
  As soon as the creation is completed, an email with access to GitLab will be sent to the email specified in the form. 
  Do not forget to turn off self-registration after the first login.

  #### Kubernetes Cluster
  To begin with, we will create a service account for the Kubernetes cluster
  ```
  # creating an account
  yc iam service-account create kube-infra
  ```
  ```
  # assign the editor role to the service account
  # the resources needed by the Kubernetes cluster will be created on his behalf
  yc resource-manager folder add-access-binding \
  --name=default \
  --service-account-name=kube-infra \
  --role=editor
  ```

  Creating a public zonal cluster in the ru-central1-b zone
  ```
  yc managed-kubernetes cluster create \
  --name=kube-infra \
  --public-ip \
  --network-name=default \
  --service-account-name=kube-infra \
  --node-service-account-name=kube-infra \
  --release-channel=rapid \
  --zone=ru-central1-b \
  --version 1.24
  --security-group-ids=${SG_ID} \
  --folder-name default
  ```

  Creating a working group from a single node
  ```
  yc managed-kubernetes node-group create \
 --name=group-1 \
 --cluster-name=kube-infra \
 --cores=2 \
 --memory=4G \
 --preemptible \
 --auto-scale=initial=1,min=1,max=2 \
 --network-interface=subnets=default-ru-central1-b,ipv4-address=nat,security-group-ids=${SG_ID} \
 --folder-name default \
 --metadata="ssh-keys=%USER_NAME%:%PUB_KEY%" # use your own
  ```

  Getting a cubconfig
  ```
  yc managed-kubernetes cluster get-credentials --name=kube-infra --external
  ```
  
  Check that there is access and the node has been created
  ```
  kubectl get nodes
  # Example of correct command output
  # NAME                        STATUS   ROLES    AGE   VERSION
  # cl1a9gl68r543b4673a6-ibef   Ready    <none>   1m    v1.21.5
  ```

  #### Yandex ALB Ingress Controller Deployment
  ALB Ingress Controller is served by special Ingress. They only support Service with the Node Port type and are configured using annotations. ingress.alb.yc.io/*
  The ALB controller will require a special service account, with which it will access the Yandex Cloud API and create the necessary resources for work.

  ```
  yc iam service-account create --name ingress-controller

  # The alb.editor role is needed to create load balancers
  yc resource-manager folder add-access-binding default \
  --service-account-name=ingress-controller \
  --role alb.editor

  # The vpc.publicAdmin role is needed to manage external addresses
  yc resource-manager folder add-access-binding default \
  --service-account-name=ingress-controller \
  --role vpc.publicAdmin

  # The certificate-manager.certificates.downloader role
  # needed to download certificates from Yandex Certificate Manager
  yc resource-manager folder add-access-binding default \
  --service-account-name=ingress-controller \
  --role certificate-manager.certificates.downloader

  # The compute role.viewer is needed to add nodes to the load balancer
  yc resource-manager folder add-access-binding default \
  --service-account-name=ingress-controller \
  --role compute.viewer
  ```

  To perform actions on behalf of the service account, we will need to create an authorization key
  ```
  yc iam key create --service-account-name ingress-controller --output sa-key.json
  ```

  After that, a file sa-key.jsonwill be created in the directory in which we executed this command — it will be required for further interaction.
  Don't forget to add the sa-key.json to a file .gitignore, since it contains sensitive data!
  ```
  echo sa-key.json >> .gitignore
  ```

  #### Install the ALB Ingress Controller in the cluster and the Helm batch manager.
  ```
  url -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
  chmod 700 get_helm.sh
  ./get_helm.sh
  helm completion bash | sudo tee /etc/bash_completion.d/helm
  ```

  Create a charts folder — inside it we will save all used Helm charts:
  ```
  mkdir charts
  ```

  Log in to Yandex helm registry
  ```
  export HELM_EXPERIMENTAL_OCI=1
  cat sa-key.json | helm registry login cr.yandex --username 'json_key' --password-stdin
  ```

  Download the Ingress Controller chart to the charts folder
  ```
  helm pull oci://cr.yandex/yc-marketplace/yandex-cloud/yc-alb-ingress/yc-alb-ingress-controller-chart \ 
  --version v0.1.17 \
  --untar \
  --untardir=charts
  ```

  The chart will be stored in the charts/yc-alb-ingress-controller-chart directory
  Installing the chart in the cluster
  ```
  export FOLDER_ID=$(yc config get folder-id)
  export CLUSTER_ID=$(yc managed-kubernetes cluster get kube-infra | head -n 1 | awk -F ': ' '{print $2}')

  helm install \
  --create-namespace \
  --namespace yc-alb-ingress \
  --set folderId=$FOLDER_ID \
  --set clusterId=$CLUSTER_ID \
  --set-file saKeySecretKey=sa-key.json \
  yc-alb-ingress-controller ./charts/yc-alb-ingress-controller-chart/
  ```

  Check that the resources have been created
  ```
  kubectl -n yc-alb-ingress get all
  ```
  
  Now let's start checking the Ingress Controller using the demo application
  #### Deploying the demo application
  Deployment and Service
  Describe Deployment and Service. Note that the service must be of the Node Port type.
  Saving the manifests to the file manifests/httpbin.yaml
  ```
  vim manifests/httpbin.yaml

  apiVersion: apps/v1
  kind: Deployment
  metadata:
    name: httpbin
    labels:
      app: httpbin
  spec:
    replicas: 1
    selector:
      matchLabels:
        app: httpbin
    template:
      metadata:
        labels:
          app: httpbin
      spec:
        containers:
          - name: httpbin
            image: kong/httpbin:latest
            ports:
              - name: http
                containerPort: 80
  ---
  apiVersion: v1
  kind: Service
  metadata:
    name: httpbin
  spec:
    type: NodePort
    selector:
      app: httpbin
    ports:
      - name: http
        port: 80
        targetPort: 80
        protocol: TCP
        nodePort: 30081
  ```

  Deploying the application in K8S
  ```
  # creating a namespace
  kubectl create namespace httpbin
  # apply manifests
  kubectl apply -n httpbin -f manifests/httpbin.yaml 
  ```

  #### Ingress
  Ingress will require a domain name to route requests within the cluster.
  To delegate the domain to the NS of the Yandex server inside the domain settings, change the NS records to ns1.yandexcloud.net, ns2.yandexcloud.net.
  In the domain settings, we will prescribe Yandex nameservers so that domain records can also be managed via Yandex Cloud:
  ns1.yandexcloud.net
  ns2.yandexcloud.net

  Create a public zone using the Yandex Cloud DNS service:
  ```
  yc dns zone create \
  --name yc-courses --zone <your domain with a dot at the end> \
  --public-visibility
  ```

  Creating a reserved IP address for the load balancer and storing its value in a variable:
  ```
  yc vpc address create --name=infra-alb \
  --labels reserved=true \
  --external-ipv4 zone=ru-central1-b

  export INFRA_ALB_ADDRESS=$(yc vpc address get infra-alb --format json | jq -r .external_ipv4_address.address)
  ```

  Create a wildcard A record that points to the IP of the load balancer:
  ```
  yc dns zone add-records --name yc-courses \
  --record "*.infra.$DOMAIN. 600 A $INFRA_ALB_ADDRESS"
  ```

  Now you can use any domains like <application>.infra.<domain>, and we will get to the load balancer we need for the cluster.
  Now we need to wait for the recording to appear. You can use the host or dig command to check:
  ```
  host test.infra.<domain>
  ```

  When the host command starts issuing the IP address of the load balancer, you can start deploying Ingress. Creating the manifests/ingress.yaml file:
  ```
  vim manifests/ingress.yaml
  apiVersion: networking.k8s.io/v1
  kind: Ingress
  metadata:
    name: httpbin
    annotations:
      ingress.alb.yc.io/subnets: <SUBNET_ID>
      ingress.alb.yc.io/external-ipv4-address: <LOAD_BALANCER_ID>
      ingress.alb.yc.io/group-name: infra-ingress
      ingress.alb.yc.io/security-groups: <SECURITY_GROUP_ID>
  spec:
    rules:
      - host: httpbin.infra.<domain>
        http:
          paths:
            - path: /
              pathType: Prefix
              backend:
                service:
                  name: httpbin
                  port:
                    number: 80  
  ```

  Pay attention to the annotations:
  ingress.alb.yc.io/group-name : infra-alb is responsible for the load balancer, ingresses with the same group will be served by the same load balancer
  ingress.alb.yc.io/subnets : <subnet_id> – the network that the load balancer will work with
  The <subnet_id> parameter can be obtained using the command:
  ```
  export SUBNET_ID=$(yc vpc subnet get default-ru-central1-b | head -1 | awk -F ': ' '{print $2}')
  echo $SUBNET_ID
  ```
  Insert the received ID into the manifest and publish the demo application:
  ```
  # applying the manifest
  kubectl -n httpbin apply -f manifests/ingress.yaml  
  ```

  The load balancer is created within 3-5 minutes. You can check with the command:
  ```
  yc application-load-balancer load-balancer list
  ```

  When the load balancer switches to the active status, open the page
  ```
  httpbin.infra.<domain>
  ```

  #### Connecting certificates
  There is a Yandex Certificate Manager service for this.
  Create a certificate for the domain name that was previously used for the httpbin application:
  ```
  yc certificate-manager certificate request \
  --name kube-infra \
  --domains "*.infra.<domain>" \
  --challenge dns 
  ```

  To automatically verify domain ownership, we will need to create a special CNAME record leading to the certificate-manager:
  ```
  yc dns zone add-records --name yc-courses --record \
  "_acme-challenge.infra.<DOMAIN>. 600 CNAME <CERT_ID>.cm.yandexcloud.net." 
  ```

  Waiting for the certificate to switch to the ISSUED status. The certificate can be issued for a long time ⏳
  
  Adding a TLS certificate to Ingress:
  ```
  vim manifests/ingress.yaml

  apiVersion: networking.k8s.io/v1
  kind: Ingress
  metadata:
    name: httpbin
    annotations:
      ingress.alb.yc.io/subnets: <SUBNET_ID>
      ingress.alb.yc.io/external-ipv4-address: <LOAD_BALANCER_IP>
      ingress.alb.yc.io/group-name: infra-ingress
      ingress.alb.yc.io/security-groups: <SECURITY_GROUP_ID>
  spec:
    tls:
      - hosts:
          - "httpbin.infra.<domain>"
        secretName: yc-certmgr-cert-id-<CERT_ID>
    rules:
      - host: httpbin.infra.<domain>
        http:
          paths:
            - path: /
              pathType: Prefix
              backend:
                service:
                  name: httpbin
                  port:
                    number: 80 
  ```

  Apply manifest:
  ```
  kubectl -n httpbin apply -f manifests/ingress.yaml
  # and check
  yc application-load-balancer load-balancer list
  ```

  Check that everything works. Open the link `https://httpbin.infra.<domain>`
  
  We will not use the httpbin demo application anymore. Then expand it by other means. 
  The certificate will still be required, and we will create deployment, servisand ingress again, so delete:
  ```
  kubectl delete -n httpbin -f manifests/ingress.yaml
  kubectl delete -n httpbin -f manifests/httpbin.yaml
  kubectl delete ns httpbin
  ```

  
  
</details>

<details>
  <summary><h3>Short version</h3></summary>
  
  #### Oops..it isn't ready. Maybe later :)

</details>
