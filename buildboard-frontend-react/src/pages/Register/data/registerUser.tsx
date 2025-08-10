import {type ActionFunction, redirect} from "react-router-dom";
import {type ValidationError} from "@pages/shared/ValidationError.tsx";
import {default as API_ENDPOINTS} from '@/constants.ts'
import generateErrorAlert from "@pages/shared/alertGenerator.ts";

type ErrorMessages = "Username already exists"

type ErrorResponse = {
    msg: ErrorMessages
}
type SuccessResponse = {
    [K: string]: string
}
type RegisterationResponse = SuccessResponse | ErrorResponse

const hasErrorOccured = (response: Response, _data: SuccessResponse | ErrorResponse): _data is ErrorResponse => response.status === 404
const userAlreadyExists = (username: string) => generateErrorAlert("Registration unsuccessful", `The username [${username}] already exists`)

const registerUser: ActionFunction = async ({request}) => {
    const formData = await request.formData()
    const response = await fetch(API_ENDPOINTS.register(), {
        method: "POST",
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded',
        },
        body: formData,
    })
    const data = await response.json() as RegisterationResponse

    if (hasErrorOccured(response, data) && data.msg === 'Username already exists')
        return {Element: userAlreadyExists(formData.get("username") as string)} satisfies ValidationError
    if (!response.ok)
        throw new Error("Unknown error" + '\n' + await response.json())
    return redirect("/homepage")
}

export default registerUser;