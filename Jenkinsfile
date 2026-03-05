@Library('MyLibrary') _
pipeline{

  agent none

  
  node('docker-agent'){

    stage('Clone repository'){
      steps{ checkout scm }
    }

    stage('Build code'){
      
      parallel{
        stage('Gateway'){
           sh 'make build-gateway' 
        }
        stage('Users'){
           sh 'make build-users' 
        }
        stage('Client'){
           sh 'make build-client' 
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
