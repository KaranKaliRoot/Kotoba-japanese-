# Kotoba (言葉)

**Offline Japanese learning app for JLPT N5–N1** — pure Kotlin + Jetpack Compose, no network required.

## Features

| Tab | Content |
|-----|---------|
| **Home** | Dashboard with due counts, hourly quote widget, streak |
| **Vocab** | Anki SM-2 flashcards (SRS) + searchable word list |
| **Kanji** | KanjiVG stroke-order canvas (Duolingo-style drawing), compounds, N5–N1 grid |
| **Grammar** | **Sou Matome** weekly & **Shin Kanzen Master** functional syllabi, 4-choice quiz |
| **Reading** | Furigana toggle, English translation toggle, tap-to-lookup word popup |

## Architecture

- **Manual DI** — `AppContainer` + `ViewModelProvider.Factory` (no Hilt)
- **Room** — local persistence for vocab, kanji, grammar, stories, progress, settings
- **Navigation Compose** — type-safe routes with `NavType` arguments
- **Flow/StateFlow** — reactive UI state
- **Glance** — hourly reminder widget (WorkManager)
- **compileSdk 37**, AGP 8.10, Compose compiler 2.1.21, JVM 17

## Build

```bash
./gradlew assembleDebug
# APK at app/build/outputs/apk/debug/app-debug.apk
```

## License

MIT