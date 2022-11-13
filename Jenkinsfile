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
         stage('sonar') {
             steps {
                 sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=nizar";
             }
         }
         stage('unit test') {
             steps {
                 sh "mvn clean test -Ptest";
             }
         }
        stage('ls before build') {
            steps {
                sh "ls";
            }
        }
        stage('build package') {
            steps {
                sh "mvn clean package -Pprod";
            }
        }
        stage('ls after build') {
            steps {
                sh "ls";
            }
          }
         stage('deploy-jar-Nexus'){
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
        //  stage('Pull from nexus') {
        //     steps {
        //         sh "mvn clean install -Pprod";
        //     }
        //   }    

        // stage('Build image') {
        //     steps {
        //         sh "docker build -t tpachat ."
        //     }
        // }

        stage('Pull artifact from Nexus'){
            steps{
                
                sh "curl http://192.168.1.42:8081/repository/pipeline-app-registery/com/esprit/examen/tpAchatProject/1.0/tpAchatProject-1.0.jar --output tpAchatProject-1.0.jar";
            }
        }
        stage('Build docker image') {
            steps{
                script{
                    dockerImage = docker.build "192.168.1.42:8082/repository/docker-private-registery/tp_achat:latest"
                }
            }
        }

        stage('Push docker image to Nexus') {
            steps {
                
                script{
                    withDockerRegistry([credentialsId: 'nexus-credentials', url: 'http://192.168.1.42:8082/repository/docker-private-registery/']) {
                        sh "docker push 192.168.1.42:8082/repository/docker-private-registery/tp_achat:latest"
                    }
                }
            }
        }
        
        stage('Running Docker Image From Nexus') {
            steps {
                
                script{
                        sh "docker run 192.168.1.42:8082/repository/docker-private-registery/tp_achat --name tp_achat_container_from_nexus"                 
                }
            }
        }

//         stage('deploy-Nexus-docker-hosted'){
//             steps{
//                 nexusArtifactUploader artifacts: [
//                     [
//                         artifactId: 'tpAchatProject',
//                         classifier: '',
//                         file: 'target/tpAchatProject-1.0.jar',
//                         type: 'jar'
//                     ]
//                 ],
//                 credentialsId: 'nexus-credentials',
//                 groupId: 'com.esprit.examen',
//                 nexusUrl: '192.168.1.42:8081',
//                 nexusVersion: 'nexus3',
//                 protocol: 'http',
//                 repository: 'pipeline-app-registery',
//                 version: '1.0'
//
//             }
          
//         }

    }
}

