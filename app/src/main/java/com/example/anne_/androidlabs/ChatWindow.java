package com.example.anne_.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME="ChatWindow";
    final ArrayList<String> messages=new ArrayList<>();
    private ChatDatabaseHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        final ListView chatView=findViewById(R.id.chatView);
        final EditText editChat=findViewById(R.id.editChat);
        final ChatAdapter messageAdapter=new ChatAdapter(this);

        helper=new ChatDatabaseHelper(getApplicationContext());
        db = helper.getWritableDatabase();
        cursor= db.query(ChatDatabaseHelper.TABLE_NAME,null,null,null,null,
                null,null);
        for (int i=0;i<cursor.getColumnCount();i++){
            System.out.println(cursor.getColumnName(i));
        }
        Log.i(ACTIVITY_NAME,"Cursor's column count=" + cursor.getColumnCount());
        while (cursor.moveToNext()){
            Log.i(ACTIVITY_NAME,"SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

            messages.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

        }
        cursor.close();

        chatView.setAdapter(messageAdapter);
        Button send=findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messages.add(editChat.getText().toString());
                writeDB(editChat.getText().toString());
                messageAdapter.notifyDataSetChanged();
                editChat.setText("");
            }
        });
        }
        private void writeDB(String msg) {
            ContentValues cValue = new ContentValues();
            cValue.put(ChatDatabaseHelper.KEY_MESSAGE, msg);
            long id = helper.getWritableDatabase().
                    insert(ChatDatabaseHelper.TABLE_NAME, null, cValue);
            cursor = helper.getWritableDatabase().query(ChatDatabaseHelper.TABLE_NAME, new String[]
                            {ChatDatabaseHelper.KEY_MESSAGE}, ChatDatabaseHelper.KEY_ID + " = " + id,
                    null, null, null, null);
            cursor.moveToFirst();
            cursor.close();
    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx,0);
        }
        public int getCount(){
            return messages.size();
        }
        public String getItem(int position){
            return messages.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater=ChatWindow.this.getLayoutInflater();
            View result;
            if (position%2==0){
                result=inflater.inflate(R.layout.chat_row_incoming,null);

            }else{
                result=inflater.inflate(R.layout.chat_row_outgoing,null);
            }
            TextView message=result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
        public long getItemId(int position){
            return position;
        }




    }
    public void onDestroy(){
        db.close();
        super.onDestroy();
    }

}

