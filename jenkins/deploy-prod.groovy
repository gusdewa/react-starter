pipeline {
  agent {
    node {
      label 'esbdevoppapp1'
    }
  }

  stages {
    stage('Config') {
      steps {
        sh "echo building version=${VERSION}"
        // Openshift
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'prod-oc-dmpadmin-password',
          usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']
        ]) {
          sh "oc login https://master.${WHICH_OCP}.digitalcore.domain.co.id:8443 -u ${USERNAME} -p '${PASSWORD}' --insecure-skip-tls-verify"
        }
        sh 'oc project prod-dmp'
        // Docker Registry Deployment
        sh "docker login -u dmpadmin -p \$(oc whoami -t) docker-registry-default.${WHICH_OCP}.digitalcore.domain.co.id:443"
      }
    }
    stage('Promote') {
      steps {
        // JFROG-DOCKER
        withCredentials([string(credentialsId: 'jfrog-docker-password', variable: 'PASSWORD')]) {
            sh "docker login -u 19335063 -p ${PASSWORD} docker.cicd-jfrog.domain.co.id:443"
        }
        sh "docker pull docker.cicd-jfrog.domain.co.id:443/anyname-webapp:${VERSION}"
        sh "docker tag docker.cicd-jfrog.domain.co.id:443/anyname-webapp:${VERSION} anyname-webapp:${VERSION}"
        sh "docker tag anyname-webapp:${VERSION} docker-registry-default.${WHICH_OCP}.digitalcore.domain.co.id:443/prod-dmp/anyname-webapp:latest"
      }
    }
    stage('Deploy') {
      steps {
        sh "docker push docker-registry-default.${WHICH_OCP}.digitalcore.domain.co.id:443/prod-dmp/anyname-webapp:latest"
        sh 'oc whoami'
        // Re Login
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'prod-oc-dmpadmin-password',
          usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']
        ]) {
          sh "oc login https://master.${WHICH_OCP}.digitalcore.domain.co.id:8443 -u ${USERNAME} -p '${PASSWORD}' --insecure-skip-tls-verify"
        }
        sh 'oc project prod-dmp'
        // Re Login
        sh 'oc get dc'
        sh 'oc rollout latest anyname-webapp'
        sh 'oc logs -f dc/anyname-webapp'
        sh 'oc describe dc/anyname-webapp'
        sh 'sleep 5'
      }
    }
  }
}
