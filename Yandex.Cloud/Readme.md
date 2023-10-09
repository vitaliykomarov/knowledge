# Yandex.Cloud project

## Step 1. GitOps model

<details>
  <summary>Long version</summary>

  Configuring the CLI profile for Yandex.Cloud
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

  Kubernetes Cluster
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

  Install the ALB Ingress Controller in the cluster and the Helm batch manager.
  ```
  url -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
  chmod 700 get_helm.sh
  ./get_helm.sh
  helm completion bash | sudo tee /etc/bash_completion.d/helm
  ```


</details>

<details>
  <summary>Short version</summary>
  
  #### Oops..it isn't ready. Maybe later :)

</details>
