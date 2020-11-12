    
def sendSuccess() {
    // success templates
    slackTemplate = "${WORKSPACE}/pipeline/slack/successful_deploy.md"


    if (!SLACK_CHANNEL && !SLACK_CREDENTIAL_ID && !SLACK_DOMAIN){
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
    }else{
        echo "WARNING: Slack properties are empty or null!"
        echo "If you intend to send Slack notification, please define environment variables bellow:"
        echo "SLACK_CHANNEL = \"#<SLACK-CHANNEL-NAME>\""
        echo "SLACK_CREDENTIAL_ID = \"<JENKINS-CREDENTIAL-WITH-SLACK-APP-TOKEN>\""
        echo "SLACK_DOMAIN = \"<YOUR-SLACK-DOMAIN>\""
    }

} 

def sendFailure(){

    // failure templates
    slackTemplate = "${WORKSPACE}/pipeline/slack/failed_deploy.md"

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
    slackTemplate = "${WORKSPACE}/pipeline/slack/approval_deploy.md"

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