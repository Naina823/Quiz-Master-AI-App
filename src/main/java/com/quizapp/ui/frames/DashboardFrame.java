package com.quizapp.ui.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.quizapp.models.User;
import com.quizapp.ui.components.GradientPanel;
import com.quizapp.ui.components.ModernButton;
import com.quizapp.ui.components.RoundedPanel;

public class DashboardFrame extends JFrame {
    
    private User currentUser;
    
    private static final Color PRIMARY_BG = new Color(20, 25, 35);
    private static final Color CARD_BG = new Color(30, 40, 55);
    private static final Color ACCENT_BLUE = new Color(100, 140, 255);
    private static final Color ACCENT_GREEN = new Color(80, 200, 120);
    private static final Color ACCENT_ORANGE = new Color(251, 146, 60);
    private static final Color ACCENT_PURPLE = new Color(168, 85, 247);
    
    public DashboardFrame(User user) {
        this.currentUser = user;
        initializeFrame();
        initializeComponents();
    }
    
    private void initializeFrame() {
        setTitle("Quiz Master - Dashboard");
        setSize(1200, 800);
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
        
        addHeader(mainPanel);
        addWelcomeSection(mainPanel);
        addStatsCards(mainPanel);
        addQuickActions(mainPanel);
        addMenuOptions(mainPanel);
        
        setContentPane(mainPanel);
    }
    
    private void addHeader(JPanel parent) {
        JLabel appTitle = new JLabel("QUIZ MASTER");
        appTitle.setBounds(30, 20, 200, 35);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appTitle.setForeground(ACCENT_BLUE);
        
        JLabel userLabel = new JLabel("Welcome, " + currentUser.getUsername());
        userLabel.setBounds(900, 25, 200, 25);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(new Color(180, 190, 210));
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(1080, 20, 80, 30);
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutBtn.setForeground(new Color(239, 68, 68));
        logoutBtn.setBackground(new Color(40, 50, 65));
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> logout());
        
        parent.add(appTitle);
        parent.add(userLabel);
        parent.add(logoutBtn);
    }
    
    private void addWelcomeSection(JPanel parent) {
        JLabel welcomeText = new JLabel("Ready to Test Your Knowledge?");
        welcomeText.setBounds(50, 80, 600, 40);
        welcomeText.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeText.setForeground(Color.WHITE);
        
        JLabel subText = new JLabel("Choose a mode to start your quiz journey");
        subText.setBounds(50, 125, 600, 25);
        subText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subText.setForeground(new Color(160, 170, 190));
        
        parent.add(welcomeText);
        parent.add(subText);
    }
    
    private void addStatsCards(JPanel parent) {
        int cardWidth = 240;
        int cardHeight = 140;
        int startX = 50;
        int startY = 180;
        int gap = 30;
        
        createStatCard(parent, "Quizzes Taken", 
            String.valueOf(currentUser.getTotalQuizzesTaken()),
            "📚", ACCENT_GREEN, startX, startY);
        
        createStatCard(parent, "Best Score",
            "85%",
            "🏆", ACCENT_BLUE, startX + cardWidth + gap, startY);
        
        createStatCard(parent, "Current Streak",
            "5 days",
            "🔥", ACCENT_ORANGE, startX + (cardWidth + gap) * 2, startY);
        
        createStatCard(parent, "Your Rank",
            "#12",
            "🎯", ACCENT_PURPLE, startX + (cardWidth + gap) * 3, startY);
    }
    
    private void createStatCard(JPanel parent, String title, String value, 
                                String emoji, Color accentColor, int x, int y) {
        RoundedPanel card = new RoundedPanel(15);
        card.setBackground(CARD_BG);
        card.setBounds(x, y, 240, 140);
        card.setLayout(null);
        
        JLabel iconLabel = new JLabel(emoji);
        iconLabel.setBounds(20, 20, 50, 50);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setBounds(20, 70, 200, 35);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(20, 105, 200, 20);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(160, 170, 190));
        
        JPanel accentLine = new JPanel();
        accentLine.setBounds(0, 0, 5, 140);
        accentLine.setBackground(accentColor);
        
        card.add(iconLabel);
        card.add(valueLabel);
        card.add(titleLabel);
        card.add(accentLine);
        
        parent.add(card);
    }
    
    private void addQuickActions(JPanel parent) {
        JLabel sectionTitle = new JLabel("Quick Start");
        sectionTitle.setBounds(50, 360, 300, 30);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(Color.WHITE);
        parent.add(sectionTitle);
        
        int cardWidth = 340;
        int cardHeight = 220;
        int startX = 50;
        int startY = 410;
        int gap = 40;
        
        createModeCard(parent, "Offline Quiz",
            "Practice with pre-stored questions",
            "📚 Database Questions\n⚡ Instant Start\n🎯 All Difficulties",
            ACCENT_BLUE, startX, startY, () -> startOfflineQuiz());
        
        createModeCard(parent, "Online Quiz (AI)",
            "Dynamic AI-generated questions",
            "🤖 AI-Powered\n🔄 Fresh Questions\n🎲 8 Categories",
            ACCENT_GREEN, startX + cardWidth + gap, startY, () -> startOnlineQuiz());
        
        createModeCard(parent, "Leaderboard",
            "View top scores and rankings",
            "🏆 Top Players\n📊 Your History\n🎖️ Achievements",
            ACCENT_ORANGE, startX + (cardWidth + gap) * 2, startY, () -> openLeaderboard());
    }
    
    private void createModeCard(JPanel parent, String title, String description,
                               String features, Color accentColor, int x, int y,
                               Runnable action) {
        RoundedPanel card = new RoundedPanel(15);
        card.setBackground(CARD_BG);
        card.setBounds(x, y, 340, 220);
        card.setLayout(null);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(25, 20, 290, 30);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setBounds(25, 55, 290, 20);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(new Color(160, 170, 190));
        
        JTextArea featuresArea = new JTextArea(features);
        featuresArea.setBounds(25, 90, 290, 70);
        featuresArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        featuresArea.setForeground(new Color(180, 190, 210));
        featuresArea.setBackground(CARD_BG);
        featuresArea.setEditable(false);
        featuresArea.setLineWrap(true);
        featuresArea.setWrapStyleWord(true);
        
        ModernButton startBtn = new ModernButton("START", accentColor);
        startBtn.setBounds(25, 165, 290, 40);
        startBtn.addActionListener(e -> action.run());
        
        card.add(titleLabel);
        card.add(descLabel);
        card.add(featuresArea);
        card.add(startBtn);
        
        parent.add(card);
    }
    
    private void addMenuOptions(JPanel parent) {
        int btnY = 680;
        int btnWidth = 180;
        int gap = 30;
        int startX = 50;
        
        ModernButton profileBtn = new ModernButton("My Profile", new Color(100, 140, 255));
        profileBtn.setBounds(startX, btnY, btnWidth, 45);
        profileBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Profile feature coming soon!"));
        
        ModernButton settingsBtn = new ModernButton("Settings", new Color(100, 140, 255));
        settingsBtn.setBounds(startX + btnWidth + gap, btnY, btnWidth, 45);
        settingsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Settings feature coming soon!"));
        
        ModernButton helpBtn = new ModernButton("Help & FAQ", new Color(100, 140, 255));
        helpBtn.setBounds(startX + (btnWidth + gap) * 2, btnY, btnWidth, 45);
        helpBtn.addActionListener(e -> showHelp());
        
        parent.add(profileBtn);
        parent.add(settingsBtn);
        parent.add(helpBtn);
    }
    
    private void startOfflineQuiz() {
        String[] difficulties = {"Easy", "Medium", "Hard"};
        String difficulty = (String) JOptionPane.showInputDialog(
            this,
            "Select Difficulty Level:",
            "Start Offline Quiz",
            JOptionPane.QUESTION_MESSAGE,
            null,
            difficulties,
            difficulties[0]
        );
        
        if (difficulty != null) {
            String[] counts = {"10 Questions", "15 Questions", "20 Questions"};
            String countStr = (String) JOptionPane.showInputDialog(
                this,
                "Select Number of Questions:",
                "Start Offline Quiz",
                JOptionPane.QUESTION_MESSAGE,
                null,
                counts,
                counts[0]
            );
            
            if (countStr != null) {
                int questionCount = Integer.parseInt(countStr.split(" ")[0]);
                
                new QuizFrame(currentUser, difficulty, "Offline", questionCount).setVisible(true);
                dispose();
            }
        }
    }
    
    private void startOnlineQuiz() {
        // Step 1: Select Category
        String[] categories = {
            "General Knowledge", 
            "Science & Technology", 
            "History & Geography",
            "Sports & Games",
            "Movies & Entertainment",
            "Literature & Arts",
            "Current Affairs",
            "Mathematics & Logic"
        };
        
        String category = (String) JOptionPane.showInputDialog(
            this,
            "🎯 Select Quiz Category:\n🤖 AI will generate unique questions!",
            "Choose Category",
            JOptionPane.QUESTION_MESSAGE,
            null,
            categories,
            categories[0]
        );
        
        if (category == null) return;
        
        // Step 2: Select Difficulty
        String[] difficulties = {"Easy", "Medium", "Hard"};
        String difficulty = (String) JOptionPane.showInputDialog(
            this,
            "Select Difficulty Level:\n📚 Category: " + category,
            "AI-Powered Quiz",
            JOptionPane.QUESTION_MESSAGE,
            null,
            difficulties,
            difficulties[0]
        );
        
        if (difficulty == null) return;
        
        // Step 3: Select Question Count
        String[] counts = {"5 Questions", "10 Questions"};
        String countStr = (String) JOptionPane.showInputDialog(
            this,
            "Select Number of Questions:\n" +
            "📚 Category: " + category + "\n" +
            "💪 Difficulty: " + difficulty + "\n\n" +
            "⚠️ AI generation may take 10-30 seconds",
            "AI-Powered Quiz",
            JOptionPane.QUESTION_MESSAGE,
            null,
            counts,
            counts[0]
        );
        
        if (countStr == null) return;
        
        int questionCount = Integer.parseInt(countStr.split(" ")[0]);
        
        // Show loading message
        JOptionPane.showMessageDialog(this,
            "🤖 AI is generating " + questionCount + " unique questions...\n\n" +
            "📚 Category: " + category + "\n" +
            "💪 Difficulty: " + difficulty + "\n\n" +
            "This may take 10-30 seconds. Please wait.",
            "Generating Questions",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Start quiz with selected category
        new QuizFrame(currentUser, difficulty, "Online", questionCount, category).setVisible(true);
        dispose();
    }
    
    private void openLeaderboard() {
        new LeaderboardFrame(currentUser).setVisible(true);
        dispose();
    }
    
    private void showHelp() {
        String helpText = "📚 QUIZ MASTER - HELP GUIDE\n\n" +
            "HOW TO PLAY:\n" +
            "1. Choose between Offline or Online mode\n" +
            "2. Select your difficulty level\n" +
            "3. Choose number of questions\n" +
            "4. Answer questions within time limit\n" +
            "5. View your score and leaderboard ranking\n\n" +
            "SCORING:\n" +
            "• Each correct answer = 1 point\n" +
            "• No negative marking\n" +
            "• Bonus for completing faster\n\n" +
            "DIFFICULTY LEVELS:\n" +
            "• Easy: Basic questions, more time\n" +
            "• Medium: Moderate difficulty\n" +
            "• Hard: Challenging questions, less time\n\n" +
            "Good luck! 🎯";
        
        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textArea.setBackground(CARD_BG);
        textArea.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 350));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Help & Guide",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            new WelcomeFrame().setVisible(true);
            dispose();
        }
    }
}