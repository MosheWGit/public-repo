package org.kinneret.behemoth;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class pesukimpage extends AppCompatActivity {


    private TextView pesukim;
    LinearLayout myLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesukimpage);

        Intent intent = getIntent();

        String sefer = intent.getStringExtra("Sefer");
        Integer perek = intent.getIntExtra("Perek",0);
        Integer number = intent.getIntExtra("number",0);


        LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_layout);
        //pesukim = (TextView) findViewById(R.id.pesukim);
         pesukim = new TextView(this);

        if(sefer.equals("Shmot") && (perek == 32 || perek ==34)){
            //THis is the code to eventually add a listen button
            /*final Button button = (Button) findViewById(R.id.perek_sound);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                   playSound();

                }
            });

            /*try{
                StringBuilder buf = new StringBuilder();
                InputStream json = getAssets().open("Shmot32.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
                String str;

                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }

                in.close();
                pesukim.setText(buf.toString());
            }
            catch(IOException e){

            }*/

           /* String text2 =getString( R.string.Shmot_32);

            Spannable spannable = new SpannableString(text2);

            spannable.setSpan(new ForegroundColorSpan(Color.BLUE), text2.length(), (text2).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            pesukim.setText(spannable, TextView.BufferType.SPANNABLE);*/
            String myText = getString(R.string.Shmot_32);
            if(perek == 34){

                myText = getString(R.string.Shmot_34);
            }

            String [] translatedText = myText.split(":");
            int pasukCount = 1;
            InputStream is = null;
            try{
                is = getAssets().open("shmotheb.html");
                Document doc = Jsoup.parse(is,"UTF-8","https://www.example.com");
                Elements perakim = doc.getElementsByTag("H2");
                Element myPerek = perakim.get(31);
                if(perek == 34){
                    myPerek = perakim.get(33);
                }
                Element table = myPerek.nextElementSibling();
                Elements pesukimHebrew = table.getElementsByClass("h");
                for(Element pasuk : pesukimHebrew){
                   // builder.append(pasuk.text()).append("\n\n");
                    TextView newView = new TextView(this);
                    TextView newViewTow = new TextView(this);
                    String tempString = translatedText[pasukCount] + "\n";

                    pasukCount+=2;
                    String tempTwo = pasuk.text() + "\n";
                //    String tempTwo = translatedText[pasukCount++] + "\n";
                   // String tempString = translatedText[pasukCount++] + "\n";
                    newView.setText(tempTwo);
                    newViewTow.setText(tempString);
                    newView.setTextColor(getResources().getColor(R.color.blue));
                    newViewTow.setTextColor(getResources().getColor(R.color.black));
                    myLayout.addView(newView);
                    myLayout.addView(newViewTow);
                }

            }catch(Exception e){
                System.out.println(e);
            }



            //pesukim.setText(R.string.Shmot_32);

        }
        else{
            setUpPage(sefer, perek, number);
        }



    }

    private void setUpPage(final String sefer, final Integer perekid, final Integer num){
        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_layout);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder  = new StringBuilder();
                String perekFinder = "Chapter " + perekid;
                String tempUri = "https://www.mechon-mamre.org/p/pt/pt0" + num + ".htm";
                URI machonSefer =  URI.create(tempUri);
                InputStream is = null;

                try{
                    Log.d("Pesukim","reached");
                    /*Document doc  = Jsoup.connect(tempUri).get();*/
                    if(sefer.equals("Bereishit")){
                        is = getAssets().open("bereishitheb.html");
                    }else if(sefer.equals("Shmot")) {
                        is = getAssets().open("shmotheb.html");
                    }else if(sefer.equals("Vayikrah")){
                        is = getAssets().open("vayikraheb.html");
                    }else if( sefer.equals("Bamidbar")){
                        is = getAssets().open("bamidbarheb.html");
                    }else if (sefer.equals(("Divarim"))){
                        is = getAssets().open("devarimheb.html");
                    }

                    else{
                        is = getAssets().open("bereishitheb.html");
                    }
                    Document doc = Jsoup.parse(is,"UTF-8","https://www.example.com");
                    Elements perakim = doc.getElementsByTag("H2");
                    Element perek = perakim.get(perekid - 1);
                    Element table = perek.nextElementSibling();
                    Elements pesukimHebrew = table.getElementsByClass("h");
                    for(Element pasuk : pesukimHebrew){
                        builder.append(pasuk.text()).append("\n\n");
                    }
                    /*for(Element perek: perakim){
                        String current = perek.text();
                        if(current.equals(perekFinder)){
                            Element table = perek.nextElementSibling();
                            Elements pesukimHebrew = table.getElementsByClass("h");
                            for(Element pasuk : pesukimHebrew){
                                builder.append(pasuk.text()).append("\n");
                            }
                        }
                    }*/

                }catch(Exception e){
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pesukim.setText(builder.toString());
                        myLayout.addView(pesukim);
                    }
                });
            }

        }).start();
    }

    public void playSound(){

    }



}
