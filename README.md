# Penny

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org/)
[![Gradle](https://img.shields.io/badge/Build-Gradle-lightgrey.svg)](https://gradle.org/)
[![API 28+](https://img.shields.io/badge/API-28%2B-brightgreen.svg)](https://android-developers.googleblog.com/)

A modern, feature-rich personal finance management Android application built with Jetpack Compose and clean architecture principles.

[Features](#features) • [Architecture](#architecture) • [Getting Started](#getting-started) • [Contributing](#contributing) • [License](#license)

---

## 🎯 About

Penny is a sophisticated Android application designed to help users manage their personal finances efficiently. It provides intuitive tools for tracking transactions, managing categories, and visualizing spending statistics through beautiful, modern UI built entirely with Jetpack Compose.

### Key Highlights
- 📱 **100% Jetpack Compose UI** - Modern declarative UI framework
- 🏗️ **Clean Architecture** - Modular, scalable, and maintainable codebase
- 💉 **Dependency Injection** - Hilt for seamless DI integration
- 🗄️ **Local Database** - Room for persistent data storage
- 🎨 **Material Design 3** - Beautiful, consistent design system
- ⚡ **Kotlin Coroutines** - Efficient asynchronous operations

---

## ✨ Features

- **Transaction Management**
  - Create, update, and delete financial transactions
  - Track income and expenses
  - Organize transactions by date and category

- **Category Management**
  - Create custom expense categories
  - Assign icons and colors to categories
  - Easy category organization

- **Statistics & Analytics**
  - Visual spending statistics with charts
  - Category-based expense breakdowns
  - Period-based financial insights

- **Notifications**
  - Real-time notifications for important events
  - Customizable notification preferences

- **Material Design**
  - Modern, responsive UI
  - Smooth animations and transitions
  - Accessibility-first design approach

---

## 🏗️ Architecture

Penny follows **Clean Architecture** principles with a modular structure:

```
Penny/
├── app/                          # Main application module
│   ├── src/main/java/
│   │   └── io/github/t1geryan/penny/
│   │       ├── MainActivity.kt                # Entry point
│   │       ├── PennyApplication.kt            # Hilt setup
│   │       ├── data/                          # Data layer
│   │       │   ├── database/                  # Room entities & DAOs
│   │       │   ├── network/                   # API clients & interceptors
│   │       │   ├── repositories/              # Data source implementations
│   │       │   ├── mappers/                   # Entity to model mappers
│   │       │   └── notifications/             # Notification handlers
│   │       ├── ui/                            # Presentation layer
│   │       │   ├── features/                  # Feature screens
│   │       │   ├── base/                      # Base composables
│   │       │   ├── navigation/                # Navigation setup
│   │       │   ├── permissions/               # Permission handlers
│   │       │   └── utils/                     # UI utilities
│   │       └── di/                            # Dependency injection
│   └── build.gradle.kts                       # App-level dependencies
│
├── core/                         # Shared core modules
│   ├── ui/                       # UI-related core modules
│   │   ├── theme/                # Compose theme configuration
│   │   ├── icons/                # Icon definitions
│   │   └── navigation/           # Navigation graph setup
│   ├── models/                   # Domain models
│   ├── constants/                # App-wide constants
│   ├── coroutines/               # Coroutine utilities
│   └── mvi/                      # MVI architecture components
│
├── domain/                       # Business logic layer
│   └── src/main/
│       └── ...                   # Use cases & entities
│
└── gradle/
    └── libs.versions.toml        # Centralized dependency versions

```

### Architecture Layers

1. **Data Layer** (`app/data/`)
   - Manages data sources (local database, network APIs)
   - Repositories implement the repository pattern
   - Mappers convert between entities and domain models

2. **Domain Layer** (`domain/`)
   - Pure business logic with no Android dependencies
   - Use cases for specific business operations
   - Domain entities and interfaces

3. **Presentation Layer** (`app/ui/`)
   - Jetpack Compose UI components
   - MVI (Model-View-Intent) pattern
   - State management and side effects

4. **Core Modules**
   - Shared utilities and configurations
   - Centralized theme and navigation
   - Constants and coroutine helpers

---

## 🛠️ Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **UI Framework** | Jetpack Compose | 2026.04 |
| **Language** | Kotlin | 2.3.21 |
| **Build System** | Gradle KTS | 9.0.1 |
| **Min API** | Android | 28 |
| **Target API** | Android | 36 |
| **Dependency Injection** | Hilt | 2.59.2 |
| **Database** | Room | 2.8.4 |
| **Networking** | Retrofit | 3.0.0 |
| **HTTP Client** | OkHttp | 5.3.2 |
| **Async** | Coroutines | 1.10.2 |
| **Serialization** | Kotlinx Serialization | 1.11.0 |
| **Navigation** | Compose Navigation | 2.9.8 |
| **Testing** | JUnit | 4.13.2 |
| **Code Quality** | Detekt | 1.23.8 |
| **Charts** | Compose Charts | 0.2.5 |

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** - Latest stable version (Arctic Fox or newer)
- **JDK 11** - Java Development Kit
- **Android SDK** - API level 28 or higher
- **Gradle** - Version 9.0.1 (included via wrapper)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/t1geryan/Penny.git
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned `Penny` directory
   - Click "Open"

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device (API 28+) or start an emulator
   - Click the "Run" button in Android Studio or:
   ```bash
   ./gradlew installDebug
   ```

### Gradle Commands

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run Detekt (code quality analysis)
./gradlew detekt

# Build release APK
./gradlew assembleRelease

# Run the app
./gradlew installDebug

# Clean build files
./gradlew clean
```

---

## 📱 Features Deep Dive

### Transaction Management
Track all your financial transactions with detailed information:
- Transaction amount
- Category classification
- Transaction type (income/expense)
- Date and time
- Notes and descriptions

### Category Management
Organize your finances with custom categories:
- Create unlimited categories
- Assign custom icons and colors
- Edit and manage existing categories
- Delete unused categories

### Statistics Dashboard
Visualize your spending patterns:
- Total income and expenses overview
- Category-wise expense breakdown
- Time-period analysis
- Interactive charts and graphs

### Push Notifications
Stay informed with timely notifications:
- Transaction alerts
- Budget warnings
- Important reminders

---

## 🎨 UI Components

Penny features a comprehensive set of custom Jetpack Compose components:

- **Material Design 3** components for consistency
- **Custom composables** for domain-specific features
- **Reusable UI patterns** across the application
- **Responsive layouts** that adapt to different screen sizes
- **Dark mode support** for comfortable viewing

---

## 🧪 Testing

The project includes comprehensive test coverage:

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests
./gradlew connectedAndroidTest

# Run all tests
./gradlew test connectedAndroidTest
```

Test structure:
- `app/src/test/` - Unit tests
- `app/src/androidTest/` - Instrumented tests

---

## 📊 Project Structure

### Modules Overview

| Module | Purpose | Type |
|--------|---------|------|
| `app` | Main application with UI and data layers | Android App |
| `core:ui:theme` | Material Design theme configuration | Library |
| `core:ui:icons` | Icon definitions and resources | Library |
| `core:ui:navigation` | Navigation graph and routes | Library |
| `core:models` | Domain models | Library |
| `core:constants` | Application constants | Library |
| `core:mvi` | MVI architecture components | Library |
| `core:coroutines` | Coroutine utilities | Library |
| `domain` | Business logic and use cases | Library |

---

## 🔧 Configuration

### Android Manifest
Located at `app/src/main/AndroidManifest.xml`, requires:
- `POST_NOTIFICATIONS` permission for notification support

### Version Management
All dependencies are centralized in `gradle/libs.versions.toml`:
- Update versions in one place
- Easy dependency management
- Consistent versions across modules

### Code Quality
Detekt configuration in `config/detekt.yml`:
- Static code analysis
- Custom rule configuration
- HTML reports in `app/build/reports/detekt/`

---

## 🚨 Troubleshooting

### Build Issues

**Problem**: "Gradle sync failed"
- **Solution**: Run `./gradlew clean` then sync again

**Problem**: "Cannot find symbol" errors
- **Solution**: Rebuild project: `Build > Clean Project` → `Build > Rebuild Project`

**Problem**: Hilt compilation errors
- **Solution**: Rebuild project and invalidate Android Studio caches

### Runtime Issues

**Problem**: App crashes on startup
- **Solution**: Check Logcat for errors and ensure all permissions are properly declared

**Problem**: Database migration errors
- **Solution**: Clear app data and reinstall

---

## 🤝 Contributing

Contributions are welcome! Here's how to get started:

1. **Fork the repository**
   ```bash
   gh repo fork t1geryan/penny
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Make your changes**
   - Write clean, well-documented code
   - Follow Kotlin coding conventions
   - Run Detekt: `./gradlew detekt`

4. **Commit your changes**
   ```bash
   git commit -m 'Add amazing feature'
   ```

5. **Push to your fork**
   ```bash
   git push origin feature/amazing-feature
   ```

6. **Open a Pull Request**
   - Describe your changes clearly
   - Reference any related issues
   - Ensure all checks pass

### Coding Standards

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add documentation for public APIs
- Keep functions small and focused
- Write unit tests for new features

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024-2026 t1geryan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 📧 Support

For support, issues, and questions:

- **GitHub Issues**: [Create an issue](https://github.com/t1geryan/Penny/issues)
- **Discussions**: [GitHub Discussions](https://github.com/t1geryan/Penny/discussions)

---

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern Android UI toolkit
- [Hilt](https://dagger.dev/hilt/) - Dependency injection framework
- [Room](https://developer.android.com/topic/libraries/architecture/room) - Database abstraction
- [Material Design 3](https://m3.material.io/) - Design system
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Async programming

---

## 📈 Roadmap

Planned features for future releases:

- [ ] Settings screen
- [ ] Alerts tab
- [ ] Recurring transactions
- [ ] Data export (CSV, PDF)
- [ ] Multi-currency support improvements
- [ ] Spending insights and recommendations

---

Made with ❤️ by [t1geryan](https://github.com/t1geryan)

⭐ If you find this project helpful, please consider giving it a star!

