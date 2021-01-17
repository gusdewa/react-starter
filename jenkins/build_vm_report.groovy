pipeline {
  agent {
    node {
      label 'esbdevoppapp1'
    }
  }

  environment {
    ALT_JOURNEY = 'DEFAULT'
    BABEL_DISABLE_CACHE = 1
    BASENAME = '/report'
    FB_PIXEL_ID = "${FB_PIXEL_ID}"
    GA_ID = "${GA_ID}"
    GTM_AUTH = "${GTM_AUTH}"
    GTM_ID = "${GTM_ID}"
    GTM_PREVIEW = "${GTM_PREVIEW}"
    MAINTENANCE_MODE = 'false'
    PATH = "/usr/local/lib/nodejs/node-v12.13.1-linux-x64/bin:$PATH"
    REPORT_MODE = 'true'
    SIMPLIFIED_SUBMISSION = 'false'
    SERVICE_URL = "${SERVICE_URL}"
  }

  stages {
    stage('Config') {
      steps {
        // NPM Config
        sh 'chown -R $(whoami) ~/.npm'
        sh 'npm config set registry https://cicd-jfrog.petrosea.co.id/artifactory/api/npm/repo-npm-dmp/ -g'
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
        sh 'tar -zcvf build-report.tar.gz ./build'
        sh 'chmod 777 build-report.tar.gz'
        sh 'chmod 777 ./scripts/deploy_vm_prod_report.sh'
        sh 'pwd && ls -l'
      }
    }
    stage('Deploy VM 1') {
      steps {
        sh 'scp ./build-report.tar.gz jenkins@10.54.21.21:/apps/'
        sh 'scp ./scripts/deploy_vm_prod_report.sh jenkins@10.54.21.21:/apps/'
        sh "ssh -o StrictHostKeyChecking=no jenkins@10.54.21.21 '/bin/sh /apps/deploy_vm_prod_report.sh'"
      }
    }
    stage('Deploy VM 2') {
      steps {
        sh 'scp ./build-report.tar.gz jenkins@10.54.21.20:/apps/'
        sh 'scp ./scripts/deploy_vm_prod_report.sh jenkins@10.54.21.20:/apps/'
        sh "ssh -o StrictHostKeyChecking=no jenkins@10.54.21.20 '/bin/sh /apps/deploy_vm_prod_report.sh'"
      }
    }
  }
}
