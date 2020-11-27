def genDeployInfo(){
    
    def ENV_NAME = ""
    def HELM_NAME = ""
    def REPOSITORY = ""
    def NAMESPACE = ""


    if (BRANCH_NAME == 'develop'){
        ENV_NAME = DEVELOP_ENV
        HELM_NAME = "${APP_NAME}-${DEVELOP_ENV}"
        NAMESPACE = DEVELOP_NAMESPACE
        // image repository
        REPOSITORY = "${REGISTRY}/${APP_NAME}-${DEVELOP_ENV}"
    } else if (BRANCH_NAME.contains("release/")){

        if (MULTIPLE_RELEASES) {
            // Split branch name and get semantic version
            def delimiterPos = "${BRANCH_NAME}".indexOf('/')
            def releaseVersion = "${BRANCH_NAME}".substring( delimiterPos + 1 ).replace('.','-') 
            ENV_NAME = RELEASE_ENV
            HELM_NAME = "${APP_NAME}-${RELEASE_ENV}-${releaseVersion}"
            NAMESPACE = RELEASE_NAMESPACE
            // image repository
            REPOSITORY = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}-${releaseVersion}"

        } else {
            
            ENV_NAME = RELEASE_ENV
            HELM_NAME = "${APP_NAME}-${RELEASE_ENV}"
            NAMESPACE = RELEASE_NAMESPACE
            // image repository
            REPOSITORY = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}"
        }

    } else if (BRANCH_NAME == 'master'){
        ENV_NAME = PROD_ENV
        HELM_NAME = "${APP_NAME}-${PROD_ENV}"
        // image repository 
        REPOSITORY = "${REGISTRY}/${APP_NAME}-${PROD_ENV}"
        NAMESPACE = PROD_NAMESPACE
    }



    return [ENV_NAME, HELM_NAME, REPOSITORY, NAMESPACE]
}

return this