<p align="center">
  <h1 align="center">📰 NewsPulse</h1>
  <p align="center">
    A modern news aggregator built with <strong>Kotlin Multiplatform</strong> &amp; <strong>Compose Multiplatform</strong>
    <br />
    <em>One codebase. Multiple platforms. Real-time news.</em>
  </p>
  <p align="center">
    <img src="https://img.shields.io/badge/Kotlin-2.4.0-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Compose_Multiplatform-1.11.1-4285F4?logo=jetpackcompose&logoColor=white" alt="Compose Multiplatform" />
    <img src="https://img.shields.io/badge/Platform-Android_|_iOS-green" alt="Platforms" />
    <img src="https://img.shields.io/badge/Architecture-Clean_Architecture-orange" alt="Architecture" />
    <img src="https://img.shields.io/badge/License-MIT-blue" alt="License" />
  </p>
</p>

---

## ✨ Overview

**NewsPulse** is a cross-platform news application that delivers the latest headlines powered by [NewsAPI](https://newsapi.org/). Built using Kotlin Multiplatform (KMP) with a fully shared UI layer via Compose Multiplatform, it demonstrates a production-grade mobile architecture with shared business logic and UI across **Android** and **iOS**.

## 🚀 Features

- 📰 **Live News Feed** — Browse the latest headlines fetched in real-time from NewsAPI
- ❤️ **Favorites** — Double-tap any article to save it to your favorites collection
- 🔄 **Offline Support** — Articles are cached locally using Room database for offline reading
- 🎨 **Material Design 3** — Clean, modern UI following Material You design guidelines
- 📱 **Cross-Platform** — Shared codebase for both Android and iOS
- ⚡ **Reactive UI** — Fully reactive state management with Kotlin Flows and Compose

## 🏗️ Architecture

NewsPulse follows **Clean Architecture** principles with a clear separation of concerns across three Gradle modules:

```
┌─────────────────────────────────────────────────────┐
│                    androidApp                        │
│              (Android Entry Point)                   │
├─────────────────────────────────────────────────────┤
│                     sharedUI                         │
│     (Compose Multiplatform Screens & ViewModels)     │
├─────────────────────────────────────────────────────┤
│                   sharedLogic                        │
│  (Domain Models, Use Cases, Data Sources, Repos)     │
└─────────────────────────────────────────────────────┘
```

### Module Breakdown

| Module | Responsibility |
|--------|---------------|
| **`:androidApp`** | Android application entry point, Koin initialization, platform-specific configuration |
| **`:sharedUI`** | Compose Multiplatform screens, ViewModels, UI components, and navigation |
| **`:sharedLogic`** | Domain layer (models, use cases, repository interfaces), data layer (DTOs, mappers, local/remote data sources, repository implementations), and DI modules |

### Layer Architecture

```
┌──────────────────────────────┐
│     Presentation Layer       │ ← Screens, ViewModels, UI State/Effects/Events
├──────────────────────────────┤
│       Domain Layer           │ ← Use Cases, Models, Repository Interfaces
├──────────────────────────────┤
│        Data Layer            │ ← Repository Impl, Data Sources, DTOs, Mappers
├──────────────────┬───────────┤
│   Remote (Ktor)  │ Local (Room) │
└──────────────────┴───────────┘
```

The presentation layer uses a **unidirectional data flow (UDF)** pattern with:
- **UIState** — Sealed interface representing screen states (`Loading`, `Success`, `Error`)
- **UIEvent** — User-triggered actions dispatched to the ViewModel
- **UIEffect** — One-time side effects (e.g., toast messages)

## 🛠️ Tech Stack

| Category | Technology | Version |
|----------|-----------|---------|
| **Language** | [Kotlin](https://kotlinlang.org/) | 2.4.0 |
| **UI Framework** | [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/) | 1.11.1 |
| **Design System** | [Material Design 3](https://m3.material.io/) | — |
| **Networking** | [Ktor Client](https://ktor.io/) | 3.5.0 |
| **Serialization** | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) | 1.11.0 |
| **Local Database** | [Room (KMP)](https://developer.android.com/kotlin/multiplatform/room) | 2.8.4 |
| **Dependency Injection** | [Koin](https://insert-koin.io/) | 3.5.3 |
| **Image Loading** | [Coil 3](https://coil-kt.github.io/coil/) | 3.5.0 |
| **Async** | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines) | 1.11.0 |
| **Build System** | [Gradle (Version Catalog)](https://docs.gradle.org/current/userguide/platforms.html) | — |
| **Code Generation** | [KSP](https://github.com/google/ksp) | 2.3.9 |

## 📁 Project Structure

```
NewsPulse/
├── androidApp/                          # Android application module
│   └── src/main/
│       └── kotlin/.../
│           ├── MainActivity.kt          # Android entry point
│           └── NewsApplication.kt       # Application class with Koin setup
│
├── sharedUI/                            # Shared Compose Multiplatform UI
│   └── src/commonMain/
│       └── kotlin/.../
│           ├── App.kt                   # Root composable with navigation
│           ├── di/                      # UI-layer DI module
│           └── screens/
│               ├── home/
│               │   ├── view/
│               │   │   ├── HomeScreen.kt
│               │   │   └── components/
│               │   │       └── NewsCard.kt
│               │   └── viewmodel/
│               │       ├── HomeContract.kt   # UIState, UIEvent, UIEffect
│               │       └── HomeViewModel.kt
│               ├── favorites/
│               │   ├── view/
│               │   └── viewmodel/
│               └── details/
│                   └── DetailsScreen.kt
│
├── sharedLogic/                         # Shared business logic
│   └── src/
│       ├── commonMain/kotlin/.../
│       │   ├── domain/
│       │   │   ├── model/
│       │   │   │   └── Article.kt       # Domain model
│       │   │   ├── repository/
│       │   │   │   └── INewsRepository.kt
│       │   │   └── usecase/
│       │   │       ├── FetchNewsUseCase.kt
│       │   │       ├── GetArticlesUseCase.kt
│       │   │       ├── GetFavoriteArticlesUseCase.kt
│       │   │       └── ToggleFavoriteUseCase.kt
│       │   ├── data/
│       │   │   ├── remote/
│       │   │   │   ├── dto/             # Network DTOs
│       │   │   │   ├── datasource/      # Remote data source
│       │   │   │   ├── contract/        # Data source interfaces
│       │   │   │   └── utils/           # Network configuration
│       │   │   ├── local/
│       │   │   │   ├── dao/             # Room DAOs
│       │   │   │   ├── entity/          # Room entities
│       │   │   │   ├── contract/        # Local data source interfaces
│       │   │   │   └── utils/           # Database setup
│       │   │   ├── mapper/              # Entity ↔ Domain mappers
│       │   │   └── repository/          # Repository implementation
│       │   └── di/                      # Koin modules
│       ├── androidMain/                 # Android-specific implementations
│       └── iosMain/                     # iOS-specific implementations
│
├── iosApp/                              # iOS application (Xcode project)
├── gradle/
│   └── libs.versions.toml              # Centralized dependency versions
└── build.gradle.kts                    # Root build configuration
```
---

<p align="center">
  Built with ❤️ using Kotlin Multiplatform
</p>
