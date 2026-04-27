

import axios from "axios";


const API = process.env.SERVER_URL

console.log(API)

const baseAxios = axios.create({
    baseURL: API,
    validateStatus: () => true
})


export function getBaseAxios() {
    return baseAxios;
}



