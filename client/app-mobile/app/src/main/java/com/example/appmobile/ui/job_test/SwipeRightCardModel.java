package com.example.appmobile.ui.job_test;

import androidx.annotation.ColorInt;

public class SwipeRightCardModel{
    @ColorInt
    public Integer backgroundColor;
    public String ans1;
    public String ans2;
    public String question;
    public Integer id;

    public SwipeRightCardModel(Integer backgroundColor,
                               String ans1,
                               String ans2,
                               String question, Integer id) {
        this.backgroundColor = backgroundColor;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.question = question;
        this.id = id;
    }

}
