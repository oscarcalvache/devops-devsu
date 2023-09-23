pipeline {
    agent any
    environment{
        def devsuDatasorceUrl = credentials('devsu-datasource-url')
        def devsuDbUser = credentials('devsu-db-user')
        def devsuDbPassword = credentials('devsu-db-password')
    }
    stages {
        stage('Test') {
            steps {
                script {
                    sh 'make build_config DEVSU_DATASOURCE_URL="$devsuDatasourceUrl" DEVSU_DB_USER="$devsuDbUser" DEVSU_DB_PASSWORD="$devsuDbPassword"'
                    sh 'mvn clean test'
                }
            }
        }
        stage('Static code Analysis & Coverage') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
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
                    snykTokenId: 'snyk-token',
                    severity: 'critical'
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
                    sh 'cat src/main/resources/application.properties'
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

