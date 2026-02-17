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

## [Async task queues](https://docs.bentoml.com/en/latest/get-started/async-task-queues.html)

- Async task queues는 오래 걸리는 작업을 백그라운드에서 비동기로 실행할 수 있게 해주는 기능이다.
- 작업을 제출하면 즉시 task ID를 반환하고, 나중에 상태 조회나 결과 확인이 가능하다.
- `@bentoml.task` 데코레이터로 작업 엔드포인트를 정의할 수 있다.
- BentoML은 자동으로 제출, 상태 조회, 결과 조회, 취소, 재시도용 API를 생성한다.
- 배치 처리, 생성형 AI 작업, 즉시 응답이 필요 없는 작업에 적합하다.

## [Packaging for deployment](https://docs.bentoml.com/en/latest/get-started/packaging-for-deployment.html)

- 이 페이지는 BentoML 서비스를 배포 가능한 패키지(Bento) 로 만드는 방법을 설명한다.
- `bentoml build` 명령으로 프로젝트를 패키징하고, `.bentoignore`로 제외 파일을 설정할 수 있다.
- 생성된 Bento는 고유 버전을 가지며 `bentoml list`로 확인할 수 있다.
- `bentoml containerize` 명령으로 Docker 이미지로 변환할 수 있다.
- 이렇게 만든 이미지는 Docker가 지원되는 환경 어디서든 실행 가능하다.

### Bento 빌드의 장점 요약

1. 재현성 보장

   코드, 모델, 의존성을 하나의 불변 패키지로 묶어 언제든 동일 환경을 재현할 수 있다.

2. 모델 버전 고정

   빌드 시점의 모델 파일이 함께 저장되어 외부 변경(Hugging Face 등)의 영향을 받지 않는다.

3. 배포 방식 분리

    동일한 Bento를 Docker, Kubernetes, Cloud 등 다양한 환경에 그대로 배포할 수 있다.
4. 운영 기능 내장

   HTTP 서버, 배칭, 비동기 작업, 리소스 설정 등 서빙에 필요한 기능이 기본 제공된다.

5. 버전 관리 및 롤백 용이

   서비스 단위로 버전이 관리되어 문제 발생 시 이전 버전으로 쉽게 되돌릴 수 있다.

## [LLM inference: vLLM](https://docs.bentoml.com/en/latest/examples/vllm.html)

- 이 페이지는 vLLM을 사용해 LLM(예: Llama 3.1)을 고성능으로 서빙하는 방법을 설명한다.
- vLLM은 PagedAttention, 연속 배칭 등으로 높은 처리량과 효율적인 GPU 메모리 사용을 제공한다.
- BentoML은 서비스 정의, 리소스 설정(GPU 타입/개수), 모델 패키징을 담당한다.
- 결과적으로 OpenAI 호환 API 형태의 LLM 서버를 쉽게 구축할 수 있다.
- BentoCloud를 이용하면 CLI 한 줄로 프로덕션 배포도 가능하다.

## [Agent: Function calling](https://docs.bentoml.com/en/latest/examples/function-calling.html)

- 이 페이지는 LLM이 외부 함수를 실제로 호출하는 AI 에이전트를 BentoML로 구현하는 방법을 설명한다.
- LLM은 OpenAI 호환 chat.completions API를 통해 함수 정의(JSON 스키마)를 전달받고, 필요 시 함수 이름과 인자(arguments) 를 반환한다.
- 서비스는 이를 받아 실제 Python 함수를 실행한 뒤, 결과를 다시 LLM에 전달해 최종 자연어 응답을 생성한다.
- LLM 서비스와 비즈니스 로직 서비스(Exchange Assistant)를 분리해 확장성과 유지보수성을 높였다.
- 로컬 GPU 환경 또는 BentoCloud에 배포해 운영할 수 있다.

## [Agent: LangGraph](https://docs.bentoml.com/en/latest/examples/langgraph.html)

- LangGraph는 에이전트의 상태/분기/도구 호출 같은 ‘오케스트레이션 로직’**을 담당하고, BentoML은 이를 HTTP/REST API로 노출해 운영 가능한 서비스로 만든다.
- 아키텍처는 크게 (1) LangGraph 에이전트를 담은 BentoML 서비스와 (2) 텍스트 생성을 담당하는 LLM 두 축으로 구성된다.
- 요청 흐름은 User → BentoML API → LangGraph(Agent 노드에서 LLM 호출) → 필요 시 Tool 노드(예: DuckDuckGo) → 다시 Agent → 응답 반환이다.
- LLM은 외부 API(예: Claude/OpenAI) 를 붙일 수도 있고, 오픈소스 LLM을 BentoML로 같이 호스팅하는 구성도 가능하다(운영/확장 분리).
- 결과적으로 LangGraph의 복잡한 에이전트 로직을 그대로 유지하면서 BentoML의 배포/스케일링/관측(운영) 레이어 위에 올려 프로덕션으로 가져가는 패턴을 보여준다.

## [LLM safety: ShieldGemma](https://docs.bentoml.com/en/latest/examples/shieldgemma.html)

- 이 페이지는 ShieldGemma를 활용해 LLM 안전성을 강화하는 방법을 설명한다.
- 사용자 프롬프트를 바로 대형 모델(GPT-4o 등)에 전달하지 않고, 먼저 ShieldGemma(소형 안전 모델) 로 유해성 점수를 평가한다.
- 프롬프트의 정책 위반 확률(score)이 설정한 임계값(threshold) 을 초과하면 요청을 차단하고, 초과하지 않으면 그때만 LLM이 응답을 생성한다.
- 아키텍처는 Gemma(안전 검사 서비스) → ShieldAssistant(게이트 + LLM 호출) 구조의 2단계 게이팅(gating) 패턴이다.
- 이를 통해 비용을 절감하면서도, 유해하거나 정책 위반 가능성이 있는 입력을 사전에 차단하는 안전한 LLM 서비스 구조를 구현한다.
