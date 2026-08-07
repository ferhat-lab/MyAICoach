# Meeting Notes

---

# 2026-08-07

## Amaç

Onboarding mimarisini güçlendirmek ve yeniden kullanılabilir UI bileşenlerinin temelini oluşturmak.

---

## Alınan Kararlar

### 1. UserProfile Refactor

OnboardingState içerisindeki kullanıcı bilgileri UserProfile modeline taşındı.

Nedeni:

- Daha temiz state yönetimi
- Firestore kayıtlarını kolaylaştırmak
- Prompt Builder için tek veri modeli oluşturmak

---

### 2. SelectionCard

Tekrarlayan seçim ekranları için ortak bir SelectionCard bileşeni oluşturuldu.

İlk kullanım alanları:

- Learning Goal
- Daily Goal

Gelecekte kullanılacak:

- Premium
- Settings
- Profile
- Character (gerekirse)

---

### 3. Interest Kuralları

Kullanıcı

minimum 3

maksimum 5

ilgi alanı seçebilecek.

Bu sayede AI daha kaliteli kişiselleştirme yapabilecek.

---

### 4. Character Sistemi

İlk sürümde Character Selection kaldırıldı.

Karar:

Tek AI karakter kullanılacak.

Adı (şimdilik):

Vani

Nedeni:

- Tek ses profili yönetmek daha kolay.
- Tek animasyon sistemi geliştirilecek.
- Marka kimliği güçlenecek.
- TTS ve Animation tarafı sadeleşecek.

Future:

v2 sürümünde çoklu karakter sistemi tekrar değerlendirilecek.

---

### 5. Docs Yapısı

docs klasörü aktif olarak kullanılacak.

Dosyalar:

- roadmap.md
- architecture.md
- ai.md
- ui.md
- decisions.md
- meeting-notes.md

---

## Yapılan Geliştirmeler

Tamamlanan onboarding adımları:

✔ Nickname

✔ Age

✔ English Level

✔ Interests

✔ Learning Goal

✔ Daily Goal

Character kaldırıldı.

---

## Sonraki Oturum

- AI Intro ekranı
- Preparing Plan ekranı
- Home Dashboard tasarımı
- SelectionCard animasyonları

---

## Notlar

Artık geliştirme süreci:

Önce ürün fikri

↓

Sonra UX

↓

Sonra mimari

↓

En son kod

şeklinde ilerleyecek.