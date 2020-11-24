def genDeployInfo(){
    
    def envName
    def helmName
    def namespace    
    def repository


    switch (BRANCH_NAME) {
        case 'develop': 
            (envName, helmName, namespace, repository) = genDevelopInfo(); 
            break;
        case ~/^release\/.*/: 
            (envName, helmName, namespace, repository) = genReleaseInfo(); 
            break;
        case 'master': 
            (envName, helmName, namespace, repository) = genProdInfo(); 
            break;
        default: 
            repository = "${REGISTRY}/${APP_NAME}-tst:${IMAGE_TAG}";
    }


    return [envName, helmName, namespace, repository]
}

// Generate Develop deploy info
def genDevelopInfo(){
    def envName
    def helmName
    def namespace    
    def repository


    envName = DEVELOP_ENV
    helmName = "${APP_NAME}-${DEVELOP_ENV}"
    namespace = DEVELOP_NAMESPACE
    repository = "${REGISTRY}/${APP_NAME}-${DEVELOP_ENV}"

    return [envName, helmName, namespace, repository]
}

// Generate Release deploy info
def genReleaseInfo(){
    def envName
    def helmName
    def namespace
    def repository    

    echo "multiple releases = ${MULTIPLE_RELEASES}"

    if (MULTIPLE_RELEASES) {
        // Split branch name and get semantic version
        def delimiterPos = "${BRANCH_NAME}".indexOf('/')
        def releaseVersion = "${BRANCH_NAME}".substring( delimiterPos + 1 ).replace('.','-') 

        envName = RELEASE_ENV
        helmName = "${APP_NAME}-${RELEASE_ENV}-${releaseVersion}"
        namespace = RELEASE_NAMESPACE
        repository = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}-${releaseVersion}"
    } else {
        envName = RELEASE_ENV
        helmName = "${APP_NAME}-${RELEASE_ENV}"
        namespace = RELEASE_NAMESPACE
        repository = "${REGISTRY}/${APP_NAME}-${RELEASE_ENV}"
    }    

    return [envName, helmName, namespace, repository]
}

// Generate Production deploy info
def genProdInfo(){
    def envName
    def helmName
    def namespace
    def repository

    envName = PROD_ENV
    helmName = "${APP_NAME}-${PROD_ENV}"
    namespace = PROD_NAMESPACE    
    repository = "${REGISTRY}/${APP_NAME}-${PROD_ENV}"    

    return [envName, helmName, namespace, repository]
}

return this