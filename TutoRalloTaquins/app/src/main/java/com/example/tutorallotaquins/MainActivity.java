package com.example.tutorallotaquins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.Random;

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
        // Je voulais mettre les 3 prochaines lignes dans un constructeur, mais après quelques
        // recherches il semble que ça soit inutile dans mon cas
        this.direction = Direction.NO_MOVE_POSSIBLE;
        this.currentEmptyButtonIndex = 8; //Indice du bouton numéroté "9".
        this.numberButtons = new Button[]{BT_1, BT_2, BT_3, BT_4, BT_5, BT_6, BT_7, BT_8, BT_9};

        shuffleButtons(10);

        this.BT_startGame.setOnClickListener(view -> { // on écoute le bouton "start game" pour détecter les clics
            BT_startGame.setEnabled(false); // on désactive le bouton start game
            BT_startGame.setVisibility(View.INVISIBLE); // on rend invisible le bouton start game
            for (int i = 0; i < this.numberButtons.length; i++) { // on rend cliquables tous les boutons
                this.numberButtons[i].setEnabled(true);
                this.numberButtons[i].setOnClickListener(new NumberButtonClick(i));
            }
        });
    }

    private void getActivityLayoutElements() { // On va récupèrer tous les éléments affichés
        // sur l'écran (depuis le XML)
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

    private void shuffleButtons(int levelSuffle) {
        int numberOfShuffle = levelSuffle * 2; // rendre pair le niveau de mélange

        Button[] allNumberedButtons = this.numberButtons;

        Random random = new Random();

        while (numberOfShuffle > 0) {
            int randomIndex = random.nextInt(allNumberedButtons.length-1); // entre 0 et 7 inclus
            Button button1 = allNumberedButtons[randomIndex];
            if (randomIndex % 3 == 0) { // premiere colonne
                int indexOfRightButton = randomIndex + 1;
                Button button2 = allNumberedButtons[indexOfRightButton]; // button on the right of button1
                CharSequence tmpButton1Text = button1.getText();
                button1.setText(button2.getText());
                button2.setText(tmpButton1Text);
            } else if (randomIndex == 1 || randomIndex == 2 || randomIndex == 4) {
                int indexOfBottomButton = randomIndex + 3;
                Button button2 = allNumberedButtons[indexOfBottomButton]; // button on the bottom of button1
                CharSequence tmpButton1Text = button1.getText();
                button1.setText(button2.getText());
                button2.setText(tmpButton1Text);
            } else {
                int indexOfLeftButton = randomIndex - 1;
                Button button2 = allNumberedButtons[indexOfLeftButton]; // button on the left of button1
                CharSequence tmpButton1Text = button1.getText();
                button1.setText(button2.getText());
                button2.setText(tmpButton1Text);
            }
            numberOfShuffle--;
        }
    }


    private class NumberButtonClick implements View.OnClickListener {
        // on écoute les boutons numérotés pour réagir aux clics
        // On définit une classe pour généraliser le comportement au clic et pouvoir le dupliquer facilement
        // à tous les boutons sans duplication de code

        int indexOfButton;

        public NumberButtonClick(int indexOfButton) {
            this.indexOfButton = indexOfButton;
        }

        @Override
        public void onClick(View v) { // Au clic, on met à jour la direction
            if (indexOfButton % 3 != 2 && currentEmptyButtonIndex == indexOfButton + 1) { // si pas dernière colonne
                direction = Direction.RIGHT;
            } else if (indexOfButton % 3 != 0 && currentEmptyButtonIndex == indexOfButton - 1) { // si pas première colonne
                direction = Direction.LEFT;
            } else if (indexOfButton <= 5 && currentEmptyButtonIndex == indexOfButton + 3) { // si pas dernière ligne
                direction = Direction.BOTTOM;
            } else if (indexOfButton >= 3 && currentEmptyButtonIndex == indexOfButton - 3) { // si pas première ligne
                direction = Direction.TOP;
            } else {
                direction = Direction.NO_MOVE_POSSIBLE;
            }
            if (direction != Direction.NO_MOVE_POSSIBLE) { // si un mouvement est possible...
                moveButton((Button) v, direction);
            }
        }
    }

    private void moveButton(Button button, Direction direction) { // DIRECTION is a type of mine
        int currentButtonIndex = -1;
        Animation animation = null;
        switch (direction) {
            case TOP: {
                currentButtonIndex = currentEmptyButtonIndex + 3;
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.up);
                break;
            }
            case BOTTOM: {
                currentButtonIndex = currentEmptyButtonIndex - 3;
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.down);
                break;
            }
            case LEFT: {
                currentButtonIndex = currentEmptyButtonIndex + 1;
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.left);
                break;
            }
            case RIGHT: {
                currentButtonIndex = currentEmptyButtonIndex - 1;
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.right);
                break;
            }
        }
        if (animation != null) { // obligation car animation n'est pas certain d'être initialisé et non nul

            int finalCurrentButtonIndex = currentButtonIndex;
            animation.setAnimationListener(new Animation.AnimationListener() { // on écoute l'animation pour réagir
                // lorsqu'elle sera en exécution
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // A la fin de l'animation, on interverti les informations des deux boutons

                    CharSequence tmpCurrentStrNumberButton = button.getText();
                    Object tmpCurrentButtonTag = button.getTag();

                    button.setText(numberButtons[currentEmptyButtonIndex].getText());
                    button.setTag(numberButtons[currentEmptyButtonIndex].getTag());
                    button.setVisibility(View.INVISIBLE);
                    button.setEnabled(false);


                    numberButtons[currentEmptyButtonIndex].setText(tmpCurrentStrNumberButton);
                    numberButtons[currentEmptyButtonIndex].setTag(tmpCurrentButtonTag);
                    numberButtons[currentEmptyButtonIndex].setVisibility(View.VISIBLE);
                    numberButtons[currentEmptyButtonIndex].setEnabled(true);

                    currentEmptyButtonIndex = finalCurrentButtonIndex;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            button.startAnimation(animation);
        }
    }

    public enum Direction { // Enum décrivant les directions possibles
        // '\0' = NO_MOVE_POSSIBLE
        // '->' = RIGHT
        // '<-' = LEFT, etc
        TOP, BOTTOM, LEFT, RIGHT, NO_MOVE_POSSIBLE
    }
}