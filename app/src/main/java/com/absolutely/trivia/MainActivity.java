package com.absolutely.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.absolutely.trivia.data.AnswerListAsyncResponse;
import com.absolutely.trivia.data.QuestionBank;
import com.absolutely.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionCounterTextVIew;
    private TextView questionTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextView scoreText;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionCounterTextVIew = findViewById(R.id.counter_text);
        questionTextView = findViewById(R.id.question_textview);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.prev_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        scoreText = findViewById(R.id.score_textView);

        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionCounterTextVIew.setText((currentQuestionIndex + 1) + " out of " + questionArrayList.size());
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
//                Log.d("JSON", "processFinished: " + questionArrayList);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prev_button:
//                if(currentQuestionIndex > 0) {
//                    prevQuestion();
//                }
                break;
            case R.id.next_button:
                if((currentQuestionIndex + 1) != questionList.size()) {
                    nextQuestion();
                }
                break;
            case R.id.false_button:
                if(!questionList.get(currentQuestionIndex).isAnswerTrue()) {
                    Toast.makeText(MainActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
                    updateScore();
                } else {
                    Toast.makeText(MainActivity.this, "That's wrong!", Toast.LENGTH_SHORT).show();
                }
                nextQuestion();
                break;
            case R.id.true_button:
                if(questionList.get(currentQuestionIndex).isAnswerTrue()) {
                    Toast.makeText(MainActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
                    updateScore();
                } else {
                    Toast.makeText(MainActivity.this, "That's wrong!", Toast.LENGTH_SHORT).show();
                }
                nextQuestion();
                break;
            default:
                break;
        }
    }

    public void nextQuestion() {
        currentQuestionIndex++;
        questionCounterTextVIew.setText((currentQuestionIndex + 1) + " out of " + questionList.size());
        questionTextView.setText(questionList.get(currentQuestionIndex).getAnswer());
    }

    public void prevQuestion() {
        currentQuestionIndex--;
        questionCounterTextVIew.setText((currentQuestionIndex + 1) + " out of " + questionList.size());
        questionTextView.setText(questionList.get(currentQuestionIndex).getAnswer());
    }

    public void updateScore() {
        score++;
        scoreText.setText("Score: " + score);
    }
}
