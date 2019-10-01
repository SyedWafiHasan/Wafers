package com.wafihasan.wafers;

public class Items
{
    public String imageUrl;
    public String hiresUrl;

    public Items(String imageUrl, String hiresUrl)
    {
        this.imageUrl = imageUrl;
        this.hiresUrl = hiresUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getHiresUrl()
    {
        return hiresUrl;
    }
}
