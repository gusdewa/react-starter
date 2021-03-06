pipeline {
  agent {
    node {
      label 'esbdevoppapp1'
    }
  }

  environment {
    ALT_JOURNEY = "${ALT_JOURNEY}"
    BABEL_DISABLE_CACHE = 1
    BASENAME = '/'
    FB_PIXEL_ID = "${FB_PIXEL_ID}"
    GA_ID = "${GA_ID}"
    GTM_AUTH = "${GTM_AUTH}"
    GTM_ID = "${GTM_ID}"
    GTM_PREVIEW = "${GTM_PREVIEW}"
    GOOGLE_MAP_API_KEY = "${GOOGLE_MAP_API_KEY}"
    MAINTENANCE_MODE = "${MAINTENANCE_MODE}"
    PATH = "/usr/local/lib/nodejs/node-v12.13.1-linux-x64/bin:$PATH"
    REPORT_MODE = 'false'
    SIMPLIFIED_SUBMISSION = "${SIMPLIFIED_SUBMISSION}"
    SERVICE_URL = "${SERVICE_URL}"
  }

  stages {
    stage('Config') {
      steps {
        // NPM Config
        sh 'chown -R $(whoami) ~/.npm'
        sh 'npm config set registry https://cicd-jfrog.domain.co.id/artifactory/api/npm/repo-npm-dmp/ -g'
        sh 'npm config set strict-ssl false -g'
        sh 'printenv'
      }
    }
    stage('Build') {
      steps {
        sh 'rm -Rf ./build'
        sh 'rm -Rf ./node_modules'
        sh 'npm install --silent'
        sh 'npm run build --silent'
        sh "docker build . -t anyname-webapp:${VERSION} --quiet"
        sh "docker tag anyname-webapp:${VERSION} docker.cicd-jfrog.domain.co.id:443/anyname-webapp:${VERSION}"
      }
    }
    stage('Put into artifact') {
      steps {
        // JFROG-DOCKER
        withCredentials([string(credentialsId: 'jfrog-docker-password', variable: 'PASSWORD')]) {
            sh "docker login -u 19335063 -p ${PASSWORD} docker.cicd-jfrog.domain.co.id:443"
        }
        sh "docker push docker.cicd-jfrog.domain.co.id:443/anyname-webapp:${VERSION}"
      }
    }
  }

  post {
      always {
          build job: 'prod-deploy-webapp-ocp', parameters: [[$class: 'StringParameterValue', name: 'VERSION', value: "${VERSION}"], [$class: 'StringParameterValue', name: 'WHICH_OCP', value: 'ocp1']]
          build job: 'prod-deploy-webapp-ocp', parameters: [[$class: 'StringParameterValue', name: 'VERSION', value: "${VERSION}"], [$class: 'StringParameterValue', name: 'WHICH_OCP', value: 'ocp2']]
      }
  }
}
