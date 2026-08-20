import os
import asyncio
import json
import logging
from threading import Thread
from typing import Optional
from fastapi import FastAPI, Request, HTTPException
from fastapi.responses import StreamingResponse
import torch
from transformers import AutoModelForCausalLM, AutoTokenizer, TextIteratorStreamer

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(message)s")
logger = logging.getLogger("Qwen3-LLM-Service")

MODEL_ID = os.getenv("MODEL_ID", "Qwen/Qwen2.5-3B-Instruct")
DEVICE = "cuda" if torch.cuda.is_available() else "cpu"

app = FastAPI(title="Qwen3 Real LLM Streaming Service", version="1.0.0")

model = None
tokenizer = None
model_loaded = False
load_error: Optional[str] = None

def load_model():
    global model, tokenizer, model_loaded, load_error
    try:
        logger.info(f"🚀 Real Qwen Model Yükleniyor: {MODEL_ID} (Cihaz: {DEVICE})...")
        tokenizer = AutoTokenizer.from_pretrained(MODEL_ID, trust_remote_code=True)

        if DEVICE == "cuda":
            model = AutoModelForCausalLM.from_pretrained(
                MODEL_ID,
                torch_dtype=torch.float16,
                device_map="auto",
                trust_remote_code=True
            )
        else:
            logger.warning("⚠️ CUDA bulunamadı, CPU modunda yükleniyor.")
            model = AutoModelForCausalLM.from_pretrained(
                MODEL_ID,
                torch_dtype=torch.float32,
                device_map="cpu",
                trust_remote_code=True
            )

        model_loaded = True
        logger.info(f"✅ Qwen Model Başarıyla Yüklendi! (VRAM Kullanımı: {torch.cuda.memory_allocated() / 1024**2:.1f} MB)")
    except Exception as e:
        load_error = str(e)
        logger.error(f"❌ Model yükleme hatası: {e}")

# Uygulama başlatılırken modeli arka planda yükle
@app.on_event("startup")
async def startup_event():
    asyncio.create_task(asyncio.to_thread(load_model))

@app.get("/health")
async def health():
    if model_loaded:
        vram_mb = torch.cuda.memory_allocated() / 1024**2 if DEVICE == "cuda" else 0
        return {
            "status": "ok",
            "model": MODEL_ID,
            "device": DEVICE,
            "vramAllocatedMb": round(vram_mb, 1),
            "thinkingMode": False
        }
    elif load_error:
        return {
            "status": "error",
            "model": MODEL_ID,
            "errorMessage": load_error
        }
    else:
        return {
            "status": "loading",
            "model": MODEL_ID,
            "message": "Model henüz yükleniyor..."
        }

@app.post("/v1/chat/completions")
async def chat_completions(request: Request):
    if not model_loaded:
        if load_error:
            raise HTTPException(status_code=500, detail=f"Model yüklenemedi: {load_error}")
        raise HTTPException(status_code=530, detail="Model henüz yüklenme aşamasında.")

    data = await request.json()
    messages = data.get("messages", [])
    temperature = data.get("temperature", 0.2)
    max_tokens = data.get("max_tokens", 80)
    stream = data.get("stream", True)

    user_prompt = messages[-1].get("content", "") if messages else "Hello"
    logger.info(f"🧠 Gerçek Qwen Token Üretimi Başlatıldı: \"{user_prompt}\" (max_tokens={max_tokens}, temp={temperature})")

    # Chat Template Yapılandırması
    prompt_text = tokenizer.apply_chat_template(messages, tokenize=False, add_generation_prompt=True)
    inputs = tokenizer(prompt_text, return_tensors="pt").to(DEVICE)

    streamer = TextIteratorStreamer(tokenizer, skip_prompt=True, skip_special_tokens=True)

    generation_kwargs = dict(
        **inputs,
        streamer=streamer,
        max_new_tokens=max_tokens,
        temperature=temperature,
        do_sample=temperature > 0.0,
        pad_token_id=tokenizer.eos_token_id
    )

    # Inference İşlemini Ayrı Thread'de Başlat
    thread = Thread(target=model.generate, kwargs=generation_kwargs)
    thread.start()

    async def sse_token_generator():
        try:
            for new_text in streamer:
                if not new_text:
                    continue
                chunk = {
                    "id": "chatcmpl-qwen3-real",
                    "object": "chat.completion.chunk",
                    "choices": [{
                        "index": 0,
                        "delta": {"content": new_text},
                        "finish_reason": None
                    }]
                }
                yield f"data: {json.dumps(chunk)}\n\n"
                await asyncio.sleep(0.01)

            yield "data: [DONE]\n\n"
        except Exception as e:
            logger.error(f"⚠️ SSE Token Akış Hatası: {e}")

    return StreamingResponse(sse_token_generator(), media_type="text/event-stream")
