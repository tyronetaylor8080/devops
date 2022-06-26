import java.text.SimpleDateFormat

def call(){
    def message = new org.common.Utils()
    def repoUrl = "myharbor.com"
    def dateFormat = new SimpleDateFormat("yyyyMMddHHmm")
    def DOCKER_TAG = dateFormat.format(new Date())

    pipeline {
        agent any

        stages {
            stage('Clean Workspace') {
                steps {
                    script{
                        message.PrintMes("Clean Workspace","red")
                    }
                    cleanWs(
                        cleanWhenAborted: true, 
                        cleanWhenFailure: true, 
                        cleanWhenNotBuilt: true, 
                        cleanWhenSuccess: true, 
                        cleanWhenUnstable: true, 
                        cleanupMatrixParent: true, 
                        disableDeferredWipeout: true,
                        deleteDirs: true
                    )
                }
            }

            stage('CheckOut SCM') {
                steps {
                    script {
                        message.PrintMes("CheckOut SCM","cyan")                     
                        checkout scm
                        checkout([$class: 'GitSCM', branches: [[name: '${Branch}']], 
                        doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], 
                        userRemoteConfigs: [[credentialsId: '14397715-8a5b-4bad-9b94-c08804d0a979', 
                        url: '${gitRepo}']]])
                    }
                }
            }

            stage('Build') {
                steps {
                    script {
                        message.PrintMes("Build","blue")   
                        sh '''
                        mvn  clean package -Dmaven.test.skip=true
cat <<EOF> target/Dockerfile
FROM myharbor.com/base/java8:latest
COPY ${ProjectName}-1.0-SNAPSHOT.jar ${ProjectName}-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/${ProjectName}-1.0-SNAPSHOT.jar"]
EOF
                        '''
                        withDockerRegistry(credentialsId: "a87302b3-ad48-450c-bd77-cac799f4dc28", url: "http://${repoUrl}") {
                            def imageUrl = "${repoUrl}/${ProjectName}/${ProjectName}:${DOCKER_TAG}"
                            docker.build("${imageUrl}","-f target/Dockerfile target/")
                            sh """
                                docker push "${imageUrl}"
                                docker rmi "${imageUrl}"   
                            """            
                        }
                        
                    }
                }
            }

            // stage('Test') {
            //     steps {
            //         script {
            //             message.PrintMes("Test","magenta")
            //             sh 'pwd'                        
            //         }
            //     }
            // }
        }

        post {
            always {
                script{
	                message.PrintMes("post condition executed: always ","blue")
                }
	        }
	        success {
                script{
	        	    message.PrintMes( "post condition executed: success","green")
                }
	        }
	        aborted {
                script{
	        	    message.PrintMes( "post condition executed: aborted","yellow")
                }
	        }
	        failure {
                script{
	        	    message.PrintMes( "post condition executed: failure","red")
                }
	        }
        }
    }
}