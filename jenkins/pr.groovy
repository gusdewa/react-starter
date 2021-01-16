pipeline {
  agent {
    node {
      label 'esbdevoppapp1'
    }
  }

  environment {
    PATH = "/usr/local/lib/nodejs/node-v12.13.1-linux-x64/bin:$PATH"
    BABEL_DISABLE_CACHE = 1
  }

  stages {
    stage('Config') {
      steps {
        // NPM Config
        sh 'chown -R $(whoami) ~/.npm'
        sh 'npm config set registry https://cicd-jfrog.telkomsel.co.id/artifactory/api/npm/repo-npm-dmp/ -g'
        sh 'npm config set strict-ssl false -g'
      }
    }
    stage('Clean up') {
      steps {
        sh 'rm -Rf ./node_modules'
      }
    }
    stage('Install') {
      steps {
        sh 'npm install --silent'
      }
    }
    stage('Test') {
      steps {
        sh 'npm run lint:staged'
        sh 'npm run test'
      }
    }
  }
}
