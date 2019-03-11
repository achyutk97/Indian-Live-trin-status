package com.example.achyutkulkani.livetrainstatus;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrainStatusSearch extends AppCompatActivity {

    Spinner spinner;
    Button getStatus;
   // EditText trainName;
    String todaysDate[];
    String date;
    AutoCompleteTextView train_no;
    EditText station_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_status_search);
        spinner = findViewById(R.id.spinner);
        getStatus = findViewById(R.id.button);
        train_no = findViewById(R.id.textView2);
        station_code = findViewById(R.id.station_code);
        todaysDate = new String[]{"Starting Date - Today", "Starting Date - Yesterday", "Starting Date - 2 days ago","Starting Date - 3 days ago","Starting Date - 4 days ago"};
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,todaysDate);
        spinner.setAdapter(adapter);
        ReusableCode reusableCode=new ReusableCode();
        final String trainDetails[]=new String[reusableCode.train.length];
        for(int i=0;i<reusableCode.train.length;i++)
        {
            String temp_details[]=reusableCode.train[i].split("\t");
            trainDetails[i]=temp_details[0].replaceAll("\\s+", "").trim()+"- "+temp_details[1];
        }
        train_no.setThreshold(2);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.select_dialog_item,trainDetails);
        train_no.setAdapter(arrayAdapter);


        getStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();
                date = getDateTime(text);
                Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();

                String traindetail[]=train_no.getText().toString().split("-");
                String trainno=traindetail[0].replaceAll("\\s+", "").trim();
                String stationcode = station_code.getText().toString();
                ReusableCode key = new ReusableCode();
                String APIKey = key.APIKey;
                Toast.makeText(getApplicationContext(),trainno,Toast.LENGTH_LONG).show();

                if (train_no.getText().toString().isEmpty()||station_code.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Confirm your Train Number, Station code and Start Date",Toast.LENGTH_SHORT).show();
                }else{


                    String url = "https://api.railwayapi.com/v2/live/train/"+ trainno +"/station/"+ stationcode +"/date/"+ date +"/apikey/o1b2kzpsl9/";
                    //Toast.makeText(TrainStatusSearch.this, "fetching!", Toast.LENGTH_SHORT).show();
                    // getJsonStatus(url);
                    Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TrainStatusSearch.this, Railway_Result_Activity.class);
                    intent.putExtra("URL", url);
                    startActivity(intent);
                }

            }
        });


    }




    private String getDateTime(String text) {
        String Today = null;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal0 = Calendar.getInstance();
        switch (text){
            case "Starting Date - Today" :
                cal0.add(Calendar.DATE, -0);
                Today = dateFormat.format(cal0.getTime());
                break;
            case "Starting Date - Yesterday" :
                cal0.add(Calendar.DATE, -1);
                Today = dateFormat.format(cal0.getTime());
                break;
           /* case "Starting Date - 2 days ago" :
                cal0.add(Calendar.DATE, -2);
                Today = dateFormat.format(cal0.getTime());
                break;
            case "Starting Date - 3 days ago" :
                cal0.add(Calendar.DATE, -3);
                Today = dateFormat.format(cal0.getTime());
                break;
            case "Starting Date - 4 days ago" :
                cal0.add(Calendar.DATE, -4);
                Today = dateFormat.format(cal0.getTime());
                break;*/
        }
        return Today;
    }
}
