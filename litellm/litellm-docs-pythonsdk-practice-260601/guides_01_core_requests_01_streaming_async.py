import time
import traceback

import litellm
from litellm import completion
from litellm import acompletion
import asyncio
from dotenv import load_dotenv

load_dotenv()

"""
Docs: https://docs.litellm.ai/docs/completion/stream
"""


def streaming_responses_usage():
    messages = [{"role": "user", "content": "Hey, how's it going?"}]
    response = completion(model="gpt-3.5-turbo", messages=messages, stream=True)
    for part in response:
        print(part.choices[0].delta.content or "")


def streaming_responses_helper_function():
    messages = [{"role": "user", "content": "Hey, how's it going?"}]
    response = completion(model="gpt-3.5-turbo", messages=messages, stream=True)

    chunks = []
    for chunk in response:
        chunks.append(chunk)

    print(litellm.stream_chunk_builder(chunks, messages))


def async_completion_usage():
    async def test_get_response():
        user_message = "Hello, how are you?"
        messages = [{"role": "user", "content": user_message}]
        response = await acompletion(model="gpt-3.5-turbo", messages=messages)
        return response

    response = asyncio.run(test_get_response())
    print(response)


def async_streaming_usage():
    async def completion_call():
        try:
            print("test acompletion + streaming")
            response = await acompletion(
                model="gpt-3.5-turbo",
                messages=[{"role": "user", "content": "Hey, how's it going?"}],
                stream=True
            )
            print(f"response: {response}")
            async for chunk in response:
                print(chunk)
        except:
            print(f"error occurred: {traceback.format_exc()}")
            pass

    asyncio.run(completion_call())


def error_handling_infinite_loops():
    litellm.set_verbose = False
    loop_amount = litellm.REPEATED_STREAMING_CHUNK_LIMIT + 1
    chunks = [
                 litellm.ModelResponse(**{
                     "id": "chatcmpl-123",
                     "object": "chat.completion.chunk",
                     "created": 1694268190,
                     "model": "gpt-3.5-turbo-0125",
                     "system_fingerprint": "fp_44709d6fcb",
                     "choices": [
                         {"index": 0, "delta": {"content": "How are you?"}, "finish_reason": "stop"}
                     ],
                 }, stream=True)
             ] * loop_amount
    completion_stream = litellm.ModelResponseListIterator(model_responses=chunks)

    response = litellm.CustomStreamWrapper(
        completion_stream=completion_stream,
        model="gpt-3.5-turbo",
        custom_llm_provider="cached_response",
        logging_obj=litellm.Logging(
            model="gpt-3.5-turbo",
            messages=[{"role": "user", "content": "Hey"}],
            stream=True,
            call_type="completion",
            start_time=time.time(),
            litellm_call_id="12345",
            function_id="1245",
        ),
    )
    for chunk in response:
        continue  # expect to raise InternalServerError


if __name__ == '__main__':
    error_handling_infinite_loops()
