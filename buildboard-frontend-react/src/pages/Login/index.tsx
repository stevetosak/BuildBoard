import icon from "@assets/Icon.jpg"
import {Label} from '@components/ui/label'
import {Input} from "@components/ui/input"
import {Button} from "@components/ui/button.tsx";
import {Form, useActionData} from "react-router-dom";
import ValidationErrorLayout, {type ValidationActionResult } from "@pages/shared/ValidationError.tsx";

const LoginPage = () => {
    const loginError = useActionData<ValidationActionResult>()

    return (
        <main className={'w-screen h-screen bg-bg-1 grid place-items-center text-lg group'}>
            <ValidationErrorLayout error={loginError}>
                <div className={`grid grid-rows-[10em_auto] rounded bg-[#02142d] py-[2em] px-[4em] gap-5`}>
                    <div className={'relative'}>
                        <img className={'w-full h-full absolute'} src={icon} alt={'logo'}/>
                    </div>
                    <Form className={'text-white grid gap-5'} method={'post'}>
                        <div className={'input-wrapper'}>
                            <Label htmlFor={'username'}>Username</Label>
                            <Input required id={'username'} name={'username'} className={"user-info-input-hover"}/>
                        </div>
                        <div className={'input-wrapper'}>
                            <Label htmlFor={'password'}>Password</Label>
                            <Input type={'password'} required id={'password'} name={'password'}
                                   className={"user-info-input-hover"}/>
                        </div>
                        <Button
                            className={'user-info-card-btn'}>Submit</Button>
                    </Form>
                </div>
            </ValidationErrorLayout>
        </main>
    )
}

export default LoginPage