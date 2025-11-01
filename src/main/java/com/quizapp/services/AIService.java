package com.quizapp.services;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.quizapp.models.Question;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIService {
    // ===== PASTE YOUR GROQ API KEY HERE =====
    // Get your FREE API key at: https://console.groq.com/keys
    private static final String GROQ_API_KEY = "YOUR_GROQ_API_KEY_HERE";
    // =========================================
    
    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    /**
     * Check if internet connection is available
     */
    public boolean isInternetAvailable() {
        // Try multiple methods to check internet
        
        // Method 1: Try to connect to Groq API directly
        try {
            Request testRequest = new Request.Builder()
                .url("https://api.groq.com")
                .head()
                .build();
            
            Response response = client.newCall(testRequest).execute();
            response.close();
            System.out.println("✅ Internet connection verified!");
            return true;
        } catch (Exception e) {
            // Continue to next method
        }
        
        // Method 2: Try Google DNS
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 3000);
            System.out.println("✅ Internet connection verified!");
            return true;
        } catch (IOException e) {
            // Continue to next method
        }
        
        // Method 3: Try Cloudflare DNS
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("1.1.1.1", 53), 3000);
            System.out.println("✅ Internet connection verified!");
            return true;
        } catch (IOException e) {
            System.err.println("⚠️ No internet connection detected");
            return false;
        }
    }

    /**
     * Generate quiz questions using Groq API (FREE!)
     */
    public List<Question> generateQuestions(String difficulty, int count, String category) {
        List<Question> questions = new ArrayList<>();
        
        System.out.println("🚀 Generating " + count + " " + difficulty + " questions using Groq AI (Llama 3)...");
        
        // Check API key
        if (GROQ_API_KEY.equals("YOUR_GROQ_API_KEY_HERE")) {
            System.err.println("❌ ERROR: Please set your Groq API key in AIService.java");
            System.err.println("🔑 Get your FREE API key at: https://console.groq.com/keys");
            System.err.println("✅ No credit card required!");
            return questions;
        }
        
        // Skip internet check - just try to call the API directly
        System.out.println("🌐 Attempting to connect to Groq API...");
        
        try {
            String prompt = buildPrompt(difficulty, count, category);
            String aiResponse = callGroqAPI(prompt);
            
            if (aiResponse != null && !aiResponse.isEmpty()) {
                questions = parseAIResponse(aiResponse, difficulty, category);
            } else {
                System.err.println("⚠️ API returned empty response");
            }
            
            if (questions.isEmpty()) {
                System.out.println("⚠️ AI generation failed. No questions parsed.");
            } else {
                System.out.println("✅ Successfully generated " + questions.size() + " questions!");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error generating AI questions: " + e.getMessage());
            e.printStackTrace();
        }
        
        return questions;
    }

    /**
     * Build the prompt for AI model
     */
    private String buildPrompt(String difficulty, int count, String category) {
        return "Generate EXACTLY " + count + " multiple choice quiz questions.\n\n" +
               "Topic: " + category + "\n" +
               "Difficulty: " + difficulty + "\n\n" +
               "CRITICAL: Use this EXACT format for EACH question:\n\n" +
               "Q1: [Question text]\n" +
               "A) [Option A]\n" +
               "B) [Option B]\n" +
               "C) [Option C]\n" +
               "D) [Option D]\n" +
               "Correct: A\n\n" +
               "Q2: [Question text]\n" +
               "A) [Option A]\n" +
               "B) [Option B]\n" +
               "C) [Option C]\n" +
               "D) [Option D]\n" +
               "Correct: B\n\n" +
               "Requirements:\n" +
               "- Generate EXACTLY " + count + " questions numbered Q1, Q2, Q3, etc.\n" +
               "- Each question MUST have exactly 4 options: A), B), C), D)\n" +
               "- Only ONE correct answer per question\n" +
               "- All options must be plausible\n" +
               "- Questions must be " + difficulty + " difficulty\n" +
               "- Topic: " + category + "\n" +
               "- DO NOT include explanations or extra text\n" +
               "- Follow the format EXACTLY\n\n" +
               "Generate " + count + " questions now:";
    }

    /**
     * Call Groq API (OpenAI compatible)
     */
    private String callGroqAPI(String prompt) {
        try {
            // Build Groq API request (OpenAI format)
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", prompt);
            
            JsonArray messages = new JsonArray();
            messages.add(userMessage);
            
            JsonObject payload = new JsonObject();
            // Updated to use current Groq model (llama3-8b-8192 is decommissioned)
            payload.addProperty("model", "llama-3.1-8b-instant"); // Current active model
            payload.add("messages", messages);
            payload.addProperty("temperature", 0.7);
            payload.addProperty("max_tokens", 2048);
            payload.addProperty("top_p", 0.9);

            RequestBody body = RequestBody.create(
                payload.toString(),
                MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(GROQ_API_URL)
                    .addHeader("Authorization", "Bearer " + GROQ_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            System.out.println("📡 Calling Groq API (Llama 3)...");
            
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "No error details";
                    System.err.println("❌ API Error: " + response.code() + " - " + response.message());
                    System.err.println("Error details: " + errorBody);
                    
                    if (response.code() == 401) {
                        System.err.println("💡 Tip: Invalid API key. Get a new one at https://console.groq.com/keys");
                    } else if (response.code() == 429) {
                        System.err.println("💡 Tip: Rate limit exceeded. Wait a moment and try again");
                    }
                    return null;
                }
                
                String responseBody = response.body().string();
                System.out.println("📥 API Response received!");
                
                // Parse Groq response (OpenAI format)
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                
                if (jsonResponse.has("choices")) {
                    JsonArray choices = jsonResponse.getAsJsonArray("choices");
                    if (choices.size() > 0) {
                        JsonObject choice = choices.get(0).getAsJsonObject();
                        JsonObject responseMessage = choice.getAsJsonObject("message");
                        return responseMessage.get("content").getAsString();
                    }
                }
                
                System.err.println("⚠️ Unexpected response format");
                return null;
            }

        } catch (Exception e) {
            System.err.println("❌ API call failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Parse AI response into Question objects
     */
    private List<Question> parseAIResponse(String aiText, String difficulty, String category) {
        List<Question> questions = new ArrayList<>();
        
        try {
            System.out.println("🔍 Parsing AI response...");
            System.out.println("=== RAW AI RESPONSE START ===");
            System.out.println(aiText);
            System.out.println("=== RAW AI RESPONSE END ===");
            
            // Split by "Q" followed by a digit and colon, keeping the delimiter
            // Use lookbehind to split AFTER "Correct: X"
            String[] parts = aiText.split("(?<=Correct: [ABCD])\\s*(?=Q\\d+:)");
            
            System.out.println("📊 Found " + parts.length + " parts after splitting");
            
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i].trim();
                if (part.isEmpty()) {
                    System.out.println("⏭️ Skipping empty part " + (i+1));
                    continue;
                }
                
                System.out.println("🔄 Parsing part " + (i+1) + ": " + part.substring(0, Math.min(30, part.length())) + "...");
                
                Question question = parseQuestion(part, difficulty, category);
                if (question != null) {
                    questions.add(question);
                    System.out.println("✓ Parsed question " + questions.size() + ": " + question.getQuestionText().substring(0, Math.min(50, question.getQuestionText().length())));
                }
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error parsing AI response: " + e.getMessage());
            e.printStackTrace();
        }
        
        return questions;
    }

    /**
     * Parse individual question from text
     */
    private Question parseQuestion(String text, String difficulty, String category) {
        try {
            String[] lines = text.split("\n");
            
            String questionText = "";
            String optionA = "";
            String optionB = "";
            String optionC = "";
            String optionD = "";
            String correctAnswer = "";
            
            System.out.println("🔎 Parsing question with " + lines.length + " lines");
            
            for (String line : lines) {
                line = line.trim();
                
                System.out.println("  Line: " + line);
                
                if (line.matches("Q\\d+:.*")) {
                    questionText = line.replaceFirst("Q\\d+:\\s*", "").trim();
                    System.out.println("  ✓ Found question text");
                } else if (line.matches("[Aa]\\).*") || line.matches("[Aa]\\..*")) {
                    optionA = line.replaceFirst("[Aa][\\)\\.]\\s*", "").trim();
                    System.out.println("  ✓ Found option A");
                } else if (line.matches("[Bb]\\).*") || line.matches("[Bb]\\..*")) {
                    optionB = line.replaceFirst("[Bb][\\)\\.]\\s*", "").trim();
                    System.out.println("  ✓ Found option B");
                } else if (line.matches("[Cc]\\).*") || line.matches("[Cc]\\..*")) {
                    optionC = line.replaceFirst("[Cc][\\)\\.]\\s*", "").trim();
                    System.out.println("  ✓ Found option C");
                } else if (line.matches("[Dd]\\).*") || line.matches("[Dd]\\..*")) {
                    optionD = line.replaceFirst("[Dd][\\)\\.]\\s*", "").trim();
                    System.out.println("  ✓ Found option D");
                } else if (line.matches("(?i)Correct:?\\s*[A-D].*") || line.matches("(?i)Answer:?\\s*[A-D].*")) {
                    String corrLine = line.replaceFirst("(?i)(Correct|Answer):?\\s*", "").trim();
                    if (corrLine.length() > 0) {
                        correctAnswer = corrLine.substring(0, 1).toUpperCase();
                        System.out.println("  ✓ Found correct answer: " + correctAnswer);
                    }
                }
            }
            
            // Validate all fields are present
            if (!questionText.isEmpty() && !optionA.isEmpty() && !optionB.isEmpty() && 
                !optionC.isEmpty() && !optionD.isEmpty() && !correctAnswer.isEmpty() &&
                correctAnswer.matches("[ABCD]")) {
                
                Question question = new Question();
                question.setQuestionText(questionText);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
                question.setCorrectAnswer(correctAnswer);
                question.setDifficulty(difficulty);
                question.setCategory(category);
                
                System.out.println("  ✅ Question parsed successfully!");
                return question;
            } else {
                System.err.println("  ⚠️ Incomplete question - Missing fields:");
                if (questionText.isEmpty()) System.err.println("    - Question text");
                if (optionA.isEmpty()) System.err.println("    - Option A");
                if (optionB.isEmpty()) System.err.println("    - Option B");
                if (optionC.isEmpty()) System.err.println("    - Option C");
                if (optionD.isEmpty()) System.err.println("    - Option D");
                if (correctAnswer.isEmpty()) System.err.println("    - Correct answer");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error parsing individual question: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}