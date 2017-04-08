package com.universitylecture.universitylecture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.view.Window.FEATURE_NO_TITLE;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
    }
}
