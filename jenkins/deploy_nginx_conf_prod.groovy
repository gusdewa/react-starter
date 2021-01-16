pipeline {
  agent {
    node {
      label 'esbdevoppapp1'
    }
  }

  stages {
    stage('Config') {
      steps {
        sh 'chmod 777 ./scripts/deploy_nginx_prod.sh'
        sh 'chmod 777 ./nginx/prod/default.conf'
        sh 'chmod 777 ./nginx/prod/nginx.conf'
      }
    }
    stage('Deploy VM 1') {
      steps {
        sh "scp ./nginx/prod/default.conf jenkins@10.54.21.21:/apps/"
        sh "scp ./nginx/prod/nginx.conf jenkins@10.54.21.21:/apps/"
        sh "scp ./scripts/deploy_nginx_prod.sh jenkins@10.54.21.21:/apps/"
        sh "ssh -o StrictHostKeyChecking=no jenkins@10.54.21.21 '/bin/sh /apps/deploy_nginx_prod.sh'"
      }
    }
    stage('Deploy VM 2') {
      steps {
        sh "scp ./nginx/prod/default.conf jenkins@10.54.21.21:/apps/"
        sh "scp ./nginx/prod/nginx.conf jenkins@10.54.21.21:/apps/"
        sh "scp ./scripts/deploy_nginx_prod.sh jenkins@10.54.21.21:/apps/"
        sh "ssh -o StrictHostKeyChecking=no jenkins@10.54.21.21 '/bin/sh /apps/deploy_nginx_prod.sh'"
      }
    }
  }
}
