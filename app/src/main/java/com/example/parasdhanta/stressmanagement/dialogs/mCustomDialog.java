package com.example.parasdhanta.stressmanagement.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.parasdhanta.stressmanagement.R;

/**
 * Created by Paras Dhanta on 11/24/2016.
 */

public class mCustomDialog extends DialogFragment {
    Dialog mDialog;
    AppCompatActivity activity;

    volatile String dialogTitle;

    public String getDescriptionContent() {
        return descriptionContent;
    }

    public void setDescriptionContent(String descriptionContent) {
        this.descriptionContent = descriptionContent;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    volatile String descriptionContent;

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void setLayoutResourceId(int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private int layoutResourceId;

    public mCustomDialog() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setDialogTitle(getArguments().getString("dialog_title"));
        setDescriptionContent(getArguments().getString("desc_content"));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new Dialog(activity, R.style.AppCompatAlertDialogStyle);

        mDialog.setContentView(getLayoutResourceId());

        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView heading = (TextView) mDialog.findViewById(R.id.viewmore_dia_heading);

        TextView descContent = (TextView) mDialog.findViewById(R.id.description_content);

        Button closeButton = (Button) mDialog.findViewById(R.id.desc_dialog_close);

        heading.setText(getDialogTitle());

        descContent.setText(getDescriptionContent());

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mDialog.cancel();
            }
        });

        return mDialog;
    }

    public void getInstance(AppCompatActivity activity,int resourceId) {
        this.activity = activity;
        setLayoutResourceId(resourceId);

    }

}
