pipeline{
    environment {
        registry = "markuvinicius/sirius-telegram-bot"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }

    agent any

    tools {
        maven 'maven'
        jdk 'jdk8'
    }

    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Checkout'){
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage ('Test') {
            steps {
                echo 'Testing'
                sh '''
                        mvn clean test
                    '''
            }
        }
/*

        stage('SonarQube analysis') {
            steps{
                    sh '''
                            mvn sonar:sonar \
                             -DskipTests=true \
                             -Dsonar.host.url=http://localhost:9000 \
                             -Dsonar.projectKey=techdog-rest-api \
                             -Dsonar.login=0daca2dd926081f3a2731a9d44cbc81bd708007d \
                             -Dsonar.exclusions=src/test/*.java,src/main/java/com/techdog/configuration/*.java,src/main/java/com/techdog/constants/*.java,src/main/java/com/techdog/entity/*.java,src/main/java/com/techdog/exception/*.java
                       '''
            }
        }
*/

        stage ('Build Application') {
            steps {
                echo 'BUILDING PROJECT'

                sh '''
                    mvn clean install
                '''
            }
        }

        stage('Build Docker image') {
            steps{
              script {
                dockerImage = docker.build registry + ":$BUILD_NUMBER"
              }
            }
        }

        stage('Push Docker Image') {
            steps{
              echo 'PUSHING DOCKER IMAGE TO REPO'
              script {
                docker.withRegistry( '', registryCredential ) {
                            dockerImage.push("$BUILD_NUMBER")
                            dockerImage.push('latest')
                }
              }
            }
        }

    }
}
