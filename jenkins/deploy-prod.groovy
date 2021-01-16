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
          sh "oc login https://master.${WHICH_OCP}.digitalcore.telkomsel.co.id:8443 -u ${USERNAME} -p '${PASSWORD}' --insecure-skip-tls-verify"
        }
        sh 'oc project prod-dmp'
        // Docker Registry Deployment
        sh "docker login -u dmpadmin -p \$(oc whoami -t) docker-registry-default.${WHICH_OCP}.digitalcore.telkomsel.co.id:443"
      }
    }
    stage('Promote') {
      steps {
        // JFROG-DOCKER
        withCredentials([string(credentialsId: 'jfrog-docker-password', variable: 'PASSWORD')]) {
            sh "docker login -u 19335063 -p ${PASSWORD} docker.cicd-jfrog.telkomsel.co.id:443"
        }
        sh "docker pull docker.cicd-jfrog.telkomsel.co.id:443/klop-webapp:${VERSION}"
        sh "docker tag docker.cicd-jfrog.telkomsel.co.id:443/klop-webapp:${VERSION} klop-webapp:${VERSION}"
        sh "docker tag klop-webapp:${VERSION} docker-registry-default.${WHICH_OCP}.digitalcore.telkomsel.co.id:443/prod-dmp/klop-webapp:latest"
      }
    }
    stage('Deploy') {
      steps {
        sh "docker push docker-registry-default.${WHICH_OCP}.digitalcore.telkomsel.co.id:443/prod-dmp/klop-webapp:latest"
        sh 'oc whoami'
        // Re Login
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'prod-oc-dmpadmin-password',
          usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']
        ]) {
          sh "oc login https://master.${WHICH_OCP}.digitalcore.telkomsel.co.id:8443 -u ${USERNAME} -p '${PASSWORD}' --insecure-skip-tls-verify"
        }
        sh 'oc project prod-dmp'
        // Re Login
        sh 'oc get dc'
        sh 'oc rollout latest klop-webapp'
        sh 'oc logs -f dc/klop-webapp'
        sh 'oc describe dc/klop-webapp'
        sh 'sleep 5'
      }
    }
  }
}
