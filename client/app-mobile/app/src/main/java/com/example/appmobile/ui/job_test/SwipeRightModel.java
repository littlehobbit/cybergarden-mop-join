package com.example.appmobile.ui.job_test;

import androidx.annotation.ColorInt;

public class SwipeRightModel {
    public SwipeRightModel(SwipeRightCardModel top, SwipeRightCardModel bottom) {
        this.top = top;
        this.bottom = bottom;
    }
    public SwipeRightCardModel top;
    public SwipeRightCardModel bottom;
}