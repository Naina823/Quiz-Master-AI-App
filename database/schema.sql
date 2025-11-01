-- ================================================
-- Quiz App Database Schema
-- ================================================

CREATE DATABASE IF NOT EXISTS quiz_game_db;
USE quiz_game_db;

-- ================================================
-- 1. Users Table
-- ================================================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    total_quizzes_taken INT DEFAULT 0,
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- ================================================
-- 2. Questions Table (Offline Mode)
-- ================================================
CREATE TABLE questions (
    q_id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer ENUM('A', 'B', 'C', 'D') NOT NULL,
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    category VARCHAR(50) DEFAULT 'General',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_difficulty (difficulty),
    INDEX idx_category (category)
);

-- ================================================
-- 3. Scores Table (Leaderboard & History)
-- ================================================
CREATE TABLE scores (
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    mode ENUM('Offline', 'Online') NOT NULL,
    time_taken INT NOT NULL, -- in seconds
    quiz_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_scores (user_id, score),
    INDEX idx_leaderboard (score DESC, time_taken ASC)
);

-- ================================================
-- 4. Quiz Sessions Table (Track Active Quizzes)
-- ================================================
CREATE TABLE quiz_sessions (
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    mode ENUM('Offline', 'Online') NOT NULL,
    total_questions INT NOT NULL,
    current_question INT DEFAULT 1,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Active', 'Completed', 'Abandoned') DEFAULT 'Active',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ================================================
-- 5. Sample Questions (For Testing)
-- ================================================

-- Easy Questions
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer, difficulty, category) VALUES
('What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 'C', 'Easy', 'Geography'),
('How many continents are there?', '5', '6', '7', '8', 'C', 'Easy', 'Geography'),
('What is 5 + 3?', '6', '7', '8', '9', 'C', 'Easy', 'Math'),
('Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Saturn', 'B', 'Easy', 'Science'),
('What is the largest ocean?', 'Atlantic', 'Indian', 'Arctic', 'Pacific', 'D', 'Easy', 'Geography'),
('How many days are in a week?', '5', '6', '7', '8', 'C', 'Easy', 'General'),
('What color is the sky on a clear day?', 'Green', 'Blue', 'Red', 'Yellow', 'B', 'Easy', 'General'),
('What is the boiling point of water in Celsius?', '90', '95', '100', '105', 'C', 'Easy', 'Science'),
('Which animal is known as the King of the Jungle?', 'Tiger', 'Lion', 'Elephant', 'Bear', 'B', 'Easy', 'General'),
('How many hours are in a day?', '12', '20', '24', '30', 'C', 'Easy', 'General');

-- Medium Questions
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer, difficulty, category) VALUES
('What is the chemical symbol for Gold?', 'Go', 'Gd', 'Au', 'Ag', 'C', 'Medium', 'Science'),
('Who wrote "Romeo and Juliet"?', 'Charles Dickens', 'William Shakespeare', 'Jane Austen', 'Mark Twain', 'B', 'Medium', 'Literature'),
('What is the square root of 144?', '10', '11', '12', '13', 'C', 'Medium', 'Math'),
('In which year did World War II end?', '1943', '1944', '1945', '1946', 'C', 'Medium', 'History'),
('What is the speed of light?', '300,000 km/s', '150,000 km/s', '450,000 km/s', '600,000 km/s', 'A', 'Medium', 'Science'),
('Who painted the Mona Lisa?', 'Vincent van Gogh', 'Pablo Picasso', 'Leonardo da Vinci', 'Michelangelo', 'C', 'Medium', 'Art'),
('What is the largest planet in our solar system?', 'Saturn', 'Jupiter', 'Neptune', 'Uranus', 'B', 'Medium', 'Science'),
('Which programming language is known for web development?', 'Python', 'Java', 'JavaScript', 'C++', 'C', 'Medium', 'Technology'),
('What is the capital of Japan?', 'Seoul', 'Beijing', 'Tokyo', 'Bangkok', 'C', 'Medium', 'Geography'),
('How many bones are in the human body?', '196', '206', '216', '226', 'B', 'Medium', 'Science');

-- Hard Questions
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer, difficulty, category) VALUES
('What is the time complexity of Quick Sort in average case?', 'O(n)', 'O(n log n)', 'O(n²)', 'O(log n)', 'B', 'Hard', 'Technology'),
('Which element has the atomic number 79?', 'Silver', 'Platinum', 'Gold', 'Mercury', 'C', 'Hard', 'Science'),
('Who developed the theory of general relativity?', 'Isaac Newton', 'Niels Bohr', 'Albert Einstein', 'Stephen Hawking', 'C', 'Hard', 'Science'),
('What is the Fibonacci sequence 10th term?', '34', '55', '89', '144', 'B', 'Hard', 'Math'),
('In which year was the first computer virus created?', '1971', '1981', '1986', '1991', 'A', 'Hard', 'Technology'),
('What is the pH value of pure water?', '6', '7', '8', '9', 'B', 'Hard', 'Science'),
('Which SQL command is used to remove a table?', 'REMOVE', 'DELETE', 'DROP', 'TRUNCATE', 'C', 'Hard', 'Technology'),
('What is the most abundant gas in Earth atmosphere?', 'Oxygen', 'Nitrogen', 'Carbon Dioxide', 'Argon', 'B', 'Hard', 'Science'),
('Which design pattern is used in Java for creating objects?', 'Decorator', 'Factory', 'Observer', 'Singleton', 'B', 'Hard', 'Technology'),
('What is the capital of Bhutan?', 'Thimphu', 'Kathmandu', 'Dhaka', 'Colombo', 'A', 'Hard', 'Geography');

-- ================================================
-- Useful Views for Leaderboard
-- ================================================

-- Global Leaderboard View
CREATE VIEW global_leaderboard AS
SELECT 
    u.username,
    s.score,
    s.total_questions,
    s.difficulty,
    s.time_taken,
    s.quiz_date,
    ROUND((s.score * 100.0 / s.total_questions), 2) AS percentage
FROM scores s
JOIN users u ON s.user_id = u.user_id
ORDER BY s.score DESC, s.time_taken ASC
LIMIT 10;

-- User Personal Best View
CREATE VIEW user_best_scores AS
SELECT 
    u.user_id,
    u.username,
    MAX(s.score) AS best_score,
    s.difficulty,
    COUNT(s.score_id) AS total_attempts
FROM scores s
JOIN users u ON s.user_id = u.user_id
GROUP BY u.user_id, s.difficulty;

-- ================================================
-- Sample User (For Testing)
-- Password: "password123" (hashed with bcrypt/SHA-256)
-- ================================================
INSERT INTO users (username, email, password_hash) VALUES
('testuser', 'test@example.com', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f');
-- Note: In production, use proper password hashing (BCrypt)