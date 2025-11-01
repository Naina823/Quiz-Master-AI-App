package com.quizapp.ui.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.quizapp.models.Question;
import com.quizapp.models.Score;
import com.quizapp.models.User;
import com.quizapp.ui.components.GradientPanel;
import com.quizapp.ui.components.ModernButton;
import com.quizapp.ui.components.RoundedPanel;

public class ResultFrame extends JFrame {
    
    private User currentUser;
    private Score score;
    private List<Question> questions;
    
    private static final Color CARD_BG = new Color(30, 40, 55);
    private static final Color ACCENT_BLUE = new Color(100, 140, 255);
    private static final Color ACCENT_GREEN = new Color(80, 200, 120);
    private static final Color ACCENT_RED = new Color(239, 68, 68);
    
    public ResultFrame(User user, Score score, List<Question> questions) {
        this.currentUser = user;
        this.score = score;
        this.questions = questions;
        
        initializeFrame();
        initializeComponents();
    }
    
    private void initializeFrame() {
        setTitle("Quiz Master - Results");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initializeComponents() {
        GradientPanel mainPanel = new GradientPanel(
            new Color(15, 20, 30),
            new Color(25, 35, 50)
        );
        mainPanel.setLayout(null);
        
        JLabel titleLabel = new JLabel("QUIZ COMPLETED!", SwingConstants.CENTER);
        titleLabel.setBounds(200, 40, 500, 50);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        
        RoundedPanel resultsCard = new RoundedPanel(20);
        resultsCard.setBackground(CARD_BG);
        resultsCard.setBounds(150, 120, 600, 400);
        resultsCard.setLayout(null);
        
        String emoji = getPerformanceEmoji();
        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setBounds(200, 30, 200, 80);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        
        JLabel scoreLabel = new JLabel(score.getScore() + "/" + score.getTotalQuestions(), 
            SwingConstants.CENTER);
        scoreLabel.setBounds(150, 120, 300, 50);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        scoreLabel.setForeground(getScoreColor());
        
        double percentage = score.getPercentage();
        JLabel percentageLabel = new JLabel(String.format("%.1f%%", percentage), 
            SwingConstants.CENTER);
        percentageLabel.setBounds(150, 175, 300, 30);
        percentageLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        percentageLabel.setForeground(new Color(180, 190, 210));
        
        JLabel messageLabel = new JLabel(getPerformanceMessage(), SwingConstants.CENTER);
        messageLabel.setBounds(100, 220, 400, 30);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageLabel.setForeground(new Color(160, 170, 190));
        
        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 20, 15));
        statsPanel.setBounds(100, 270, 400, 100);
        statsPanel.setOpaque(false);
        
        addStatRow(statsPanel, "Difficulty:", score.getDifficulty());
        addStatRow(statsPanel, "Time Taken:", score.getFormattedTime());
        addStatRow(statsPanel, "Correct Answers:", score.getScore() + "/" + score.getTotalQuestions());
        
        resultsCard.add(emojiLabel);
        resultsCard.add(scoreLabel);
        resultsCard.add(percentageLabel);
        resultsCard.add(messageLabel);
        resultsCard.add(statsPanel);
        
        ModernButton dashboardBtn = new ModernButton("BACK TO DASHBOARD", ACCENT_BLUE);
        dashboardBtn.setBounds(150, 540, 270, 50);
        dashboardBtn.addActionListener(e -> backToDashboard());
        
        ModernButton leaderboardBtn = new ModernButton("VIEW LEADERBOARD", ACCENT_GREEN);
        leaderboardBtn.setBounds(480, 540, 270, 50);
        leaderboardBtn.addActionListener(e -> viewLeaderboard());
        
        JButton reviewBtn = new JButton("Review Answers");
        reviewBtn.setBounds(350, 610, 200, 40);
        reviewBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reviewBtn.setForeground(new Color(180, 190, 210));
        reviewBtn.setContentAreaFilled(false);
        reviewBtn.setBorderPainted(false);
        reviewBtn.setFocusPainted(false);
        reviewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reviewBtn.addActionListener(e -> reviewAnswers());
        
        mainPanel.add(titleLabel);
        mainPanel.add(resultsCard);
        mainPanel.add(dashboardBtn);
        mainPanel.add(leaderboardBtn);
        mainPanel.add(reviewBtn);
        
        setContentPane(mainPanel);
    }
    
    private void addStatRow(JPanel parent, String label, String value) {
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelComp.setForeground(new Color(160, 170, 190));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueComp.setForeground(Color.WHITE);
        
        parent.add(labelComp);
        parent.add(valueComp);
    }
    
    private String getPerformanceEmoji() {
        double percentage = score.getPercentage();
        if (percentage >= 90) return "🏆";
        if (percentage >= 75) return "🎉";
        if (percentage >= 60) return "👍";
        if (percentage >= 40) return "📚";
        return "💪";
    }
    
    private Color getScoreColor() {
        double percentage = score.getPercentage();
        if (percentage >= 75) return ACCENT_GREEN;
        if (percentage >= 50) return new Color(251, 146, 60);
        return ACCENT_RED;
    }
    
    private String getPerformanceMessage() {
        double percentage = score.getPercentage();
        if (percentage >= 90) return "Outstanding Performance!";
        if (percentage >= 75) return "Great Job!";
        if (percentage >= 60) return "Good Effort!";
        if (percentage >= 40) return "Keep Practicing!";
        return "Don't Give Up!";
    }
    
    private void reviewAnswers() {
        StringBuilder review = new StringBuilder();
        review.append("<html><body style='width: 500px; font-family: Segoe UI;'>");
        review.append("<h2 style='color: #6488FF;'>Quiz Review</h2>");
        
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            review.append("<div style='margin: 15px 0; padding: 10px; background: #1E2837; border-radius: 5px;'>");
            review.append("<b style='color: #50C878;'>Q").append(i + 1).append(":</b> ");
            review.append(q.getQuestionText()).append("<br>");
            review.append("<b style='color: #6488FF;'>Correct Answer:</b> ");
            review.append(q.getCorrectAnswer()).append(". ").append(q.getOption(q.getCorrectAnswer()));
            review.append("</div>");
        }
        
        review.append("</body></html>");
        
        JLabel reviewLabel = new JLabel(review.toString());
        JScrollPane scrollPane = new JScrollPane(reviewLabel);
        scrollPane.setPreferredSize(new Dimension(550, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Answer Review",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void backToDashboard() {
        new DashboardFrame(currentUser).setVisible(true);
        dispose();
    }
    
    private void viewLeaderboard() {
        new LeaderboardFrame(currentUser).setVisible(true);
        dispose();
    }
}