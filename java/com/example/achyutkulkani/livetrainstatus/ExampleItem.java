package com.example.achyutkulkani.livetrainstatus;

public class ExampleItem   {
   // private String mImageUrl;
    private String mCreator;
    private int mLikes;

    public ExampleItem(String creator,int likes){
       //mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
    }

   /* public String getmImageUrl(){
        return mImageUrl;
    }*/

    public String getmCreator(){
        return mCreator;
    }

    public int getmLikesCount(){
        return mLikes;
    }
}
