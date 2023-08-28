How to install Kubernetes cluster
========================
CentOS 8
~~~~~~~~~~~~~~

Master node: master.k8s

Disable SELinux:

.. code:: bash

  setenforce 0 && sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

Only for test cluster or you can configure firewall rules:

.. code:: bash

  systemctl disable firewalld && systemctl stop firewalld

Add repo:

.. code:: bash

  cat <<EOF > /etc/yum.repos.d/kubernetes.repo
  [kubernetes]
  name=Kubernetes
  baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
  enabled=1
  gpgcheck=1
  repo_gpgcheck=1
  gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
  EOF

Install packages:

.. code:: bash

  dnf install -y --nobest docker kubelet kubeadm kubectl kubernetes-cni

Install container runtime (containerd):

.. code:: bash

  wget https://github.com/containerd/containerd/releases/download/v1.7.4/containerd-1.7.4-linux-amd64.tar.gz
  tar Cxzvf /usr/local containerd-1.7.4-linux-amd64.tar.gz
  wget https://raw.githubusercontent.com/containerd/containerd/main/containerd.service
  mv containerd.service /lib/systemd/system
  systemctl daemon-reload
  systemctl enable --now containerd
  wget https://github.com/opencontainers/runc/releases/download/v1.1.9/runc.amd64
  install -m 755 runc.amd64 /usr/local/sbin/runc
  wget https://github.com/containernetworking/plugins/releases/download/v1.3.0/cni-plugins-linux-amd64-v1.3.0.tgz
  mkdir -p /opt/cni/bin
  tar Cxzvf /opt/cni/bin cni-plugins-linux-amd64-v1.3.0.tgz

Enable services if not enabled:

.. code:: bash

  systemctl enable docker && systemctl start docker
  # or for Podman 
  systemctl enable podman && systemctl start podman
  systemctl enable kubelet && systemctl start kubelet

Disable swap to improve perfomance:

.. code:: bash

  swapoff -a && sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab

Set bridged packets to traverse iptables rules:

.. code:: bash

  sysctl -w net.bridge.bridge-nf-call-iptables=1
  cat <<EOF > /etc/sysctl.d/k8s.conf
  net.bridge.bridge-nf-call-ip6tables = 1
  net.bridge.bridge-nf-call-iptables = 1
  EOF
  sysctl --system
  modprobe br_netfilter

Repeate all actions or clone VM to create nodes. Don't forget reinitialize the MAC adress for clone VM/s.
Change the hostname on the nodes if they were cloned:

.. code:: bash

  # node1
  hostnamectl --static set-hostname node1.k8s
  # node2
  hostnamectl --static set-hostname node2.k8s

Add names and IP to hosts on all nodes:

.. code:: bash

  # As an example
  cat <<EOF >> /etc/hosts
  192.168.1.10 master.k8s
  192.168.1.11 node1.k8s
  192.168.1.12 node2.k8s
  EOF

Initialize a Kubernetes on master node

.. code:: bash

  kubeadm init

**Important:** Because I shutdown master node and cloned it for nodes my bridged packets settings were reset after switching on.
Just repeated this step again on all nodes.

If something went wrong - ``kubeadm reset && rm -rf /etc/cni/net.d``

kubeadm init will write a comand to join nodes

.. code:: bash

  #As an example
  kubeadm join 192.168.1.10:6443 --token %token% \
	--discovery-token-ca-cert-hash %token%

Commands to configure control

.. code:: bash

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

  export KUBECONFIG=$HOME/admin.conf

Installed Weave CNI

.. code:: bash

  #As an example
  kubectl apply -f https://github.com/weaveworks/weave/releases/download/v2.8.1/weave-daemonset-k8s.yaml


How to control Kubernetes cluster from another workstation
~~~~~~~~~~~~~~~~~~
Copy config from master node

.. code:: bash

  scp root@192.168.1.10:/etc/kubernetes/admin.conf ~/.kube/config2

Configure cluster and context:

.. code:: bash

  kubectl config set-cluster kubernetes --server=https://192.168.1.10:6443
  kubectl config set-context kubernetes --cluster=kubernetes --user=kubernetes-admin --kubeconfig="config2"
  #Load config
  export KUBECONFIG=~/.kube/config:~/.kube/config2
  #Or merge them into one
  cp ~/.kube/config ~/.kube/config-backup
  export KUBECONFIG=~/.kube/config:~/.kube/config2
  kubectl config view --flatten > all-in-one-kubeconfig.yaml
  mv all-in-one-kubeconfig.yaml ~/.kube/config
  #Rename context
  kubectl config rename-context kubernetes-admin@kubernetes kubernetes
