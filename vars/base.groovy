
def call(){
    def message = new com.Utils()
    
    pipeline {
        agent any

        stages {
            stage('Clean Workspace') {
                steps {
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

            stage('Hello') {
                steps {
                    script {
                        sh 'pwd'                        
                        message.PrintMes("current_job: ${JOB_NAME}","red")
                    }
                }
            }

        }
        post {
            always {
                script{
	                message.PrintMes("post condition executed: always ...","red")
                }
	        }
	        changed {
                script{
	        	    message.PrintMes( "post condition executed: changed ...","blue")
                }
	        }
	        aborted {
                script{
	        	    message.PrintMes( "post condition executed: aborted ...","red")
                }
	        }
	        failure {
                script{
	        	    message.PrintMes( "post condition executed: failure ...","magenta")
                }
	        }
        }
    }
}