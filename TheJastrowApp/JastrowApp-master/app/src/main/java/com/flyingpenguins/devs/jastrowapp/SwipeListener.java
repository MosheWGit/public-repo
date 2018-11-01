package com.flyingpenguins.devs.jastrowapp;

import android.view.MotionEvent;

/**
 * Created by noah on 1/17/18.
 */

public interface SwipeListener {
    void OnSwipe(MotionEvent e1, MotionEvent e2, float velX, float velY);
}
