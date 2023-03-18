package com.example.tutorallotaquins;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button BT_startGame;
    private Button BT_1, BT_2, BT_3, BT_4, BT_5, BT_6, BT_7, BT_8, BT_9;
    private Button[] numberButtons;

    private int currentEmptyButtonIndex;

    private Direction direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getActivityLayoutElements();
        // I wanted to put the next three lines in a constructor but after some researches it seems like
        // it's useless/the same in my case.
        this.direction = Direction.NO_MOVE_POSSIBLE;
        this.currentEmptyButtonIndex = 8;
        this.numberButtons = new Button[]{BT_1, BT_2, BT_3, BT_4, BT_5, BT_6, BT_7, BT_8, BT_9};

        this.BT_startGame.setOnClickListener(view -> {
            BT_startGame.setEnabled(false);
            BT_startGame.setVisibility(View.INVISIBLE);
            for (int i = 0; i < this.numberButtons.length; i++) {
                this.numberButtons[i].setEnabled(true);
                this.numberButtons[i].setOnClickListener(new NumberButtonClick(i));
            }
        });
    }

    private void getActivityLayoutElements() {
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

    private class NumberButtonClick implements View.OnClickListener {

        int indexOfButton;

        public NumberButtonClick(int indexOfButton) {
            this.indexOfButton = indexOfButton;
        }

        @Override
        public void onClick(View v) {
            if (indexOfButton % 3 != 2 && currentEmptyButtonIndex == indexOfButton + 1) { // if not last column
                direction = Direction.R;
            } else if (indexOfButton % 3 != 0 && currentEmptyButtonIndex == indexOfButton - 1) { // if not first column
                direction = Direction.L;
            } else if (indexOfButton <= 5 && currentEmptyButtonIndex == indexOfButton + 3) { // if not last line
                direction = Direction.B;
            } else if (indexOfButton >=3 && currentEmptyButtonIndex == indexOfButton - 3) { // if not first line
                direction = Direction.T;
            }
            else {
                direction = Direction.NO_MOVE_POSSIBLE;
            }
            if(direction != Direction.NO_MOVE_POSSIBLE){
                moveButton((Button)v, direction);
            }
        }
    }

    private void moveButton(Button button, Direction direction) { // DIRECTION is a type of mine
        int currentButtonIndex = -1;
        switch (direction){
            case T: currentButtonIndex = currentEmptyButtonIndex + 3; break;
            case B: currentButtonIndex = currentEmptyButtonIndex - 3; break;
            case L: currentButtonIndex = currentEmptyButtonIndex + 1; break;
            case R: currentButtonIndex = currentEmptyButtonIndex - 1; break;
        }
        CharSequence tmpCurrentStrNumberButton = button.getText();
        Object tmpCurrentButtonTag = button.getTag();

        button.setText(this.numberButtons[currentEmptyButtonIndex].getText());
        button.setTag(this.numberButtons[currentEmptyButtonIndex].getTag());
        button.setVisibility(View.INVISIBLE);
        button.setEnabled(false);

        this.numberButtons[currentEmptyButtonIndex].setText(tmpCurrentStrNumberButton);
        this.numberButtons[currentEmptyButtonIndex].setTag(tmpCurrentButtonTag);
        this.numberButtons[currentEmptyButtonIndex].setVisibility(View.VISIBLE);
        this.numberButtons[currentEmptyButtonIndex].setEnabled(true);

        this.currentEmptyButtonIndex = currentButtonIndex;
    }

    public enum Direction {
        T, B, L, R, NO_MOVE_POSSIBLE
    }
}