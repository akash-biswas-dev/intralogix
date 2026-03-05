@Library('MyLibrary') _
pipeline{

	agent none

	stages {
		// Test stage
		stage('Test'){
			// Create test env later.
			agent { label 'docker-agent' }

			stages{
				stage('Clone the repostitory') {
					steps{
						echo 'Clone repository.'
						//checkout scm
					}
				}
				// Add tests when available.
				stage('Users'){
					steps{
						echo 'Running tests for users.'
					}
				}
			}
		}
		// Build stage
		stage('Build'){
			agent {
				label 'docker-agent'
			}

			stages{

				stage('Clone repository'){
					steps{
						checkout scm
					}
				}

				stage('Build docker image'){

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

			}

		}

		stage('Push to container registry'){
			steps{
				echo 'Push to container repository'

        withCredentials([
          usernamePassword(
            credentialsId: 'docker-credential',
            usernameVariable: 'USER',
            passwordVariable: 'PASS'
          )
        ]) {
           sh '''
              echo "$PASS" | docker login -u "$USER" --password-stdin
              make push-all
          '''
        }
			}
		}
    stage('Clean Builds'){
      steps{
        sh 'docker image rm $(docker images -aq)'
      }
    }
	}
}
