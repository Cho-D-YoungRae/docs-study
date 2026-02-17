# [BentoML](https://docs.bentoml.com/en/latest/index.html)

## [Adaptive batching](https://docs.bentoml.com/en/latest/get-started/adaptive-batching.html)

- Adaptive batching은 여러 요청을 자동으로 묶어 모델 처리 효율을 높이는 서버 측 배칭 기능이다.
- 트래픽이 많을 때는 큰 배치로 처리해 처리량을 높이고, 적을 때는 작은 배치로 빠르게 응답한다.
- 실시간 요청 패턴에 따라 배치 크기와 대기 시간을 동적으로 조정한다.
- `@bentoml.api(batchable=True)`로 활성화할 수 있다.
- max_batch_size와 max_latency_ms로 동작을 제어한다.
