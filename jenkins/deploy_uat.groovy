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
        sh 'docker login -u dmpadmin -p $(oc whoami -t) docker-registry-default.dcore-dev.petrosea.co.id:443'
      }
    }
    stage('Promote') {
      steps {
        // JFROG-DOCKER
        withCredentials([string(credentialsId: 'jfrog-docker-password', variable: 'PASSWORD')]) {
            sh "docker login -u 19335063 -p ${PASSWORD} docker.cicd-jfrog.petrosea.co.id:443"
        }
        sh 'docker pull docker.cicd-jfrog.petrosea.co.id:443/anyname-webapp:latest'
        sh 'docker tag docker.cicd-jfrog.petrosea.co.id:443/anyname-webapp:latest anyname-webapp:latest'
        sh 'docker tag anyname-webapp:latest docker-registry-default.dcore-dev.petrosea.co.id:443/uat-dmp/anyname-webapp:latest'
      }
    }
    stage('Deploy') {
      steps {
        // Openshift
        withCredentials([string(credentialsId: 'oc-dmpadmin-password', variable: 'OC_PASSWORD')]) {
            sh "oc login https://10.42.98.95:8443 -u dmpadmin -p ${OC_PASSWORD}"
        }
        sh 'oc project uat-dmp'
        sh 'docker push docker-registry-default.dcore-dev.petrosea.co.id:443/uat-dmp/anyname-webapp:latest'
        sh 'oc rollout latest anyname-webapp'
        sh 'oc logs -f dc/anyname-webapp'
        sh 'oc describe dc/anyname-webapp'
      }
    }
  }
}
