package com.darkguyy.anuragsharma.twaran2k17;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by anuragsharma on 29/03/17.
 */

public class DetailActivity extends AppCompatActivity {
    private String description;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        description = getIntent().getStringExtra("detail");

        TextView textView = (TextView) findViewById(R.id.textview_detail);
        textView.setText(description);

    }
}
