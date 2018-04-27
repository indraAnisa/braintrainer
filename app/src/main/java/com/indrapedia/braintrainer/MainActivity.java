package com.indrapedia.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeLeftTextView, resultTextView, msgTextView, quizTextView;
    Button goButton, playButton, button1, button2, button3, button4;
    android.support.v7.widget.GridLayout grid1, grid2;
    int answer, numOfQuestion, numCorrectAnswer;
    CountDownTimer timer;
    boolean gameActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        numOfQuestion = 0;
        numCorrectAnswer = 0;

        timer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftTextView.setText(Integer.toString((int) l / 1000) + 's');
            }

            @Override
            public void onFinish() {
                timeLeftTextView.setText("0s");
                playButton.setVisibility(View.VISIBLE);
                msgTextView.setText("FINISH");
                gameActive = false;
            }
        };
    }

    private void initUI() {
        grid1 = findViewById(R.id.grid1);
        grid2 = findViewById(R.id.grid2);

        playButton = findViewById(R.id.playButton);
        goButton = findViewById(R.id.goButton);
        msgTextView = findViewById(R.id.msgTextView);
        quizTextView = findViewById(R.id.quizTextView);
        resultTextView = findViewById(R.id.resultTextView);
        timeLeftTextView = findViewById(R.id.timeTextView);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        grid1.setVisibility(View.INVISIBLE);
        grid2.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.INVISIBLE);
    }

    public void goButtonClick(View view) {
        grid1.setVisibility(View.VISIBLE);
        grid2.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.INVISIBLE);
        newQuiz();
        timer.start();
        gameActive = true;

    }


    public void buttonAnswerClick(View view) {
        if (gameActive) {
            Button buttonAnswer = (Button) view;
            int selectedAnswer = Integer.parseInt(buttonAnswer.getText().toString());

            if (answer == selectedAnswer) {
                msgTextView.setText("Correct!");
                numCorrectAnswer++;
            } else {
                msgTextView.setText("Wrong :(");
            }
            numOfQuestion++;

            resultTextView.setText(Integer.toString(numCorrectAnswer) + '/' + Integer.toString(numOfQuestion));
            newQuiz();
        }
    }


    private void newQuiz() {
        int max = 20;
        int min = 1;

        Random random = new Random();

        int num1 = min + random.nextInt(max);
        int num2 = min + random.nextInt(max);

        answer = num1 + num2;

        ArrayList<Integer> choice = new ArrayList();

        int i = 0;
        while (i <= 3) {
            int randomAnswer = (answer / 3) + random.nextInt(answer + 10);

            if (answer != randomAnswer && !choice.contains(randomAnswer)) {
                choice.add(randomAnswer);
                i++;
            }
        }
        //fill all answer first
        button1.setText(String.valueOf(choice.get(0)));
        button2.setText(String.valueOf(choice.get(1)));
        button3.setText(String.valueOf(choice.get(2)));
        button4.setText(String.valueOf(choice.get(3)));

        //then fill correct answer in random position
        Button buttonAnswer = (Button) grid2.getChildAt(random.nextInt(4));

        buttonAnswer.setText(String.valueOf(answer));

        quizTextView.setText(String.valueOf(num1) + " + " + String.valueOf(num2));


    }

    public void playAgainClick(View view) {
        newQuiz();
        timer.start();
        numCorrectAnswer = 0;
        numOfQuestion = 0;
        resultTextView.setText("0/0");
        msgTextView.setText("");
        playButton.setVisibility(View.INVISIBLE);
        gameActive = true;
    }


}
