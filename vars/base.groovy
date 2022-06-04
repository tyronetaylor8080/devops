
def call(){
    def utils = new com.Utils()
    
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
                        sh 'echo ${JOB_NAME},${JOB_BASE_NAME}'
                        utils.PrintMes()

                    }
                }
            }
        }
    }
}