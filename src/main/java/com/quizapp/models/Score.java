package com.quizapp.models;

import java.sql.Timestamp;

public class Score {
    private int scoreId;
    private int userId;
    private String username;
    private int score;
    private int totalQuestions;
    private String difficulty;
    private String mode;
    private int timeTaken;
    private Timestamp quizDate;
    
    public Score() {}
    
    public Score(int userId, int score, int totalQuestions, String difficulty, 
                 String mode, int timeTaken) {
        this.userId = userId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.difficulty = difficulty;
        this.mode = mode;
        this.timeTaken = timeTaken;
    }
    
    public int getScoreId() {
        return scoreId;
    }
    
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public int getTotalQuestions() {
        return totalQuestions;
    }
    
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getMode() {
        return mode;
    }
    
    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public int getTimeTaken() {
        return timeTaken;
    }
    
    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }
    
    public Timestamp getQuizDate() {
        return quizDate;
    }
    
    public void setQuizDate(Timestamp quizDate) {
        this.quizDate = quizDate;
    }
    
    public double getPercentage() {
        if (totalQuestions == 0) return 0.0;
        return (score * 100.0) / totalQuestions;
    }
    
    public String getFormattedTime() {
        int minutes = timeTaken / 60;
        int seconds = timeTaken % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    @Override
    public String toString() {
        return "Score{" +
                "scoreId=" + scoreId +
                ", userId=" + userId +
                ", score=" + score +
                ", totalQuestions=" + totalQuestions +
                ", percentage=" + getPercentage() + "%" +
                '}';
    }
}