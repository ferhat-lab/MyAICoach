# MyAICoach: Expressive & Cohesive UI Design System Specification

> **Design Goal**: Break the monotony of repetitive stacked-card layouts by creating distinct, highly expressive, and asymmetrical visual compositions across all 7 core screens while preserving 100% brand consistency.

---

## 🎨 1. Brand Tokens & Color System

| Token | Hex | Usage |
| :--- | :--- | :--- |
| **Deep Navy (Background)** | `#0B1324` | Main application canvas background |
| **Surface Dark** | `#172033` | Card backgrounds, sheet containers |
| **Primary Purple** | `#7C3AED` | Core action buttons, active progress indicators |
| **Accent Sky Blue** | `#38BDF8` | Audio indicators, Secondary highlights |
| **Accent Magenta** | `#EC4899` | Badges, streak accents, special targets |
| **Success Green** | `#22C55E` | Correct answers, streak achievements |
| **Amber Gold** | `#F59E0B` | Trophy badges, XP rewards |
| **Clean White** | `#FFFFFF` | Primary typography and icons |

---

## 📐 2. The 7 Screen Architecture Blueprint

To avoid "stacked card burnout", every screen uses a unique composition:

### 1. Home Dashboard (`HomeScreen.kt`)
- **Composition**: Asymmetrical Hero Header + Dual Radial Progress Gauge + Vertical Timeline Learning Path.
- **Visual Features**: 
  - Flame streak chip (`🔥 7 Gün Streak`) with magenta gradient border.
  - Circular progress ring (`%80 Günlük Hedef`).
  - Vertical timeline nodes (`1`, `2`, `3`, `4`) with pulsing active node.

### 2. Lesson Selection (`LessonCategoryScreen.kt`)
- **Composition**: Horizontal Hero Featured Carousel + 2-Column Asymmetric Category Grid.
- **Visual Features**: 
  - Featured unit banner with glowing gradient background.
  - Category cards (Grammar, Daily Life, Travel, Business) with contextual iconography and completion badges.

### 3. Vocabulary Learning Screen (`WordIntroductionCard.kt`)
- **Composition**: Centered Hero Typography + Concentric Pulse Audio Button + Highlighted Example Sentence.
- **Visual Features**:
  - `✦ YENİ KELİME` primary purple badge.
  - Large white typography for target word.
  - Circular purple-cyan gradient audio button with `0.94f` press scale reaction.

### 4. Word Matching Exercise (`MatchingCard.kt`)
- **Composition**: Dual-Column Socket Grid + Canvas Bezier Rope Connections + Bottom Control Sheet.
- **Visual Features**:
  - Left column (English) & Right column (Turkish) spaced `44.dp` apart.
  - Edge socket nodes (`drawCircle`) at right edge of left cards and left edge of right cards.
  - Distinct pair colors (Purple `#A855F7`, Sky Blue `#0284C7`, Orange `#EA580C`, Magenta `#DB2777`, Green `#16A34A`).
  - Automatic first-unused-color allocation algorithm ensuring no duplicate pair colors.

### 5. Multiple-Choice Quiz Screen (`MultipleChoiceCard.kt`)
- **Composition**: Top Banner Prompt + Micro-Interactive Option List.
- **Visual Features**:
  - `LessonChoiceOption` with `scale(0.98f)` press animation.
  - Selection states: Idle (Surface), Selected (Purple container), Correct (Green container + `✓`), Incorrect (Red container + `✕`).

### 6. Lesson Result Screen (`LessonCompletionCard.kt`)
- **Composition**: Centered Gold Trophy Radial Badge + Animated XP Counter + Stat Breakdown Card.
- **Visual Features**:
  - Radial gold gradient badge (`#F59E0B` → `#D97706`).
  - Live animated XP counter (`+0 → +50 XP`).
  - Heart count summary (`❤️ 5/5`).

### 7. Profile & Achievements Screen (`ProfileScreen.kt`)
- **Composition**: Top User Hero Avatar Card + 2x2 Grid Achievement Badges + Weekly Progress Chart.
- **Visual Features**:
  - User level progress ring.
  - Badges (Early Bird, Word Master, 7-Day Streak) with unlocked glow vs locked gray states.

---

## 🛠️ Jetpack Compose Technical Implementation Rules

1. **Never Nest Scrollable Containers**: Always use `Column(Modifier.verticalScroll(rememberScrollState()))` or `LazyColumn` for whole screens.
2. **Reuse Token Constants**: Maintain brand colors in `com.ferhat.myaicoach.ui.theme.Color.kt`.
3. **Smooth State Transitions**: Use `animateColorAsState` for background/border transitions and `animateFloatAsState` for scale animations.
