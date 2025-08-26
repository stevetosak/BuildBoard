import {type ActionFunction, redirect } from "react-router-dom";
import {default as API_ENDPOINTS} from '@constants/api-endpoints'
import {type ValidationError} from "@pages/shared/ValidationError.tsx";
import generateErrorAlert from "@pages/shared/alertGenerator.tsx";
import type { JWTResponse } from "@shared/security-utils";
import { formDataToJson } from "@/shared/form-processing";

export const invalidCreds= generateErrorAlert("Login unsucessful", "The username or password is incorrect")

const validateUser:ActionFunction = async ({request}) => {
   const response = await fetch(API_ENDPOINTS.auth(), {
       method: 'POST',
       headers: {
           'Content-Type': 'application/json',
       },
       body: await formDataToJson(request.formData())
   })

   if(response.status === 401){
       return {Element : invalidCreds} satisfies ValidationError
   }
   if(!response.ok){
       throw new Error("Unknown error" + '\n' + await response.json())
   }

   const {token} = await response.json() as JWTResponse
   localStorage.setItem("token", token) 

   return redirect('/homepage');
}

export default validateUser;