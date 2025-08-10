import type {ReactElement} from "react";

export type ValidationError = {
    Element : ReactElement
}

export type ValidationActionResult = ValidationError | undefined

type ValidationErrorProps = {
    error: ValidationError | undefined
    children : ReactElement | ReactElement[]
}



const ValidationErrorLayout = ({error,children}:ValidationErrorProps) => {
   return (
       <div className={'flex flex-col gap-5'}>
           {error && error.Element}
           {children}
       </div>
   )
}

export default ValidationErrorLayout;