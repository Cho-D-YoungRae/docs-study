import litellm
from litellm import completion, get_supported_openai_params, supports_response_schema
from pydantic import BaseModel
from dotenv import load_dotenv

load_dotenv()

"""
Docs: https://docs.litellm.ai/docs/completion/json_mode
"""

def quick_start():
    response = completion(
        model="gpt-4o-mini",
        response_format={"type": "json_object"},
        messages=[
            {"role": "system", "content": "You are a helpful assistant designed to output JSON."},
            {"role": "user", "content": "Who won the world series in 2020?"}
        ]
    )
    print(response.choices[0].message.content)

def check_if_model_supports_response_format():
    params = get_supported_openai_params(model="anthropic.claude-3", custom_llm_provider="bedrock")
    print("params", params)
    assert "response_format" in params

def check_if_model_supports_json_schema():
    assert supports_response_schema(model="gemini-1.5-pro-preview-0215", custom_llm_provider="bedrock")

def pass_in_json_schema():
    messages = [{"role": "user", "content": "List 5 important events in the XIX century"}]

    class CalendarEvent(BaseModel):
        name: str
        date: str
        participants: list[str]

    class EventsList(BaseModel):
        events: list[CalendarEvent]

    resp = completion(
        model="gpt-4o-2024-08-06",
        messages=messages,
        response_format=EventsList
    )

    print("Received={}".format(resp))

    event_list = EventsList.model_validate_json(resp.choices[0].message.content)

    print("Parsed events:", event_list.events)

if __name__ == '__main__':
    pass_in_json_schema()