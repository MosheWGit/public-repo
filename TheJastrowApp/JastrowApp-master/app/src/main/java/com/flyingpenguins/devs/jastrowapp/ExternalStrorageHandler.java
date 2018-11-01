package com.flyingpenguins.devs.jastrowapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.google.android.vending.expansion.zipfile.APKExpansionSupport;
import com.google.android.vending.expansion.zipfile.ZipResourceFile;

import java.io.IOException;
import java.io.InputStream;

import static com.flyingpenguins.devs.jastrowapp.Globals.Expansions.MAINVERSION;
import static com.flyingpenguins.devs.jastrowapp.Globals.Expansions.PATCHVERSION;

/**
 * Created by noah on 1/13/18.
 */

public class ExternalStrorageHandler {


    public static ImageSource getImageSource(int page, Context context){
        String pageNumber = IntToString.toStringOfLengthX(page, 4);

        String fileName = "drawable/jastrow" + pageNumber + ".png";



        try {
            ZipResourceFile expansionFile = APKExpansionSupport.getAPKExpansionZipFile(context, MAINVERSION, PATCHVERSION);



            InputStream iS = expansionFile.getInputStream(fileName);
            Bitmap bM = BitmapFactory.decodeStream(iS);
// Get an input stream for a known file inside the expansion file ZIPs
            return ImageSource.bitmap(bM);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }


    }
}
