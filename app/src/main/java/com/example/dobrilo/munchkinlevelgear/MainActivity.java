package com.example.dobrilo.munchkinlevelgear;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NameDialog.Communicator, ClassDialog.Communicator{
    ImageButton levelPlus, levelMinus, gearPlus, gearMinus;
    int level, gear, strength;
    Toolbar toolbar;

    ArrayList<String> races = new ArrayList<String>();
    ArrayList<String> classes = new ArrayList<String>();

    ClassDialog classDialog;

    SoundPool sound;
    int instrument =  R.raw.beepwarmguitar;
    int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //set toolbar as an action bar
        setSupportActionBar(toolbar);

        showNameDialog();
        createSoundPool();
        soundId = sound.load(getApplicationContext(), instrument,1);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);



        level = 0;
        gear = 0;
        strength = level + gear;

        levelPlus = (ImageButton) findViewById(R.id.levelPlus);
        levelPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView strengthTextView = (TextView) findViewById(R.id.strength);
                TextView levelTextView = (TextView)findViewById(R.id.level);
                if(level<10) {
                    level++;
                    levelTextView.setText(""+ level);
                    strength = level+gear;
                    strengthTextView.setText(""+ strength);
                    sound.play(soundId, 1,1,0,0,1);
                }
                if(level == 10){
                    strengthTextView.setText("Victory!");
                }

            }
        });

        levelMinus = (ImageButton) findViewById(R.id.levelMinus);
        levelMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView strengthTextView = (TextView) findViewById(R.id.strength);
                TextView levelTextView = (TextView)findViewById(R.id.level);
                if(level>0) {
                    level--;
                }
                strength = level+gear;
                levelTextView.setText(""+level);
                strengthTextView.setText(""+strength);
                sound.play(soundId, 1,1,0,0,1);

            }
        });

        gearPlus = (ImageButton) findViewById(R.id.gearPlus);
        gearPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView strengthTextView = (TextView) findViewById(R.id.strength);
                TextView gearTextView = (TextView)findViewById(R.id.gear);
                gear++;
                gearTextView.setText(""+gear);
                strength = level+gear;
                strengthTextView.setText(""+strength);
                sound.play(soundId, 1,1,0,0,1);

            }
        });
        gearMinus = (ImageButton) findViewById(R.id.gearMinus);
        gearMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView strengthTextView = (TextView) findViewById(R.id.strength);
                TextView gearTextView = (TextView)findViewById(R.id.gear);
                if(gear>0) {
                    gear--;
                    gearTextView.setText(""+gear);
                    strength = level+gear;
                    strengthTextView.setText(""+strength);
                    sound.play(soundId, 1,1,0,0,1);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resId = item.getItemId();

        switch (resId){

            case R.id.classId:
                showClassRaceDialog();
                break;
            case R.id.changeUsername:
                showNameDialog();
                break;
            case R.id.sounds:
                item.setChecked(!item.isChecked());
                if(!item.isChecked()){
                    sound.release();
                }
                else{
                    createSoundPool();
                    soundId = sound.load(getApplicationContext(), instrument,1);
                    sound.play(soundId, 1,1,0,1,1);
                }
                break;
            case R.id.reset:
                level = 0;
                gear = 0;
                strength = 0;
                TextView strengthTextView = (TextView) findViewById(R.id.strength);
                strengthTextView.setText(""+strength);
                TextView levelTextView = (TextView)findViewById(R.id.level);
                levelTextView.setText(""+level);
                TextView gearTextView = (TextView)findViewById(R.id.gear);
                gearTextView.setText(""+gear);

                TextView name = (TextView) findViewById(R.id.name);
                name.setText("Player");
                TextView race = (TextView) findViewById(R.id.race);
                race.setText("Race");
                TextView classView = (TextView) findViewById(R.id.classes);
                classView.setText("Class");

                break;
        }
        return true;
    }
    public void showNameDialog(){
        FragmentManager manager = getFragmentManager();
        NameDialog nameDialog = new NameDialog();
        nameDialog.show(manager,"NameDialog");
    }
    public void showClassRaceDialog(){
        FragmentManager m = getFragmentManager();
        if(classDialog == null) {
            classDialog = new ClassDialog();
        }
        classDialog.show(m, "ClassDialog");
    }

    public void createSoundPool(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            sound = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else{
            sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
    }

    @Override
    public void onNameDialogMessage(String message) {
        TextView name = (TextView) findViewById(R.id.name);
        if(message.equals("Player")){
            name.setText("Player");
        }
        else{
            name.setText(message);
        }
    }

    @Override
    public void onCheckedClassDialogMessage(String message) {
        ArrayList<String> raceArray = new ArrayList<String>();
        raceArray.add("Dwarf");
        raceArray.add("Elf");
        raceArray.add("Halfling");
        raceArray.add("Human");
        if (raceArray.contains(message)) {
            races.add(message);
            TextView currentRace = (TextView) findViewById(R.id.race);
            String newRace = "";
            for (int i = 0; i < races.size(); i++) {
                if (i >= 1) {
                    newRace += ", ";
                }
                newRace += races.get(i);
            }
            currentRace.setText(newRace);
        } else {
            classes.add(message);
            TextView currentClass = (TextView) findViewById(R.id.classes);
            String newClass = "";
            for(int i=0; i<classes.size(); i++){
                if(i>=1){
                    newClass+=", ";
                }
                newClass += classes.get(i);
            }
            currentClass.setText(newClass);
        }
    }

    @Override
    public void onUnceckedClassDialogMessage(String message) {
        ArrayList<String> raceArray = new ArrayList<String>();
        raceArray.add("Dwarf");
        raceArray.add("Elf");
        raceArray.add("Halfling");
        raceArray.add("Human");
        if (raceArray.contains(message)) {
            if(races.contains(message)) {
                races.remove(message);
            }
            TextView currentRace = (TextView) findViewById(R.id.race);
            String newRace = "";
            for(int i=0; i<races.size(); i++){
                if(i>=1) {
                    newRace += ", ";
                }
                newRace += races.get(i);
            }
            if (newRace.isEmpty()){
                newRace = "Race";
            }
            currentRace.setText(newRace);

        } else {

            if (classes.contains(message)) {
                classes.remove(message);
            }
            TextView currentClass = (TextView) findViewById(R.id.classes);
            String newClass = "";
            for (int i = 0; i < classes.size(); i++) {
                if (i >= 1) {
                    newClass += ", ";
                }
                newClass += classes.get(i);
            }
            if (newClass.isEmpty()){
                newClass = "Class";
            }
            currentClass.setText(newClass);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sound.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createSoundPool();
        soundId = sound.load(getApplicationContext(), instrument,1);
    }
}
