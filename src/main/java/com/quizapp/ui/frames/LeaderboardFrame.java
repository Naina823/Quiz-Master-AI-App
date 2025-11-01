package com.quizapp.ui.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.quizapp.models.Score;
import com.quizapp.models.User;
import com.quizapp.services.QuizService;
import com.quizapp.ui.components.GradientPanel;
import com.quizapp.ui.components.ModernButton;
import com.quizapp.ui.components.RoundedPanel;

public class LeaderboardFrame extends JFrame {
    
    private User currentUser;
    private QuizService quizService;
    
    private static final Color PRIMARY_BG = new Color(20, 25, 35);
    private static final Color CARD_BG = new Color(30, 40, 55);
    private static final Color ACCENT_BLUE = new Color(100, 140, 255);
    
    public LeaderboardFrame(User user) {
        this.currentUser = user;
        this.quizService = new QuizService();
        
        initializeFrame();
        initializeComponents();
    }
    
    private void initializeFrame() {
        setTitle("Quiz Master - Leaderboard");
        setSize(1000, 700);
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
        
        JLabel titleLabel = new JLabel("🏆 LEADERBOARD", SwingConstants.CENTER);
        titleLabel.setBounds(300, 30, 400, 50);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(50, 100, 900, 500);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(CARD_BG);
        tabbedPane.setForeground(Color.WHITE);
        
        JPanel globalPanel = createGlobalLeaderboardPanel();
        tabbedPane.addTab("🌍 Global Rankings", globalPanel);
        
        JPanel historyPanel = createPersonalHistoryPanel();
        tabbedPane.addTab("📊 My History", historyPanel);
        
        ModernButton backBtn = new ModernButton("BACK TO DASHBOARD", ACCENT_BLUE);
        backBtn.setBounds(350, 620, 300, 50);
        backBtn.addActionListener(e -> backToDashboard());
        
        mainPanel.add(titleLabel);
        mainPanel.add(tabbedPane);
        mainPanel.add(backBtn);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createGlobalLeaderboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(PRIMARY_BG);
        
        List<Score> leaderboard = quizService.getLeaderboard();
        
        if (leaderboard.isEmpty()) {
            JLabel emptyLabel = new JLabel("No scores yet. Be the first!", SwingConstants.CENTER);
            emptyLabel.setBounds(200, 150, 500, 40);
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            emptyLabel.setForeground(new Color(160, 170, 190));
            panel.add(emptyLabel);
            return panel;
        }
        
        String[] columns = {"Rank", "Player", "Score", "Difficulty", "Time", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < leaderboard.size(); i++) {
            Score s = leaderboard.get(i);
            String rank = getRankDisplay(i + 1);
            String scoreDisplay = s.getScore() + "/" + s.getTotalQuestions() + 
                " (" + String.format("%.1f%%", s.getPercentage()) + ")";
            String date = s.getQuizDate().toString().substring(0, 10);
            
            model.addRow(new Object[]{
                rank,
                s.getUsername(),
                scoreDisplay,
                s.getDifficulty(),
                s.getFormattedTime(),
                date
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 860, 420);
        scrollPane.getViewport().setBackground(PRIMARY_BG);
        scrollPane.setBorder(null);
        
        panel.add(scrollPane);
        return panel;
    }
    
    private JPanel createPersonalHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(PRIMARY_BG);
        
        List<Score> userScores = quizService.getUserScores(currentUser.getUserId());
        
        if (userScores.isEmpty()) {
            JLabel emptyLabel = new JLabel("No quiz history yet. Take a quiz to get started!", 
                SwingConstants.CENTER);
            emptyLabel.setBounds(200, 150, 500, 40);
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            emptyLabel.setForeground(new Color(160, 170, 190));
            panel.add(emptyLabel);
            return panel;
        }
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBounds(50, 20, 800, 80);
        statsPanel.setOpaque(false);
        
        int totalQuizzes = userScores.size();
        double avgScore = userScores.stream()
            .mapToDouble(Score::getPercentage)
            .average()
            .orElse(0);
        double bestScore = userScores.stream()
            .mapToDouble(Score::getPercentage)
            .max()
            .orElse(0);
        
        addStatCard(statsPanel, "Total Quizzes", String.valueOf(totalQuizzes), "📝");
        addStatCard(statsPanel, "Average Score", String.format("%.1f%%", avgScore), "📊");
        addStatCard(statsPanel, "Best Score", String.format("%.1f%%", bestScore), "🏆");
        
        String[] columns = {"Date", "Score", "Difficulty", "Mode", "Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Score s : userScores) {
            String date = s.getQuizDate().toString().substring(0, 16);
            String scoreDisplay = s.getScore() + "/" + s.getTotalQuestions() + 
                " (" + String.format("%.1f%%", s.getPercentage()) + ")";
            
            model.addRow(new Object[]{
                date,
                scoreDisplay,
                s.getDifficulty(),
                s.getMode(),
                s.getFormattedTime()
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 120, 860, 320);
        scrollPane.getViewport().setBackground(PRIMARY_BG);
        scrollPane.setBorder(null);
        
        panel.add(statsPanel);
        panel.add(scrollPane);
        return panel;
    }
    
    private void addStatCard(JPanel parent, String title, String value, String emoji) {
        RoundedPanel card = new RoundedPanel(10);
        card.setBackground(CARD_BG);
        card.setLayout(new BorderLayout(10, 5));
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(160, 170, 190));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(valueLabel);
        textPanel.add(titleLabel);
        
        card.add(emojiLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        
        parent.add(card);
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(35, 45, 60));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(60, 80, 110));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(50, 60, 75));
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(CARD_BG);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(new Color(35, 45, 60));
        centerRenderer.setForeground(Color.WHITE);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    private String getRankDisplay(int rank) {
        switch (rank) {
            case 1: return "🥇 1st";
            case 2: return "🥈 2nd";
            case 3: return "🥉 3rd";
            default: return "#" + rank;
        }
    }
    
    private void backToDashboard() {
        new DashboardFrame(currentUser).setVisible(true);
        dispose();
    }
}