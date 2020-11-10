def generateRepo(){
    
    def REPOSITORY = ""

    if (BRANCH_NAME == 'develop'){
        // image repository and tag
        REPOSITORY = "${REGISTRY}/${APP_NAME}-${DEVELOP_ENV}:${IMAGE_TAG}"
    } else if (BRANCH_NAME.contains("release/")){
        // Split branch name and get semantic version
        def delimiterPos = "${BRANCH_NAME}".indexOf('/')
        def releaseVersion = "${BRANCH_NAME}".substring( delimiterPos + 1 ).replace('.','-') 
        // image repository and tag
        REPOSITORY = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}-${releaseVersion}:${IMAGE_TAG}"
    } else if (BRANCH_NAME == 'master'){
        // image repository and tag
        REPOSITORY = "${REGISTRY}/${APP_NAME}-${PROD_ENV}:${IMAGE_TAG}"
    }

    return REPOSITORY
}

return this