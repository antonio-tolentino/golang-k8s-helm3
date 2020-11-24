def generateRepo(){
    
    def REPOSITORY = ""

    switch (BRANCH_NAME) {
        case 1: 
            REPOSITORY = genDevelop(); 
            break;
        case 2: 
            REPOSITORY = genRelease(); 
            break;
        case : 
            REPOSITORY = genProd(); 
            break;
        default: 
            REPOSITORY = "${REGISTRY}/${APP_NAME}-tst:${IMAGE_TAG}";
    }


    return REPOSITORY
}

//Generate Develop repository
genDevelop(){
    def REPOSITORY = ""

    // image repository and tag
    REPOSITORY = "${REGISTRY}/${APP_NAME}-${DEVELOP_ENV}:${IMAGE_TAG}"

    return REPOSITORY
}

//Generate Release repository
genRelease(){

    def REPOSITORY = ""

    if (MULTIPLE_RELEASES){
        // Split branch name and get semantic version
        def delimiterPos = "${BRANCH_NAME}".indexOf('/')
        def releaseVersion = "${BRANCH_NAME}".substring( delimiterPos + 1 ).replace('.','-') 
        // image repository and tag
        REPOSITORY = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}-${releaseVersion}:${IMAGE_TAG}"
    }else{
        // image repository and tag
        REPOSITORY = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}:${IMAGE_TAG}"
    }

    return REPOSITORY
}

// Generate Production repository
genProd(){
    def REPOSITORY = ""

    // image repository and tag
    REPOSITORY = "${REGISTRY}/${APP_NAME}-${PROD_ENV}:${IMAGE_TAG}"

    return REPOSITORY
}

return this