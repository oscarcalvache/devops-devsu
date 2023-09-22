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
        stage('Static code Analysis & Coverage') {
            steps {
                withSonarQubeEnv('Sonar') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                }
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Vulnerability Scan'){
            steps {
                snykSecurity(
                    snykInstallation: 'snyk',
                    snykTokenId: 'snyk-token'
                    )
            }
        }
        stage('Build Image') {
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
                    sh "make config_deploy BUILD_NUMBER=${buildNumber}"
                    withKubeConfig([credentialsId: 'kubernetes', contextName: 'docker-desktop']) {
                        sh 'kubectl apply -f k8s/'
                    }
                }
            }
        }
    }
}

