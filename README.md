
https://docs.okd.io/3.11/install/example_inventories.html

# prerequisites

  disable windows hypervisor and virtual compute platform features from program fatures on/off

# download the minishift release from github and extract in c:\soft\minishift folder

# set the various config parameters: https://docs.okd.io/3.11/minishift/command-ref/minishift_config.html

  c:\soft\minishift> minishift config set vm-driver virtualbox

  c:\soft\minishift> minishift config set disk-size 10GB

  c:\soft\minishift> minishift config set memory 6GB

  c:\soft\minishift> minishift config set cpus 4

  c:\soft\minishift> minishift config set skip-check-openshift-release true

# start the cluster which initiates, validates and creates the minishift cluster

  c:\soft\minishift> minishift start

# get the oc path environment and run the below output commands

  c:\soft\minishift> minishift oc-env

    SET PATH=C:\Users\atlantis\.minishift\cache\oc\v3.11.0\windows;%PATH%
    REM Run this command to configure your shell:
    REM     @FOR /f "tokens=*" %i IN ('minishift oc-env') DO @call %i

# login to the minishift cluster 

  c:\soft\minishift> minishift console url: it opens the url  https://192.168.99.101:8443/console in the browser.

        user id: developer
        password: developer

# get the login command to log in to the cluster from the oc 

  go to the menu written developer in the top right corner and click on "copy the login command"

  the clipboard has the following command "oc login https://192.168.99.101:8443 --token=GVW14WaeEkTzwehltGEYCrDdGoP03TRgxUyxGVY-am0" 

# now login to the cluster using the copied command

  c:\soft\minishift> oc login https://192.168.99.101:8443 --token=GVW14WaeEkTzwehltGEYCrDdGoP03TRgxUyxGVY-am0

# configure the docker env

  c:\soft\minishift> minishift docker-env

    SET DOCKER_TLS_VERIFY=1
    SET DOCKER_HOST=tcp://192.168.99.101:2376
    SET DOCKER_CERT_PATH=C:\Users\atlantis\.minishift\certs
    REM Run this command to configure your shell:
    REM     @FOR /f "tokens=*" %i IN ('minishift docker-env') DO @call %i

# now we can use the docker commands on the local machine

 
# compile and deploy a java microservice to okd

  https://openliberty.io/guides/okd.html#what-is-origin-community-distribution-of-kubernetes-okd


# create a service account 

   c:\soft\minishift> oc create sa <sa-name>

# get service account

  c:\soft\minishift> oc get sa

# create a group ( login as admin )

  c:\soft\minishift> oc adm groups new mygroup

# assign a role to  a group

  c:\soft\minishift> oc policy add-role-to-group edit mygroup

# add a user named melvin to a group named mygroup

  c:\soft\minishift> oc adm groups add-users mygroup melvin

# get the groups

  c:\soft\minishift> oc get groups

# add cluster level role to a user

  c:\soft\minishift> $ oc adm policy add-cluster-role-to-user cluster-admin melvin

# create a secret from a string literal

  c:\soft\minishift> oc create secret generic mysecret --from-literal key1=secret1 --from-literal key2=secret2 -n myproj 

# create password file for users with htpasswd

  c:\soft\minishift> htpasswd -c users.txt melvin

# create a secret from a htpasswd generated file 

  c:\soft\minishift> oc create secret generic mysecret --from-file htpasswd=users.txt -n myproj

# add labels to nodes

  c:\soft\minishift> oc label node hostname env=production

# expose a service 

  c:\soft\minishift> oc expose service servcie_name --port 80

# expose an app : get the service for the app and then use the service name to expose the app

  c:\soft\minishift> oc get svc

  c:\soft\minishift> oc expose svc/name

# expose deployment in minishift

  c:\soft\minishift> oc expose deployment/hello-limit --port 80 --target-port 8080

# scale replicaset 

  c:\soft\minishift> oc scale --replicas 3 deployment/hello-limit

# autoscale a deployment

  c:\soft\minishift> oc autoscale dc/hello --min 1 --max 10 --cpu-percent 80 

# get all the configured clusters

  c:\soft\minishift> oc config get-clusters

# view the combined configuration

  c:\soft\minishift> oc config view

# use the different commands in oc config <sub commands>

    current-context Displays the current-context
    delete-cluster  Delete the specified cluster from the kubeconfig
    delete-context  Delete the specified context from the kubeconfig
    get-clusters    Display clusters defined in the kubeconfig
    get-contexts    Describe one or many contexts
    rename-context  Renames a context from the kubeconfig file.
    set             Sets an individual value in a kubeconfig file
    set-cluster     Sets a cluster entry in kubeconfig
    set-context     Sets a context entry in kubeconfig
    set-credentials Sets a user entry in kubeconfig
    unset           Unsets an individual value in a kubeconfig file
    use-context     Sets the current-context in a kubeconfig file
    view            Display merged kubeconfig settings or a specified kubeconfig file

# get pod spec in yaml format

  c:\soft\minishift> oc get pods -n default

  c:\soft\minishift> oc get pod docker-registry-1-bdwls -o yaml -n default

# get api resources

  c:\soft\minishift> oc api-resources

# get all the objects in the default namespace and store the yaml output

  c:\soft\minishift> oc get deploy,sts,svc,configmap,secret -n default -o yaml --export > default.yaml

# bash script to export yaml to sub folders

  for n in $(kubectl get -o=name pvc,configmap,serviceaccount,secret,ingress,service,deployment,statefulset,hpa,job,cronjob)
  do
    mkdir -p $(dirname $n)
    kubectl get -o=yaml --export $n > $n.yaml
  done

# another bash script to export yaml to a single folder

  for n in $(kubectl get -o=name pvc,configmap,ingress,service,secret,deployment,statefulset,hpa,job,cronjob | grep -v 'secret/default-token')
  do
    kubectl get -o=yaml --export $n > $(dirname $n)_$(basename $n).yaml
  done

# stop the cluster

  c:\soft\minishift> minishift stop

# delete the cluster

  c:\soft\minishift> minishift delete

# delete the c:\users\atlantis\.minishift folder

-------------------------------------------------------------------------------------------------------------------------------------------------------

# oc project commands

  # current project
    
    $ oc project

  # list projects

    $ oc get project

  # switch to a project named melvin

    $ oc project melvin

  # view the cluster config

    $ oc config view


-----------------------------------------------------------------------------------------------------------------------------------------------------
                SOURCE TO IMAGE TO GIT PULL, BUILD, CONTAINERIZE, DEPLOY A SPRING BOOT APP TO MINISHIFT/ OPENSHIFT PLATFORM
-----------------------------------------------------------------------------------------------------------------------------------------------------

	git repo for building, testing and deploying a spring boot app using the openshift s2i
	
	project in the laptop: c:\soft\minishift-examples\demo
	project workspace: c:\soft\minishift-examples\demo-ws
	
	-----------------------------------------------------------------------------------------------------------------
	
	https
	
	https://github.com/messages-one/minishift-examples.git
	
	echo "# minishift-examples" >> README.md
	git init
	git add README.md
	git commit -m "first commit"
	git branch -M main
	git remote add origin https://github.com/messages-one/minishift-examples.git
	git push -u origin main
	
	git remote add origin https://github.com/messages-one/minishift-examples.git
	git branch -M main
	git push -u origin main
	
	--------------------------------------------------------------------------------------------------------------------
	
	ssh
	
	git@github.com:messages-one/minishift-examples.git
	
	echo "# minishift-examples" >> README.md
	git init
	git add README.md
	git commit -m "first commit"
	git branch -M main
	git remote add origin git@github.com:messages-one/minishift-examples.git
	git push -u origin main
	
	git remote add origin git@github.com:messages-one/minishift-examples.git
	git branch -M main
	git push -u origin main

# create a project

  c:\soft\minishift> oc new-project minishift-demo-project
    
# get docker client from

  https://download.docker.com/win/static/stable/x86_64/

# copy the docker.exe in c:\soft\minishift folder

# get the docker env details from minishift

  c:\soft\minishift> minishift docker-env

# execute the output of the above command one by one


# login to the registry.redhat.io

	  https://access.redhat.com/RegistryAuthentication#creating-registry-service-accounts-6
	
	  redhat developer account:
	
	   user name: messages.one@outlook.com
	   password:  discovery
	
	   # creating registry service account
	
	      https://access.redhat.com/RegistryAuthentication#creating-registry-service-accounts-6

# login to the registry.redhat.io from docker

  	c:\soft\minishift> docker login https://registry.redhat.io

    username: messages.one@outlook.com
    password: aprilJones@67

# pull the jdk11 s2i image: check this page:  https://docs.openshift.com/online/pro/using_images/s2i_images/java.html 

  c:\soft\minishift> docker pull registry.redhat.io/ubi8/openjdk-11

# pull the latest openjdk-17 s2i image from registry.access.redhat.com   use the same credentials as above.
    list of downloadable container images for minishift/ openshift: https://catalog.redhat.com/software/containers/explore

  c:\soft\minishift> docker pull registry.access.redhat.com/ubi8/openjdk-17:1.12-1.1651233093

# create a new app and begin the build process

    c:\soft\minishift> oc new-app registry.redhat.io/ubi8/openjdk-11~https://github.com/messages-one/minishift-examples.git --name=minishift-demo

  # to use the jdk-17 s2i 

    c:\soft\minishift> oc new-app registry.access.redhat.com/ubi8/openjdk-17~https://github.com/messages-one/minishift-examples.git --name=minishift-demo

# check the compiler logs if a build fails

    c:\soft\minishift> oc logs -f bc/minishift-demo

# restart the build

    c:\soft\minishift> oc start-build minishift-demo

# when the build is successful we get a docker image named

    172.30.1.1:5000/demo-minishift-s2i/minishift-demo:latest

# check that the image exists

    c:\soft\minishift> docker images

      REPOSITORY                                          TAG                 IMAGE ID       CREATED       SIZE
      172.30.1.1:5000/demo-minishift-s2i/minishift-demo   latest              c1ea62183de9   2 hours ago   515MB
      registry.access.redhat.com/ubi8/openjdk-17          1.12-1.1651233093   de955e624ee3   2 weeks ago   404MB
      registry.redhat.io/ubi8/openjdk-11                  latest              e229a9be4f0b   2 weeks ago   435MB
     
# get pods
  
    c:\soft\minishift> oc get pods

# delete multiple pods

    c:\soft\minishift> oc delete pods minishift-demo-1-build minishift-demo-2-build minishift-demo-3-build

------------------------------------------------------------------------------------------------------------------------------------------------

# enable admin addon. this plugin helps to login to Minishift as cluster admin. 

    c:\soft\minishift> minishift addons apply admin-user

#  grant role cluster-admin to user admin.

    c:\soft\minishift> oc login -u system:admin
    c:\soft\minishift> oc adm policy add-cluster-role-to-user cluster-admin admin
    c:\soft\minishift> oc login -u admin -p admin

#  The image used for building runnable Java apps (openjdk18-openshift) is not available by default on Minishift. 
#  We can import it manually from RedHat registry using oc import-image command or just enable and apply plugin xpaas.

    c:\soft\minishift> minishift addons apply xpaas

-------------------------------------------------------------------------------------------------------------------------------------------------

# create a new project

  c:\soft\minishift>oc new-project melvin

    Now using project "melvin" on server "https://192.168.99.101:8443".

    You can add applications to this project with the 'new-app' command. For example, try:

        oc new-app centos/ruby-25-centos7~https://github.com/sclorg/ruby-ex.git

        to build a new example application in Ruby.

c:\soft\minishift>oc new-app openshift/hello-openshift

  --> Found Docker image 7af3297 (4 years old) from Docker Hub for "openshift/hello-openshift"

      * An image stream tag will be created as "hello-openshift:latest" that will track this image
      * This image will be deployed in deployment config "hello-openshift"
      * Ports 8080/tcp, 8888/tcp will be load balanced by service "hello-openshift"
      * Other containers can access this service through the hostname "hello-openshift"

  --> Creating resources ...
      imagestream.image.openshift.io "hello-openshift" created
      deploymentconfig.apps.openshift.io "hello-openshift" created
      service "hello-openshift" created
  --> Success
      Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
       'oc expose svc/hello-openshift'
      Run 'oc status' to view your app.

# create an ingress object ingress.yaml

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: hello-openshift
spec:
  rules:
  - host: hello-openshift.yourcluster.example.com   # change the host name. yourcluster.example.com is the cluster name given at the time of creation
    http:
      paths:
      - backend:
          # Forward to a Service called 'hello-openshift'
          service:
            name: hello-openshift
            port:
              number: 8080
        path: /
        pathType: Exact

# apply the ingress object. it also creates a route which is a wildcard domain

  c:\soft\minishift> oc apply -f ingress.yaml

# get the ingress object

  c:\soft\minishift> oc get ingress

# get the route

  c:\soft\minishift> oc get route

# access the app

  c:\soft\minishift> curl hello-openshift.apps.ocp1.example.com

# delete the route

  c:\soft\minishift> oc delete route hello-openshift-5cbw4

# delete the ingress object in this project

  c:\soft\minishift> oc delete ingress --all

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------


c:\soft\minishift>minishift start

	-- Starting profile 'minishift'
	-- Check if deprecated options are used ... OK
	-- Checking if https://github.com is reachable ... OK
	-- Checking if requested OpenShift version 'v3.11.0' is valid ... OK
	-- Checking if requested OpenShift version 'v3.11.0' is supported ... OK
	-- Checking if requested hypervisor 'virtualbox' is supported on this platform ... OK
	-- Checking if VirtualBox is installed ... OK
	-- Checking the ISO URL ... OK
	-- Downloading OpenShift binary 'oc' version 'v3.11.0'
	 53.59 MiB / 53.59 MiB [===================================================================================================================================================] 100.00% 0s-- Downloading OpenShift v3.11.0 checksums ... OK
	-- Checking if provided oc flags are supported ... OK
	-- Starting the OpenShift cluster using 'virtualbox' hypervisor ...
	-- Minishift VM will be configured with ...
	   Memory:    10 GB
	   vCPUs :    4
	   Disk size: 10 GB
	
	   Downloading ISO 'https://github.com/minishift/minishift-centos-iso/releases/download/v1.17.0/minishift-centos7.iso'
	 375.00 MiB / 375.00 MiB [=================================================================================================================================================] 100.00% 0s
	-- Starting Minishift VM .............................. OK
	-- Checking for IP address ... OK
	-- Checking for nameservers ... OK
	-- Checking if external host is reachable from the Minishift VM ...
	   Pinging 8.8.8.8 ... OK
	-- Checking HTTP connectivity from the VM ...
	   Retrieving http://minishift.io/index.html ... OK
	-- Checking if persistent storage volume is mounted ... OK
	-- Checking available disk space ... 1% used OK
	-- Writing current configuration for static assignment of IP address ... OK
	   Importing 'openshift/origin-control-plane:v3.11.0' . CACHE MISS
	   Importing 'openshift/origin-docker-registry:v3.11.0' . CACHE MISS
	   Importing 'openshift/origin-haproxy-router:v3.11.0' . CACHE MISS
	-- OpenShift cluster will be configured with ...
	   Version: v3.11.0
	-- Pulling the OpenShift Container Image ................................................ OK
	-- Copying oc binary from the OpenShift container image to VM ... OK
	-- Starting OpenShift cluster ......................................................................................................
	Getting a Docker client ...
	Checking if image openshift/origin-control-plane:v3.11.0 is available ...
	Pulling image openshift/origin-cli:v3.11.0
	E0518 10:05:18.049787    2908 helper.go:173] Reading docker config from /home/docker/.docker/config.json failed: open /home/docker/.docker/config.json: no such file or directory, will attempt to pull image docker.io/openshift/origin-cli:v3.11.0 anonymously
	Image pull complete
	Pulling image openshift/origin-node:v3.11.0
	E0518 10:05:21.554543    2908 helper.go:173] Reading docker config from /home/docker/.docker/config.json failed: open /home/docker/.docker/config.json: no such file or directory, will attempt to pull image docker.io/openshift/origin-node:v3.11.0 anonymously
	Pulled 5/6 layers, 84% complete
	Pulled 5/6 layers, 93% complete
	Pulled 6/6 layers, 100% complete
	Extracting
	Image pull complete
	Checking type of volume mount ...
	Determining server IP ...
	Using public hostname IP 192.168.99.101 as the host IP
	Checking if OpenShift is already running ...
	Checking for supported Docker version (=>1.22) ...
	Checking if insecured registry is configured properly in Docker ...
	Checking if required ports are available ...
	Checking if OpenShift client is configured properly ...
	Checking if image openshift/origin-control-plane:v3.11.0 is available ...
	Starting OpenShift using openshift/origin-control-plane:v3.11.0 ...
	I0518 10:06:39.635654    2908 config.go:40] Running "create-master-config"
	I0518 10:06:44.802680    2908 config.go:46] Running "create-node-config"
	I0518 10:06:47.154653    2908 flags.go:30] Running "create-kubelet-flags"
	I0518 10:06:47.860266    2908 run_kubelet.go:49] Running "start-kubelet"
	I0518 10:06:48.235906    2908 run_self_hosted.go:181] Waiting for the kube-apiserver to be ready ...
	I0518 10:09:04.267752    2908 interface.go:26] Installing "kube-proxy" ...
	I0518 10:09:04.269164    2908 interface.go:26] Installing "kube-dns" ...
	I0518 10:09:04.269183    2908 interface.go:26] Installing "openshift-service-cert-signer-operator" ...
	I0518 10:09:04.269196    2908 interface.go:26] Installing "openshift-apiserver" ...
	I0518 10:09:04.269324    2908 apply_template.go:81] Installing "openshift-apiserver"
	I0518 10:09:04.269671    2908 apply_template.go:81] Installing "kube-dns"
	I0518 10:09:04.269732    2908 apply_template.go:81] Installing "openshift-service-cert-signer-operator"
	I0518 10:09:04.270353    2908 apply_template.go:81] Installing "kube-proxy"
	I0518 10:09:09.973408    2908 interface.go:41] Finished installing "kube-proxy" "kube-dns" "openshift-service-cert-signer-operator" "openshift-apiserver"
	I0518 10:13:25.033463    2908 run_self_hosted.go:242] openshift-apiserver available
	I0518 10:13:25.034638    2908 interface.go:26] Installing "openshift-controller-manager" ...
	I0518 10:13:25.034677    2908 apply_template.go:81] Installing "openshift-controller-manager"
	I0518 10:13:28.840818    2908 interface.go:41] Finished installing "openshift-controller-manager"
	Adding default OAuthClient redirect URIs ...
	I0518 10:13:28.883527    2908 interface.go:26] Installing "sample-templates" ...
	I0518 10:13:28.883572    2908 interface.go:26] Installing "openshift-web-console-operator" ...
	I0518 10:13:28.883584    2908 interface.go:26] Installing "centos-imagestreams" ...
	I0518 10:13:28.883592    2908 interface.go:26] Installing "openshift-router" ...
	I0518 10:13:28.883601    2908 interface.go:26] Installing "persistent-volumes" ...
	I0518 10:13:28.883609    2908 interface.go:26] Installing "openshift-image-registry" ...
	Adding sample-templates ...
	Adding web-console ...
	Adding centos-imagestreams ...
	Adding router ...
	Adding persistent-volumes ...
	Adding registry ...
	W0518 10:13:28.891991    2908 create_secret.go:78] Error reading $HOME/.docker/config.json: open /home/docker/.docker/config.json: no such file or directory, imagestream import credentials will not be setup
	I0518 10:13:28.892158    2908 apply_list.go:67] Installing "centos-imagestreams"
	I0518 10:13:28.892882    2908 interface.go:26] Installing "sample-templates/mongodb" ...
	I0518 10:13:28.892917    2908 interface.go:26] Installing "sample-templates/mysql" ...
	I0518 10:13:28.892932    2908 interface.go:26] Installing "sample-templates/postgresql" ...
	I0518 10:13:28.892945    2908 interface.go:26] Installing "sample-templates/dancer quickstart" ...
	I0518 10:13:28.892960    2908 interface.go:26] Installing "sample-templates/jenkins pipeline ephemeral" ...
	I0518 10:13:28.892973    2908 interface.go:26] Installing "sample-templates/sample pipeline" ...
	I0518 10:13:28.892995    2908 interface.go:26] Installing "sample-templates/mariadb" ...
	I0518 10:13:28.893007    2908 interface.go:26] Installing "sample-templates/cakephp quickstart" ...
	I0518 10:13:28.893020    2908 interface.go:26] Installing "sample-templates/django quickstart" ...
	I0518 10:13:28.893064    2908 interface.go:26] Installing "sample-templates/nodejs quickstart" ...
	I0518 10:13:28.893082    2908 interface.go:26] Installing "sample-templates/rails quickstart" ...
	I0518 10:13:28.893176    2908 apply_list.go:67] Installing "sample-templates/rails quickstart"
	I0518 10:13:28.894361    2908 apply_list.go:67] Installing "sample-templates/sample pipeline"
	I0518 10:13:28.895181    2908 apply_list.go:67] Installing "sample-templates/mongodb"
	I0518 10:13:28.895628    2908 apply_template.go:81] Installing "openshift-web-console-operator"
	I0518 10:13:28.895813    2908 apply_list.go:67] Installing "sample-templates/mysql"
	I0518 10:13:28.896097    2908 apply_list.go:67] Installing "sample-templates/mariadb"
	I0518 10:13:28.896133    2908 apply_list.go:67] Installing "sample-templates/postgresql"
	I0518 10:13:28.896570    2908 apply_list.go:67] Installing "sample-templates/cakephp quickstart"
	I0518 10:13:28.896968    2908 apply_list.go:67] Installing "sample-templates/dancer quickstart"
	I0518 10:13:28.896974    2908 apply_list.go:67] Installing "sample-templates/django quickstart"
	I0518 10:13:28.897364    2908 apply_list.go:67] Installing "sample-templates/nodejs quickstart"
	I0518 10:13:28.897478    2908 apply_list.go:67] Installing "sample-templates/jenkins pipeline ephemeral"
	I0518 10:13:46.802428    2908 interface.go:41] Finished installing "sample-templates/mongodb" "sample-templates/mysql" "sample-templates/postgresql" "sample-templates/dancer quickstart" "sample-templates/jenkins pipeline ephemeral" "sample-templates/sample pipeline" "sample-templates/mariadb" "sample-templates/cakephp quickstart" "sample-templates/django quickstart" "sample-templates/nodejs quickstart" "sample-templates/rails quickstart"
	I0518 10:16:26.792909    2908 interface.go:41] Finished installing "sample-templates" "openshift-web-console-operator" "centos-imagestreams" "openshift-router" "persistent-volumes" "openshift-image-registry"
	Login to server ...
	Creating initial project "myproject" ...
	Server Information ...
	OpenShift server started.
	
	The server is accessible via web console at:
	    https://192.168.99.101:8443/console
	
	You are logged in as:
	    User:     developer
	    Password: <any value>
	
	To login as administrator:
	    oc login -u system:admin
	
	
	-- Exporting of OpenShift images is occuring in background process with pid 15260.
	
	c:\soft\minishift>

