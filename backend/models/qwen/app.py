import asyncio
import json
import logging
from fastapi import FastAPI, Request
from fastapi.responses import StreamingResponse

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("Qwen3-LLM-Service")

app = FastAPI(title="Qwen3 LLM Streaming Service", version="1.0.0")

@app.get("/health")
async def health():
    return {"status": "ok", "model": "Qwen/Qwen2.5-3B-Instruct", "service": "vLLM/Qwen3 Streaming API"}

@app.post("/v1/chat/completions")
async def chat_completions(request: Request):
    """
    OpenAI & vLLM Uyumlu Streaming Chat Completion Endpoint'i.
    Gelen istemci isteğine göre token-by-token (SSE - Server-Sent Events) jeton akışı üretir.
    """
    data = await request.json()
    messages = data.get("messages", [])
    user_prompt = messages[-1].get("content", "") if messages else "Hello"

    logger.info(f"🧠 Qwen3 Token Üretimi Başlatıldı: \"{user_prompt}\"")

    async def token_generator():
        # vLLM / Qwen3 PyTorch Token Streaming Simülasyonu
        response_text = f"Hello! I am Vani, your AI cat coach. I heard you say: '{user_prompt}'. How can I help you practice English today? 🐱"
        words = response_text.split(" ")

        for word in words:
            await asyncio.sleep(0.06)  # ~60ms Time-To-Next-Token (vLLM speed)
            chunk = {
                "id": "chatcmpl-qwen3-mock",
                "object": "chat.completion.chunk",
                "choices": [{
                    "index": 0,
                    "delta": {"content": word + " "},
                    "finish_reason": None
                }]
            }
            yield f"data: {json.dumps(chunk)}\n\n"

        yield "data: [DONE]\n\n"

    return StreamingResponse(token_generator(), media_type="text/event-stream")
