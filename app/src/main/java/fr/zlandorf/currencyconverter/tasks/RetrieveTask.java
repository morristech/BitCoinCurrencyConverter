package fr.zlandorf.currencyconverter.tasks;

import android.os.AsyncTask;
import android.util.Log;

import fr.zlandorf.currencyconverter.models.entities.Provider;
import fr.zlandorf.currencyconverter.models.entities.Rate;
import fr.zlandorf.currencyconverter.services.HttpService;

import java.util.List;

public abstract class RetrieveTask extends AsyncTask<Void, Void, List<Rate>> {

    public interface RetrieveTaskListener {
        void onTaskFinished(Provider provider, List<Rate> rates);
        void onTaskFailed(Provider provider);
    }

    protected RetrieveTaskListener listener;
    protected HttpService httpService;

    protected RetrieveTask(RetrieveTaskListener listener, HttpService httpService) {
        this.listener = listener;
        this.httpService = httpService;
    }

    public void setListener(RetrieveTaskListener listener) {
        this.listener = listener;
    }

    /**
     * Retrieves a list of exchange rates from an external server
     *
     * Retrieve tasks must implement this method
     *
     * @return a list {@link Rate}
     * @throws Exception
     */
    public abstract List<Rate> retrieveRates() throws Exception;

    public abstract Provider getProvider();

    @Override
    protected void onPostExecute(List<Rate> rates) {
        super.onPostExecute(rates);
        if (rates != null && listener != null) {
            listener.onTaskFinished(getProvider(), rates);
        }
    }

    @Override
    protected List<Rate> doInBackground(Void... params) {
        try {
            return retrieveRates();
        } catch (Exception e) {
            if (listener != null) {
                listener.onTaskFailed(getProvider());
            }
            Log.e("RETRIEVE_TASK", "Exception retrieving rates : [" + e.getMessage() + "] task : ["+getClass().getSimpleName()+"]");
            e.printStackTrace();
        }
        return null;
    }
}
