import {Form, useActionData} from "react-router-dom";
import ValidationErrorLayout, {type ValidationActionResult} from "@pages/shared/ValidationError.tsx";
import icon from "@assets/Icon.jpg";
import {Label} from "@components/ui/label.tsx";
import {Input} from "@components/ui/input.tsx";
import {Button} from "@components/ui/button.tsx";
import {Textarea} from "@components/ui/textarea.tsx";

const Register = () => {
    const registerError = useActionData<ValidationActionResult>()
    return (
        <main className={'w-screen h-screen bg-bg-1 grid place-items-center text-lg group'}>
            <ValidationErrorLayout error={registerError}>
                <div
                    className={`grid grid-rows-[10em_auto]  rounded bg-[#02142d] py-[2em] px-[4em] gap-5`}>
                    <div className={'relative'}>
                        <img className={'w-full h-full absolute'} src={icon} alt={'logo'}/>
                    </div>
                    <Form className={'text-white grid gap-5 grid grid-cols-[1fr_1fr]'} method={'post'}>
                        <div className="input-wrapper">
                            <Label htmlFor="username">Username</Label>
                            <Input required type="text" name="username" className="user-info-input-hover"/>
                        </div>

                        <div className="input-wrapper">
                            <Label htmlFor="password">Password</Label>
                            <Input required type="password" name="password" className="user-info-input-hover"/>
                        </div>
                        <div className="input-wrapper">
                            <Label htmlFor="name">Name</Label>
                            <Input required  type="text" name="name" className="user-info-input-hover"/>
                        </div>
                        <div className="input-wrapper">
                            <Label htmlFor="email">Email</Label>
                            <Input required  type="email" name="email" className="user-info-input-hover"/>
                        </div>
                        <div className={'input-wrapper col-span-2'}>
                            <Label htmlFor={'description'}>Description</Label>
                            <Textarea placeholder="Write your description here" name="description"/>
                        </div>
                        <div className={'col-span-2 input-wrapper'}>
                            <Label htmlFor={'sex'}>Choose your sex</Label>
                            <select name={'sex'} id={'sex'} className={'user-info-input-hover border-1 border-white p-2 rounded w-full'}>
                                <option selected>Male</option>
                                <option>Female</option>
                            </select>
                        </div>
                        <Button className={'col-span-2 user-info-card-btn'}>Submit</Button>
                    </Form>
                </div>
            </ValidationErrorLayout>
        </main>
    )
}

export default Register