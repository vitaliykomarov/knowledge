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

  #### Deploying Gitlab Runner
  Creating a group of yc-courses projects in a managed GitLab and an empty infra project.\
  In the project, open Settings → CI/CD → Runners\
  In the tab on the left, copy the URL and token.\
  Download the GitLab Runner chart and install:
  ```
  export HELM_EXPERIMENTAL_OCI=1 && \
  helm pull oci://cr.yandex/yc-marketplace/yandex-cloud/gitlab-org/gitlab-runner/chart/gitlab-runner \
  --version 0.49.1-8 \
  --untar \
  --untardir=charts

  helm install gitlab-runner charts/gitlab-runner \
  --set gitlabUrl=<URL> \
  --set runnerRegistrationToken=<token> \
  --set rbac.create=true \
  --namespace gitlab \
  --create-namespace 
  ```

  After deploying Runner, we can refresh the page and see Runner in the list.\
  Create a file in the repository .gitlab-ci.yml with the following contents:
  ```
  stages:
  - echo
  
  echo job:
    stage: echo
    script:
      - echo "Hello world!"
  ```

  We accept the changes and send a push. Our first pipeline will appear in the CI/CD section of the repository.\
  Go inside the pipeline.\
  Click on the job.\
  We see our printed message.

  #### ArgoCD
  As the name suggests, the GitOps approach will require a repository.\
  For the repository, we define the following structure:
  ```
  infra
  - charts/
  - - here we will add charts
  - values/
  - - here we will add files with values for charts
  - .gitignore – ignored Git files
  ```
  Deploying ArgoCD in a cluster
  First of all, we will install ArgoCD inside the created cluster.
  To do this, download the chart locally:
  ```
  export HELM_EXPERIMENTAL_OCI=1 && \
  helm pull oci://cr.yandex/yc-marketplace/yandex-cloud/argo/chart/argo-cd \
  --version=4.5.3-1 \
  --untar \
  --untardir=charts
  ```

  Creating a separate values/argocd.yaml file. We will configure our ArgoCD in it. We will fill it with the contents later.
  ```
  mkdir values
  touch argocd.yaml
  ```
  Install the chart:
  ```
  helm install -n argocd \
    --create-namespace \
    argocd charts/argo-cd
  ```

  After that, ArgoCD will deploy inside the cluster.\
  To access the ArgoCD web interface, you need to perform a portforward to the desired service:
  ```
  kubectl port-forward svc/argocd-server -n argocd 8080:443 
  ```
  Next, open the address `http://localhost:8080` in your browser.\
  The user name is admin. The password can be obtained from the Secret created by ArgoCD during deployment:
  ``` 
  kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d; echo
  ```

  #### Linking ArgoCD with GitLab
  Setting up GitLab\
  The infra repository will store all the infrastructure components that you deploy via ArgoCD.\
  ArgoCD itself will need read access to this repository, so you need to create a token with access to the repository for argo-cd.\ 
  You need to grant the read_repository right and the Developer role.
  ```
  Gitlab
  	project Infra
  		Settings->Access Tokens
  Create token for Argo-CD
  Token name 		agro-cd
  Role:			Developer
  Scopes:			read_repositor
  ```

  Setting up ArgoCD\
  Add to the previously created file values/argocd.yaml the following content:
  ```
  vim values/argocd.yaml

  configs:
    repositories:
      infra:
        password: <AccessToken>
        project: default
        type: git
        url: https://<GitlabHost>/yc-courses/infra.git
        username: gitlab-ci-token
  ```
  AccessToken — token received in GitLab.\
  GitlabHost — the address of your GitLab instance.

  After that, we will apply the chart again:
  ```
  helm -n argocd upgrade --install \
      argocd \
      charts/argo-cd \
      -f values/argocd.yaml
  ```

  A new Secret named argocd-repo-infra should appear in the argocd namespace:
  ```
  kubectl -n argocd get secret argocd-repo-infra
  #NAME                TYPE     DATA   AGE
  #argocd-repo-infra   Opaque   5      15h
  ```
  You can verify this in the UI at `https://localhost:8080/settings/repos`.

  #### Secret Management
  To hide passwords from the Git repository, we will connect the Helm Secrets tool.
  It allows you to encrypt sensitive information using various methods and decrypt it when applied.
  Installing helm-secrets via the helm plugin:
  ```
  helm plugin install https://github.com/jkroepke/helm-secrets --version v3.15.0
  ```
  Installing dependencies:\
  Sops is a utility that can encrypt and decrypt configuration files\
  Age is an encryption utility that Sops already uses to work with configuration files\
  Now we will prepare the key with which the values files will be encrypted:
  ```
  age-keygen -o key.txt
  ```
  Be sure to add key.txt in .gitignore so that it doesn't end up in Git 
  `add key.txt and *.dec in .gitignore`\
  Specify the path to the encryption key for Sops:
  ```
  export SOPS_AGE_KEY_FILE=$(pwd)/key.txt
  export SOPS_AGE_RECIPIENTS=<the public key that the above command printed out>
  ```
  $(pwd) is specified here specifically. Use an absolute path, so encryption and decryption will work correctly.\
  Encrypt the values/argocd file.yaml:
  ```
  helm secrets enc values/argocd.yaml
  ```
  Now we can safely send the commit to GitLab.\
  To update the application, we will use the helm secrets command instead of helm.\
  For example, installing the ArgoCD chart:
  ```
  helm secrets -n argocd upgrade --install \
      argocd \
      charts/argo-cd \
      -f values/argocd.yaml
  ```
  Be sure to commit all changes and send them to GitLab so that the ArgoCD application can download the chart from the repository.

  #### Connecting Helm Secrets to ArgoCD
  Now we need to teach ArgoCD how to work with encrypted values files.\
  Inside the argocd container, install helm secrets.\ 
  Creating a Secret inside the cluster with the key key.txt , which was created earlier.\
  We throw the Secret inside the argocd container.\
  Creating a Secret:
  ```
  kubectl -n argocd create secret generic \
  helm-secrets-private-keys --from-file=key.txt
  ```

  Edit values/argocd.yaml.\
  To do this, use the helm secrets dec values/argocd.yaml command and create a decoded version .dec and making changes to it:
  ```
  helm secrets dec values/argocd.yaml
  vim values/argocd.yaml.dec
  
  configs:
    repositories:
      infra:
        password: <AccessToken>
        project: default
        type: git
        url: https://<GitlabHost>/yc-courses/infra.git
        username: gitlab-ci-token
  server:
      config:
          # Teaching helm to interact with schemas of values files
          # we are specifically interested in secrets
          helm.valuesFileSchemes: secrets+gpg-import, secrets+gpg-import-kubernetes, secrets+age-import, secrets+age-import-kubernetes, secrets, https
  
  # Copying the configuration from the documentation
  repoServer:
      env:
          - name: HELM_PLUGINS
            value: /custom-tools/helm-plugins/
          - name: HELM_SECRETS_HELM_PATH
            value: /usr/local/bin/helm
          - name: HELM_SECRETS_SOPS_PATH
            value: /custom-tools/sops
          - name: HELM_SECRETS_KUBECTL_PATH
            value: /custom-tools/kubectl
          - name: HELM_SECRETS_CURL_PATH
            value: /custom-tools/curl
          - name: HELM_SECRETS_VALUES_ALLOW_SYMLINKS
            value: "false"
          - name: HELM_SECRETS_VALUES_ALLOW_ABSOLUTE_PATH
            value: "false"
          - name: HELM_SECRETS_VALUES_ALLOW_PATH_TRAVERSAL
            value: "true"
      volumes:
          - name: custom-tools
            emptyDir: {}
          # Creating a volume from Secret with a key key.txt
          - name: helm-secrets-private-keys
            secret:
              secretName: helm-secrets-private-keys
      volumeMounts:
          - mountPath: /custom-tools
            name: custom-tools
          # Mounting volume with Secret with key key.txt
          - mountPath: /helm-secrets-private-keys/
            name: helm-secrets-private-keys
      initContainers:
          - name: download-tools
            image: alpine:latest
            command:
              - sh
              - -ec
            env:
              - name: HELM_SECRETS_VERSION
                value: 3.15.0
              - name: SOPS_VERSION
                value: 3.7.3
              - name: KUBECTL_VERSION
                value: 1.24.0
            args:
              - |
                mkdir -p /custom-tools/helm-plugins
                wget -qO- https://github.com/jkroepke/helm-secrets/releases/download/v${HELM_SECRETS_VERSION}/helm-secrets.tar.gz | tar -C /custom-tools/helm-plugins -xzf-;
  
                wget -qO /custom-tools/sops https://github.com/mozilla/sops/releases/download/v${SOPS_VERSION}/sops-v${SOPS_VERSION}.linux
                wget -qO /custom-tools/kubectl https://dl.k8s.io/release/v${KUBECTL_VERSION}/bin/linux/amd64/kubectl
                wget -qO /custom-tools/curl https://github.com/moparisthebest/static-curl/releases/latest/download/curl-amd64 \
  
                chmod +x /custom-tools/*
            volumeMounts:
              - mountPath: /custom-tools
                name: custom-tools 
  ```
  Do not forget to encrypt our file back:  
  ```
  helm secrets enc values/argocd.yaml
  helm secrets clean . # delete the .dec (unencrypted) version of the file
  ```
  To protect yourself from commits with unencrypted values, you can add the string *.dec to .gitignore. 

  Applying a chart with updated values:
  ```
  helm secrets -n argocd upgrade --install \
    argocd \
    charts/argo-cd \
    -f values/argocd.yaml
  ```

  Let's make sure everything works:
  ```
  kubectl -n argocd get all
  ```
  Let's check that ArgoCD is still opening.

  #### App of apps
  We can create applications in ArgoCD by adding new Application manifests. In order not to do it manually and adhere to the IaC approach, we will resort to using the App of Apps pattern. 
  Its essence is to create an application that creates other applications. First we need to create a Helm chart, in which we will describe the rest of the applications.\
  Create a directory for the chart and create a file inside this directory Chart.yaml with the following contents:
  ```
  mkdir charts/apps
  vim charts/apps/Chart.yaml

  apiVersion: v2
  name: applications
  description: Applications
  version: 0.1.0
  appVersion: "1.0"
  ```
  Next, create the file "values.yaml" with empty values:
  ```
  vim charts/apps/values.yaml
  
  spec:
    source:
      repoURL:
      targetRevision:
    destination:
      server:
  ```

  We have described the parameters that will be used inside the chart.\
  Creating the templates directory, where we will describe all applications:
  ```
  mkdir charts/apps/templates
  ```
  Let's describe the parameters for the chart in the values/apps.yaml file:
  ```
  spec:
    source:
      repoURL: https://<GitlabHost>/yc-courses/infra.git
      targetRevision: HEAD
    destination:
      server: https://kubernetes.default.svc
  ```
  Now let's create the Application itself. To do this, go to the file values/argocd.yaml let's add the application specification.
  But before that its values/argocd.yaml needs to be decoded:
  ```
  helm secrets dec values/argocd.yaml
  vim values/argocd.yaml.dec
  
  configs:
    repositories:
      infra:
        password: <AccessToken>
        project: default
        type: git
        url: https://<GitlabHost>/yc-courses/infra.git
        username: gitlab-ci-token
  
  server:
    config:
          helm.valuesFileSchemes: secrets+gpg-import, secrets+gpg-import-kubernetes, secrets+age-import, secrets+age-import-kubernetes, secrets, https
    additionalApplications:
      - name: apps
        namespace: argocd
        project: default
        source:
          # Specifying the path to values, note that it is relative
          helm:
            valueFiles:
              - ../../values/apps.yaml
          # The path to the chart
          path: charts/apps
          # Repository
          repoURL: https://<GitlabHost>/yc-courses/infra.git
        destination:
          # Install all applications in the same argocd namespace
          namespace: argocd
          server: https://kubernetes.default.svc
        syncPolicy:
          automated: { }
  
  repoServer:
      env:
          - name: HELM_PLUGINS
            value: /custom-tools/helm-plugins/
          # In case wrapper scripts are used, HELM_SECRETS_HELM_PATH needs to be the path of the real helm binary
          - name: HELM_SECRETS_HELM_PATH
            value: /usr/local/bin/helm
          - name: HELM_SECRETS_SOPS_PATH
            value: /custom-tools/sops
          - name: HELM_SECRETS_KUBECTL_PATH
            value: /custom-tools/kubectl
          - name: HELM_SECRETS_CURL_PATH
            value: /custom-tools/curl
          # https://github.com/jkroepke/helm-secrets/wiki/Security-in-shared-environments
          - name: HELM_SECRETS_VALUES_ALLOW_SYMLINKS
            value: "false"
          - name: HELM_SECRETS_VALUES_ALLOW_ABSOLUTE_PATH
            value: "false"
          - name: HELM_SECRETS_VALUES_ALLOW_PATH_TRAVERSAL
            value: "true"
      volumes:
          - name: custom-tools
            emptyDir: {}
          - name: helm-secrets-private-keys
            secret:
              secretName: helm-secrets-private-keys
      volumeMounts:
          - mountPath: /custom-tools
            name: custom-tools
          - mountPath: /helm-secrets-private-keys/
            name: helm-secrets-private-keys
      initContainers:
          - name: download-tools
            image: alpine:latest
            command:
              - sh
              - -ec
            env:
              - name: HELM_SECRETS_VERSION
                value: 3.15.0
              - name: SOPS_VERSION
                value: 3.7.3
              - name: KUBECTL_VERSION
                value: 1.24.0
            args:
              - |
                mkdir -p /custom-tools/helm-plugins
                wget -qO- https://github.com/jkroepke/helm-secrets/releases/download/v${HELM_SECRETS_VERSION}/helm-secrets.tar.gz | tar -C /custom-tools/helm-plugins -xzf-;
  
                wget -qO /custom-tools/sops https://github.com/mozilla/sops/releases/download/v${SOPS_VERSION}/sops-v${SOPS_VERSION}.linux
                wget -qO /custom-tools/kubectl https://dl.k8s.io/release/v${KUBECTL_VERSION}/bin/linux/amd64/kubectl
                wget -qO /custom-tools/curl https://github.com/moparisthebest/static-curl/releases/latest/download/curl-amd64 \
  
                chmod +x /custom-tools/*
            volumeMounts:
              - mountPath: /custom-tools
                name: custom-tools
  ```

  Do not forget to encrypt values/argocd.yaml
  ```
  helm secrets enc values/argocd.yaml
  helm secrets clean .
  ```
  Applying the chart again:
  ```
  helm secrets -n argocd upgrade --install \
      argocd \
      charts/argo-cd \
      -f values/argocd.yaml 
  ```
  Check in the ArgoCD interface that the application was created and moved to the synced status.

  Now the following actions are required for any new application:\
  -Add the application chart to the charts folder\
  -Add Namespace and Application to templates of the apps chart\
  -Add values file for deployment


  #### Deploying the ALB Ingress Controller via ArgoCD
  For the fastest possible setup, I recommend deleting the previous installation of Ingress Controller.\
  To do this, first delete all Ingress:
  ```
  kubectl delete ingresses --all --all-namespaces 
  ```
  The command will be executed for a long time, since the ALB load balancer, which was previously created by the Ingress controller itself, will be deleted. 
  Be sure to wait for this command to be executed before running the following.\
  Then we will delete the namespace with the controller:
  ```
  kubectl delete namespace yc-alb-ingress 
  ```

  Synchronize the current repository structure.
  ```
  infra/
  - charts/
  - - apps/
  - - - templates/
  - - - Chart.yaml
  - - - values.yaml
  - - argo-cd/
  - - - argocd chart's files
  - values/
  - - argo-cd.yaml
  - - apps.yaml
  - .gitignore 
  ```

  Let's transfer the Ingress Controller chart that we downloaded to the infra repository or just download it again:
  ```
  helm pull oci://cr.yandex/yc-marketplace/yandex-cloud/yc-alb-ingress/yc-alb-ingress-controller-chart \
  --version v0.1.17 \
  --untar \
  --untardir=charts
  ```

  Create a file values/alb.yaml with the values:
  ```
  vim values/alb.yaml
  
  folderId: <Folder_ID>
  clusterId: <Cluster_ID>
  saKeySecretKey: |
    here we insert the contents of the sa-key.json file
  ```

  Encrypt the file:
  ```
  helm secrets enc values/alb.yaml
  ```

  Now let's add an application with Ingress to our chart with all applications.\
  To do this, create a file charts/apps/templates/alb.yaml:
  ```
  vim charts/apps/templates/alb.yaml
  
  # Создаем неймспейс для контроллера
  apiVersion: v1
  kind: Namespace
  metadata:
    name: yc-alb-ingress
    annotations:
      # Using this parameter, we specify the order of creation
      argocd.argoproj.io/sync-wave: "-1" 
  ---
  apiVersion: argoproj.io/v1alpha1
  kind: Application
  metadata:
    name: yc-alb-ingress
    # Pay attention to the namespace – it should be argocd
    namespace: argocd
  spec:
    destination:
      # And here is the namespace, where the chart itself will be installed
      namespace: yc-alb-ingress
      server: {{ .Values.spec.destination.server }}
    project: default
    source:
      # Specifying the path to the chart
      path: charts/yc-alb-ingress-controller-chart
      repoURL: {{ .Values.spec.source.repoURL }}
      targetRevision: {{ .Values.spec.source.targetRevision }}
      # Note that we specify the path to the encrypted values in the new format
      # secrets+age-import://<key-volume-mount>/<key-name>.txt?<relative/path/to/the/encrypted/secrets.yaml>
      helm:
        valueFiles:
          - secrets+age-import:///helm-secrets-private-keys/key.txt?../../values/alb.yaml
    syncPolicy:
      automated: {}
  ```

  Now commit and send to GitLab
  ```
  git add .
  git commit -m "" # your own comment
  git push
  ```

  Enjoying automatic deployment from the Git repository.\
  Open the web interface and wait for automatic synchronization.\
  Synchronization can take time – argocd polls repositories every three minutes.\
  We can go inside the yc-alb-ingress application and see all the deployed components.\
  That's all! Ingress Controller is deployed in a few minutes.

  #### GitLab Runner
  Adding the chart to the repository
  ```
  helm repo add gitlab https://charts.gitlab.io
  helm pull gitlab/gitlab-runner --untar --untardir=charts --version=0.41.0
  ```

  Adding values/gitlab-runner.yaml:
  ```
  vim values/gitlab-runner.yaml

  gitlabUrl: <URL Gitlab>
  runnerRegistrationToken: <Registration_Token>
  rbac:
    create: true
  ```
  Encrypting the file:
  ```
  helm secrets enc values/gitlab-runner.yaml 
  ```

  Adding an application, creating a file charts/apps/templates/gitlab-runner.yaml:
  ```
  vim charts/apps/templates/gitlab-runner.yaml

  # Creating a namespace for gitlab-runner
  apiVersion: v1
  kind: Namespace
  metadata:
    name: gitlab-runner
    annotations:
      # Using this parameter, we specify the order of creation
      argocd.argoproj.io/sync-wave: "-1"
  ---
  apiVersion: argoproj.io/v1alpha1
  kind: Application
  metadata:
    name: gitlab-runner
    # Pay attention to the namespace – it should be argocd
    namespace: argocd
  spec:
    destination:
      # And here is the namespace, where the chart itself will be installed
      namespace: gitlab-runner
      server: {{ .Values.spec.destination.server }}
    project: default
    source:
      # Specifying the path to the chart
      path: charts/gitlab-runner
      repoURL: {{ .Values.spec.source.repoURL }}
      targetRevision: {{ .Values.spec.source.targetRevision }}
      # Specify the path to the file
      helm:
        valueFiles:
          - secrets+age-import:///helm-secrets-private-keys/key.txt?../../values/gitlab-runner.yaml
    syncPolicy:
      automated: {}
  ```

  Commit, send it to GitLab and wait for the apps application to sync and start creating a gitlab-runner application:
  ```
  helm secrets clean .
  git add .
  git commit -m "update alb"
  git push
  ```

  We go inside the application and make sure that all components are deployed.\ 
  Check that runner has appeared in GitLab.\
  Restarting the echo task.

  #### Httpbin
  The easiest way would be to create a Helm chart without parameters and from a single file.\
  Create a file charts/httpbin/templates/all.yaml, into which we copy the manifests, and make subnetId and certificateID parameters:
  ```
  mkdir charts/httpbin
  mkdir charts/httpbin/templates
  vim charts/httpbin/templates/all.yaml
  
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
  ---
  apiVersion: networking.k8s.io/v1
  kind: Ingress
  metadata:
    name: httpbin
    annotations:
      ingress.alb.yc.io/subnets: {{ .Values.subnetId }}
      ingress.alb.yc.io/external-ipv4-address: <IP_ADDR>
      ingress.alb.yc.io/group-name: infra-ingress
      ingress.alb.yc.io/security-groups: <ID_OF_THE_SG_yc-secure-group>
  spec:
    tls:
      - hosts:
          - "httpbin.infra.<domain>"
        secretName: yc-certmgr-cert-id-{{ .Values.certificateId }}
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
  Create a file charts/httpbin/Chart.yaml:
  ```
  vim charts/httpbin/Chart.yaml

  apiVersion: v2
  name: httpbin
  description: httpbin
  version: 0.1.0
  appVersion: "1.0"
  ```
  Create a file charts/httpbin/values.yaml:
  ```
  vim charts/httpbin/values.yaml

  subnetId:
  certificateId:
  ```
  Now write the chart values to the values/httpbin.yaml:
  ```
  vim values/httpbin.yaml

  subnetId: <SUBNET_ID>
  certificateId: <CERT_ID>
  ```
  Encrypt the file:
  ```
  helm secrets enc values/httpbin.yaml
  ```
  Adding a new application, creating a file charts/apps/templates/httpbin.yaml:
  ```
  vim charts/apps/templates/httpbin.yaml

  apiVersion: v1
  kind: Namespace
  metadata:
    name: httpbin
    annotations:
      argocd.argoproj.io/sync-wave: "-1"
  ---
  apiVersion: argoproj.io/v1alpha1
  kind: Application
  metadata:
    name: httpbin
    namespace: argocd
  spec:
    destination:
      namespace: httpbin
      server: {{ .Values.spec.destination.server }}
    project: default
    source:
      path: charts/httpbin
      repoURL: {{ .Values.spec.source.repoURL }}
      targetRevision: {{ .Values.spec.source.targetRevision }}
      helm:
        valueFiles:
          - secrets+age-import:///helm-secrets-private-keys/key.txt?../../values/httpbin.yaml
    syncPolicy:
      automated: {} 
  ```
  Commit and send to GitLab:
  ```
  git add .
  git commit -m "create httpbin"
  git push
  ```
  Opening ArgoCD
  Opening the application

  #### Ingress for ArgoCD
  Editing values/argocd.yaml:
  ```
  vim values/argocd.yaml

  server:
    # ... previous values
    
    # Changing the service type to NodePort
    service:
        nodePortHttp: 30082
        type: NodePort
    # Turn on ingress, prescribe the host, subnet, address and group
    ingress:
        annotations:
            ingress.alb.yc.io/subnets: <subnetId>
            ingress.alb.yc.io/external-ipv4-address: <IP_ADDR>
            ingress.alb.yc.io/group-name: infra-ingress
            ingress.alb.yc.io/security-groups: <ID_SG_yc-secure-group>
            # communication between the load balancer and argocd is also via https
            ingress.alb.yc.io/transport-security: tls
        enabled: true
        # specify that https is used
        https: true
        tls:
            - hosts:
                - argocd.infra.<domain>
              secretName: yc-certmgr-cert-id-<CERT_ID>
        hosts:
            - argocd.infra.<domain> 
  ```
  Applying the chart:
  ```
  helm secrets -n argocd upgrade --install \
      argocd \
      charts/argo-cd \
      -f values/argocd.yaml
  ```
  Check that ArgoCD has started opening via the link `https://argocd.infra.<domain>`.
  

</details>

<details>
  <summary><h3>Short version</h3></summary>
  
  #### Oops..it isn't ready. Maybe later :)

</details>
