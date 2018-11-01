package com.flyingpenguins.devs.jastrowapp;

import android.content.BroadcastReceiver;

import com.google.android.vending.expansion.downloader.impl.DownloaderService;

/**
 * Created by noah on 1/15/18.
 */

public class Downloader extends DownloaderService {
    @Override
    public String getPublicKey(){
        return Globals.PUBLICKEY;
    }

    @Override
    public byte[] getSALT() {
        return new byte[]{3,-8,74,23,-74,100,18,69,106,27,-95,35,94,-10,2,76,34,23,75,38};
    }

    @Override
    public Class<? extends BroadcastReceiver> getAlarmReceiverClass() {
        return Restarter.class;
    }
}
