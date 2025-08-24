import {clsx, type ClassValue} from "clsx";
import {twMerge} from "tailwind-merge";
import * as React from "react";

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs));
}

export const getDateFormat = (date: Date,time = true) => {
    if(time) return `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()} &nbsp ${date.toLocaleTimeString()}`

}

export const messageDateFormat = (dateString: string) => {
    const date = new Date(dateString);
    const now = new Date();
    const diff: number = now.getTime() - date.getTime()

    const second = 1000;
    const minute = second * 60
    const hour = minute * 60
    const day = hour * 24

    if (diff < minute){
        return "just now"
    }
    if (diff < hour) {
        return `${Math.floor(diff / minute)} minutes ago`
    }
    if (diff < day) {
        return `${Math.floor(diff / hour)} hours ago`
    }
    if (diff >= day && diff < day * 2) {
        return `yesterday ${date.toLocaleTimeString()}`
    }
    return getDateFormat(date)

}

export const handlePressEnter = <T extends HTMLElement>(
    e: React.KeyboardEvent<T>,
    callback: (...args: any[]) => any // callback accepts any parameters
) => {
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault();
        callback(); // you can pass arguments if needed
    }
};




