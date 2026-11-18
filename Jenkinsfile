pipeline {
  agent any

  parameters {
    string(name: 'DOCKER_IMAGE', defaultValue: 'bank-api', description: 'Image repository')
    string(name: 'DOCKER_REGISTRY_HOST', defaultValue: 'docker.io', description: 'Registry host')
    booleanParam(name: 'DOCKER_REGISTRY_PUSH', defaultValue: false, description: 'Push images to the registry')
  }

  environment {
    MAVEN_ARGS = '-B -ntp'
  }

  options {
    timestamps()
    disableConcurrentBuilds()
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Test') {
      steps {
        sh './mvnw ${MAVEN_ARGS} test'
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
      }
    }

    stage('Package') {
      steps {
        sh './mvnw ${MAVEN_ARGS} -DskipTests package'
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} -t ${DOCKER_IMAGE}:latest .'
      }
    }

    stage('Registry Push') {
      when {
        allOf {
          branch 'main'
          expression {
            return env.DOCKER_REGISTRY_PUSH == 'true'
          }
        }
      }
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'docker-registry-credentials',
          usernameVariable: 'REGISTRY_USERNAME',
          passwordVariable: 'REGISTRY_PASSWORD'
        )]) {
          sh 'echo "$REGISTRY_PASSWORD" | docker login -u "$REGISTRY_USERNAME" --password-stdin ${DOCKER_REGISTRY_HOST}'
          sh 'docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}'
          sh 'docker push ${DOCKER_IMAGE}:latest'
        }
      }
    }
  }

  post {
    cleanup {
      sh 'docker image rm -f ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest >/dev/null 2>&1 || true'
    }
  }
}
