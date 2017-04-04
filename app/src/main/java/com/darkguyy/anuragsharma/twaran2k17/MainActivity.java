package com.darkguyy.anuragsharma.twaran2k17;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Notification> notificationsList;
    private NotificationAdapter notificationAdapter;
    private ListView listView;
    private LottieAnimationView lottieAnimationView;
    private TextView emptyTextview;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.notification_listview);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        notificationsList = new ArrayList<>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_home_swipe_refresh_layout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notification = notificationAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("detail",notification.getDescription());
                startActivity(intent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    notificationAdapter.clear();
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                        String topic = dataSnapshot1.getKey();
                        String description = dataSnapshot1.getValue().toString();
                        //Toast.makeText(MainActivity.this,topic,Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,description,Toast.LENGTH_SHORT).show();
                        Notification notification = new Notification(topic,description);
                        notificationsList.add(notification);
                        //Collections.reverse(notificationsList);
                        notificationAdapter = new NotificationAdapter(MainActivity.this,notificationsList);
                        listView.setAdapter(notificationAdapter);

                    }
                    lottieAnimationView = (LottieAnimationView) MainActivity.this.findViewById(R.id.animation_view);
                    lottieAnimationView.cancelAnimation();
                    lottieAnimationView.setVisibility(View.GONE);
                    if(notificationsList.size()!=0){
                        emptyTextview = (TextView) findViewById(R.id.empty_textview);
                        emptyTextview.setVisibility(View.GONE);
                    }
                    else{
                        TextView emptyTextview = (TextView) findViewById(R.id.empty_textview);
                        emptyTextview.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("ERROR", "Failed to read value.", error.toException());
                }
            });


            notificationAdapter = new NotificationAdapter(this,notificationsList);
            listView.setAdapter(notificationAdapter);

                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //refreshContent();
                        notificationAdapter.clear();
                        // Read from the database

                        if(lottieAnimationView.getVisibility() == View.GONE)
                        {
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                        String topic = dataSnapshot1.getKey();
                                        String description = dataSnapshot1.getValue().toString();
                                        //Toast.makeText(MainActivity.this,topic,Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(MainActivity.this,description,Toast.LENGTH_SHORT).show();
                                        Notification notification = new Notification(topic,description);
                                        notificationsList.add(notification);
                                        //Collections.reverse(notificationsList);
                                        notificationAdapter = new NotificationAdapter(MainActivity.this,notificationsList);
                                        listView.setAdapter(notificationAdapter);

                                    }
                                    lottieAnimationView = (LottieAnimationView) MainActivity.this.findViewById(R.id.animation_view);
                                    lottieAnimationView.cancelAnimation();
                                    lottieAnimationView.setVisibility(View.GONE);
                                    if(notificationsList.size()!=0){
                                        emptyTextview = (TextView) findViewById(R.id.empty_textview);
                                        emptyTextview.setVisibility(View.GONE);
                                    }
                                    else{
                                        TextView emptyTextview = (TextView) findViewById(R.id.empty_textview);
                                        emptyTextview.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("ERROR", "Failed to read value.", error.toException());
                                }
                            });
                        }

                        mSwipeRefreshLayout.setRefreshing(false);

                    }

                });

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            lottieAnimationView = (LottieAnimationView) MainActivity.this.findViewById(R.id.animation_view);
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyTextview = (TextView) findViewById(R.id.empty_textview);
            emptyTextview.setVisibility(View.VISIBLE);
            emptyTextview.setText("No Internet Connection");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            Intent intent = new Intent(MainActivity.this,TeamContacts.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
