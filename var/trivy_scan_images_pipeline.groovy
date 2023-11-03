pipeline {
    agent any

    stages {
        stage('Run Trivy on Local Docker Image') {
            steps {
                script {
                    // Define the Docker image you want to scan
                    def dockerImage = 'sonarqube:lts-community'

                    // Define the path to the Trivy executable
                    def trivyPath = '/usr/local/bin/trivy'

                    // Run Trivy as the Jenkins user to scan the local Docker image
                    def trivyCommand = "$trivyPath image $dockerImage"

                    // Run Trivy and capture the exit code
                    def exitCode = sh(script: trivyCommand, returnStatus: true)

                    // Check the exit code and take action as needed
                    if (exitCode == 0) {
                        echo "Trivy scan succeeded for $dockerImage"
                    } else {
                        error "Trivy scan failed for $dockerImage"
                    }
                }
            }
        }
    }

    post {
        always {
            // You can perform post-build actions here
        }
    }
}

