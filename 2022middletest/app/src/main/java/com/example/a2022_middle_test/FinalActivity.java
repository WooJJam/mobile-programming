package com.example.a2022_middle_test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinalActivity extends AppCompatActivity {

    private TextView finalScoreTextView;
    private TextView rankingTextView;
    private List<RankingItem> rankingList;
    private Button backToMainButton;
    private TextView timeView;
    private TextView scoreView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        int score = getIntent().getIntExtra("score", 0);
        String username = getIntent().getStringExtra("username");
        int firstNumberMax = getIntent().getIntExtra("firstNumberMax", 20);
        int secondNumberMax = getIntent().getIntExtra("secondNumberMax", 20);
        int elapsedTimeInSeconds = getIntent().getIntExtra("elapsedSeconds", 0);
        int finalScore = score - elapsedTimeInSeconds + (firstNumberMax - 10) + (secondNumberMax - 10);


        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        rankingTextView = findViewById(R.id.rankingTextView);
        timeView = findViewById(R.id.elapsedTimeTextView);
        scoreView = findViewById(R.id.scoreTextView);

        // 최종 점수 계산

        // 최종 점수 표시
        finalScoreTextView.setText("최종 점수: " + finalScore);

        // 랭킹 표시
        showRanking(finalScore, username);

        String elapsedTimeFormatted = formatElapsedTime(elapsedTimeInSeconds);
        timeView.setText("경과 시간: " + elapsedTimeFormatted);
        scoreView.setText("획득한 점수: " + score);

        backToMainButton = findViewById(R.id.backToMainButton); // Initialize the back button
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 Return to the main screen (MainActivity)
                Intent intent = new Intent(FinalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String formatElapsedTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    private void showRanking(int finalScore, String username) {
        // 랭킹 데이터 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences("Ranking", MODE_PRIVATE);
        String rankingData = sharedPreferences.getString("rankingData", "");

        // 랭킹 데이터 파싱
        rankingList = parseRankingData(rankingData);

        // 새로운 점수를 랭킹에 추가
        RankingItem newItem = new RankingItem(username, finalScore);
        rankingList.add(newItem);

        // 랭킹 정렬
        Collections.sort(rankingList, (item1, item2) -> item2.getScore() - item1.getScore());

        // 랭킹 데이터 업데이트
        StringBuilder updatedRankingData = new StringBuilder();
        for (int i = 0; i < rankingList.size(); i++) {
            if (i >= 5) {
                break; // 상위 5개만 유지
            }
            RankingItem item = rankingList.get(i);
            updatedRankingData.append(item.getName()).append(":").append(item.getScore()).append(",");
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("rankingData", updatedRankingData.toString());
        editor.apply();

        // 랭킹 표시
        StringBuilder rankingText = new StringBuilder();
        for (int i = 0; i < rankingList.size(); i++) {
            if (i >= 5) {
                break; // 상위 5개만 표시
            }
            RankingItem item = rankingList.get(i);
            rankingText.append(i + 1).append(". ").append(item.getName()).append(" - ").append(item.getScore()).append("\n");
        }
        rankingTextView.setText(rankingText.toString());
    }

    private List<RankingItem> parseRankingData(String rankingData) {
        List<RankingItem> rankingList = new ArrayList<>();
        String[] parts = rankingData.split(",");
        for (String part : parts) {
            try {
                String[] itemParts = part.split(":");
                if (itemParts.length == 2) {
                    String name = itemParts[0];
                    int score = Integer.parseInt(itemParts[1]);
                    RankingItem item = new RankingItem(name, score);
                    rankingList.add(item);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return rankingList;

    }
}
