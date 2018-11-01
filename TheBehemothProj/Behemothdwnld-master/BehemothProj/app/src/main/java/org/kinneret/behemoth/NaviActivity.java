package org.kinneret.behemoth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NaviActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);



    }

    public void buttonClicked(View view){
        switch(view.getId()){
            case R.id.Yehoshua:
                OpenSefer(view, "Yehoshua");
                break;
            case R.id.Shoftim:
                OpenSefer(view, "Shoftim");
                break;
            case R.id.Shmuel1:
                OpenSefer(view, "Shmuel1");
                break;
            case R.id.Shmuel2:
                OpenSefer(view, "Shmuel2");
                break;
        }
    }


    private void OpenSefer(View view, String sefer){

        Intent intent = new Intent(this, SeferPerakim.class);
        intent.putExtra("Sefer",sefer);
        startActivity(intent);

    }
}
