# Vani: MyAICoach AI Companion & Mascot Architecture

> **Mascot Concept**: "Vani" — A friendly, intelligent white cat coach with heterochromia (one blue eye, one amber eye) wearing a deep navy scarf with a glowing soundwave badge.

---

## 🎭 1. Mascot States & Reactive Animations

Vani acts as an active, empathetic conversational AI coach during lessons and speaking sessions:

| State | Visual Behavior | Trigger Event |
| :--- | :--- | :--- |
| **IDLE** | Sitting alert, tail swaying gently, friendly smile | Dashboard hero, default scenario entry |
| **LISTENING** | Wearing glowing headset, leaning forward, paw near ear | User microphone active (`STT / VAD`) |
| **THINKING** | Paw on chin, subtle sparkle/glow above head | LLM token generation in progress |
| **SPEAKING** | Animated mouth movement, soundwaves pulsing around collar badge | VoxCPM2 TTS audio playback active |
| **HAPPY / CHEERING**| Eyes closed happily, paws raised, star particles | High-confidence correct answer / streak completion |
| **GENTLE_HINT** | Kind head tilt, soft smile, pointing to correction box | Gentle correction state (`TutorPolicy`) |

---

## 📱 2. UI Integration Across Screens

### A. Home Dashboard (`HomeScreen.kt`)
- Vani appears in the top Hero Banner with a dynamic speech bubble:
  * *"Bugün 5 dakika İngilizce konuşalım mı?"*
  * *"Harika gidiyorsun! 7 günlük serini koru!"*
- Includes a direct "Vani ile Konuş" (Speak with Vani) CTA button.

### B. Speaking Practice Screen (`SpeakingScenarioScreen.kt`)
- Centered 3D animated Vani Mascot Component with real-time reactive state transitions (`IDLE` → `LISTENING` → `THINKING` → `SPEAKING`).
- Real-time speech transcript bubble below Vani.
- Pulsing microphone button (`Konuşmak için Basılı Tut`) at screen bottom.

---

## 🛠️ 3. Jetpack Compose Technical Implementation

```kotlin
enum class MascotState {
    IDLE,
    LISTENING,
    THINKING,
    SPEAKING,
    HAPPY_CHEERING,
    GENTLE_HINT
}

@Composable
fun VaniMascotView(
    state: MascotState,
    modifier: Modifier = Modifier
) {
    // Renders Lottie animation or reactive asset composition based on state
}
```
