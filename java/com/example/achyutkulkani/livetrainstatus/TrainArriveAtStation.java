package com.example.achyutkulkani.livetrainstatus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainArriveAtStation extends AppCompatActivity {

    EditText stationcode;
    Button getStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_arrive_at_station);

        stationcode= findViewById(R.id.textView8);
        getStatus = findViewById(R.id.button2);

        getStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stcode = stationcode.getText().toString();

                if (stationcode.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Confirm your Train Number and Start Date",Toast.LENGTH_SHORT).show();
                }else{
                    String url = "http://api.railwayapi.com/v2/arrivals/station/"+stcode+"/hours/4/apikey/o1b2kzpsl9";

                    Intent intent = new Intent(TrainArriveAtStation.this, Station_result.class);
                    intent.putExtra("URL", url);
                    startActivity(intent);
                }
            }
        });


    }
}
