package com.mse8.teamwe.cantoolapp.tableview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mse8.teamwe.cantoolapp.R;

/**
 * Created by JIChunYang on 2017/10/24.
 */

public class TableActivity extends Activity {

    private TableView _tableActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_tableview);

        this._tableActivity = (TableView) findViewById(R.id.table_view);

        int[] tmp = {18, 17, 16, 31, 30, 29, 28};
        this._tableActivity.CanSignalLayout(tmp);
    }
}
