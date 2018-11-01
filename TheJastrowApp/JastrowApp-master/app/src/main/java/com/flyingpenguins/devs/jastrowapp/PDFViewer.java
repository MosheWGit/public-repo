package com.flyingpenguins.devs.jastrowapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;




public class PDFViewer extends AppCompatActivity implements SwipeListener{

    private SubsamplingScaleImageView image;
    private ImageSource imageSource;
    private int resId;
    private int pageNumber;

    private GestureDetector gD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);






        if(image == null){
            image = (SubsamplingScaleImageView) findViewById(R.id.pdfView);
        }

        if(savedInstanceState != null){
            pageNumber = savedInstanceState.getInt("pgNum");

        }
        else {
            pageNumber = getPageNumberFromIntent();
        }

        updateImage(pageNumber);

        image.setSwipeListener(this);



    }

    private void updateImage(int pageNumber) {
        image.setImage(ExternalStrorageHandler.getImageSource(pageNumber, this));
    }

    private int getPageNumberFromIntent() {
        Intent i = getIntent();
        return (Integer) i.getExtras().get("Page");

    }



    @Override
    public void onSaveInstanceState(Bundle outState){

        outState.putInt("pgNum", pageNumber);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void OnSwipe(MotionEvent e1, MotionEvent e2, float velX, float velY) {
        float xDirection = e2.getX(0) - e1.getX(0);
        float yDirection = e2.getY(0) - e1.getY(0);
        float angle= yDirection/xDirection;


        if(Math.abs(angle)<Globals.MAXSWIPEANGLE&&Math.abs(velX)>Globals.MINSWIPESPEED) {

            if(xDirection>0){
                if(pageNumber > 1) {
                    updateImage(--pageNumber);
                }
            }else{
                if(pageNumber < 1704) {
                    updateImage(++pageNumber);
                }
            }
        }
    }
}
