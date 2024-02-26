package com.lightcore.main;

public class GetDataFrbs {
    private final GetFirebaseInterface getFirebaseInterface;

    public GetDataFrbs(GetFirebaseInterface getFirebaseInterface) {
        this.getFirebaseInterface = getFirebaseInterface;
    }

    public Object[] execute(DataModelDomain dataModelDomain, UserModel userModel){
        return new DataModelDomain[]{getFirebaseInterface.getData(dataModelDomain, userModel)};
    }

}