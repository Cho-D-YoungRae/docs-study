from fastapi import FastAPI

import tutorial.ch01

app = FastAPI()

app.include_router(tutorial.ch01.router)
