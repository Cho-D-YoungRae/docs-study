import litellm
from litellm import batch_completion, batch_completion_models, batch_completion_models_all_responses
from dotenv import load_dotenv

load_dotenv()

def send_multiple_completion_calls_to_1model():
    responses = batch_completion(
        model="gpt-3.5-turbo",
        messages=[
            [{"role": "user", "content": "good morning"}],
            [{"role": "user", "content": "what's the time?"}],
        ]
    )

    for response in responses:
        print(response.choices[0].message.content)

def send_1completion_call_to_many_models_return_fastest_response():
    response = batch_completion_models(
        models=["gpt-3.5-turbo", "gpt-4o"],
        messages=[{"role": "user", "content": "Hey, how's it going"}]
    )

    print(response.choices[0].message.content)

def send_1completion_call_to_many_models_return_all_responses():
    responses = batch_completion_models_all_responses(
        models=["gpt-3.5-turbo", "gpt-4o"],
        messages=[{"role": "user", "content": "Hey, how's it going"}]
    )
    for response in responses:
        print(response.choices[0].message.content)

if __name__ == '__main__':
    send_1completion_call_to_many_models_return_all_responses()
