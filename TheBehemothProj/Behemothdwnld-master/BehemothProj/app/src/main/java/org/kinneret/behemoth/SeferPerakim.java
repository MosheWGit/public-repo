package org.kinneret.behemoth;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SeferPerakim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sefer_perakim);
        Intent intent = getIntent();
        String sefer = intent.getStringExtra("Sefer");
        Integer number = intent.getIntExtra("Number",0);

        Integer perakim = 0;
        switch (sefer) {
            case "Bereishit":
                //50
                perakim = 50;
                break;
            case "Shmot":
                //40
                perakim = 40;
                break;
            case "Vayikrah":
                //27
                perakim = 27;
                break;
                //36
            case "Bamidbar":
                perakim = 36;
                break;
            case "Divarim":
                //34
                perakim = 34;
                break;
            case "Yehoshua":
                perakim= 24;
                break;
            case "Shoftim":
                perakim= 21;
                break;
            case "Shmuel1":
                perakim = 31;
                break;
            case "Shmuel2":
                perakim = 24;
                break;

        }

        createLayoutDynamically(sefer, perakim, number);


    }

    /*
    private void createLayoutDynamically(Integer n, String sefer) {


        //Toast.makeText(SeferPerakim.this, converted, Toast.LENGTH_LONG).show();
        final String seferName = sefer;
        final TextView myText = new TextView(this);

        for (int i = 1; i <= n; i++) {
            Button myButton = new Button(this);
            String converted = convertIntToHeb(i);
            myButton.setText("פרק " + converted);
            myButton.setId(i);
            myButton.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            final int id_ = myButton.getId();

            final LinearLayout layout = (LinearLayout) findViewById(R.id.PerakimList);
            layout.addView(myButton);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Toast.makeText(SeferPerakim.this, "Button clicked index = " + id_, Toast.LENGTH_SHORT).show();
                    if(id_ == 1 && seferName.equals("Bereishit")){
                        //Toast.makeText(SeferPerakim.this, "GOT HERE", Toast.LENGTH_SHORT).show();
                        myText.setText(R.string.Shmot_32);

                        LinearLayout myLayout = new LinearLayout(getApplicationContext());
                        myLayout.addView(myText);
                        setContentView(myLayout);
                        //MAKE A NEW XML FILE for a filled page. Make a new class to to fix going back problem.
                        //Declare it as a nestedscroll view in the xml file to be cool.


                    }


                }
            });
        }

    }*/

    private void createLayoutDynamically(final String s, final Integer n, final Integer number) {


        //Toast.makeText(SeferPerakim.this, converted, Toast.LENGTH_LONG).show();

        for (int i = 1; i <= n; i++) {
            Button myButton = new Button(this);
            String converted = convertIntToHeb(i);
            myButton.setText("פרק " + converted);
            myButton.setId(i);
            myButton.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            final int id = myButton.getId();

            final LinearLayout layout = (LinearLayout) findViewById(R.id.PerakimList);
            layout.addView(myButton);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    getPesukim(s, id, number);

                }
            });
        }


    }

    private void getPesukim(String sefer, int id, Integer num){
        Intent intent  = new Intent(this, pesukimpage.class);
        intent.putExtra("Sefer",sefer);
        intent.putExtra("Perek", id);
        intent.putExtra("number",num);
        startActivity(intent);

    }

    private String convertIntToHeb(Integer n){
        char char1 = (char) 1514;
        char char2 = (char) 1514;
        Integer chardig2 = n % 10;;
        String converted = null;

        if(n < 10){
            char1 = (char) (1487 + n);
        }
        else if(n >= 10 && n < 20){
            char1 = (char) 1497;
            char2 = (char) (1487 + chardig2);

        }
        else if(n >= 20 && n < 30){
            char1 = (char) 1499;
            char2 = (char) (1487 + chardig2);

        }
        else if(n >= 30 && n < 40){
            char1 = (char) 1500;
            char2 = (char) (1487 + chardig2);

        }
        else if(n >= 40 && n < 50){
            char1 = (char) 1502;
            char2 = (char) (1487 + chardig2);

        }
        else if(n >= 50){
            char1 = (char) 1504;
            //char2 = (char) (1487 + chardig2);

        }


        if(chardig2 == 0 || n < 10){
            converted = Character.toString(char1);
        }
        else{
            converted =  Character.toString(char1) + Character.toString(char2);

        }
        //converted =  char2;
       // Log.d("Printable",converted);
        return converted;
    }

}
