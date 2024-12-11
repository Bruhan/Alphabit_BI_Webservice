pipeline {
  agent {
    docker {
      image 'maven:3.3.9-jdk-8'
    }

  }
  stages {
    stage('initialize') {
      steps {
        echo 'initialized'
      }
    }

    stage('clean') {
      steps {
        sh 'mvn clean'
      }
    }

    stage('build') {
      steps {
        sh 'mvn install'
        sh 'ls'
        sh 'java -jar target/Management_Web_Service.jar'
      }
    }

  }
}