
def call(){
    def message = new org.common.Utils()
    
    pipeline {
        agent any

        stages {
            stage('Clean Workspace') {
                steps {
                    script{
                        message.PrintMes("Clean Workspace","cyan")
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
                        sh 'pwd' 
                        sh 'echo current_job: ${JOB_NAME}'                       
                    }
                }
            }

            stage('Build') {
                steps {
                    script {
                        message.PrintMes("Build","blue")                      
                    }
                }
            }

            stage('Test') {
                steps {
                    script {
                        message.PrintMes("Test","magenta")
                        sh 'pwd'                        
                    }
                }
            }

            stage('Deploy') {
                steps {
                    script {
                        message.PrintMes("Deploy","cyan")
                        sh 'pwd'                        
                    }
                }
            }

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