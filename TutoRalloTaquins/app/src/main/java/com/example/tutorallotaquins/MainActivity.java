package com.example.tutorallotaquins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button BT_startGame;
    private Button BT_1, BT_2, BT_3, BT_4, BT_5, BT_6, BT_7, BT_8, BT_9;
    private Button[] numberButtons;

    private void getActivityLayoutElements(){
        this.BT_1 = findViewById(R.id.BT_1);
        this.BT_2 = findViewById(R.id.BT_2);
        this.BT_3 = findViewById(R.id.BT_3);
        this.BT_4 = findViewById(R.id.BT_4);
        this.BT_5 = findViewById(R.id.BT_5);
        this.BT_6 = findViewById(R.id.BT_6);
        this.BT_7 = findViewById(R.id.BT_7);
        this.BT_8 = findViewById(R.id.BT_8);
        this.BT_9 = findViewById(R.id.BT_9);
        this.BT_startGame = findViewById(R.id.BT_start_game);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getActivityLayoutElements();
        this.numberButtons = new Button[]{BT_1, BT_2, BT_3, BT_4, BT_5, BT_6, BT_7, BT_8, BT_9};
        this.BT_startGame.setOnClickListener(view -> {
            BT_startGame.setEnabled(false);
            BT_startGame.setVisibility(View.INVISIBLE);
            for (Button btn : this.numberButtons) {
                btn.setEnabled(true);
            }
        });
    }
}