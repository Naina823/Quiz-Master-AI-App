package com.quizapp.ui.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.quizapp.models.Question;
import com.quizapp.models.Score;
import com.quizapp.models.User;
import com.quizapp.services.QuizService;
import com.quizapp.ui.components.GradientPanel;
import com.quizapp.ui.components.ModernButton;
import com.quizapp.ui.components.RoundedPanel;

public class QuizFrame extends JFrame {
    
    private User currentUser;
    private String difficulty;
    private String mode;
    private int totalQuestions;
    private String category; // NEW: Category field
    
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeRemaining;
    private Timer quizTimer;
    private long startTime;
    
    private QuizService quizService;
    
    private JLabel questionNumberLabel;
    private JLabel timerLabel;
    private JLabel questionTextLabel;
    private ButtonGroup optionsGroup;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ModernButton nextButton;
    private JProgressBar progressBar;
    
    private static final Color CARD_BG = new Color(30, 40, 55);
    private static final Color ACCENT_BLUE = new Color(100, 140, 255);
    private static final Color ACCENT_GREEN = new Color(80, 200, 120);
    
    // NEW: Constructor with category parameter (for Online mode)
    public QuizFrame(User user, String difficulty, String mode, int questionCount, String category) {
        this.currentUser = user;
        this.difficulty = difficulty;
        this.mode = mode;
        this.totalQuestions = questionCount;
        this.category = category;
        this.quizService = new QuizService();
        
        loadQuestions();
        
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "No questions available for this difficulty!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        initializeFrame();
        initializeComponents();
        startQuiz();
    }
    
    // OLD: Constructor without category (for Offline mode - backward compatibility)
    public QuizFrame(User user, String difficulty, String mode, int questionCount) {
        this(user, difficulty, mode, questionCount, "General Knowledge");
    }
   
    // UPDATED: Load questions method with category support
    private void loadQuestions() {
        if (mode.equals("Online")) {
            System.out.println("🤖 Fetching AI-generated questions for: " + category);
            questions = quizService.getAIGeneratedQuestions(difficulty, totalQuestions, category);
        } else {
            System.out.println("📚 Fetching database questions...");
            questions = quizService.getQuestions(difficulty, totalQuestions);
        }
    }
    
    private void initializeFrame() {
        setTitle("Quiz Master - Quiz in Progress");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                handleQuit();
            }
        });
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initializeComponents() {
        GradientPanel mainPanel = new GradientPanel(
            new Color(15, 20, 30),
            new Color(25, 35, 50)
        );
        mainPanel.setLayout(null);
        
        addTopBar(mainPanel);
        
        RoundedPanel questionCard = new RoundedPanel(20);
        questionCard.setBackground(CARD_BG);
        questionCard.setBounds(100, 120, 800, 500);
        questionCard.setLayout(null);
        
        questionNumberLabel = new JLabel();
        questionNumberLabel.setBounds(30, 20, 300, 30);
        questionNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionNumberLabel.setForeground(ACCENT_BLUE);
        
        questionTextLabel = new JLabel();
        questionTextLabel.setBounds(30, 60, 740, 80);
        questionTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        questionTextLabel.setForeground(Color.WHITE);
        questionTextLabel.setVerticalAlignment(SwingConstants.TOP);
        
        optionsGroup = new ButtonGroup();
        
        optionA = createOptionButton();
        optionA.setBounds(30, 160, 740, 50);
        
        optionB = createOptionButton();
        optionB.setBounds(30, 220, 740, 50);
        
        optionC = createOptionButton();
        optionC.setBounds(30, 280, 740, 50);
        
        optionD = createOptionButton();
        optionD.setBounds(30, 340, 740, 50);
        
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);
        
        nextButton = new ModernButton("NEXT QUESTION", ACCENT_GREEN);
        nextButton.setBounds(550, 420, 220, 50);
        nextButton.addActionListener(e -> handleNextQuestion());
        
        progressBar = new JProgressBar(0, totalQuestions);
        progressBar.setBounds(30, 420, 500, 20);
        progressBar.setForeground(ACCENT_BLUE);
        progressBar.setBackground(new Color(40, 50, 65));
        progressBar.setBorderPainted(false);
        
        questionCard.add(questionNumberLabel);
        questionCard.add(questionTextLabel);
        questionCard.add(optionA);
        questionCard.add(optionB);
        questionCard.add(optionC);
        questionCard.add(optionD);
        questionCard.add(nextButton);
        questionCard.add(progressBar);
        
        mainPanel.add(questionCard);
        setContentPane(mainPanel);
    }
    
    private void addTopBar(JPanel parent) {
        JLabel titleLabel = new JLabel("QUIZ IN PROGRESS");
        titleLabel.setBounds(30, 20, 300, 30);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        timerLabel = new JLabel();
        timerLabel.setBounds(700, 20, 200, 40);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        timerLabel.setForeground(ACCENT_GREEN);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JLabel difficultyLabel = new JLabel(difficulty.toUpperCase());
        difficultyLabel.setBounds(400, 25, 100, 30);
        difficultyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        difficultyLabel.setForeground(Color.WHITE);
        difficultyLabel.setOpaque(true);
        difficultyLabel.setBackground(getDifficultyColor());
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        difficultyLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        JButton quitBtn = new JButton("Quit Quiz");
        quitBtn.setBounds(900, 20, 80, 30);
        quitBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        quitBtn.setForeground(new Color(239, 68, 68));
        quitBtn.setBackground(new Color(40, 50, 65));
        quitBtn.setBorderPainted(false);
        quitBtn.setFocusPainted(false);
        quitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitBtn.addActionListener(e -> handleQuit());
        
        parent.add(titleLabel);
        parent.add(timerLabel);
        parent.add(difficultyLabel);
        parent.add(quitBtn);
    }
    
    private JRadioButton createOptionButton() {
        JRadioButton btn = new JRadioButton();
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(40, 50, 65));
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 70, 90), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return btn;
    }
    
    private Color getDifficultyColor() {
        switch (difficulty.toLowerCase()) {
            case "easy": return ACCENT_GREEN;
            case "medium": return new Color(251, 146, 60);
            case "hard": return new Color(239, 68, 68);
            default: return ACCENT_BLUE;
        }
    }
    
    private void startQuiz() {
        startTime = System.currentTimeMillis();
        timeRemaining = quizService.calculateTimeLimit(totalQuestions, difficulty);
        
        quizTimer = new Timer(1000, e -> updateTimer());
        quizTimer.start();
        
        displayQuestion();
    }
    
    private void displayQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            finishQuiz();
            return;
        }
        
        Question q = questions.get(currentQuestionIndex);
        
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + totalQuestions);
        questionTextLabel.setText("<html>" + q.getQuestionText() + "</html>");
        
        optionA.setText("A. " + q.getOptionA());
        optionB.setText("B. " + q.getOptionB());
        optionC.setText("C. " + q.getOptionC());
        optionD.setText("D. " + q.getOptionD());
        
        optionsGroup.clearSelection();
        
        progressBar.setValue(currentQuestionIndex + 1);
        
        if (currentQuestionIndex == totalQuestions - 1) {
            nextButton.setText("FINISH QUIZ");
        }
    }
    
    private void updateTimer() {
        timeRemaining--;
        
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        
        if (timeRemaining <= 30) {
            timerLabel.setForeground(new Color(239, 68, 68));
        }
        
        if (timeRemaining <= 0) {
            quizTimer.stop();
            JOptionPane.showMessageDialog(this,
                "Time's up!",
                "Quiz Ended",
                JOptionPane.WARNING_MESSAGE);
            finishQuiz();
        }
    }
    
    private void handleNextQuestion() {
        if (optionsGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this,
                "Please select an answer!",
                "No Answer Selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String selectedAnswer = getSelectedAnswer();
        Question currentQuestion = questions.get(currentQuestionIndex);
        
        if (currentQuestion.isCorrect(selectedAnswer)) {
            score++;
        }
        
        currentQuestionIndex++;
        
        if (currentQuestionIndex < totalQuestions) {
            displayQuestion();
        } else {
            finishQuiz();
        }
    }
    
    private String getSelectedAnswer() {
        if (optionA.isSelected()) return "A";
        if (optionB.isSelected()) return "B";
        if (optionC.isSelected()) return "C";
        if (optionD.isSelected()) return "D";
        return "";
    }
    
    private void finishQuiz() {
        quizTimer.stop();
        
        long endTime = System.currentTimeMillis();
        int timeTaken = (int) ((endTime - startTime) / 1000);
        
        Score scoreObj = new Score(
            currentUser.getUserId(),
            score,
            totalQuestions,
            difficulty,
            mode,
            timeTaken
        );
        
        quizService.saveScore(scoreObj);
        
        new ResultFrame(currentUser, scoreObj, questions).setVisible(true);
        dispose();
    }
    
    private void handleQuit() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to quit?\nYour progress will not be saved.",
            "Quit Quiz",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            quizTimer.stop();
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        }
    }
}