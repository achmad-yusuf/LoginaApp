package com.example.achmadyusuf.loginapp;

public class SpinnerLoadingPlace {
    public String id;
    public String place;

    public SpinnerLoadingPlace(String id,String place){
            this.id=id;
            this.place=place;

    }
    @Override
    public String toString(){
        return place;
    }

}
