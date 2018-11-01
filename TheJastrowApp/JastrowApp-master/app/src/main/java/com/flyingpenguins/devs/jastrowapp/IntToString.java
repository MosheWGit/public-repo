package com.flyingpenguins.devs.jastrowapp;

/**
 * Created by noah on 12/13/17.
 */

public class IntToString {

    public static String toStringOfLengthX(int number, int X){
        String toReturn = "" + number;
        String zeros = "";
        for(int i = toReturn.length(); i < X; i++){
            zeros += "0";
        }
        toReturn = zeros + toReturn;

        return toReturn;
    }
}
