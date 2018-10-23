package com.example.anne_.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ChatDatabaseHelper";
    private static String DATABASE_NAME="Messages.db";
    private static int VERSION_NUM=6;
    final static String TABLE_NAME="Conversation";
    final static String KEY_ID="_id";
    final static String KEY_MESSAGE="Message";
    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE "+ TABLE_NAME+" ("+KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_MESSAGE +"text);");
        Log.i(TAG, "Calling onCreate");

        db.execSQL( "CREATE TABLE " + TABLE_NAME + " ( " +  KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " text);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(TAG,"Calling onUpgrade, oldVersion=" + oldVersion+"newVersion="+newVersion);
    }

    public void onDownGrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(TAG,"Calling onDowngrade, oldVersion=" + oldVersion+"newVersion="+newVersion);
    }
}
