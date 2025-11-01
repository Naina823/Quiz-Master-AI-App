package com.quizapp;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.quizapp.config.DatabaseConfig;
import com.quizapp.ui.frames.WelcomeFrame;

public class Main {
    
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                
                customizeUIDefaults();
                
                if (DatabaseConfig.testConnection()) {
                    System.out.println("✓ Database connection successful!");
                    
                    WelcomeFrame welcomeFrame = new WelcomeFrame();
                    welcomeFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Database connection failed!\nPlease check MySQL configuration.",
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                    "Failed to initialize application: " + e.getMessage(),
                    "Initialization Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private static void customizeUIDefaults() {
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ProgressBar.arc", 10);
        
        UIManager.put("Button.default.background", new java.awt.Color(100, 140, 255));
        UIManager.put("Button.default.foreground", java.awt.Color.WHITE);
        
        UIManager.put("Panel.background", new java.awt.Color(30, 35, 45));
        
        java.awt.Font defaultFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        UIManager.put("defaultFont", defaultFont);
    }
}