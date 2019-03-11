package com.example.achyutkulkani.livetrainstatus;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
public class Railway_Result_Activity extends AppCompatActivity {

    TextView tn1,tn2,tn3,tn4,tn5,tn6,tn7,tn8,tn9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway__result_);

        tn1 = findViewById(R.id.tn1);
        tn2 = findViewById(R.id.tn2);
        tn3 = findViewById(R.id.tn3);
        tn4 = findViewById(R.id.tn4);
        tn5 = findViewById(R.id.tn5);
        tn6 = findViewById(R.id.tn6);
        tn7 = findViewById(R.id.tn7);
        tn8 = findViewById(R.id.tn8);
        tn9 = findViewById(R.id.textView4);

        Intent intent = getIntent();
        String str = intent.getStringExtra("URL");

        Log.i("Hello",str);
        getJsonStatus(str);


    }
    private void getJsonStatus(String url) {
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String code=response.getString("response_code");

                    switch (code) {
                        case "200":
                            JSONObject jsonObject = response.getJSONObject("train");
                            JSONObject jsonObject1 = response.getJSONObject("station");
                            JSONObject jsonObject2 = response.getJSONObject("status");
                            tn1.setText(jsonObject.getString("number")+" "+jsonObject.getString("name"));
                            tn2.setText(jsonObject1.getString("name")+"("+jsonObject1.getString("code")+")");
                            tn3.setText(response.getString("start_date"));
                            tn4.setText(jsonObject2.getString("scharr"));

                            String ss = jsonObject2.getString("scharr");
                            String s = jsonObject2.getString("actarr");
                            Log.i("hello",ss);
                            Log.i("hello",s);
                            String sss = jsonObject2.getString("schdep");
                            String ssss = jsonObject2.getString("actdep");
                            tn5.setText(jsonObject2.getString("actarr"));

                            if(!ss.equals(s)){
                                tn5.setTextColor(Color.RED);
                            }
                            tn6.setText(jsonObject2.getString("schdep"));
                            tn7.setText(jsonObject2.getString("actdep"));
                            if(!sss.equals(ssss)){
                                tn7.setTextColor(Color.RED);
                            }

                            tn8.setText(jsonObject2.getString("latemin") +"min");
                            tn9.setText("\n\n\nLast Position =  "+response.getString("position"));




                            break;
                        case "204":
                            Toast.makeText(getApplicationContext(), "Not able to fetch required data, Try Again Later", Toast.LENGTH_SHORT).show();
                            return;
                        case "404":
                            Toast.makeText(getApplicationContext(), "Service Down / Source not responding Please Try Again Later", Toast.LENGTH_SHORT).show();
                            return;
                        case "510":
                            Toast.makeText(getApplicationContext(), "Train is not scheduled to run on this day", Toast.LENGTH_SHORT).show();
                            return;
                        case "401":
                            Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_SHORT).show();
                            return;
                        case "403":
                            Toast.makeText(getApplicationContext(), "Quota for the day exhausted, Try Again on Nextday", Toast.LENGTH_SHORT).show();
                            return;
                        case "405":
                            AlertDialog.Builder aleart = new AlertDialog.Builder(Railway_Result_Activity.this);
                            aleart.setTitle(Html.fromHtml("<font color='#4d587b'>Update Require..</font>"));
                            aleart.setMessage("Account Expired , Update Your Application Now...");
                            aleart.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            aleart.setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog alertDialog = aleart.create();
                            alertDialog.show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Some Error Occure, Try Again later", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),
                            " Check Your Connection..",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(),
                            "AuthFailureError , Try again later",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),
                            "ServerError, Try again later",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "NetworkError, Try again later",
                            Toast.LENGTH_LONG).show();

                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(),
                            "ParseError, Try again later",
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Some Error Occure in Network, Try again later",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
        requestQueue2.add(jsonObjectRequest2);
    }
}


