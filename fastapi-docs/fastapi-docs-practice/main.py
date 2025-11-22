from fastapi import FastAPI

import tutorial.ch01
import tutorial.ch02

app = FastAPI()

app.include_router(tutorial.ch01.router)
app.include_router(tutorial.ch02.router)

@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: str | None = None):
    return {"item_id": item_id, "q": q}
