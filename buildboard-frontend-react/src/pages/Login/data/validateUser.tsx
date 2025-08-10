import {type ActionFunction, redirect} from "react-router-dom";
import {default as API_ENDPOINTS} from '@/constants'
import {type ValidationError} from "@pages/shared/ValidationError.tsx";
import generateErrorAlert from "@pages/shared/alertGenerator.tsx";

export const invalidCreds= generateErrorAlert("Login unsucessful", "The username or password is incorrect")

const validateUser:ActionFunction = async ({request}) => {
   const resposne = await fetch(API_ENDPOINTS.auth(), {
       method: 'POST',
       headers: {
           'Content-Type': 'application/x-www-form-urlencoded',
       },
       body: await request.formData()
   })

   if(resposne.status === 401){
       return {Element : invalidCreds} satisfies ValidationError
   }
   if(!resposne.ok){
       throw new Error("Unknown error" + '\n' + await resposne.json())
   }

   return redirect('/homepage')
}

export default validateUser;