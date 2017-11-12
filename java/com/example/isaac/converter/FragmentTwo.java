package com.example.isaac.converter;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentTwo extends Fragment {
    private final String cryptoCompareUrl = "https://min-api.cryptocompare.com/data/price";
    private final String coinText = "ETH";
    private Spinner spinItem;
    private String spinText = null;
    private TextView resultView = null;
    private String responseText = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_fragment_two,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        resultView =(TextView) view.findViewById(R.id.eth);
        spinItem = (Spinner) view.findViewById(R.id.eth_codes);
        spinItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinText = adapterView.getItemAtPosition(i).toString();
                spinText = spinText.trim();
                new getEthExchangeRate().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

	private class getEthExchangeRate extends AsyncTask<Void, Void, Void> {
		@Override
        protected void onPreExecute() {
		    super.onPreExecute();
		}
		 @Override
        protected Void doInBackground(Void... arg0) {
             HttpHandler handler = new HttpHandler();
             final String response = handler.makeServiceCall(cryptoCompareUrl, coinText.trim(), spinText.trim());

            try {
                 JSONObject jsonObj = new JSONObject(response);
                responseText = jsonObj.getString(spinText);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             return null;
         }
             @Override
		protected void onPostExecute(Void result) {
		    super.onPostExecute(result);
            resultView.setText(responseText.trim());
		}
	}
}
