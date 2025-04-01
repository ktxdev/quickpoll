pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'localhost:5000'
        APP_NAME = 'quickpoll-core-api'
        APP_DIR = 'server/core-api'

    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/ktxdev/quickpoll.git'
            }
        }

        stage('Build') {
            steps {
                dir(env.APP_DIR) {
                    sh './gradlew clean build -x test'
                }
            }
        }

        stage('Test') {
            steps {
                dir(env.APP_DIR) {
                    sh './gradlew test'
                }
            }
            post {
                always {
                    junit '${env.APP_DIR}/build/test-results/test/**/*.xml'
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