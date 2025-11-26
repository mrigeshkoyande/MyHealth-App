🏥 MyHealth App

A modern and intuitive health companion app built using Kotlin, Jetpack Compose, and MVVM Architecture. MyHealth App helps users track daily workouts, monitor nutrition, view personalized dashboards, and maintain healthy habits — all with a clean & polished UI powered by Material Design 3.
| Category              | Details                                                              |
| --------------------- | -------------------------------------------------------------------- |
| 📌 Exercise Tracker   | Log activities, track calories burned, and view progress             |
| 📊 Dashboard          | Insightful charts and analytics for daily, weekly, and monthly views |
| 🥗 Nutrition Tracking | Record calorie intake, meal logging, and macro suggestions           |
| 🎨 UI/UX              | Clean and intuitive user interface using Material Design 3           |
| 💾 Data Persistence   | Local storage using SharedPreferences                                |
| 🔧 MVVM Architecture  | Scalable, modular, test-friendly design                              |
🧰 Tech Stack

Kotlin 2.0

Jetpack Compose

Material Design 3

MVVM Architecture

SharedPreferences

Android Studio (Giraffe or above)


📂 Project Structure

MyHealth-App/
 ├── app/
 │   ├── data/
 │   │   ├── preferences/
 │   │   └── repository/
 │   ├── ui/
 │   │   ├── components/
 │   │   ├── screens/
 │   │   └── theme/
 │   ├── viewmodel/
 │   ├── utils/
 │   └── MainActivity.kt
 └── README.md

 
🧑‍💻 Getting Started
✅ Prerequisites

Android Studio Hedgehog / Jellyfish recommended

JDK 17+

Gradle updated via Android Studio

📦 Installation
git clone https://github.com/mrigeshkoyande/MyHealth-App.git
cd MyHealth-App

▶️ Build & Run

Open in Android Studio → Select device/emulator → Click Run ▶️

📚 Usage Guide
| Module               | How to Use                                              |
| -------------------- | ------------------------------------------------------- |
| **Exercise Tracker** | Add exercises, duration, difficulty, calories, and save |
| **Dashboard**        | View progress charts and activity insights              |
| **Nutrition**        | Record daily food and calorie intake                    |
| **Settings**         | Modify goals, preferences, and storage data             |

🗂 Data Persistence

This project uses SharedPreferences to store:

User profile

Tracking history

Preferences and settings

Progress indicators

Future versions will include Room Database for advanced offline data handling.

🏗 Architecture

The project follows MVVM + State Management with:

ViewModel handling business logic

Composable UI reflecting state changes

Repository layer separating data sources

Unidirectional Data Flow

🛠 Build & Deployment
| Build Type            | Command                          |
| --------------------- | -------------------------------- |
| Debug Build           | `./gradlew assembleDebug`        |
| Release Build         | `./gradlew assembleRelease`      |
| Unit Testing          | `./gradlew test`                 |
| Instrumentation Tests | `./gradlew connectedAndroidTest` |

🧭 Roadmap

 Room Database Integration

 AI-based Nutrition Suggestions

 Firebase Backup Support

 WearOS Smartwatch Companion App

 Cloud Sync + Multi-Device Support

 🤝 Contributing

Contributions are welcome!

Fork the repo

Create a new feature branch

Commit changes

Submit a pull request

📄 License

This project is licensed under the MIT License.
Feel free to modify and build upon it.

📬 Support & Contact

🔗 GitHub Repo:
https://github.com/mrigeshkoyande/MyHealth-App

If you encounter issues, open an Issue Ticket on the repository.

⭐ If you found this useful, please consider giving the repo a star!
![IMG-20251126-WA0015](https://github.com/user-attachments/assets/abc53752-1430-4a8e-adf3-c94ba75c4955)
![IMG-20251126-WA0012](https://github.com/user-attachments/assets/c04329f7-dd42-44c9-9339-ba989cc812ea)
![IMG-20251126-WA0014](https://github.com/user-attachments/assets/5f5ae5ef-a064-48d0-9d11-870c8eebc350)
![IMG-20251126-WA0013](https://github.com/user-attachments/assets/7af7a3c6-a7e5-4682-be2f-d31dff0befbf)
![IMG-20251126-WA0016](https://github.com/user-attachments/assets/bd74926e-95f6-4640-b790-5c798ab99312)
![IMG-20251126-WA0017](https://github.com/user-attachments/assets/158b75ea-c361-4ee9-bda4-8333c529d44a)
![IMG-20251126-WA0019](https://github.com/user-attachments/assets/094d04e1-c565-43b8-841f-2f4c6f9af60b)
<img width="746" height="394" alt="Screenshot 2025-11-26 232608" src="https://github.com/user-attachments/assets/215f07fa-ea92-461f-a9b2-f7fcc991995d" />








