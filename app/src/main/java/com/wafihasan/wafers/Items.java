package com.wafihasan.wafers;

public class Items {
    public String imageUrl;
    public String hiresUrl;
    public int likes;
    public int views;
    public int imageWidth;
    public int imageHeight;
    public String userName;


    public Items(String imageUrl, String hiresUrl, int likes, int views, int imageWidth, int imageHeight, String userName) {
        this.imageUrl = imageUrl;
        this.hiresUrl = hiresUrl;
        this.likes = likes;
        this.views = views;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHiresUrl() {
        return hiresUrl;
    }

    public int getLikes() {
        return likes;
    }

    public int getViews() {
        return views;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getUserName() {
        return userName;
    }
}

