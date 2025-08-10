const API_ENDPOINTS = { 
    host : import.meta.env.VITE_HOST, 
    endpoints : { 
        user : '/users',
        threads : '/threads'
    }
}

export default API_ENDPOINTS