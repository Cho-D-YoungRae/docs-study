from fastapi import APIRouter

# First Steps: https://fastapi.tiangolo.com/ko/tutorial/first-steps/

router = APIRouter()


@router.get("/ch01")
async def root():
    return {"message": "Hello World"}
