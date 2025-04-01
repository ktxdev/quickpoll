pipeline {
    agent any

    tools {
        jdk 'Default'  // Ensure JDK 17 is configured in Jenkins
        gradle 'Gradle 8.4'  // Ensure Gradle 8 is configured in Jenkins
    }

    environment {
        DOCKER_REGISTRY = 'localhost:5000'
        APP_NAME = 'quickpoll-core-api'
        APP_DIR = 'server/core-api'
        COMPOSE_FILE = "docker-compose.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                dir(env.APP_DIR) {
                    sh './gradlew clean build -x test'
                }
            }
        }


        stage('Docker Build') {
            steps {
                script {
                    dir(env.APP_DIR) {
                        docker.build("${APP_NAME}:${env.BUILD_ID}")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker-compose down || true
                docker-compose up -d --build
                '''
            }
        }
    }

    post {
        always {
            cleanWs()
            script {
                docker.systemPrune()  // Clean unused Docker objects
            }
        }
    }
}