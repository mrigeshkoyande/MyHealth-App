# MyHealth App

MyHealth App is an Android health companion built with Kotlin, Jetpack Compose, and an MVVM-style structure. It focuses on everyday wellness tracking with a simple interface for workouts, nutrition, dashboards, and personal progress.

## Features

| Area | Details |
| --- | --- |
| Exercise Tracking | Log workouts, calories burned, and daily activity progress |
| Dashboard | View health insights across daily, weekly, and monthly views |
| Nutrition Tracking | Record meals, calories, and nutrition-related activity |
| UI | Jetpack Compose UI with Material 3 components |
| Storage | Local persistence using SharedPreferences |
| Architecture | Modular structure based on MVVM principles |

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- MVVM architecture
- SharedPreferences
- Navigation Compose
- MPAndroidChart

## Requirements

- Android Studio with current Android SDK support
- Android SDK 35
- Minimum SDK 28
- Java 11
- Gradle wrapper included in this repository

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/mrigeshkoyande/MyHealth-App.git
cd MyHealth-App
```

### 2. Open the project

Open the project folder in Android Studio.

### 3. Sync Gradle

Wait for Android Studio to finish Gradle sync and download dependencies.

### 4. Choose a device

Use either:

- an Android emulator, or
- a physical Android device with USB debugging enabled

### 5. Run the app

Click the Run button in Android Studio, or use the Gradle command below.

## Build Commands

### Windows

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest
```

### macOS / Linux

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
```

### Other useful commands

```bash
./gradlew assembleRelease
./gradlew connectedAndroidTest
```

## Project Structure

```text
app/
	src/main/
		java/google/com/myhealth/
			ui/
			viewmodel/
			data/
			utils/
		res/
```

## Usage Overview

| Module | What You Can Do |
| --- | --- |
| Exercise Tracker | Add exercise details, duration, difficulty, and calories |
| Dashboard | Review progress charts and activity summaries |
| Nutrition | Record meals and calorie intake |
| Settings | Update goals and app preferences |

## Data Handling

The app currently stores local data with SharedPreferences, including:

- user profile data
- tracking history
- settings and preferences
- progress values

Room can be added later if the project moves toward more complex offline storage.

## Architecture Notes

The app is organized around:

- ViewModels for state and business logic
- Compose UI for rendering state
- repository-style separation for data access
- unidirectional data flow where possible

## Roadmap

- Room database integration
- AI-based nutrition suggestions
- Firebase backup support
- Wear OS companion support
- Cloud sync across devices

## Contributing

Contributions are welcome. The best way to make it easy for others to review your work is to follow a clear step-by-step process.

### Step-by-step contribution flow

#### 1. Check existing issues

Look through open issues first. If your change is not already tracked, open an issue describing:

- the problem
- the proposed fix or feature
- screenshots if the change is UI-related

#### 2. Fork the repository

Create your own fork on GitHub, then clone it locally.

```bash
git clone https://github.com/<your-username>/MyHealth-App.git
cd MyHealth-App
```

#### 3. Create a branch

Use a focused branch name that describes the work.

```bash
git checkout -b feature/add-water-tracker
```

Examples:

- `feature/add-dashboard-card`
- `fix/nutrition-summary-bug`
- `docs/update-readme`

#### 4. Make small, focused changes

Keep the scope limited to one feature, one bug fix, or one documentation update per pull request.

Good contribution areas:

- bug fixes
- UI polish
- code cleanup
- test improvements
- README and documentation updates
- performance improvements

#### 5. Test before opening a pull request

Run the relevant checks locally.

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest
```

If you changed UI behavior, include screenshots or a short screen recording in your pull request.

#### 6. Commit with a clear message

Use short, descriptive commit messages.

```bash
git commit -m "Add nutrition summary validation"
```

Better commit examples:

- `Fix dashboard crash on empty state`
- `Add reusable health stat card`
- `Update README contribution guide`

#### 7. Push your branch

```bash
git push origin feature/add-water-tracker
```

#### 8. Open a pull request

Your pull request should explain:

- what changed
- why it changed
- how it was tested
- whether screenshots are included for UI changes

#### 9. Respond to review feedback

Keep the same branch updated and reply to review comments directly. Small follow-up commits are fine.

## Pull Request Checklist

Before submitting, make sure that:

- the app builds successfully
- tests related to your change pass
- the change is limited in scope
- code style matches the existing project
- UI updates include screenshots when helpful
- the pull request description explains the change clearly

## Support

Repository: https://github.com/mrigeshkoyande/MyHealth-App

If you find a bug or want to suggest an improvement, open an issue in the repository.

## Screenshots

![IMG-20251126-WA0015](https://github.com/user-attachments/assets/abc53752-1430-4a8e-adf3-c94ba75c4955)
![IMG-20251126-WA0012](https://github.com/user-attachments/assets/c04329f7-dd42-44c9-9339-ba989cc812ea)
![IMG-20251126-WA0014](https://github.com/user-attachments/assets/5f5ae5ef-a064-48d0-9d11-870c8eebc350)
![IMG-20251126-WA0013](https://github.com/user-attachments/assets/7af7a3c6-a7e5-4682-be2f-d31dff0befbf)
![IMG-20251126-WA0016](https://github.com/user-attachments/assets/bd74926e-95f6-4640-b790-5c798ab99312)
![IMG-20251126-WA0017](https://github.com/user-attachments/assets/158b75ea-c361-4ee9-bda4-8333c529d44a)
![IMG-20251126-WA0019](https://github.com/user-attachments/assets/094d04e1-c565-43b8-841f-2f4c6f9af60b)
<img width="746" height="394" alt="Screenshot 2025-11-26 232608" src="https://github.com/user-attachments/assets/215f07fa-ea92-461f-a9b2-f7fcc991995d" />








