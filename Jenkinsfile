pipeline {
    agent {label "agent_final_1"}
    stages {
        stage('GIT') {
            steps {
                echo 'github';
                git branch:'main',
                    url: 'https://github.com/NizarBHEsprit/Docker_TP_Achat.git',
                    credentialsId: 'agent_devops_1';
            }
        }
          stage('Sonar') {
              steps {
                  sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=nizar";
              }
          }
          stage('Units Tests') {
              steps {
                  sh "mvn clean test -Ptest";
              }
          }
        stage('Building Package') {
            steps {
                sh "mvn clean package -Pprod";
            }
        }
         stage('Artifact Deployment: Private Nexus Maven Repository'){
            steps{
                nexusArtifactUploader artifacts: [
                    [
                        artifactId: 'tpAchatProject', 
                        classifier: '', 
                        file: 'target/tpAchatProject-1.0.jar', 
                        type: 'jar'
                    ]
                ], 
                credentialsId: 'nexus-credentials', 
                groupId: 'com.esprit.examen', 
                nexusUrl: '192.168.1.42:8081', 
                nexusVersion: 'nexus3', 
                protocol: 'http', 
                repository: 'pipeline-app-registery', 
                version: '1.0'
                
            }
         }

        stage('Pulling Artifact To Nexus Repository'){
            steps{
                
                sh "curl http://192.168.1.42:8081/repository/pipeline-app-registery/com/esprit/examen/tpAchatProject/1.0/tpAchatProject-1.0.jar --output tpAchatProject-1.0.jar";
            }
        }
        stage('Building Docker Image Using Artifact') {
            steps{
                script{
                    dockerImage = docker.build "192.168.1.42:8082/repository/docker-private-registery/tp_achat:latest"
                }
            }
        }

        stage('Pushing Docker Image To Nexus Docker Repo') {
            steps {
                
                script{
                    withDockerRegistry([credentialsId: 'nexus-credentials', url: 'http://192.168.1.42:8082/repository/docker-private-registery/']) {
                        sh "docker push 192.168.1.42:8082/repository/docker-private-registery/tp_achat:latest"
                      
                    }
                }
            }
        }
        
        stage('Running Docker Container From Nexus Docker Repo') {
            steps {
                
                script{
                        sh "docker-compose up -d"                 
                }
            }
        }

    }
}

