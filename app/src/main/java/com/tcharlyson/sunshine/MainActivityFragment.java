package com.tcharlyson.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        String[] forecastArray = {
                "Today - Raining - 12°/15°",
                "Tues - Cloudy - 09°/10°",
                "Weds - Sunny - 11°/19°",
                "Thurs - Raining - 12°/15°",
                "Fri - Cloudy - 09°/10°",
                "Sat - Sunny - 11°/19°",
                "Sun - Raining - 12°/15°",
                "Today - Raining - 12°/15°",
                "Tues - Cloudy - 09°/10°",
                "Weds - Sunny - 11°/19°",
                "Thurs - Raining - 12°/15°",
                "Fri - Cloudy - 09°/10°",
                "Sat - Sunny - 11°/19°",
                "Sun - Raining - 12°/15°",

        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        mForecastAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekForecast);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_refresh,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_refresh)
        {
            FetchWeatherTask test = new FetchWeatherTask(mForecastAdapter);
            test.execute("Bordeaux");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}