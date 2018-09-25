package com.example.anne_.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME="ListItemsActivity";
    final int REQUEST_IMAGE_CAPTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        ImageButton image=findViewById(R.id.imageButton);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

        Switch switch1=findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CharSequence text="Switch is On";
                    int duration=Toast.LENGTH_SHORT;
                    Toast toast=Toast.makeText(ListItemsActivity.this, text,duration);
                    toast.show();
                }
                else{
                    CharSequence text="Switch is Off";
                    int duration=Toast.LENGTH_LONG;
                    Toast toast=Toast.makeText(ListItemsActivity.this, text,duration);
                    toast.show();
                }
            }


        });
        CheckBox check=findViewById(R.id.checkBox2);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int id){
                                Intent resultIntent= new Intent();
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).show();
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
    private void dispatchTakePictureIntent(){
        Intent takePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePicture,REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            ImageButton image=findViewById(R.id.imageButton);
            Bundle extras =data.getExtras();
            Bitmap imageBitMap=(Bitmap) extras.get("data");
            image.setImageBitmap(imageBitMap);
        }
    }
}
