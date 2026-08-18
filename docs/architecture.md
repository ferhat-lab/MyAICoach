# Architecture Overview

For the detailed AI & Real-time Voice Architecture Blueprint, please refer to [docs/ai_architecture.md](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/docs/ai_architecture.md).

---

## High Level Layers

```
Presentation (Jetpack Compose UI)
       ↓
ViewModel & UiState
       ↓
Domain (Curriculum, Lesson, UseCases, LessonValidator)
       ↓
Data Layer (Repositories, Firebase, Room Local DB)
       ↓
Backend Orchestrator & AI Inference Services (vLLM, VoxCPM2, STT)
```

### Presentation Layer
- **Compose Screens**: `LessonScreen`, `HomeScreen`, `OnboardingScreen`, etc.
- **Activity Cards**: Reusable composable components (`WordIntroductionCard`, `SentenceBuilderCard`, `MatchingCard`, etc.)
- **Navigation**: Jetpack Compose Navigation

### Domain Layer
- **Core Models**: `Lesson`, `LessonActivity`, `VocabularyItem`, `PhraseItem`, `GrammarTarget`
- **Validation**: `LessonValidator` (Ensures data integrity at curriculum load)
- **Use Cases**: Curriculum evaluation, state calculations

### Data Layer
- **Firebase**: User Authentication, Remote Sync
- **Room / Local Cache**: Saved progress, offline curriculum storage
- **Repositories**: `LessonRepository`, `UserRepository`

### AI & Backend Services
- See [ai_architecture.md](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/docs/ai_architecture.md) for full specs on `TurnGuard`, `ContextSelector`, `TutorPolicy`, `TargetEvaluator`, and `VoxCPM2` streaming topology.