@Library('my-library') _
pipeline{

  agent none

  stages{

    stage ('Clone repository'){
      agent { label "docker-agent"} 

      steps{
        echo 'Clone the repository'

      }
    }

    stage('Running automated tests'){
      agent {label 'docker-agent'}

      steps{

        echo 'Running tests.'
        echo 'Tests successful'

      }

    }

    stage('Build code'){
      agent {label 'docker-agent'}
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

  }

}
