package com.tcharlyson.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tcharlysonplatel on 06/01/2016.
 */
public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
    final String LOG_TAG = this.getClass().getSimpleName();
    private ArrayAdapter<String> mAdapter;
    public FetchWeatherTask(ArrayAdapter<String> adapter){
        mAdapter = adapter;
    }
    @Override
    protected String[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String format = "json";
        String units = "metric";
        int numDays = 7;
        String forecastJson = null;

        try {
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily";
            final String QUERY_PARAMS = "q";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String APP_ID = "APPID";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAMS, params[0])
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                    .appendQueryParameter(APP_ID,BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "built uri " + builtUri.toString());

            //Create the request to OpenWeatherMap and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            forecastJson = buffer.toString();
            Log.e("Receive Data", forecastJson);
            Log.e("Parsed Data", Double.toString(WeatherDataParser.getMaxTemperatureForDay(forecastJson, 0)));

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }
        WeatherDataParser parser = new WeatherDataParser();
        try {
            return parser.getWeatherDataFromJson(forecastJson, numDays);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String[] results) {
        if(results != null)
        {
            mAdapter.clear();

            for (int i=0; i < results.length; i++)
            for (String dayForecastString : results)
            {
                mAdapter.add(dayForecastString);
            }
        }
    }
}