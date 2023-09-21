pipeline {
    agent any
    stages {
        stage('Test') {
            agent {
                docker {
                    image 'maven:3.9.4'
                    args '-uroot'
                }
            }
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Build') {
            environment {
                dockerImageName = 'oscarcalvache/devsu-demo'
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
                    docker.withRegistry('', 'docker-credentials') {
                        dockerImage.push(env.BUILD_NUMBER)
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    def buildNumber = env.BUILD_NUMBER
                    sh "make config_deploy BUILD_NUMBER = ${buildNumber}"
                    withKubeConfig([credentialsId: 'kubernetes', contextName: 'docker-desktop']) {
                        sh 'kubectl apply -f k8s/'
                    }
                }
            }
        }
    }
}