import os
import json
import asyncio
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
    QwenLlmService: Qwen LLM mikroservisine (qwen_llm_service) bağlanan
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
        Qwen LLM servisinden token-by-token (SSE) canlı metin akışı okur.
        User Barge-In durumunda HTTP bağlantısı anında kapatılır.
        """
        logger.info(f"🧠 Qwen LLM İstemcisi Tetiklendi ({self.endpoint_url}): \"{user_prompt}\"")

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

        timeout = httpx.Timeout(
            connect=5.0,
            read=30.0,
            write=10.0,
            pool=5.0
        )

        try:
            async with httpx.AsyncClient(timeout=timeout) as client:
                async with client.stream("POST", self.endpoint_url, json=payload) as response:
                    if response.status_code != 200:
                        logger.error(f"⚠️ Qwen LLM Servis HTTP Hatası (Status {response.status_code})")
                        raise RuntimeError(f"QWEN_SERVICE_HTTP_ERROR:{response.status_code}")

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

        except httpx.ConnectError as e:
            logger.error(f"❌ Qwen servisine ulaşılamadı ({self.endpoint_url}): {e}")
            raise RuntimeError("QWEN_SERVICE_UNAVAILABLE") from e
        except httpx.TimeoutException as e:
            logger.error(f"❌ Qwen servisinde zaman aşımı ({self.endpoint_url}): {e}")
            raise RuntimeError("QWEN_SERVICE_TIMEOUT") from e
        except asyncio.CancelledError:
            logger.info("🛑 Qwen token stream cancelled")
            raise
        except Exception as e:
            logger.error(f"❌ QwenLlmService Beklenmeyen İstek Hatası: {e}")
            raise
