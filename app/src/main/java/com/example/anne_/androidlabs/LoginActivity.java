package com.example.anne_.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editName=findViewById(R.id.loginName);
        final SharedPreferences share=getSharedPreferences("SavedFile", Context.MODE_PRIVATE);
        String userString=share.getString("DefaultEmail","email@domain.com");
        editName.setText(userString);

        final Button logButton=findViewById(R.id.loginButton);

        logButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             String input = editName.getText().toString();
                                             SharedPreferences.Editor edit = share.edit();
                                             edit.putString("DefaultEmail", input);

                                             edit.putBoolean("Hello", false);
                                             edit.commit();

                                             Intent nextScreen = new Intent(LoginActivity.this, StartActivity.class);
                                             startActivity(nextScreen);

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
}
