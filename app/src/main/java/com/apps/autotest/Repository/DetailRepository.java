package com.apps.autotest.Repository;

public class DetailRepository {
    private static DetailRepository instance;
    public static DetailRepository getInstance(){
        if(instance == null){
            instance = new DetailRepository();
        }
        return instance;
    }
    public String getImageUrl(){
        return "https://i.pinimg.com/originals/83/ae/c9/83aec98c46763cb103b7890ce2ffb7b8.jpg";
    }
}
