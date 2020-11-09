    
def sendSuccess() {
    // success email template
    emailTemplate = "${WORKSPACE}/pipeline/email/successful_deploy_body_html.tpl"
    slackTemplate = "${WORKSPACE}/pipeline/slack/successful_deploy.md"

    // email body replace
    echo 'INFO: Replacing successful email template tokens'
    sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${emailTemplate}"
    sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${emailTemplate}"
    sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${emailTemplate}"
    sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${emailTemplate}"
    sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${emailTemplate}"
    sh "sed -i 's/{DURATION_STRING}/${currentBuild.durationString.replace(' and counting', '')}/g' ${emailTemplate}"
    sh "sed -i 's/{COMPANY_NAME}/${COMPANY_NAME}/g' ${emailTemplate}"
    sh "sed -i 's/{JENKINS_URL}/${JENKINS_URL.replace('/','\\/')}/g' ${emailTemplate}"

    // send mail notification
    MAIL_BODY = '${FILE, path="pipeline/email/successful_deploy_body_html.tpl"}' 

    echo 'INFO: Sending email notification'
    emailext body: MAIL_BODY, 
    subject: "Jenkins notification [ Job: ${JOB_NAME.replace('%2F','/')} ] [ Build: ${BUILD_NUMBER} ] [ Status: ${currentBuild.currentResult} ].",
    to: '$DEFAULT_RECIPIENTS'



    if (SLACK_CHANNEL != "" && SLACK_CREDENTIAL_ID != "" && SLACK_DOMAIN != ""){
        // Slack template replace
        echo 'INFO: Replacing start build slack template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' ${slackTemplate}"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' ${slackTemplate}"
        sh "sed -i 's/{DURATION_STRING}/${currentBuild.durationString.replace(' and counting', '')}/g' ${slackTemplate}"
        def slackMsg = readFile(file: "${slackTemplate}")
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

    // body replace
    echo 'INFO: Replacing fail email template tokens'
    sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' pipeline/email/failed_deploy_body_html.tpl"
    sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' pipeline/email/failed_deploy_body_html.tpl"
    sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' pipeline/email/failed_deploy_body_html.tpl"
    sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' pipeline/email/failed_deploy_body_html.tpl"
    sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' pipeline/email/failed_deploy_body_html.tpl"
    sh "sed -i 's/{JENKINS_URL}/${JENKINS_URL.replace('/','\\/')}/g' pipeline/email/failed_deploy_body_html.tpl"
    sh "sed -i 's/{COMPANY_NAME}/${COMPANY_NAME}/g' pipeline/email/failed_deploy_body_html.tpl"

    // send mail notification
    MAIL_BODY = '${FILE, path="pipeline/email/failed_deploy_body_html.tpl"}' 

    echo 'INFO: Sending email notification'
    emailext body: MAIL_BODY, 
    subject: "Jenkins notification [ Job: ${JOB_NAME.replace('%2F','/')} ] [ Build: ${BUILD_NUMBER} ] [ Status: ${currentBuild.currentResult} ].",
    to: emailextrecipients([culprits()])

    if (SLACK_CHANNEL != "" && SLACK_CREDENTIAL_ID != "" && SLACK_DOMAIN != ""){
        // Slack template replace
        echo 'INFO: Replacing start build slack template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' pipeline/slack/failed_deploy.md"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' pipeline/slack/failed_deploy.md"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' pipeline/slack/failed_deploy.md"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' pipeline/slack/failed_deploy.md"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' pipeline/slack/failed_deploy.md"
        def slackMsg = readFile(file: 'pipeline/slack/failed_deploy.md')
        //slack notification
        slackSend channel: "${SLACK_CHANNEL}", 
                  tokenCredentialId:"${SLACK_CREDENTIAL_ID}", 
                  teamDomain: "${SLACK_DOMAIN}",
                  botUser: true,
                  color: "danger",
                  message: "${slackMsg}"
    }
}

def sendApproval(){

    // body replace
    echo "INFO: Replacing approval email template tokens"
    sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' pipeline/email/approval_body_html.tpl"
    sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' pipeline/email/approval_body_html.tpl"
    sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' pipeline/email/approval_body_html.tpl"
    sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' pipeline/email/approval_body_html.tpl"
    sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' pipeline/email/approval_body_html.tpl"
    sh "sed -i 's/{COMPANY_NAME}/${COMPANY_NAME}/g' pipeline/email/approval_body_html.tpl"
    sh "sed -i 's/{JENKINS_URL}/${JENKINS_URL.replace('/','\\/')}/g' pipeline/email/approval_body_html.tpl"
                    
    // send mail notification
    MAIL_BODY = '${FILE, path="pipeline/email/approval_body_html.tpl"}' 
    echo "INFO: Sending email notification"
    emailext body: MAIL_BODY, 
    subject: "Jenkins notification [ Job: ${JOB_NAME.replace('%2F','/')} ] [ Build: ${BUILD_NUMBER} ] is waiting for approval.",
    to: '$DEFAULT_RECIPIENTS'

    if (SLACK_CHANNEL != "" && SLACK_CREDENTIAL_ID != "" && SLACK_DOMAIN != ""){
        // Slack template replace
        echo 'INFO: Replacing approval slack template tokens'
        sh "sed -i 's/{APP_NAME}/${APP_NAME}/g' pipeline/slack/approval_deploy.md"
        sh "sed -i 's/{JOB_NAME}/${JOB_NAME.replace('/','\\/').replace('%2F','\\/')}/g' pipeline/slack/approval_deploy.md"
        sh "sed -i 's/{BRANCH_NAME}/${BRANCH_NAME.replace('/','\\/')}/g' pipeline/slack/approval_deploy.md"
        sh "sed -i 's/{BUILD_URL}/${BUILD_URL.replace('/','\\/')}/g' pipeline/slack/approval_deploy.md"
        sh "sed -i 's/{RUN_DISPLAY_URL}/${RUN_DISPLAY_URL.replace('/','\\/')}/g' pipeline/slack/approval_deploy.md"
        def slackMsg = readFile(file: 'pipeline/slack/approval_deploy.md')
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