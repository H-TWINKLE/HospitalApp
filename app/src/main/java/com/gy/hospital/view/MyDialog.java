package com.gy.hospital.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.gy.hospital.R;

public class MyDialog extends AlertDialog {


    public MyDialog(@NonNull Context context) {

        super(context, R.style.AlertDialog);
        setView(setView());
    }

    private View setView() {
        return View.inflate(getContext(), R.layout.base_progress, null);
    }

}
