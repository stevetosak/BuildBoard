import {Alert, AlertDescription, AlertTitle} from "@components/ui/alert.tsx";
import {AlertCircleIcon} from "lucide-react";

const generateErrorAlert = (title:string, description:string) => (
    <Alert className={'border-none'} variant="destructive">
        <AlertCircleIcon />
        <AlertTitle>{title}</AlertTitle>
        <AlertDescription>
            <p>{description}</p>
        </AlertDescription>
    </Alert>
)

export default generateErrorAlert;