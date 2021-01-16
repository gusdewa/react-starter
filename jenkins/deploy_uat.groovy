pipeline {
  agent {
    node {
      label 'esbdevoppapp1'
    }
  }

  stages {
    stage('Config') {
      steps {
        // Openshift
        withCredentials([string(credentialsId: 'oc-dmpadmin-password', variable: 'OC_PASSWORD')]) {
            sh "oc login https://10.42.98.95:8443 -u dmpadmin -p ${OC_PASSWORD}"
        }
        sh 'oc project uat-dmp'
        // Docker Registry Deployment
        sh 'docker login -u dmpadmin -p $(oc whoami -t) docker-registry-default.dcore-dev.telkomsel.co.id:443'
      }
    }
    stage('Promote') {
      steps {
        // JFROG-DOCKER
        withCredentials([string(credentialsId: 'jfrog-docker-password', variable: 'PASSWORD')]) {
            sh "docker login -u 19335063 -p ${PASSWORD} docker.cicd-jfrog.telkomsel.co.id:443"
        }
        sh 'docker pull docker.cicd-jfrog.telkomsel.co.id:443/klop-webapp:latest'
        sh 'docker tag docker.cicd-jfrog.telkomsel.co.id:443/klop-webapp:latest klop-webapp:latest'
        sh 'docker tag klop-webapp:latest docker-registry-default.dcore-dev.telkomsel.co.id:443/uat-dmp/klop-webapp:latest'
      }
    }
    stage('Deploy') {
      steps {
        // Openshift
        withCredentials([string(credentialsId: 'oc-dmpadmin-password', variable: 'OC_PASSWORD')]) {
            sh "oc login https://10.42.98.95:8443 -u dmpadmin -p ${OC_PASSWORD}"
        }
        sh 'oc project uat-dmp'
        sh 'docker push docker-registry-default.dcore-dev.telkomsel.co.id:443/uat-dmp/klop-webapp:latest'
        sh 'oc rollout latest klop-webapp'
        sh 'oc logs -f dc/klop-webapp'
        sh 'oc describe dc/klop-webapp'
      }
    }
  }
}
