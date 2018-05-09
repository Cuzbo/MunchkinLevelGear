package com.example.dobrilo.munchkinlevelgear;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by Dobrilo on 7.11.2017..
 */

public class ClassDialog extends DialogFragment implements View.OnClickListener{
    Button ok;
    View view;
    ClassDialog.Communicator communicator;

    CheckBox thief, cleric, wizard, warrior;

    CheckBox dwarf, elf, halfling, human;

    @Override
    public void onAttach(Activity activity) {
        communicator = (ClassDialog.Communicator) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.class_layout, container, false);

        thief = view.findViewById(R.id.thief);
        thief.setOnClickListener(this);

        cleric = view.findViewById(R.id.cleric);
        cleric.setOnClickListener(this);

        wizard = view.findViewById(R.id.wizard);
        wizard.setOnClickListener(this);

        warrior = view.findViewById(R.id.warrior);
        warrior.setOnClickListener(this);

        dwarf = view.findViewById(R.id.dwarf);
        dwarf.setOnClickListener(this);

        elf = view.findViewById(R.id.elf);
        elf.setOnClickListener(this);

        halfling = view.findViewById(R.id.halfling);
        halfling.setOnClickListener(this);

        human = view.findViewById(R.id.human);
        human.setOnClickListener(this);

        ok = (Button) view.findViewById(R.id.classOk);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        CheckBox checked = (CheckBox)v;
        if (checked.isChecked()){
            communicator.onCheckedClassDialogMessage(checked.getText().toString());
        }
        else {
            communicator.onUnceckedClassDialogMessage(checked.getText().toString());
        }
    }


    public interface Communicator{
        public void onCheckedClassDialogMessage (String message);

        public void onUnceckedClassDialogMessage(String message);
    }
}
