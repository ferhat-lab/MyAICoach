# Teknik Kararlar

---

## 06.08.2026

### Karar

Onboarding ekranı giriş/kayıt ekranından önce gösterilecek.

### Neden?

Kullanıcı uygulamayı tanımadan giriş yapmak istemeyebilir.

Önce kişiselleştirme yapılacak,
ardından hesap oluşturulacaktır.

### Beklenen Kazanım

- Daha iyi ilk kullanıcı deneyimi
- Daha yüksek kayıt olma oranı

---

## 06.08.2026

### Karar

GameProfile ve UserProfile ayrı tutulacak.

### Neden?

Kişisel bilgiler ile oyun ilerlemesi farklı sorumluluklara sahiptir.

### Beklenen Kazanım

- Daha temiz mimari
- Daha kolay ölçeklenebilirlik

# Architecture Decisions

---

## 07.08.2026

### Decision

Onboarding'deki Character Selection kaldırıldı.

### Reason

İlk sürümde tek AI karakter (Vani) kullanılacak.

Bunun nedenleri:

- Tek ses profili yönetmek daha kolay.
- Tek animasyon sistemi geliştirilecek.
- Marka kimliği daha güçlü olacak.
- Geliştirme maliyeti düşecek.

### Future

v2 sürümünde:

- Multiple AI Characters
- Different Voice Profiles
- Unlockable Characters

tekrar değerlendirilecek.