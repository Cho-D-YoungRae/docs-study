import litellm
from litellm import completion, get_supported_openai_params, supports_response_schema
from pydantic import BaseModel
from dotenv import load_dotenv

load_dotenv()

"""
Docs: https://docs.litellm.ai/docs/reasoning_content
"""

def quick_start():
    response = completion(
        model="openai/gpt-5.4",
        messages=[
            {"role": "user", "content": "What is the capital of France?"},
        ],
        reasoning_effort="low",
    )
    print(response.choices[0].message.content)

def checking_if_a_model_supports_reasoning():
    print(litellm.supports_reasoning(model="anthropic/claude-3-7-sonnet-20250219"))
    print(litellm.supports_reasoning(model="deepseek/deepseek-chat"))

    print(litellm.supports_reasoning(model="openai/gpt-3.5-turbo"))

if __name__ == '__main__':
    checking_if_a_model_supports_reasoning()