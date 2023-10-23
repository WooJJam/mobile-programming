package com.example.a2022_middle_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText userNameEditText;
    private Button startGameButton;
    private TextView gameNameTextView;
    private TextView errorMessageTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.usernameEditText);
        startGameButton = findViewById(R.id.startGameButton);
        gameNameTextView = findViewById(R.id.gameNameTextView);
        SeekBar firstNumberSeekBar = findViewById(R.id.firstNumberSeekBar);
        SeekBar secondNumberSeekBar = findViewById(R.id.secondNumberSeekBar);
        SeekBar problemCountSeekBar = findViewById(R.id.problemCountSeekBar);


        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString();
                if (userName.isEmpty()) {
                    // 사용자 이름이 입력되지 않았을 때 Toast 메시지 표시
                    Toast.makeText(MainActivity.this, "이름을 입력하세요", Toast.LENGTH_LONG).show();
                }else {
                    // 사용자 이름과 설정값을 인텐트에 추가
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);


                    int firstNumber = firstNumberSeekBar.getProgress() + 1;
                    int secondNumber = secondNumberSeekBar.getProgress() + 1;
                    int problemCount = problemCountSeekBar.getProgress() + 1;

                    intent.putExtra("userName", userName);
                    intent.putExtra("firstNumber", firstNumber);
                    intent.putExtra("secondNumber", secondNumber);
                    intent.putExtra("problemCount", problemCount);

                    // 현재 화면에 있는 `GameActivity`를 재사용 (재사용하려면 필요한 경우 로직을 `GameActivity`에서 초기화)
                    startActivity(intent);
                }
            }
        });
        final TextView firstNumberValueTextView = findViewById(R.id.firstNumberValueTextView);

        // 초기 위치를 기본값으로 설정
        int defaulFirstNumber = 20;
        firstNumberSeekBar.setProgress(defaulFirstNumber - 1);
        firstNumberValueTextView.setText("첫번째 숫자 최대값: " + defaulFirstNumber); // 초기값 설정


        firstNumberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int firstNumber = progress + 1;
                firstNumberValueTextView.setText("첫번째 숫자 최대값: " + firstNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final TextView secondNumberValueTextView = findViewById(R.id.secondNumberValueTextView);

        // 초기 위치를 기본값으로 설정
        int defaulSecondNumber = 20;
        secondNumberSeekBar.setProgress(defaulSecondNumber - 1);
        secondNumberValueTextView.setText("두번째 숫자 최대값: " + defaulSecondNumber); // 초기값 설정


        secondNumberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int secondNumber = progress + 1;
                secondNumberValueTextView.setText("두번째 숫자 최대값: " + secondNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final TextView problemCountValueTextView = findViewById(R.id.problemCountValueTextView);

        int defaultProblemCount = 5;
        problemCountSeekBar.setProgress(defaultProblemCount - 1);
        problemCountValueTextView.setText("문제 수: " + defaultProblemCount); // 초기값 설정

        problemCountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 스크롤 값에 따라 텍스트뷰를 업데이트
                int problemCount = progress + 1; // 0부터 시작하므로 +1을 해서 1부터 20까지의 범위를 표시
                problemCountValueTextView.setText("문제 수: " + problemCount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 사용자가 터치를 시작할 때의 동작
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 사용자가 터치를 멈출 때의 동작
            }
        });
    }

}
