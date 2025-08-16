import {type ActionFunction, redirect} from "react-router-dom";
import {default as API_ENDPOINTS} from '@constants/api-endpoints'
import {type ValidationError} from "@pages/shared/ValidationError.tsx";
import generateErrorAlert from "@pages/shared/alertGenerator.tsx";
import type { JWTResponse } from "@shared/security-utils";
import { formDataToJson } from "@/shared/form-processing";

export const invalidCreds= generateErrorAlert("Login unsucessful", "The username or password is incorrect")

const validateUser:ActionFunction = async ({request}) => {
   const resposne = await fetch(API_ENDPOINTS.auth(), {
       method: 'POST',
       headers: {
           'Content-Type': 'application/json',
       },
       body: await formDataToJson(request.formData())
   })

   if(resposne.status === 401){
       return {Element : invalidCreds} satisfies ValidationError
   }
   if(!resposne.ok){
       throw new Error("Unknown error" + '\n' + await resposne.json())
   }

   const {token} = await resposne.json() as JWTResponse 
   localStorage.setItem("token", token) 

   return redirect('/homepage')
}

export default validateUser;