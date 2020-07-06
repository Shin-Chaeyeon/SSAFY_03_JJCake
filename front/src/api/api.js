import axios from 'axios'
import store from '../store/index'
import setInterceptors from './interceptor'

function createAPI() {
    return axios.create({
        baseURL: store.state.app.URL,
        headers: {
            'Accept': "*/*",
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
        }
    })
}

function createAuthAPI() {
    const instance = axios.create({
        baseURL: store.state.app.URL,
        headers: {
            'Accept': "*/*",
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
        }
    })
    return instance
}

function createiamgeAPI() {
    return axios.create({
        baseURL: store.state.app.URL,
        headers: {
            'Accept': "application/json",
            "Content-Type": "multipart/form-data",
            "Access-Control-Allow-Origin": "*"
        }
    })
}

function createimageAuthAPI() {
    const instance = axios.create({
        baseURL: store.state.app.URL,
        headers: {
            'Accept': "application/json",
            "Content-Type": "multipart/form-data",
            "Access-Control-Allow-Origin": "*"
        }
    })
    return setInterceptors(instance)
}

export const requestLogin = createAPI()
export const request = createAuthAPI()
export const requestImage = createiamgeAPI()
export const requestImageAuth = createimageAuthAPI()