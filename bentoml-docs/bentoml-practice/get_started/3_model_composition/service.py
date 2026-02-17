from __future__ import annotations
import bentoml

with bentoml.importing():
    from transformers import pipeline
    from bentoml.models import HuggingFaceModel

"""
하나의 서비스 안에서 여러 모델을 동시에 같은 하드웨어에서 실행할 수 있습니다.
각 모델에 대해 별도 API를 제공하거나, 두 모델을 사용해 결과를 결합하는 API를 만들 수 있습니다. 
"""

@bentoml.service(
    resources={"gpu": 1, "memory": "4GiB"},
    traffic={"timeout": 20},
)
class MultiModelService:
    model_a_path = HuggingFaceModel("FacebookAI/roberta-large-mnli")
    model_b_path = HuggingFaceModel("distilbert/distilbert-base-uncased")

    def __init__(self):
        # Initialize pipelines for each model
        self.pipeline_a = pipeline(task="zero-shot-classification", model=self.model_a_path,
                                   hypothesis_template="This text is about {}")
        self.pipeline_b = pipeline(task="sentiment-analysis", model=self.model_b_path)

    @bentoml.api
    def process_a(self, input_data: str, labels: list[str] = ["positive", "negative", "neutral"]) -> dict:
        return self.pipeline_a(input_data, labels)

    @bentoml.api
    def process_b(self, input_data: str) -> dict:
        return self.pipeline_b(input_data)[0]

    @bentoml.api
    def combined_process(self, input_data: str, labels: list[str] = ["positive", "negative", "neutral"]) -> dict:
        classification = self.pipeline_a(input_data, labels)
        sentiment = self.pipeline_b(input_data)[0]
        return {
            "classification": classification,
            "sentiment": sentiment
        }