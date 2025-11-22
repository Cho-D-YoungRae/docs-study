from enum import Enum

from fastapi import APIRouter

# Path Parameters: https://fastapi.tiangolo.com/tutorial/path-params/

router = APIRouter()


@router.get("/ch02/items/{item_id}")
async def read_item(item_id: int):
    return {"item_id": item_id}

# path operation 은 순서대로 작동하므로 /users/me, /users/{user_id} 순서로 작성해야 한다.

@router.get("/ch02/users/me")
async def read_user_me():
    return {"user_id": "the current user"}


@router.get("/ch02/users/{user_id}")
async def read_user(user_id: str):
    return {"user_id": user_id}

class ModelName(str, Enum):
    ALEXNET = "alexnet"
    RESNET = "resnet"
    LENET = "lenet"

@router.get("/ch02/models/{model_name}")
async def get_model(model_name: ModelName):
    if model_name is ModelName.ALEXNET:
        return {"model_name": model_name, "message": "Deep Learning FTW!"}

    if model_name.value == "lenet":
        return {"model_name": model_name, "message": "LeCNN all the images"}

    return {"model_name": model_name, "message": "Have some residuals"}

@router.get("/ch02/files/{file_path:path}")
async def read_file(file_path: str):
    return {"file_path": file_path}