package com.example.a2022_middle_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView timeTextView;
    private TextView usernameView;
    private TextView problemTextView;
    private EditText answerEditText;
    private Button submitButton;
    private String username;
    private int score = 0;
    private int problemCount1 ;
    private int firstNumberMax;
    private int secondNumberMax;
    private Handler handler = new Handler();
    private int seconds = 0;
    private Runnable timerRunnable;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 사용자 이름(username)을 첫 번째 화면에서 가져오기
        username = getIntent().getStringExtra("userName");

        timeTextView = findViewById(R.id.timeTextView);
        problemTextView = findViewById(R.id.problemTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.submitButton);
        usernameView = findViewById(R.id.usernameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);

        // 첫 번째 화면에서 받아온 데이터 확인 및 기본값 설정
        problemCount1 = getIntent().getIntExtra("problemCount", 5);

        // firstNumberMax와 secondNumberMax 기본값 설정
        firstNumberMax = getIntent().getIntExtra("firstNumber", 20);
        secondNumberMax = getIntent().getIntExtra("secondNumber", 20);

        // 게임 시간을 1초마다 증가시키는 타이머 시작
        startTimer();

        // 문제 출제
      generateProblem();

        // "제출" 버튼 클릭 시 정답 확인
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = String.valueOf(answerEditText.getText());
                if (!answer.isEmpty()) {
                    // 숫자로 변환하기 전에 공백 및 불필요한 문자 제거
                    answer = answer.trim();
                    if (answer.matches("-?\\d+")) { // 정수 패턴 확인
                        int userAnswer = Integer.parseInt(answer);
                        int correctAnswer = evaluateCurrentProblem();

                        if (userAnswer == correctAnswer) {
                            Toast.makeText(GameActivity.this, "정답입니다.", Toast.LENGTH_LONG).show();
                            // 점수를 10점 증가
                            score += 10;
                            Log.i("mine", String.valueOf(score));
                        } else {
                            Toast.makeText(GameActivity.this, "오답입니다.", Toast.LENGTH_LONG).show();
                            // 점수를 10점 감소
                            score -= 10;
                        }

                        // 점수 업데이트
                        scoreTextView.setText("점수: " + Integer.toString(score));

                        // 다음 문제 생성
                        generateProblem();
                    } else {
                        // 숫자가 아닌 입력일 경우 사용자에게 알림
                        Toast.makeText(GameActivity.this, "유효한 숫자를 입력하세요.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // 빈 입력 처리
                    Toast.makeText(GameActivity.this, "답변을 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    seconds++;
                    int minutes = seconds / 60;
                    int remainingSeconds = seconds % 60;
                    if (timeTextView != null) {
                        timeTextView.setTextColor(Color.RED);
                    }
                    timeTextView.setText("시간: " + String.format("%02d:%02d", minutes, remainingSeconds));
                    handler.postDelayed(this, 1000);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.post(timerRunnable);
    }

    private void generateProblem() {
        int correctAnswer = 0;
        if (problemCount1 > 0) {
            Random random = new Random();
            int firstNumber = random.nextInt(firstNumberMax) + 1;
            int secondNumber = random.nextInt(secondNumberMax) + 1;
            int operator = random.nextInt(4); // 0: +, 1: -, 2: *, 3: /

            String operatorSymbol = "";

            switch (operator) {
                case 0:
                    operatorSymbol = "+";
                    correctAnswer = firstNumber + secondNumber;
                    break;
                case 1:
                    operatorSymbol = "-";
                    correctAnswer = firstNumber - secondNumber;
                    break;
                case 2:
                    operatorSymbol = "*";
                    correctAnswer = firstNumber * secondNumber;
                    break;
                case 3:
                    operatorSymbol = "/";
                    if (secondNumber != 0) {
                        correctAnswer = firstNumber / secondNumber;
                    } else {
                        correctAnswer = 0;
                    }
                    break;
            }

            usernameView.setText(username + "님 환영합니다! \n");
            problemTextView.setText(firstNumber + " " + operatorSymbol + " " + secondNumber + " = ?");
            problemCount1--;
        }else {
            endGame();
        }
    }

    private int evaluateCurrentProblem() {
        String problemStr = problemTextView.getText().toString();
        String[] parts = problemStr.split("= ");

        if (parts.length != 2) {
            // 부적절한 입력
            Toast.makeText(this, "올바른 연산식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return 0; // 또는 다른 기본값 반환
        }

        try {
            String expression = parts[0].trim();
            Log.i("mine",expression);
            String operator = expression.contains("+") ? "+" :
                    (expression.contains("-") ? "-" :
                            (expression.contains("*") ? "*" : "/"));

            String[] operands = expression.split(" \\" + operator + " ");
            int operand1 = Integer.parseInt(operands[0]);
            int operand2 = Integer.parseInt(operands[1]);

            switch (operator) {
                case "+":
                    return operand1 + operand2;
                case "-":
                    return operand1 - operand2;
                case "*":
                    return operand1 * operand2;
                case "/":
                    if (operand2 != 0) {
                        return operand1 / operand2;
                    } else {
                        return 0;
                    }
                default:
                    return 0;
            }
        } catch (NumberFormatException e) {
            // 정수로 변환 중 오류 발생
            Toast.makeText(this, "올바른 숫자 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return 0; // 또는 다른 기본값 반환
        }
    }

    private void endGame() {
        Intent intent = new Intent(this, FinalActivity.class);
        intent.putExtra("score", this.score);
        intent.putExtra("elapsedSeconds", seconds);
        intent.putExtra("firstNumberMax", firstNumberMax);
        intent.putExtra("secondNumberMax", secondNumberMax);
        intent.putExtra("username",username);
        startActivity(intent);
        finish();
    }


}
