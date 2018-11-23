package com.example.anne_.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {
    protected static final String ACTIVITY_NAME="WeatherForecastActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        ForecastQuery forecast=new ForecastQuery();
        forecast.execute();



    }
    public class ForecastQuery extends AsyncTask<String,Integer,String> {

        String name,value, speed, min, max, icon;
        Bitmap picture;
        ProgressBar progress=findViewById(R.id.progress);

        @Override
        protected String doInBackground(String... strings) {
            String web="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

            try {
                URL url = new URL(web);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream input = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( input  , "UTF-8");

                while (xpp.getEventType() !=XmlPullParser.END_DOCUMENT){
                    if (xpp.getEventType()==XmlPullParser.START_TAG){
                        name=xpp.getName();
                        if (name.equals("temperature")){
                            value=xpp.getAttributeValue(null,"value");
                            publishProgress(25);
                            min=xpp.getAttributeValue(null, "min" );
                            publishProgress(50);
                            max=xpp.getAttributeValue(null,"max");
                            publishProgress(75);
                        }else if (name.equals("speed")){
                            speed=xpp.getAttributeValue(null,"value");
                        }else if (name.equals("weather")){
                            icon=xpp.getAttributeValue(null,"icon");
                            URL urlIcon=new URL("http://openweathermap.org/img/w/" + icon + ".png");

                            if (fileExistance(icon + ".png")) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(icon + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                picture = BitmapFactory.decodeStream(fis);
                                Log.i(ACTIVITY_NAME, "Image Found Locally");
                            }
                            else {
                                Log.i(ACTIVITY_NAME, "Image Downloaded");

                                picture  = HttpUtils.getImage(urlIcon);
                                FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                                if (picture != null) {
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                }
                                outputStream.flush();
                                outputStream.close();
                            }
                            publishProgress(100);
                        }


                    }
                    xpp.next();

                }}
            catch(Exception e){ }
            return null;

        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        public void onProgressUpdate(Integer...args){ // updates GUI
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(args[0]);
        }

        @Override
        public void onPostExecute (String result){ // thread is finished
            Resources res=getResources();

            TextView current=findViewById(R.id.currentTemp);
            current.setText(String.format(res.getString(R.string.current),value));

            TextView minTemp=findViewById(R.id.minTemp);
            minTemp.setText(String.format(res.getString(R.string.minTemp),min));

            TextView maxTemp=findViewById(R.id.maxTemp);
            maxTemp.setText(String.format(res.getString(R.string.maxTemp),max));

            TextView wind=findViewById(R.id.windSpeed);
            wind.setText(String.format(res.getString(R.string.windSpeed),speed));

            ImageView image=findViewById(R.id.weatherImage);
            image.setImageBitmap(picture);

            progress.setVisibility(View.INVISIBLE);



        }

    }
}
