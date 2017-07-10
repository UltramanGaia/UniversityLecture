package com.universitylecture.universitylecture.view;

import android.os.Bundle;

import com.universitylecture.universitylecture.R;

import static android.view.Window.FEATURE_NO_TITLE;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
    }
}
