import icon from "@assets/Icon.jpg";
import {Link, useNavigate} from "react-router-dom";
import {default as CIcon} from "@assets/C.svg?react"
import {default as CppIcon} from "@assets/Cpp.svg?react"
import {default as CSharp} from "@assets/CSharp.svg?react"
import {default as PyIcon} from "@assets/Pyhton.svg?react"
import {default as JsIcon} from "@assets/JS.svg?react"
import {default as JavaIcon} from "@assets/Java.svg?react"
import {default as CollaborationIcon} from '@assets/Collabooration.svg?react'
import {twMerge} from "tailwind-merge";
import { useContext } from "react";
import SecurityContext from "@/context/security-context";

const sharedIconCls = 'w-[10em] h-[10em] animate-bounce'
const liItemsDist = 'list-disc'

const LandingPage = () => {
    const user = useContext(SecurityContext)
    const navigate = useNavigate()

    if(user) 
        navigate('/homepage')

    return (
        <main className="grid grid-rows-[repeat(3,100vh)_auto] grid-cols-[100vw] bg-bg-1">
            <section className="relative overflow-hidden">
                <div
                    className="text-white text-center relative z-10 w-full h-[calc(100vh-5vh)] text-4xl lg:px-[25%] pb-[6em] grid grid-rows-[auto_1em_min-content_min-content]">
                    <div className="relative z-1">
                        <img
                            src={icon}
                            alt={"Logo"}
                            className="w-full h-full absolute top-0 left-0 "
                        />
                    </div>
                    <div className="h-[1em]"></div>
                    <h1 className="px-[15%] text-4xl">
                        Engage in the conversation, unlock solutions, and build the
                        future-toghether
                    </h1>
                    <h1>
                        Join us{" "}
                        <Link
                            className="text-accent underline"
                            to={"/homepage"}
                        >
                            now
                        </Link>
                    </h1>
                </div>
                <div
                    className="bg-[radial-gradient(circle_at_40%_top,_#0D1D35_40%,_#051224_80%,_#051224)]  absolute z-1 w-[120vw] h-full rounded-b-[60%] bottom-[20vh] lg:bottom-[10vh]"></div>
            </section>
            <section className="bg-[#001027] text-white pt-[2em] grid grid-rows-[2em_auto] gap-[4em]">
                <h1 className="text-4xl text-center">
                    What’s <span className="text-accent">Buildboard</span>
                </h1>
                <div className="w-full grid grid-cols-2 grid-rows-1 gap-[4em] px-[3em] pb-[2em] items-center">
                    <p className="text-2xl">
                        <span className="text-accent">BuildBoard</span> is a social platform
                        crafted for software developers to connect, share, and collaborate.
                        It encourages open discussions around technologies, problem-solving
                        strategies, and development approaches in a welcoming, accessible
                        environment. While centered on conversation,{" "}
                        <span className="text-accent">BuildBoard</span> also supports
                        internal collaboration features that let developers engage directly
                        on projects, fostering teamwork and idea exchange. The platform aims
                        to unite developers for meaningful dialogue, learning, and
                        real-world collaboration—all in one place.
                    </p>
                    <div
                        className="group h-full bg-[radial-gradient(circle_at_center,#327155_0%,#001027_60%)] grid grid-rows-2 grid-cols-3">
                        <PyIcon className={twMerge(sharedIconCls, 'justify-self-end self-end')}/>
                        <JavaIcon className={twMerge(sharedIconCls, 'justify-self-center self-center')}/>
                        <JsIcon className={twMerge(sharedIconCls, 'justify-self-start self-end')}/>
                        <CppIcon className={twMerge(sharedIconCls, "justify-self-end self-center")}/>
                        <CIcon className={twMerge(sharedIconCls, 'justify-self-center self-start')}/>
                        <CSharp className={twMerge(sharedIconCls, 'justify-self-start self-center')}/>
                    </div>
                </div>
            </section>
            <section
                className={'grid grid-rows-[repeat(2,min-content)_auto] gap-[2em] text-4xl py-[1em] text-white px-[2em]'}>
                <h1 className={'text-accent text-center'}>Purpose</h1>
                <div className={'lg:px-[4em]'}>
                    <p className={'text-2xl text-center'}>BuildBoard bridges the gap between information overload and
                        meaningful
                        guidance for developers.
                        Our platform creates a centralized space for open discussion, knowledge sharing, and
                        collaborative problem-solving.
                        By connecting diverse perspectives, BuildBoard helps developers find clear direction, connect
                        with peers, and tackle complex challenges together. It's not just a forum—it's a community
                        powered by discussion and mutual support.</p>
                </div>
                <CollaborationIcon className={'w-full h-full'}/>
            </section>
            <section className={'bg-[#001027] py-[2em] text-white text-2xl grid grid-rows-3 pb-[4em] gap-[2em] px-[3em] lg:px-[8em]'}>
                <h1 className={'text-center text-4xl'}>Have an <span className={'text-accent'}>Idea?</span></h1>
                <p className={'text-center'}>
                    If you have any ideas, questions, or run into any problems, feel free to reach out to us
                    anytime—we’re here to help and eager to hear from you! You can contact us through
                </p>
                <ul className={'relative left-[1em]'}>
                    <li className={twMerge(liItemsDist)}>Viktor Hristovski: <a className={'text-accent'} href={'mailto:viktorhristovski629@gmail.com'}>viktorhristovski629@gmail.com</a></li>
                    <li className={twMerge(liItemsDist)}>Stefan Toskovski: <a className={'text-accent'} href={'mailto:stefantoska84@gmail.com'}>stefantoska84@gmail.com</a></li>
                </ul>
            </section>
        </main>
    );
};

export default LandingPage;
