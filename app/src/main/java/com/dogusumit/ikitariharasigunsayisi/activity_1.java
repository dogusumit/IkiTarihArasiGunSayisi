package com.dogusumit.ikitariharasigunsayisi;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class activity_1 extends AppCompatActivity {

    private EditText ed1;
    private EditText ed2;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_1);

        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        tv1 = (TextView) findViewById(R.id.textView);

        ed1.addTextChangedListener(textWatcher);
        ed2.addTextChangedListener(textWatcher);

        //ed1.setText( getPreviousDate());
        ed2.setText( getCurrentDate());

        final AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public String getPreviousDate()
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy",Locale.US);
        return df.format(c.getTime());
    }

    public String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy",Locale.US);
        return df.format(c.getTime());
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy",Locale.US);
            String inputString1 = ed1.getText().toString();
            String inputString2 = ed2.getText().toString();
            try {
                inputString1 = inputString1.replaceAll("[,_/-]", ".");
                inputString2 = inputString2.replaceAll("[,_/-]", ".");

                Date date1 = myFormat.parse(inputString1);
                Date date2 = myFormat.parse(inputString2);
                long diff = date2.getTime() - date1.getTime();
                //tv1.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+" GÜN");

                long a=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("#,###,###,###");
                String formattedString = formatter.format(a);
                formattedString=formattedString.replaceAll(",",".");
                formattedString+=getString(R.string.str1);
                tv1.setText(formattedString);
            } catch (Exception e) {
                //tv1.setText(e.getMessage().toString());
                tv1.setText(getResources().getString(R.string.hata));
            }
        }
    };

    private void uygulamayiOyla()
    {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
            catch (Exception ane)
            {
                Toast.makeText(getApplicationContext(),ane.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void marketiAc()
    {
        try {
        Uri uri = Uri.parse("market://developer?id="+getString(R.string.play_store_id));
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id="+getString(R.string.play_store_id))));
            }
            catch (Exception ane)
            {
                Toast.makeText(getApplicationContext(),ane.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.oyla:
                uygulamayiOyla();
                return true;
            case R.id.market:
                marketiAc();
                return true;
            case R.id.cikis:
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
