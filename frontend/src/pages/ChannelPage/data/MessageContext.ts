import {createContext, useContext} from "react";
export type MessageContextType = {
    dispatchMessage: (destination: string, body?: Record<string, any>) => void
}
export const MessageContext = createContext<MessageContextType | undefined>(undefined)

export const useMessageContext = () => {
    const context = useContext(MessageContext)
    if(context == undefined) throw new Error("useMessageContext must be used within the appropriate provider!")
    return context;
}
