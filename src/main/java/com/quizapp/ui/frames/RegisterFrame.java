package com.quizapp.ui.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.quizapp.services.AuthService;
import com.quizapp.ui.components.GradientPanel;
import com.quizapp.ui.components.ModernButton;
import com.quizapp.ui.components.RoundedPanel;

public class RegisterFrame extends JFrame {
    
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private AuthService authService;
    
    private static final Color CARD_BG = new Color(30, 40, 55);
    private static final Color ACCENT_GREEN = new Color(80, 200, 120);
    
    public RegisterFrame() {
        this.authService = new AuthService();
        initializeFrame();
        initializeComponents();
    }
    
    private void initializeFrame() {
        setTitle("Quiz Master - Register");
        setSize(900, 700);
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
        
        RoundedPanel registerCard = new RoundedPanel(20);
        registerCard.setBackground(CARD_BG);
        registerCard.setBounds(225, 80, 450, 520);
        registerCard.setLayout(null);
        
        JLabel titleLabel = new JLabel("Create Account", SwingConstants.CENTER);
        titleLabel.setBounds(50, 30, 350, 40);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        addLabel(registerCard, "Username", 50, 100);
        usernameField = createStyledTextField();
        usernameField.setBounds(50, 130, 350, 40);
        
        addLabel(registerCard, "Email", 50, 185);
        emailField = createStyledTextField();
        emailField.setBounds(50, 215, 350, 40);
        
        addLabel(registerCard, "Password", 50, 270);
        passwordField = createStyledPasswordField();
        passwordField.setBounds(50, 300, 350, 40);
        
        addLabel(registerCard, "Confirm Password", 50, 355);
        confirmPasswordField = createStyledPasswordField();
        confirmPasswordField.setBounds(50, 385, 350, 40);
        
        ModernButton registerBtn = new ModernButton("CREATE ACCOUNT", ACCENT_GREEN);
        registerBtn.setBounds(50, 445, 350, 45);
        registerBtn.addActionListener(e -> handleRegister());
        
        JButton loginLink = new JButton("Already have an account? Login");
        loginLink.setBounds(100, 500, 250, 30);
        loginLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginLink.setForeground(new Color(100, 140, 255));
        loginLink.setContentAreaFilled(false);
        loginLink.setBorderPainted(false);
        loginLink.setFocusPainted(false);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addActionListener(e -> openLoginFrame());
        
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
        
        registerCard.add(titleLabel);
        registerCard.add(usernameField);
        registerCard.add(emailField);
        registerCard.add(passwordField);
        registerCard.add(confirmPasswordField);
        registerCard.add(registerBtn);
        registerCard.add(loginLink);
        
        mainPanel.add(registerCard);
        mainPanel.add(backBtn);
        
        setContentPane(mainPanel);
    }
    
    private void addLabel(JPanel parent, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 300, 25);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(180, 190, 210));
        parent.add(label);
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
    
    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
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
    
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields");
            return;
        }
        
        if (username.length() < 3) {
            showError("Username must be at least 3 characters");
            return;
        }
        
        if (!isValidEmail(email)) {
            showError("Please enter a valid email address");
            return;
        }
        
        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return;
        }
        
        boolean success = authService.register(username, email, password);
        
        if (success) {
            JOptionPane.showMessageDialog(this,
                "Account created successfully!\nPlease login with your credentials.",
                "Registration Successful",
                JOptionPane.INFORMATION_MESSAGE);
            openLoginFrame();
        } else {
            showError("Username already exists. Please choose another.");
        }
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Registration Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void openLoginFrame() {
        new LoginFrame().setVisible(true);
        dispose();
    }
}