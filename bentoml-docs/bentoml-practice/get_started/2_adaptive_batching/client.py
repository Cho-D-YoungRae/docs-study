import bentoml

if __name__ == "__main__":
    client = bentoml.SyncHTTPClient("http://localhost:3000")

    texts: list[str] = [
        "Paragraph one to summarize",
        "Paragraph two to summarize",
        "Paragraph three to summarize"
    ]

    response = client.summarize(texts=texts)

    print(f"Summarized results: {response}")