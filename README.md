🎮 Quiz Master AI – Java Quiz Game with AI Assistance

A smart and interactive **Quiz Application** built with **Java Swing + MySQL**, enhanced with **AI-powered quiz support** using Groq API.

This project simulates a real quiz exam system with features like timers, score calculation, AI hints, and category-wise questions.

---

## ✨ Features

✔️ Java Swing-based modern UI  
✔️ Timer-based quiz system  
✔️ Score calculation & result panel  
✔️ Database-driven questions (MySQL)  
✔️ AI-powered quiz helper (Groq API)  
✔️ Login & signup system  
✔️ Secure environment variables support (`.env`)

---

## 📂 Project Structure

QuizGameApp/
├── src/main/java/com/quizapp/
│ ├── ui/ (Swing UI Screens)
│ ├── services/ (App & AI Services)
│ ├── dao/ (Database Access)
│ └── models/ (Data Models)
├── resources/
├── .env.example
├── .gitignore
├── pom.xml
└── README.md

yaml
Copy code

---

## 🛠 Tech Stack

| Component | Technology |
|----------|-----------|
| Language | Java |
| UI | Java Swing |
| Database | MySQL |
| API | Groq (AI Assistant) |
| Dependency Tool | Maven |

---

## 🚀 How to Run the Project

### 1️⃣ Clone the repository
```bash
git clone https://github.com/Naina823/Quiz-Master-AI-App.git
cd Quiz-Master-AI-App
2️⃣ Install dependencies
bash
Copy code
mvn clean install
3️⃣ Set up Environment Variables
Create .env file in the root:

ini
Copy code
GROQ_API_KEY=your_key_here
DB_HOST=localhost
DB_PORT=3307
DB_NAME=quiz_master_db
DB_USER=root
DB_PASSWORD=your_mysql_password
✅ .env is already ignored in .gitignore

🗄️ Database Setup
Import the SQL file located at:

pgsql
Copy code
/database/schema.sql
▶️ Run the Application
bash
Copy code
mvn spring-boot:run
(or run Main.java from your IDE)

📸 Screenshots / Demo
Login Page
<img width="1231" height="858" alt="login" src="https://github.com/user-attachments/assets/f8474188-9e94-4005-b826-93389e355bf9" />
Home Page
<img width="1481" height="982" alt="home" src="https://github.com/user-attachments/assets/281a7133-11ad-4308-bc71-c9581291b71b" />
Category-selection Page
<img width="1479" height="975" alt="category-selection" src="https://github.com/user-attachments/assets/c667759a-e3e4-46cd-8049-50f627827f63" />
Quiz-starting Page
<img width="1484" height="982" alt="quiz-starting" src="https://github.com/user-attachments/assets/62a0bd5e-cc70-47c7-80a5-0cc91d8fa62b" />
Quiz Page
<img width="1220" height="862" alt="quiz" src="https://github.com/user-attachments/assets/9576f61d-0ebc-44f5-9879-88822f53e437" />
Result Page
<img width="1100" height="856" alt="result" src="https://github.com/user-attachments/assets/4cd68a69-9f0e-4e51-8920-a2d813fe37b9" />
Personal-Leaderboard Page
<img width="1231" height="863" alt="personal-leaderboard" src="https://github.com/user-attachments/assets/d196b53e-6875-4123-9c14-021c53022c59" />
Global-leaderbpoard Page
<img width="1236" height="858" alt="global-leaderboard" src="https://github.com/user-attachments/assets/870ddfed-80a4-4245-bb99-4e1ea4c7ed4f" />






🙌 Contributions
Pull requests are welcome. If you'd like to improve UI or add features, feel free to contribute 🚀

⭐ Support
If you like this project, don't forget to star ⭐ the repo!

👩‍💻 Author
Naina Patwa
💼 Operational Manager @ ZeroshotHire
📍 India

🔐 API key is not included to keep the project secure.

yaml
Copy code

---

### ✅ What you do now
1. Open `README.md` in VS Code  
2. Paste the content above  
3. Commit & push:

```bash
git add README.md
git commit -m "Added project README"
git push origin main

