package com.example.saveit;

public class ChallengeItem {
    private int image;
    private String title;
    private String desc;
    private String savings;
    public ChallengeItem(int img, String text1, String text2, String text3) {
        image = img;
        title = text1;
        desc = text2;
        savings = text3;
    }
    public int getImageResource() {
        return image;
    }
    public String getText1() {
        return title;
    }
    public String getText2() {
        return desc;
    }
    public String getText3() {
        return savings;
    }
}
