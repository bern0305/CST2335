package com.example.anne_.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolBar extends AppCompatActivity {
    String currentMessage="You selected item 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
        Toolbar lab8_toolbar = findViewById(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar);

        Button snackButton=findViewById(R.id.snackButton);
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.snackButton), "Message to show", Snackbar.LENGTH_SHORT).show();

        }});



    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        switch (id) {
            case R.id.action_one:
                Log.d("Toolbar", "Action 1 selected");
                Snackbar.make(findViewById(R.id.action_one),currentMessage,Snackbar.LENGTH_LONG).show();
                break;
            case R.id.action_two:
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolBar.this);
                builder.setTitle(R.string.dialog_title);
                //Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent=new Intent(TestToolBar.this,StartActivity.class);
//                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.action_three:
                builder = new AlertDialog.Builder(TestToolBar.this);
                LayoutInflater inflater=getLayoutInflater();
                final View view=inflater.inflate(R.layout.item3,null);
                builder.setView(view);
                //Add the buttons

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent=new Intent(TestToolBar.this,StartActivity.class);
//                        startActivity(intent);
                        EditText editText=view.findViewById(R.id.editText2);
                        currentMessage=editText.getText().toString();

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                dialog = builder.create();
                dialog.show();

                break;
            case R.id.about:
                Toast.makeText(TestToolBar.this,"Version 1.0 by Mary Anne",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

}
