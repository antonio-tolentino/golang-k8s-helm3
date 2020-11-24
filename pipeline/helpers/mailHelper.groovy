    
def sendSuccess() {
    // success templates
    emailTemplate = "${WORKSPACE}/pipeline/email/successful_deploy_body_html.tpl"

    try{
        // email body replace
        echo 'INFO: Replacing success email template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${emailTemplate}"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{DURATION_STRING}/${currentBuild.durationString.replace(' and counting', '')}/g' ${emailTemplate}"
        sh "sed -i 's/{COMPANY_NAME}/${COMPANY_NAME}/g' ${emailTemplate}"
        sh "sed -i 's/{JENKINS_URL}/${JENKINS_URL.replace('/','\\/')}/g' ${emailTemplate}"

        // Load mail template
        mailBody = readFile(file: "${emailTemplate}")

        // Send mail notification
        echo 'INFO: Sending email notification'
        emailext body: "${mailBody}", 
        subject: "Jenkins notification [ Job: ${JOB_NAME.replace('%2F','/')} ] [ Build: ${BUILD_NUMBER} ] [ Status: ${currentBuild.currentResult} ].",
        to: '$DEFAULT_RECIPIENTS'
    } catch(e){
        errorMsg()
    }      

} 

def sendFailure(){

    // failure templates
    emailTemplate = "${WORKSPACE}/pipeline/email/failed_deploy_body_html.tpl"

    try{
        // body replace
        echo 'INFO: Replacing failure email template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${emailTemplate}"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{JENKINS_URL}/${JENKINS_URL.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{COMPANY_NAME}/${COMPANY_NAME}/g' ${emailTemplate}"

        // Load mail template
        mailBody = readFile(file: "${emailTemplate}")

        // Send email notification
        echo 'INFO: Sending email notification'
        emailext body: "${mailBody}", 
        subject: "Jenkins notification [ Job: ${JOB_NAME.replace('%2F','/')} ] [ Build: ${BUILD_NUMBER} ] [ Status: ${currentBuild.currentResult} ].",
        to: emailextrecipients([culprits()])
    } catch(e){
        errorMsg()
    }     

}

def sendApproval(){

    // approval templates
    emailTemplate = "${WORKSPACE}/pipeline/email/approval_body_html.tpl"

    try{
        // body replace
        echo "INFO: Replacing approval email template tokens"
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${emailTemplate}"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${emailTemplate}"
        sh "sed -i 's/{COMPANY_NAME}/${COMPANY_NAME}/g' ${emailTemplate}"
        sh "sed -i 's/{JENKINS_URL}/${JENKINS_URL.replace('/','\\/')}/g' ${emailTemplate}"

        // Load mail template
        mailBody = readFile(file: "${emailTemplate}")

        // Send email notification
        echo "INFO: Sending approval email notification"
        emailext body: "${mailBody}", 
        subject: "Jenkins notification [ Job: ${JOB_NAME.replace('%2F','/')} ] [ Build: ${BUILD_NUMBER} ] is waiting for approval.",
        to: '$DEFAULT_RECIPIENTS'
    } catch(e){
        errorMsg()
    }         
    
}



def errorMsg(){
    echo "WARNING: This something wront with mail variables dependency!"
    echo "If you intend to send mail notification, please verify global environment variables bellow:"
    echo "APP_NAME = \"<APPLICATION-NAME>\""
    echo "COMPANY_NAME = \"<YOUR-COMPANY-NAME>\""
}
return this