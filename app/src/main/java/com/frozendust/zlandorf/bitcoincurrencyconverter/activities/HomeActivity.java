package com.frozendust.zlandorf.bitcoincurrencyconverter.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.frozendust.zlandorf.bitcoincurrencyconverter.R;
import com.frozendust.zlandorf.bitcoincurrencyconverter.fragments.ConverterFragment;
import com.frozendust.zlandorf.bitcoincurrencyconverter.fragments.RateListFragment;
import com.frozendust.zlandorf.bitcoincurrencyconverter.fragments.RatesTaskFragment;
import com.frozendust.zlandorf.bitcoincurrencyconverter.models.entities.Rate;
import com.frozendust.zlandorf.bitcoincurrencyconverter.tasks.RetrieveTask;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements ConverterFragment.OnFragmentInteractionListener, RetrieveTask.RetrieveTaskListener, RateListFragment.RateListListener {
    private static final String RATES_TASK_FRAGMENT = "rates_task_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fm = getSupportFragmentManager();
        RatesTaskFragment ratesTaskFragment = (RatesTaskFragment) fm.findFragmentByTag(RATES_TASK_FRAGMENT);
        if (ratesTaskFragment == null) {
            ratesTaskFragment = RatesTaskFragment.newInstance();
            fm.beginTransaction().add(ratesTaskFragment, RATES_TASK_FRAGMENT).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // Do nothing at the moment
    }

    @Override
    public void onTaskFinished(List<Rate> rates) {
        FragmentManager fm = getSupportFragmentManager();
        ConverterFragment converterFragment = (ConverterFragment) fm.findFragmentById(R.id.fragment_converter);
        if (converterFragment != null) {
            converterFragment.onRatesRetrieved(rates);
        }
    }

    @Override
    public void onRateSelected(Rate rate) {
        // TODO !
    }
}
