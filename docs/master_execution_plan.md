# MyAICoach: Uçtan Uca Geliştirme ve Mimari Uygulama Planı (Master Execution Plan)

> **Mühendislik Kararı**: Gerçek zamanlı ses (Audio), STT, LLM ve VoxCPM2 streaming altyapısını kurmadan önce **müfredat, kelime deposu, kalıplar ve öğrenci durum (StudentState) veri katmanını** sağlamlaştırmak **%100 DOĞRU VE EN GÜVENLİ MÜHENDİSLİK ADIMIDIR**.

---

## 🎯 Neden Önce Müfredat Veri Altyapısı?

1. **Net Pedagojik Bağlam (LLM Context)**: LLM (Vani Tutor), rastgele konuşmayacak; öğrencinin o an çalıştığı `targetIds` (hedef kelime ve gramer ID'leri) üzerinden diyalog kuracak.
2. **Sıfır NullPointer / Sıfır Hatalı Referans**: Canlı ses akışı (`Dual-Stream Engine`) devreye girdiğinde, tüketeceği veri kontratları tam ve `LessonValidator` tarafından doğrulanmış olacak.
3. **Gerçekçi Test Edilebilirlik**: Canlı WebSocket/gRPC katmanı yazılırken gerçek veriler üzerinden turlar test edilebilecek.

---

## 🗺️ Fazlı Geliştirme Sıralaması (Phase Roadmap)

---

### 📦 FAZ 1: Müfredat ve Taban Veri Altyapısı (Curriculum & Foundation Layer)

#### Adım 1.1: CEFR A1 & A2 Müfredat Veri Deposu (`domain/lesson/sample/`)
- Oxford 3000/5000 metodolojik seviye uyumuna dayalı %100 özgün cümle ve alıştırmalar.
- `A1Lesson1` (Tanışma & Selamlaşma), `A1Lesson2` (Günlük Aktiviteler), `A1Lesson3` (Alışveriş & Yiyecekler), `A2Lesson1` (Seyahat & Yön Sorma).
- Her derste: `VocabularyItem`, `PhraseItem`, `GrammarTarget`, `Exercise` (Matching, SentenceBuilder, Choice), `ListeningContent` ve `SpeakingScenario`.

#### Adım 1.2: `LessonRepository` & `CurriculumEngine`
- Dersleri üniteye, seviyeye (A1, A2, B1) göre getiren repository mimarisi.
- Ders içi alıştırmaların `LessonValidator` ile otomatik veri bütünlüğü kontrolü.

#### Adım 1.3: `StudentStateRepository` & Öğrenci Durum Takibi
- Öğrencinin tamamladığı dersler, öğrendiği kelime ID'leri (`knownTargetIds`) ve zayıf olduğu konular (`weakTargetIds`).
- Vani'nin konuşma anında kullanacağı pedagojik bağlam girdisi.

---

### 🎙️ FAZ 2: Canlı Ses ve Yayın Motoru (Real-time Audio & Turn Engine)

#### Adım 2.1: Tur Durum Makinesi (Turn State Machine) & Maskot Entegrasyonu
- `CREATED` → Tur oluşturuldu.
- `LISTENING` → Mikrofona basıldı (`MascotState.LISTENING`).
- `TRANSCRIBED` → Ses metne dönüştü (STT).
- `LLM_GENERATING` → Vani cevap üretiyor (`MascotState.THINKING`).
- `TTS_GENERATING` → VoxCPM2 ses akışı geliyor.
- `PLAYING` → Android ses çalıyor (`MascotState.SPEAKING`).
- `COMPLETED` → Tur bitti (`MascotState.IDLE` / `HAPPY_CHEERING`).

#### Adım 2.2: `TurnGuard` Güvenlik ve Abuse Katmanı
- **Debounce**: Mikrofona üst üste basılmasını engelleme (min 300-500 ms).
- **Per-User Concurrency = 1**: Aynı anda tek aktif tur.
- **Cancel Budget**: Hızlı iptal yapan kullanıcıya backoff süresi.

#### Adım 2.3: `User Barge-In` (Araya Girme & İptal)
- Kullanıcı Vani konuşurken mikrofona bastığı an:
  1. WebSocket'e `CANCEL_TURN` mesajı gönderilir.
  2. Android `AudioTrack.flush()` ile ses anında kesilir.
  3. Yeni STT turu başlatılır.

#### Adım 2.4: VoxCPM2 Streaming TTS & AudioTrack Playback
- Cümle/clause tabanlı ses tamponlama (+ timeout flush).
- Ses akışı ilk cümleyi üretir üretmez AudioTrack üzerinde gecikmesiz çalma.
