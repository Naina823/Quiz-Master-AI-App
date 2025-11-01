package com.quizapp.ui.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.quizapp.models.User;
import com.quizapp.services.AuthService;
import com.quizapp.ui.components.GradientPanel;
import com.quizapp.ui.components.ModernButton;
import com.quizapp.ui.components.RoundedPanel;

public class LoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthService authService;
    
    private static final Color PRIMARY_BG = new Color(20, 25, 35);
    private static final Color CARD_BG = new Color(30, 40, 55);
    private static final Color ACCENT_BLUE = new Color(100, 140, 255);
    
    public LoginFrame() {
        this.authService = new AuthService();
        initializeFrame();
        initializeComponents();
    }
    
    private void initializeFrame() {
        setTitle("Quiz Master - Login");
        setSize(900, 600);
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
        
        RoundedPanel loginCard = new RoundedPanel(20);
        loginCard.setBackground(CARD_BG);
        loginCard.setBounds(250, 100, 400, 400);
        loginCard.setLayout(null);
        
        JLabel titleLabel = new JLabel("Welcome Back!", SwingConstants.CENTER);
        titleLabel.setBounds(50, 30, 300, 40);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(50, 100, 300, 25);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(new Color(180, 190, 210));
        
        usernameField = createStyledTextField();
        usernameField.setBounds(50, 130, 300, 40);
        
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(50, 185, 300, 25);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(new Color(180, 190, 210));
        
        passwordField = new JPasswordField();
        stylePasswordField(passwordField);
        passwordField.setBounds(50, 215, 300, 40);
        
        ModernButton loginBtn = new ModernButton("LOGIN", ACCENT_BLUE);
        loginBtn.setBounds(50, 280, 300, 45);
        loginBtn.addActionListener(e -> handleLogin());
        
        JButton registerLink = new JButton("Don't have an account? Register");
        registerLink.setBounds(75, 340, 250, 30);
        registerLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        registerLink.setForeground(ACCENT_BLUE);
        registerLink.setContentAreaFilled(false);
        registerLink.setBorderPainted(false);
        registerLink.setFocusPainted(false);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.addActionListener(e -> openRegisterFrame());
        
        JButton backBtn = new JButton("← Back");
        backBtn.setBounds(20, 20, 80, 30);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setForeground(new Color(180, 190, 210));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            new WelcomeFrame().setVisible(true);
            dispose();
        });
        
        loginCard.add(titleLabel);
        loginCard.add(userLabel);
        loginCard.add(usernameField);
        loginCard.add(passLabel);
        loginCard.add(passwordField);
        loginCard.add(loginBtn);
        loginCard.add(registerLink);
        
        mainPanel.add(loginCard);
        mainPanel.add(backBtn);
        
        setContentPane(mainPanel);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(40, 50, 65));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 90), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }
    
    private void stylePasswordField(JPasswordField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(40, 50, 65));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 90), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields");
            return;
        }
        
        User user = authService.login(username, password);
        
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                "Welcome back, " + user.getUsername() + "!",
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
            new DashboardFrame(user).setVisible(true);
            dispose();
        } else {
            showError("Invalid username or password");
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Login Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void openRegisterFrame() {
        new RegisterFrame().setVisible(true);
        dispose();
    }
}