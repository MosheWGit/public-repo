package org.kinneret.behemoth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class books_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_activity);

        final Button TorahButton = (Button) findViewById(R.id.Torah) ;
        TorahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage(view);
            }
        });

        final Button naviButton = (Button) findViewById(R.id.Neviim);
        naviButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openNavi(view);
            }
        });
    }

    private void openPage(View view){
        Intent myIntent = new Intent(this, SeferPage.class);
        startActivity(myIntent);
    }

    private void openNavi(View view){
        Intent myIntent = new Intent(this, NaviActivity.class);
        startActivity(myIntent);
    }


}
