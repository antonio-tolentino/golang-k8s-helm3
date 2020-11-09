    
def sendSuccess() {
    // success templates
    emailTemplate = "${WORKSPACE}/pipeline/email/successful_deploy_body_html.tpl"
    slackTemplate = "${WORKSPACE}/pipeline/slack/successful_deploy.md"

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



    if (SLACK_CHANNEL != "" && SLACK_CREDENTIAL_ID != "" && SLACK_DOMAIN != ""){
        // Slack template replace
        echo 'INFO: Replacing success slack template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${slackTemplate}"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{DURATION_STRING}/${currentBuild.durationString.replace(' and counting', '')}/g' ${slackTemplate}"
        
        // Load Slack template
        slackMsg = readFile(file: "${slackTemplate}")

        //slack notification
        slackSend channel: "${SLACK_CHANNEL}", 
                  tokenCredentialId:"${SLACK_CREDENTIAL_ID}", 
                  teamDomain: "${SLACK_DOMAIN}",
                  botUser: true,
                  color: "good",
                  message: "${slackMsg}"
    }

} 


def sendFailure(){

    // failure templates
    emailTemplate = "${WORKSPACE}/pipeline/email/failed_deploy_body_html.tpl"
    slackTemplate = "${WORKSPACE}/pipeline/slack/failed_deploy.md"

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



    if (SLACK_CHANNEL != "" && SLACK_CREDENTIAL_ID != "" && SLACK_DOMAIN != ""){
        // Slack template replace
        echo 'INFO: Replacing failure slack template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${slackTemplate}"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${slackTemplate}"

        // Load Slack template
        slackMsg = readFile(file: "${slackTemplate}")

        // Slack notification
        slackSend channel: "${SLACK_CHANNEL}", 
                  tokenCredentialId:"${SLACK_CREDENTIAL_ID}", 
                  teamDomain: "${SLACK_DOMAIN}",
                  botUser: true,
                  color: "danger",
                  message: "${slackMsg}"
    }
}

def sendApproval(){

    // approval templates
    emailTemplate = "${WORKSPACE}/pipeline/email/approval_body_html.tpl"
    slackTemplate = "${WORKSPACE}/pipeline/slack/approval_deploy.md"

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
    echo "INFO: Sending email notification"
    emailext body: "${mailBody}", 
    subject: "Jenkins notification [ Job: ${JOB_NAME.replace('%2F','/')} ] [ Build: ${BUILD_NUMBER} ] is waiting for approval.",
    to: '$DEFAULT_RECIPIENTS'



    if (SLACK_CHANNEL != "" && SLACK_CREDENTIAL_ID != "" && SLACK_DOMAIN != ""){
        // Slack template replace
        echo 'INFO: Replacing approval slack template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${slackTemplate}"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${slackTemplate}"

        // Load slack template    
        slackMsg = readFile(file: "${slackTemplate}")

        //slack notification
        slackSend channel: "${SLACK_CHANNEL}", 
                  tokenCredentialId:"${SLACK_CREDENTIAL_ID}", 
                  teamDomain: "${SLACK_DOMAIN}",
                  botUser: true,
                  color: "warning",
                  message: "${slackMsg}"
    }

    
}
return this