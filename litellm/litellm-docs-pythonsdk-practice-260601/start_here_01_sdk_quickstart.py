from litellm import completion
from dotenv import load_dotenv

load_dotenv()

"""
Docs: https://docs.litellm.ai/docs/learn/sdk_quickstart
"""

response = completion(
    model="openai/gpt-4o",
    messages=[
        {"role": "user", "content": "Hello, how are you?"}
    ]
)

print("response message:", response.choices[0].message.content)
print("response: \n", response.to_json())