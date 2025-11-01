package com.quizapp.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class ModernButton extends JButton {
    
    private Color defaultColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color currentColor;
    
    private static final int ARC_SIZE = 15;
    private boolean isHovered = false;
    
    public ModernButton(String text, Color bgColor) {
        super(text);
        this.defaultColor = bgColor;
        this.hoverColor = brighten(bgColor, 1.2f);
        this.pressedColor = brighten(bgColor, 0.8f);
        this.currentColor = defaultColor;
        
        initializeButton();
        addMouseListeners();
    }
    
    private void initializeButton() {
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
    }
    
    private void addMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                currentColor = hoverColor;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                currentColor = defaultColor;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                currentColor = pressedColor;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                currentColor = isHovered ? hoverColor : defaultColor;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(currentColor);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC_SIZE, ARC_SIZE));
        
        if (isHovered) {
            g2d.setColor(new Color(255, 255, 255, 30));
            g2d.fill(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, ARC_SIZE, ARC_SIZE));
        }
        
        g2d.dispose();
        super.paintComponent(g);
    }
    
    private Color brighten(Color color, float factor) {
        int r = Math.min(255, (int) (color.getRed() * factor));
        int g = Math.min(255, (int) (color.getGreen() * factor));
        int b = Math.min(255, (int) (color.getBlue() * factor));
        return new Color(r, g, b);
    }
    
    public void setDefaultColor(Color color) {
        this.defaultColor = color;
        this.hoverColor = brighten(color, 1.2f);
        this.pressedColor = brighten(color, 0.8f);
        this.currentColor = color;
        repaint();
    }
}