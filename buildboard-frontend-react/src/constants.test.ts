import {test, expect} from 'vitest'
import API_ENDPOINTS from './constants'

test('Proxy working correctly', () => { 
    const host = import.meta.env.VITE_HOST
    const username = 'buildboard'
    const page = 10

    expect(API_ENDPOINTS.user(username)).toBe(host + `/users?username=${username}`)
    expect(API_ENDPOINTS.threads(page)).toBe(host + `/threads?page=${page}`)
})