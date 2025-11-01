package com.quizapp.ui.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GradientPanel extends JPanel {
    
    private Color color1;
    private Color color2;
    private GradientType gradientType;
    
    public enum GradientType {
        VERTICAL,
        HORIZONTAL,
        DIAGONAL
    }
    
    public GradientPanel(Color color1, Color color2) {
        this(color1, color2, GradientType.DIAGONAL);
    }
    
    public GradientPanel(Color color1, Color color2, GradientType type) {
        this.color1 = color1;
        this.color2 = color2;
        this.gradientType = type;
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int width = getWidth();
        int height = getHeight();
        
        GradientPaint gradient;
        
        switch (gradientType) {
            case VERTICAL:
                gradient = new GradientPaint(0, 0, color1, 0, height, color2);
                break;
            case HORIZONTAL:
                gradient = new GradientPaint(0, 0, color1, width, 0, color2);
                break;
            case DIAGONAL:
            default:
                gradient = new GradientPaint(0, 0, color1, width, height, color2);
                break;
        }
        
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }
    
    public void setColors(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        repaint();
    }
}