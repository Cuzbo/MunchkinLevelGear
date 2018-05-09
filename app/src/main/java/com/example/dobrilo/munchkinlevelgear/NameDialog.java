package com.example.dobrilo.munchkinlevelgear;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dobrilo.munchkinlevelgear.R;

/**
 * Created by Dobrilo on 7.11.2017..
 */

public class NameDialog extends DialogFragment implements View.OnClickListener {

    Button ok, cancel;
    Communicator communicator;
    View view;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.name_dialog, null);
        ok = (Button) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        EditText username = (EditText) view.findViewById(R.id.username);
        if(v.getId() == ok.getId()){
            if (!TextUtils.isEmpty(username.getText().toString())) {
                communicator.onNameDialogMessage(username.getText().toString());
            } else {
                communicator.onNameDialogMessage("Player");
            }
            dismiss();

        }
    }
    interface Communicator {
        public void onNameDialogMessage (String message);
    }
}
