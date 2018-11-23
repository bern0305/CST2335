package com.example.anne_.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME="StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(StartActivity.this,ListItemsActivity.class);
                startActivityForResult(next,50);
            }
        });

        Button chatButton=findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next=new Intent(StartActivity.this,ChatWindow.class);
                startActivity(next);
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            }
        });
        Button weatherButton=findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(StartActivity.this,WeatherForecast.class);
                startActivity(next);
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
            }
        });
        Log.i(ACTIVITY_NAME,"In onCreate()");
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");

    }
    @Override
    public void onActivityResult(int requestCode,int responseCode,Intent data){
        if (requestCode==50) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");

            if (responseCode == Activity.RESULT_OK) {
                String messagePassed = data.getStringExtra("Response");
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this, messagePassed, duration);
                toast.show();
            }
        }
    }
}
