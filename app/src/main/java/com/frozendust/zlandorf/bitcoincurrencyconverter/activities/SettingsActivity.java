package com.frozendust.zlandorf.bitcoincurrencyconverter.activities;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.frozendust.zlandorf.bitcoincurrencyconverter.R;
import com.frozendust.zlandorf.bitcoincurrencyconverter.models.entities.Currency;
import com.frozendust.zlandorf.bitcoincurrencyconverter.models.entities.Pair;
import com.google.common.collect.Lists;

import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceFragment prefsFragment = new PrefsFragment();
        prefsFragment.setArguments(getIntent().getExtras());
        getFragmentManager()
            .beginTransaction()
            .replace(android.R.id.content, prefsFragment)
            .commit();
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                // This is what the sample code did, however this method doesn't restore the activity's state for some reason
              // NavUtils.navigateUpFromSameTask(this);
                finish();
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            ListPreference preferredPairPreference = (ListPreference) findPreference(getString(R.string.pref_exchange_pair_key));

            if (preferredPairPreference != null) {
                List<Pair> pairs = getArguments().getParcelableArrayList(HomeActivity.AVAILABLE_PAIRS_EXTRA);
                if (pairs == null) {
                    pairs = Lists.newArrayList();
                }
                if (pairs.isEmpty()) {
                    // By default, the preferred pair is BTC/EUR
                    pairs.add(new Pair(Currency.BTC, Currency.EUR));
                }

                CharSequence[] entries = new String[pairs.size()];
                CharSequence[] entryValues = new String[pairs.size()];
                int i = 0;
                for (Pair pair : pairs) {
                    entries[i] = pair.toString();
                    entryValues[i] = pair.toString();
                    i++;
                }
                preferredPairPreference.setEntries(entries);
                preferredPairPreference.setEntryValues(entryValues);
            }
        }
    }

}
