def generateRepo(){
    
    def repository

    switch (BRANCH_NAME) {
        case 'develop': 
            repository = genDevelop(); 
            break;
        case ~/^release\/.*/: 
            repository = genRelease(); 
            break;
        case 'master': 
            repository = genProd(); 
            break;
        default: 
            repository = "${REGISTRY}/${APP_NAME}-tst:${IMAGE_TAG}";
    }

    return repository
}

//Generate Develop repository
def genDevelop(){
    def repository

    // image repository and tag
    repository = "${REGISTRY}/${APP_NAME}-${DEVELOP_ENV}:${IMAGE_TAG}"

    return repository
}

//Generate Release repository
def genRelease(){

    def repository

    echo "multiple releases = ${MULTIPLE_RELEASES}"    

    if (MULTIPLE_RELEASES){
        // Split branch name and get semantic version
        def delimiterPos = "${BRANCH_NAME}".indexOf('/')
        def releaseVersion = "${BRANCH_NAME}".substring( delimiterPos + 1 ).replace('.','-') 
        // image repository and tag
        repository = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}-${releaseVersion}:${IMAGE_TAG}"
    }else{
        // image repository and tag
        repository = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}:${IMAGE_TAG}"
    }

    return repository
}

// Generate Production repository
def genProd(){
    def repository

    // image repository and tag
    REPOSITORY = "${REGISTRY}/${APP_NAME}-${PROD_ENV}:${IMAGE_TAG}"

    return repository
}

return this