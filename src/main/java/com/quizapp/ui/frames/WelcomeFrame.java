package com.quizapp.ui.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.quizapp.ui.components.GradientPanel;
import com.quizapp.ui.components.ModernButton;

public class WelcomeFrame extends JFrame {
    
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 700;
    
    private static final Color PRIMARY_BG = new Color(20, 25, 35);
    private static final Color ACCENT_BLUE = new Color(100, 140, 255);
    private static final Color ACCENT_GREEN = new Color(80, 200, 120);
    
    public WelcomeFrame() {
        initializeFrame();
        initializeComponents();
    }
    
    private void initializeFrame() {
        setTitle("Quiz Master - Welcome");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initializeComponents() {
        GradientPanel mainPanel = new GradientPanel(
            new Color(20, 25, 35),
            new Color(30, 40, 60)
        );
        mainPanel.setLayout(null);
        
        JLabel titleLabel = new JLabel("QUIZ MASTER", SwingConstants.CENTER);
        titleLabel.setBounds(250, 150, 500, 80);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 56));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Test Your Knowledge, Challenge Yourself", SwingConstants.CENTER);
        subtitleLabel.setBounds(250, 240, 500, 30);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(160, 170, 190));
        
        JPanel descPanel = createDescriptionPanel();
        descPanel.setBounds(200, 300, 600, 150);
        
        ModernButton loginBtn = new ModernButton("LOGIN", ACCENT_BLUE);
        loginBtn.setBounds(300, 480, 180, 50);
        loginBtn.addActionListener(e -> openLoginFrame());
        
        ModernButton registerBtn = new ModernButton("REGISTER", ACCENT_GREEN);
        registerBtn.setBounds(520, 480, 180, 50);
        registerBtn.addActionListener(e -> openRegisterFrame());
        
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(450, 560, 100, 35);
        exitBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exitBtn.setForeground(new Color(200, 100, 100));
        exitBtn.setBackground(PRIMARY_BG);
        exitBtn.setBorderPainted(false);
        exitBtn.setFocusPainted(false);
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitBtn.addActionListener(e -> System.exit(0));
        
        mainPanel.add(titleLabel);
        mainPanel.add(subtitleLabel);
        mainPanel.add(descPanel);
        mainPanel.add(loginBtn);
        mainPanel.add(registerBtn);
        mainPanel.add(exitBtn);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createDescriptionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 0, 15));
        panel.setOpaque(false);
        
        String[] features = {
            "🎯  Multiple Difficulty Levels",
            "⏱️  Timed Challenges & Leaderboards",
            "🤖  AI-Powered Question Generation"
        };
        
        for (String feature : features) {
            JLabel label = new JLabel(feature, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            label.setForeground(new Color(180, 190, 210));
            panel.add(label);
        }
        
        return panel;
    }
    
    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.dispose();
    }
    
    private void openRegisterFrame() {
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
        this.dispose();
    }
}