from __future__ import annotations

from pathlib import Path

import bentoml
from pydantic import BaseModel

with bentoml.importing():
    from transformers import pipeline

my_image = bentoml.images.Image(python_version="3.11") \
    .python_packages("torch", "transformers")


@bentoml.service(
    image=my_image,
    resources={"cpu": "2"},
    traffic={"timeout": 30},
)
class Summarization:
    # Define the Hugging Face model as a class variable
    model_path = bentoml.models.HuggingFaceModel("sshleifer/distilbart-cnn-12-6")

    def __init__(self) -> None:
        # Load model into pipeline
        self.pipeline = pipeline('summarization', model=self.model_path)

    @bentoml.api(batchable=True)
    def summarize(self, texts: list[str]) -> list[str]:
        results = self.pipeline(texts)
        return [item['summary_text'] for item in results]

# 여러 파라미터를 함께 묶기 위한 Pydantic 모델
class BatchInput(BaseModel):
    image: Path
    threshold: float

# 배치 가능한 API를 가진 기본 서비스

@bentoml.service
class ImageService:
    @bentoml.api(batchable=True)
    def predict(self, inputs: list[BatchInput]) -> list[Path]:
        return [input.image for input in inputs]

# Wrapper 서비스
@bentoml.service
class MyService:
    batch = bentoml.depends(ImageService)

    @bentoml.api
    async def generate(self, image: Path, threshold) -> Path:
        result = await self.batch.to_async.predict([BatchInput(image=image, threshold=threshold)])
        return result[0]