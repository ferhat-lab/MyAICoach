import os
import json
import logging
from typing import AsyncGenerator, List, Dict, Any, Optional
import httpx

logger = logging.getLogger("MyAICoach-QwenLlmService")

VANI_SYSTEM_PROMPT = """You are Vani, a friendly, encouraging AI cat coach helping students learn English.
Pedagogical Guidelines:
- Level: CEFR A1 / A2
- Demeanor: Warm, friendly, supportive cat coach (use occasional playful cat emojis like 🐱).
- Maximum sentence length: 15 words per response.
- Use simple, Oxford 3000 A1/A2 vocabulary.
- Ask short follow-up questions to keep the conversation going.
"""

class QwenLlmService:
    """
    QwenLlmService: Gerçek Qwen3 LLM mikroservisine (qwen_llm_service) bağlanan
    ve Server-Sent Events (SSE) jeton akışını okuyan istemci.
    """
    def __init__(self, endpoint_url: Optional[str] = None):
        self.endpoint_url = endpoint_url or os.getenv(
            "QWEN_SERVICE_URL",
            "http://qwen_llm_service:8001/v1/chat/completions"
        )

    async def stream_tokens(
        self,
        user_prompt: str,
        system_prompt: str = VANI_SYSTEM_PROMPT,
        conversation_history: Optional[List[Dict[str, str]]] = None
    ) -> AsyncGenerator[str, None]:
        """
        Gerçek Qwen3 servisinden token-by-token (SSE) canlı metin akışı okur.
        User Barge-In durumunda HTTP bağlantısı anında kapatılır.
        """
        logger.info(f"🧠 Gerçek Qwen3 LLM İstemcisi Tetiklendi ({self.endpoint_url}): \"{user_prompt}\"")

        messages = [
            {"role": "system", "content": system_prompt}
        ]
        if conversation_history:
            messages.extend(conversation_history)
        messages.append({"role": "user", "content": user_prompt})

        payload = {
            "messages": messages,
            "temperature": 0.2,
            "max_tokens": 80,
            "stream": True
        }

        try:
            async with httpx.AsyncClient(timeout=30.0) as client:
                async with client.stream("POST", self.endpoint_url, json=payload) as response:
                    if response.status_code != 200:
                        logger.error(f"⚠️ Qwen3 Servis Hatası (Status {response.status_code})")
                        yield "I'm sorry, I encountered a temporary connection issue. 🐱"
                        return

                    async for line in response.aiter_lines():
                        line = line.strip()
                        if not line or not line.startswith("data:"):
                            continue

                        data_str = line[5:].strip()
                        if data_str == "[DONE]":
                            break

                        try:
                            chunk_json = json.loads(data_str)
                            choices = chunk_json.get("choices", [])
                            if choices:
                                delta_content = choices[0].get("delta", {}).get("content", "")
                                if delta_content:
                                    yield delta_content
                        except Exception as parse_err:
                            logger.warning(f"⚠️ SSE Parse Uyarısı: {parse_err}")

        except httpx.ConnectError:
            logger.warning(f"⚠️ Qwen3 servisine ulaşılamadı ({self.endpoint_url}). Fallback simülasyon yanıtı kullanılıyor.")
            # Fallback (Eğer GPU konteyneri kapalıysa istemci çökmesin)
            fallback_text = "Hello! I am Vani, your AI cat coach. Let's practice English together! 🐱"
            for word in fallback_text.split(" "):
                await asyncio.sleep(0.06)
                yield word + " "
        except Exception as e:
            logger.error(f"❌ QwenLlmService İstek Hatası: {e}")
