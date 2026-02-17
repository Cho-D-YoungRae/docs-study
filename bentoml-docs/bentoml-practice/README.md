# [BentoML](https://docs.bentoml.com/en/latest/index.html)

## [Adaptive batching](https://docs.bentoml.com/en/latest/get-started/adaptive-batching.html)

- Adaptive batching은 여러 요청을 자동으로 묶어 모델 처리 효율을 높이는 서버 측 배칭 기능이다.
- 트래픽이 많을 때는 큰 배치로 처리해 처리량을 높이고, 적을 때는 작은 배치로 빠르게 응답한다.
- 실시간 요청 패턴에 따라 배치 크기와 대기 시간을 동적으로 조정한다.
- `@bentoml.api(batchable=True)`로 활성화할 수 있다.
- max_batch_size와 max_latency_ms로 동작을 제어한다.

## [Model composition](https://docs.bentoml.com/en/latest/get-started/model-composition.html)

- Model composition은 여러 모델을 조합해 하나의 AI 애플리케이션을 구성하는 기능이다.
- 하나의 Service 안에서 여러 모델을 실행하거나, 여러 Service로 분리해 독립적으로 확장할 수 있다.
- 모델들은 순차 실행(Sequential) 또는 병렬 실행(Concurrent) 방식으로 연결할 수 있다.
- bentoml.depends()를 사용해 서비스 간 호출과 워크플로우를 구성한다.
- 이를 통해 RAG, 에이전트, 앙상블, 전처리→추론→후처리 같은 복합 추론 그래프를 구현할 수 있다.
