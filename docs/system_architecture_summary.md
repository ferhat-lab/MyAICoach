# MyAICoach: Sistem Mimarisi, Tamamlanan Bileşenler ve Yol Haritası (Master Architecture Blueprint)

> **Tasarım ve Mimari Vizyon**: "Vani öğretilecek bilgiyi icat etmeyecek; öğretim biçimini kişiselleştirecek."  
> CEFR ve Oxford 3000/5000 standartlarına dayalı, gerçek zamanlı AI konuşma koçluğu sunan, yüksek kaliteli ve gamified (oyunlaştırılmış) Android platformu.

---

## 🏗️ 1. Genel Sistem Mimarisi (Architecture Topology)

Proje, **Clean Architecture + MVVM + Jetpack Compose + StateFlow** prensiplerine göre katmanlandırılmıştır:

```
[ UI Layer (Jetpack Compose Screens & Components) ]
                      ↓ (User Events / Intent)
[ ViewModel Layer (LessonViewModel, HomeViewModel, StateFlow) ]
                      ↓ (Domain Contracts & Validation)
[ Domain Layer (Lesson, SpeakingScenario, LessonValidator, MascotState) ]
                      ↓ (Network / Real-time Gateway)
[ AI & Backend Infrastructure (TurnGuard, Dual-Stream, VoxCPM2 TTS, LLM Context) ]
```

### 🧠 Vani AI Konuşma Durum Makinesi (Turn State Machine):
1. `CREATED` → Tur oluşturuldu.
2. `LISTENING` → Kullanıcı mikrofona basıp konuştuğunda Vani patisini kulağına götürür (`MascotState.LISTENING`).
3. `TRANSCRIBED` → Kullanıcı sesi metne dönüştürüldü (STT).
4. `LLM_GENERATING` → LLM metin ve pedagojik context üretiyor (`MascotState.THINKING`).
5. `TTS_GENERATING` → VoxCPM2 streaming ses üretiyor.
6. `PLAYING` → Android ses çalıyor (`MascotState.SPEAKING`).
7. `COMPLETED` → Tur tamamlandı (`MascotState.IDLE` veya `HAPPY_CHEERING`).

---

## 🎨 2. Tamamlanan 7 Ekran ve UI Bileşenleri

| # | Ekran / Bileşen | Dosya Yolu | Temel Özellikler |
| :--- | :--- | :--- | :--- |
| **1** | **Ana Sayfa (Dashboard)** | [HomeScreen.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/home/HomeScreen.kt) | 🐱 **Vani AI Hero Kartı** (Aura animasyonlu avatar + Canlı Konuşma Balonu + "Vani ile Konuş" CTA), Günlük Hedef Çemberi (%80), 🔥 7 Günlük Seri ve Dikey Yol Haritası. |
| **2** | **Ders & Kategori Seçimi** | [LessonCategoryScreen.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/lesson/LessonCategoryScreen.kt) | 🌟 **Öne Çıkan Ünite Hero Banner'ı** (Gradient arka plan, %33 İlerleme), 2 Sütunlu Asimetrik CEFR Kategorileri (Gramer, Günlük Yaşam, Seyahat, İş). |
| **3** | **Kelime Öğrenme** | [WordIntroductionCard.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/lesson/components/WordIntroductionCard.kt) | ✦ Dev Kelime Başlığı, Dairesel Ses Dalga Butonu (`scale(0.94f)`) ve Cümle İçi Vurgular. |
| **4** | **Kelime Eşleştirme** | [MatchingCard.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/lesson/components/MatchingCard.kt) | 🔗 2 Sütunlu Soket Düğümleri, **Canvas Bezier İp Çizgileri**, Dokun-İptal Et & **Benzersiz Boş Renk Seçim Algoritması**. |
| **5** | **Cümle Oluşturma** | [SentenceBuilderCard.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/lesson/components/SentenceBuilderCard.kt) | 🧩 Yay Fiziği ile Mikro Çip Basılma Animasyonu (`scale(0.95f)`), Dinamik Renkli Tepsi. |
| **6** | **Quiz & Egzersiz Kartları** | [MultipleChoiceCard.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/lesson/components/MultipleChoiceCard.kt) | 📝 Mikro Animasyonlu Seçenek Kartları, Doğru (✓ Yeşil) ve Yanlış (✕ Kırmızı) Bildirimleri. |
| **7** | **Ders Sonucu Kutlama** | [LessonCompletionCard.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/lesson/components/LessonCompletionCard.kt) | 🏆 Işıldayan Altın Kupa Rozeti, Animasyonlu XP Sayacı (`+50 XP`) & Konfeti Patlaması (`ConfettiEffect.kt`). |
| **8** | **Profil ve Başarımlar** | [ProfileScreen.kt](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/app/src/main/java/com/ferhat/myaicoach/feature/profile/ProfileScreen.kt) | 👤 Profil Avatarı, CEFR Rozeti, 2x2 Işıldayan Başarım Rozetleri & Haftalık Aktiflik Grafiği. |

---

## 🐱 3. Maskotumuz Vani'nin 6 Reaktif Tepki Durumu (`MascotState`)

Dokümantasyonu [docs/mascot_system.md](file:///c:/Users/ferhat/AndroidStudioProjects/MyAICoach/docs/mascot_system.md) dosyasında tutulan Vani reaktivite sistemi:

1. **`IDLE` (Bekleme)**: Vani hazır bekler, kulaklığıyla gülüser.
2. **`LISTENING` (Dinliyor)**: Patisini kulağına götürür, kulaklıktaki ses dalgaları parlar.
3. **`THINKING` (Düşünüyor)**: Patisini çenesine götürür, parıltı efekti oluşur.
4. **`SPEAKING` (Konuşuyor)**: VoxCPM2 ses yayını sırasında ağzı hareket eder, rozeti ışıldar.
5. **`HAPPY_CHEERING` (Tebrik)**: Doğru cevaplarda sevinçle patilerini kaldırır, yıldızlar saçılır.
6. **`GENTLE_HINT` (Nazik Düzeltme)**: Hatalarda başını nazikçe eğer ve ipucu sunar.

---

## 🛡️ 4. Güvenlik, Veri Doğrulama ve Git Kuralları

- **Müfredat Veri Doğrulama (`LessonValidator.kt`)**: Yüklenen her ders çalışma anında taranır. Eksik ID veya hatalı eşleşme durumunda uygulama çökmez, yüksek kontrastlı uyarı kartı gösterilir.
- **Gizli Veri Güvenliği (`.gitignore`)**: Proje GitHub'da açık (public) olduğu için `.env`, `secrets.properties`, `google-services.json`, `*.keystore` kesinlikle Git'e eklenmez.
- **Git Commit İlerlemesi**: Her 3-4 tamamlanan geliştirmede Türkçe commit komutu önerilir. Tüm Kotlin kodlarında açıklayıcı Türkçe yorum satırları (`//`) yer alır.

---

## 🚀 5. Gelecek Adımlar (Neler Yapacağız?)

1. **Vani Canlı Konuşma Modülü (Speaking Scenario Engine Entegrasyonu)**:
   - Real-time WebSocket/gRPC dual-stream bağlantısı.
   - User Barge-In (Kullanıcı araya girdiğinde aktif turnId'yi anında iptal etme, AudioTrack flush).
   - TurnGuard (Debounce limitleri, abuse önleme).
2. **Seslendirme ve TTS Streaming Entegrasyonu**:
   - VoxCPM2 streaming audio playback ile anlık ses verme.
3. **Gelişmiş Müfredat ve Kalıp Kütüphanesi (A1-B2 CEFR)**:
   - Oxford 3000/5000 kelime tabanlı orijinal senaryo içerikleri.
