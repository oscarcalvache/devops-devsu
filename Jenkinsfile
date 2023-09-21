pipeline {
    agent any
    stages {
        stage('Test') {
            agent {
                docker {
                    image 'maven:3.9.4'
                }
            }
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Build') {
            environment {
                dockerImageName = 'devsu-project'
                dockerImage = ''
            }
            steps {
                script {
                    dockerImage = docker.build dockerImageName
                }
            }
        }
        stage('Push') {
            steps {
                script {
                    docker.withRegistry('', docker-credentials) {
                        dockerImage.push(env.BUILD_NUMBER)
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    sh "make config_deploy"
                    withKubeConfig([credentialsId: 'kubernetes', contextName: 'docker-desktop']) {
                        sh 'kubectl config get-contexts'
                        sh 'kubectl get all -A'
                    }
                }
            }
        }
    }
}