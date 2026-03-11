# Contributing to MyHealth App

Thanks for contributing to MyHealth App. This document explains the expected workflow so changes are easier to review and merge.

## Before You Start

Before opening a pull request:

- check existing issues and pull requests
- keep your change focused on one problem or feature
- prefer small, reviewable commits

## Local Setup

### 1. Fork and clone

```bash
git clone https://github.com/<your-username>/MyHealth-App.git
cd MyHealth-App
```

### 2. Create a branch

```bash
git checkout -b feature/short-description
```

Examples:

- `feature/add-health-summary-card`
- `fix/dashboard-empty-state`
- `docs/improve-contribution-guide`

### 3. Open in Android Studio

Open the project and allow Gradle sync to complete.

## Development Guidelines

- match the existing Kotlin and Compose style already used in the project
- avoid unrelated refactors in the same pull request
- keep UI changes consistent with the current app structure
- add screenshots for visible UI changes when possible

## Validation

Run relevant checks before submitting your change.

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

If you change device-specific behavior, also run instrumentation tests when possible.

## Commit Guidelines

Write short, clear commit messages that describe the actual change.

Examples:

- `Add reusable dashboard metric card`
- `Fix calorie input validation`
- `Improve contributor documentation`

## Pull Request Guidelines

Your pull request should include:

- a short summary of what changed
- the reason for the change
- notes about testing performed
- screenshots for UI updates if applicable

## Suggested Contribution Areas

- bug fixes
- UI improvements
- accessibility improvements
- test coverage improvements
- documentation improvements
- performance cleanups

## Review Process

After opening the pull request:

- respond to review comments clearly
- push follow-up commits to the same branch
- keep the discussion focused on the scope of the change

## Code of Work

Contributions should be constructive, respectful, and technically clear.