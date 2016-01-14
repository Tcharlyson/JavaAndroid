package com.tcharlyson.sunshine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tcharlysonplatel on 14/01/2016.
 */
public class WeatherDataParser {
    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */
    public static double getMaxTemperatureForDay(String jsonData, int dayIndex) throws JSONException {
        JSONObject objWeather = new JSONObject(jsonData);
        JSONArray arrDays = objWeather.getJSONArray("list");
        JSONObject objDayInfo = arrDays.getJSONObject(dayIndex);
        JSONObject objTempInfo = objDayInfo.getJSONObject("temp");
        double tempMax = objTempInfo.getDouble("max");

        return tempMax;
    }
}
