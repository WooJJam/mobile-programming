package com.example.a2022_middle_test;

public class RankingItem {
    private String name;
    private int score;

    public RankingItem(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}