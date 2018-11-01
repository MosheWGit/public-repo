package com.flyingpenguins.devs.jastrowapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.flyingpenguins.app.*;
import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderClient;
import com.google.android.vending.expansion.downloader.IDownloaderService;
import com.google.android.vending.expansion.downloader.IStub;
import com.google.android.vending.expansion.downloader.impl.DownloadInfo;
import com.google.android.vending.expansion.downloader.impl.DownloadsDB;
import com.google.android.vending.expansion.zipfile.APKExpansionSupport;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements IDownloaderClient{

    private HebrewCheck hebrewCheck;
    private IDownloaderService mRemoteService;
    private BSForJPDF pageFinder;
    private boolean eternalsMemoryOK;
    private IStub mDownloaderClientStub;
    Button button;
    EditText text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DownloadInfo[] ds =DownloadsDB.getDB(this).getDownloads();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!expansionFilesDelivered()) {
            eternalsMemoryOK = false;

            startDownload();
        }
        else eternalsMemoryOK = true;




        getPage("גע"); //this warms up the page finder

        setUpButton();
    }

    void startDownload() {
        Intent notifierIntent = new Intent(this, MainActivity.class);
        notifierIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notifierIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int startResult = DownloaderClientMarshaller.LVL_CHECK_REQUIRED;
        try {
            // Start the download service (if required)
            startResult =
                    DownloaderClientMarshaller.startDownloadServiceIfRequired(this,
                            pendingIntent, Downloader.class);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // If download has started, initialize this activity to show
        // download progress
        if (startResult != DownloaderClientMarshaller.NO_DOWNLOAD_REQUIRED) {
            // This is where you do set up to display the download
            // progress (next step)

            mDownloaderClientStub = DownloaderClientMarshaller.CreateStub(this, Downloader.class);


        }
    }

    boolean expansionFilesDelivered() {

        String fileName = Helpers.getExpansionAPKFileName(this, true,
                Globals.Expansions.MAINVERSION);
        if (!Helpers.doesFileExist(this, fileName, Globals.Expansions.SIZE,false))
            return false;

        return true;
    }

    private void setUpButton(){
        if(button == null) {
            button = (Button) findViewById(R.id.searchButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    if(text == null){
                        text = (EditText) findViewById(R.id.searchBox);
                    }
                    Editable t = text.getText();
                    if(t.toString().isEmpty()){
                        //do nothing
                    }
                    else {
                        presentPDF(text.getText().toString());
                    }
                }
            });
        }

    }

    /**
     *
     * @param text
     * @return the page number
     */
    private int getPage(String text){
        //TODO also make sure its not empty//TODO page 134 and 133 are misaligned
        if(isHebrew(text)){
            if(pageFinder == null){
                pageFinder = new BSForJPDF();
            }
            return pageFinder.search(text);
        }
        else {


            return -1;
        }
    }

    /**
     *
     * @param text the text to be checked
     * @return true if the stirng only contains hebrew characters
     */
    public boolean isHebrew(String text){

        if(hebrewCheck == null){
            hebrewCheck = new HebrewCheck();
        }
        return hebrewCheck.isBasicHebrewCharacters(text);

    }

    private void presentPDF(String keyword){
        int page = getPage(keyword);
        if(!eternalsMemoryOK){
            Toast t = new Toast(this);
            t.makeText(this, "Waiting for downloads", Toast.LENGTH_SHORT).show();
        }
        else if(page == -1){
            Toast t = new Toast(this);
            t.makeText(this, "Hebrew characters only", Toast.LENGTH_SHORT).show();
            text.setText("");
        }
        else {
            Intent intent = new Intent(this, PDFViewer.class);
            intent.putExtra("Page", page);
            startActivity(intent);
        }

    }

    @Override
    public void onServiceConnected(Messenger m) {

        mRemoteService = DownloaderServiceMarshaller.CreateProxy(m);
        mRemoteService.onClientUpdated(mDownloaderClientStub.getMessenger());
    }

    @Override
    public void onDownloadStateChanged(int i) {
        if(i == IDownloaderClient.STATE_COMPLETED){
            eternalsMemoryOK = true;
        }
    }

    @Override
    public void onDownloadProgress(DownloadProgressInfo downloadProgressInfo) {


    }

    @Override
    protected void onResume() {
        eternalsMemoryOK = expansionFilesDelivered();
        if(!eternalsMemoryOK) startDownload();
        if (null != mDownloaderClientStub) {
            mDownloaderClientStub.connect(this);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (null != mDownloaderClientStub) {
            mDownloaderClientStub.disconnect(this);
        }
        super.onStop();
    }

    /*openPDF(int page){

    }*/
}
