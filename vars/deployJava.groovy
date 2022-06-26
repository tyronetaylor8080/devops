import java.text.SimpleDateFormat

def call(){

    pipeline {
        agent any

        stages {
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