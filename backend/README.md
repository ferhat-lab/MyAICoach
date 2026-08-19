# MyAICoach Backend Voice Gateway (FAZ 3)

Real-time Dual-Stream WebSocket Gateway & Turn Orchestration Server.

---

## 🚀 Hızlı Başlatma (Docker Compose)

```bash
docker compose up --build
```

Sunucu varsayılan olarak `http://localhost:8000` portunda çalışacaktır.

---

## 📡 Endpoint Adresleri

- **Health Check**: `GET http://localhost:8000/health`
- **WebSocket Gateway**: `ws://localhost:8000/v1/voice/stream`
- **Android Emülatör Adresi**: `ws://10.0.2.2:8000/v1/voice/stream`
- **Gerçek Cihaz LAN Adresi**: `ws://<PC_LAN_IP>:8000/v1/voice/stream`

---

## ⚙️ Protokol & Güvenlik Notları

- **Protocol Version**: `protocolVersion = 1`
- **Ses Parametreleri**: 48,000 Hz, 16-bit PCM, Mono (`AUDIO_SAMPLE_RATE = 48000`)
- **Server-Side TurnGuard**: İn-memory iskelet korumadır.

### 🛡️ Production Güvenlik TODO Listesi:
- [ ] Firebase ID Token Validation / OAuth2
- [ ] TLS / WSS Entegrasyonu (`wss://`)
- [ ] Redis Distributed Lock & Sliding-Window Rate Limiting
- [ ] Compute Quota & CostGuard Politikaları
