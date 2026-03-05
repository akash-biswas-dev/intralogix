@Library('MyLibrary') _
pipeline{

    agent none
  stages {

    stage('Test...'){
      // Create test env later.
      agent { label 'docker-agent'}
      stages{
        stage('Clone Repository'){
          steps{// checkout scm }
        }
        stage('Users'){
          steps{ echo 'Running tests for users.'}
        }
        // When more availabel add here.
      }
    }
    stage('Build code'){
      agent {label 'docker-agent'}
      
      parallel{
        stage('Gateway'){
          steps{
           sh 'make build-gateway' 
          }
        }
        stage('Users'){
          steps{
            sh 'make build-gateway' 
          }
        }
        stage('Client'){
          steps{
            sh 'make build-client' 
          }
        }
      }

    }

    

    stage('Push to container registry'){
      steps{
        echo 'Push to container repository'j
      }
    }
  } 
}
