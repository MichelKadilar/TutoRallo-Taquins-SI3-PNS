package com.example.tutorallolifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> currentStates;

    private Button btn;
    private TextView currentStatesText;

    public MainActivity() {
        currentStates = new ArrayList<>();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.currentStates.add("onCreate");
        this.btn = this.findViewById(R.id.button_show_diagram);
        this.currentStatesText = this.findViewById(R.id.TV_state_list);
        currentStatesText.setText(currentStatesText.getText() + currentStates.get(currentStates.size()-1));
        btn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ShowDiagramActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.currentStates.add("onResume");
        currentStatesText.setText(currentStatesText.getText() + currentStates.get(currentStates.size()-1));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.currentStates.add("onRestart");
        currentStatesText.setText(currentStatesText.getText() + currentStates.get(currentStates.size()-1));
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.currentStates.add("onStart");
        currentStatesText.setText(currentStatesText.getText() + currentStates.get(currentStates.size()-1));
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.currentStates.add("onStop");
        currentStatesText.setText(currentStatesText.getText() + currentStates.get(currentStates.size()-1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.currentStates.add("onDestroy");
        currentStatesText.setText(currentStatesText.getText() + currentStates.get(currentStates.size()-1));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.currentStates.add("onPause");
        currentStatesText.setText(currentStatesText.getText() + currentStates.get(currentStates.size()-1));
    }

    /*public void addStateToList(String stateToAdd){
        this.currentStates.add(stateToAdd);
        this.currentStates.add(", ");
    }*/
}