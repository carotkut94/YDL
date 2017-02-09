package com.death.ydl.utils;

import android.net.Uri;
import android.util.Log;

/**
 * Created by rajora_sd on 2/8/2017.
 */

public class Utility{


    public static String getUrl(String query)
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("youtube0973.herokuapp.com")
                .appendPath("api")
                .appendPath("info")
                .appendQueryParameter("url", query)
                .appendQueryParameter("flatten", "False");
        String myUrl = builder.build().toString();
        Log.e("LINK", myUrl);
        return  myUrl;
    }



    public static String formatString(String text) {

        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }
        return json.toString();
    }

}