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
      }
    }
    stage('Build') {
      steps {
        sh 'rm -Rf ./build'
        sh 'rm -Rf ./node_modules'
        sh 'npm install --silent'
        sh 'npm run build:prod --silent'
      }
    }
    stage('Zip') {
      steps {
        sh 'tar -zcvf build.tar.gz ./build'
        sh 'chmod 777 build.tar.gz'
        sh 'chmod 777 ./scripts/deploy_vm_prod.sh'
        sh 'pwd && ls -l'
      }
    }
    stage('Deploy VM 1') {
      steps {
        sh "scp ./build.tar.gz jenkins@10.54.21.21:/apps"
        sh "scp ./scripts/deploy_vm_prod.sh jenkins@10.54.21.21:/apps"
        sh "ssh -o StrictHostKeyChecking=no jenkins@10.54.21.21 '/bin/sh /apps/deploy_vm_prod.sh'"
      }
    }
    stage('Deploy VM 2') {
      steps {
        sh "scp ./build.tar.gz jenkins@10.54.21.20:/apps"
        sh "scp ./scripts/deploy_vm_prod.sh jenkins@10.54.21.20:/apps"
        sh "ssh -o StrictHostKeyChecking=no jenkins@10.54.21.20 '/bin/sh /apps/deploy_vm_prod.sh'"
      }
    }
  }
}
