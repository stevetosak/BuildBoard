import axios from "axios";
import {getToken} from "@shared/security-utils.ts";

export const api = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 10000,
});

export const apiGetAuthenticated = async <T> (uri : string) => {
    return await api.get<T>(uri,{
        headers:{
            Authorization: `Bearer ${getToken()}`
        }
    })
}
export const apiPostAuthenticated = async <T> (uri : string,data?: any,contentType?: string) => {
    return await api.post<T>(uri,data,{
        headers: {
            Authorization: `Bearer ${getToken()}`,
            "Content-Type": contentType
        }
    })
}

