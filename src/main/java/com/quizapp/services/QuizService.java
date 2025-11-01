package com.quizapp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.quizapp.config.DatabaseConfig;
import com.quizapp.models.Question;
import com.quizapp.models.Score;

public class QuizService {
    
    public List<Question> getQuestions(String difficulty, int count) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE difficulty = ? ORDER BY RAND() LIMIT ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, difficulty);
            stmt.setInt(2, count);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Question q = new Question();
                q.setqId(rs.getInt("q_id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setOptionA(rs.getString("option_a"));
                q.setOptionB(rs.getString("option_b"));
                q.setOptionC(rs.getString("option_c"));
                q.setOptionD(rs.getString("option_d"));
                q.setCorrectAnswer(rs.getString("correct_answer"));
                q.setDifficulty(rs.getString("difficulty"));
                q.setCategory(rs.getString("category"));
                
                questions.add(q);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching questions: " + e.getMessage());
        }
        
        return questions;
    }
    
    public boolean saveScore(Score score) {
        String query = "INSERT INTO scores (user_id, score, total_questions, difficulty, mode, time_taken) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, score.getUserId());
            stmt.setInt(2, score.getScore());
            stmt.setInt(3, score.getTotalQuestions());
            stmt.setString(4, score.getDifficulty());
            stmt.setString(5, score.getMode());
            stmt.setInt(6, score.getTimeTaken());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                updateUserQuizCount(score.getUserId());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
        
        return false;
    }
    
    private void updateUserQuizCount(int userId) {
        String query = "UPDATE users SET total_quizzes_taken = total_quizzes_taken + 1 WHERE user_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error updating quiz count: " + e.getMessage());
        }
    }
    
    public List<Score> getUserScores(int userId) {
        List<Score> scores = new ArrayList<>();
        String query = "SELECT * FROM scores WHERE user_id = ? ORDER BY quiz_date DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getInt("score_id"));
                score.setUserId(rs.getInt("user_id"));
                score.setScore(rs.getInt("score"));
                score.setTotalQuestions(rs.getInt("total_questions"));
                score.setDifficulty(rs.getString("difficulty"));
                score.setMode(rs.getString("mode"));
                score.setTimeTaken(rs.getInt("time_taken"));
                score.setQuizDate(rs.getTimestamp("quiz_date"));
                
                scores.add(score);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching user scores: " + e.getMessage());
        }
        
        return scores;
    }
    
    public List<Score> getLeaderboard() {
        List<Score> leaderboard = new ArrayList<>();
        String query = "SELECT s.*, u.username " +
                      "FROM scores s " +
                      "JOIN users u ON s.user_id = u.user_id " +
                      "ORDER BY s.score DESC, s.time_taken ASC " +
                      "LIMIT 10";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getInt("score_id"));
                score.setUserId(rs.getInt("user_id"));
                score.setUsername(rs.getString("username"));
                score.setScore(rs.getInt("score"));
                score.setTotalQuestions(rs.getInt("total_questions"));
                score.setDifficulty(rs.getString("difficulty"));
                score.setMode(rs.getString("mode"));
                score.setTimeTaken(rs.getInt("time_taken"));
                score.setQuizDate(rs.getTimestamp("quiz_date"));
                
                leaderboard.add(score);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        
        return leaderboard;
    }
    
    public int calculateTimeLimit(int questionCount, String difficulty) {
        int baseTime;
        
        switch (difficulty.toLowerCase()) {
            case "easy":
                baseTime = 45;
                break;
            case "medium":
                baseTime = 35;
                break;
            case "hard":
                baseTime = 25;
                break;
            default:
                baseTime = 30;
        }
        
        return questionCount * baseTime;
    }
    
    /**
     * Get AI-generated questions (calls AIService)
     * UPDATED: Now accepts category parameter for multi-category support
     */
    public List<Question> getAIGeneratedQuestions(String difficulty, int count, String category) {
        AIService aiService = new AIService();
        
        System.out.println("🤖 Attempting to generate AI questions for category: " + category);
        
        // Try to generate AI questions directly (no pre-check)
        List<Question> aiQuestions = aiService.generateQuestions(difficulty, count, category);
        
        // If AI generation failed or returned no questions, fall back to database
        if (aiQuestions == null || aiQuestions.isEmpty()) {
            System.out.println("⚠️ AI generation failed. Using database questions as fallback.");
            return getQuestions(difficulty, count);
        }
        
        System.out.println("✅ Using " + aiQuestions.size() + " AI-generated questions for " + category);
        return aiQuestions;
    }
}