import asyncio
import json
import logging
from typing import AsyncGenerator, List, Dict, Any, Optional

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
    QwenLlmService: Qwen3 / vLLM Token-by-Token Streaming LLM Servis İstemcisi.
    Low TTFT (Time-To-First-Token) hedefli asenkron jeneratör.
    """
    def __init__(self, endpoint_url: str = "http://qwen-llm-service:8001/v1/chat/completions"):
        self.endpoint_url = endpoint_url

    async def stream_tokens(
        self,
        user_prompt: str,
        system_prompt: str = VANI_SYSTEM_PROMPT,
        conversation_history: Optional[List[Dict[str, str]]] = None
    ) -> AsyncGenerator[str, None]:
        """
        Qwen3 LLM modelinden kelime/jeton bazlı canlı metin akışı (token streaming) üretir.
        User Barge-In anında coroutine generator iptal edilebilir.
        """
        logger.info(f"🧠 Qwen3 LLM Token Akışı Başlatıldı: \"{user_prompt}\"")

        # Mock / Fallback Streaming Token Jeneratörü (Gerçek vLLM / OpenAI API Uyumlu)
        mock_response_text = "Hi there! I am Vani, your AI cat coach. What would you like to practice today? 🐱"
        words = mock_response_text.split(" ")

        for word in words:
            await asyncio.sleep(0.08)  # Real LLM token latency simulation (~80ms per token)
            yield word + " "
