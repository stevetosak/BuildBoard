import {type ActionFunction, redirect} from "react-router-dom";
import {type ValidationError} from "@pages/shared/ValidationError.tsx";
import {default as API_ENDPOINTS} from '@constants/api-endpoints.ts'
import generateErrorAlert from "@pages/shared/alertGenerator";
import { formDataToJson } from "@/shared/form-processing";
import type { JWTResponse } from "@shared/security-utils";


type ErrorResponse = {
    detail: string 
}
type RegisterationResponse = JWTResponse | ErrorResponse

const hasErrorOccured = (response: Response, _data: JWTResponse | ErrorResponse): _data is ErrorResponse => response.status > 300
const userAlreadyExists = () => generateErrorAlert("Registration unsuccessful", `The username already exists`)

const DUPLICATE_USER = 'username already exists'

const registerUser: ActionFunction = async ({request}) => {
    const response = await fetch(API_ENDPOINTS.register(), {
        method: "POST",
        headers: {
            "Content-Type": 'application/json',
        },
        body: await formDataToJson(request.formData()),
    })
    const data = await response.json() as RegisterationResponse

    if (hasErrorOccured(response, data) && data.detail.toLowerCase().includes(DUPLICATE_USER))
        return {Element: userAlreadyExists()} satisfies ValidationError
    if (!response.ok)
        throw new Error("Unknown error" + '\n' + await response.json())
    
    const {token} = data as JWTResponse
    localStorage.setItem('token', token)

    return redirect("/homepage")
}

export default registerUser;