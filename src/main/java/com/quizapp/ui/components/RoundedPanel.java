package com.quizapp.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    
    private int cornerRadius;
    private Color shadowColor = new Color(0, 0, 0, 50);
    
    public RoundedPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(shadowColor);
        g2d.fill(new RoundRectangle2D.Float(5, 5, getWidth() - 5, getHeight() - 5, cornerRadius, cornerRadius));
        
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 5, getHeight() - 5, cornerRadius, cornerRadius));
        
        g2d.dispose();
        super.paintComponent(g);
    }
}