package com.example.achyutkulkani.livetrainstatus;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_2;

public class Station_result extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_result);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();


        Intent intent = getIntent();
        String str = intent.getStringExtra("URL");
        Log.i("hello",str);
        getJsonResult(str);
    }


    private void getJsonResult(String url) {

        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String code=response.getString("response_code");
                    switch (code) {
                        case "200":
                            try {
                                JSONArray jsonArray = response.getJSONArray("trains");
                                for (int i = 0 ; i < jsonArray.length(); i++){
                                    JSONObject hit = jsonArray.getJSONObject(i);

                                    String creatorName = hit.getString("name");

                                    int likeCount = hit.getInt("number");

                                    mExampleList.add(new ExampleItem(creatorName,likeCount));
                                }

                                mExampleAdapter = new ExampleAdapter(Station_result.this,mExampleList);
                                mRecyclerView.setAdapter(mExampleAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                          /*  Log.i("hello","hi");

                            ArrayList<TrainArriveClass> trainsArray = new ArrayList<TrainArriveClass>();

                            JSONArray jsonArray = response.getJSONArray("trains");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                String no = String.valueOf(i + 1);
                                String train_no = jsonArray.getJSONObject(i).getString("number");
                                String train_name = jsonArray.getJSONObject(i).getString("name");
                                String scharr = jsonArray.getJSONObject(i).getString("scharr");
                                String schdep = jsonArray.getJSONObject(i).getString("schdep");
                                String actarr = jsonArray.getJSONObject(i).getString("actarr");
                                String actdep = jsonArray.getJSONObject(i).getString("actdep");
                                String delayarr = jsonArray.getJSONObject(i).getString("delayarr");
                                String delaydep = jsonArray.getJSONObject(i).getString("delaydep");

                                TrainArriveClass trainArrive = new TrainArriveClass(no, train_no, train_name, scharr, actarr, schdep, actdep, delayarr, delaydep);
                                trainsArray.add(i, trainArrive);

                            }
                                Log.i("hello", String.valueOf(trainsArray.toString().indexOf(0)));
                            createTableRow(trainsArray);
                            return;

                           // JSONArray jsonArray = response.getJSONArray("trains");

                           /*  trainName = new ArrayList<String>();
                             trainNumber = new ArrayList<>();
                             shar = new ArrayList<>();
                             acar = new ArrayList<>();
                             shdp = new ArrayList<>();
                             acdp = new ArrayList<>();
                             delayArr = new ArrayList<>();
                             delayDep = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length();i++){
                                trainName.add(jsonArray.getJSONObject(i).getString("name"));
                                trainNumber.add(jsonArray.getJSONObject(i).getString("number"));
                                shar.add(jsonArray.getJSONObject(i).getString("scharr"));
                                acar.add(jsonArray.getJSONObject(i).getString("actarr"));
                                shdp.add(jsonArray.getJSONObject(i).getString("schdep"));
                                acdp.add(jsonArray.getJSONObject(i).getString("actdep"));
                                delayArr.add(jsonArray.getJSONObject(i).getString("delayarr"));
                                delayDep.add(jsonArray.getJSONObject(i).getString("delaydep"));

                            }
                            liveStationStatus = findViewById(R.id.listView1);
                            CustomAdapter customAdapter = new CustomAdapter();
                            liveStationStatus.setAdapter(customAdapter);

                          //  for (int i = 0 ; i < trainName.size(); i++){
                            //    Log.i("hello", trainName.get(i));
                          //  }

*/

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
                            AlertDialog.Builder aleart;
                            aleart = new AlertDialog.Builder(Station_result.this);
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
    private void createTableRow(ArrayList<TrainArriveClass> trainsArray) {

      // TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.BLACK);
        TextView tv0 = new TextView(this);
        tv0.setText(" No ");
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setGravity(Gravity.CENTER);
        tv1.setText(" Train_Number ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setGravity(Gravity.CENTER);
        tv2.setText(" Train_Name ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Sch_Arr_Time ");
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" Sch_Dep_Time ");
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setText(" Act_Arr_Time ");
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextColor(Color.WHITE);
        tbrow0.addView(tv5);
        TextView tv6 = new TextView(this);
        tv6.setText(" Act_Dep_Time ");
        tv6.setGravity(Gravity.CENTER);
        tv6.setTextColor(Color.WHITE);
        tbrow0.addView(tv6);
        TextView tv7 = new TextView(this);
        tv7.setText(" Delay_Arr_Time ");
        tv7.setGravity(Gravity.CENTER);
        tv7.setTextColor(Color.WHITE);
        tbrow0.addView(tv7);
        TextView tv8 = new TextView(this);
        tv8.setText(" Delay_Dep_Time ");
        tv8.setGravity(Gravity.CENTER);
        tv8.setTextColor(Color.WHITE);
        tbrow0.addView(tv8);
     //  stk.addView(tbrow0);
        for (int i = 0; i < trainsArray.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(" "+trainsArray.get(i).no+" ");
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(" "+trainsArray.get(i).train_no+" ");
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextColor(Color.WHITE);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(" "+trainsArray.get(i).train_name+" ");
            t3v.setGravity(Gravity.CENTER);
            t3v.setTextColor(Color.WHITE);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(" "+trainsArray.get(i).scharr+" ");
            t4v.setGravity(Gravity.CENTER);
            t4v.setTextColor(Color.WHITE);
            tbrow.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText(" "+trainsArray.get(i).schdep+" ");
            t5v.setGravity(Gravity.CENTER);
            t5v.setTextColor(Color.WHITE);
            tbrow.addView(t5v);
            TextView t6v = new TextView(this);
            t6v.setText(" "+trainsArray.get(i).actarr+" ");
            t6v.setGravity(Gravity.CENTER);
            t6v.setTextColor(Color.WHITE);
            tbrow.addView(t6v);
            TextView t7v = new TextView(this);
            t7v.setText(" "+trainsArray.get(i).actdep+" ");
            t7v.setGravity(Gravity.CENTER);
            t7v.setTextColor(Color.WHITE);
            tbrow.addView(t7v);
            TextView t8v = new TextView(this);
            t8v.setText(" "+trainsArray.get(i).delayarr+" ");
            t8v.setGravity(Gravity.CENTER);
            t8v.setTextColor(Color.WHITE);
            tbrow.addView(t8v);
            TextView t9v = new TextView(this);
            t9v.setText(" "+trainsArray.get(i).delaydep+" ");
            t9v.setGravity(Gravity.CENTER);
            t9v.setTextColor(Color.WHITE);
            tbrow.addView(t9v);
           //stk.addView(tbrow);
        }}

}
