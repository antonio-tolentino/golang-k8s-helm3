def defineRegistry(){
    
    def registry

    switch (BRANCH_NAME) {
        case 'develop': 
            registry = registryDevelop(); 
            break;
        case ~/^release\/.*/: 
            //registry = registryRelease(); 
            break;
        case 'master': 
            //registry = registryProd(); 
            break;
        default: 
            registry = 'tst';
    }

    return registry
}

def registryDevelop(){
    return ${DEVELOP_GCP_PROJECT}
}



return this