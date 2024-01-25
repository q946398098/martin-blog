import axios from 'axios';

const instance = axios.create({
    baseURL: 'https://api.example.com', // 设置你的 API URL
    timeout: 5000, // 设置请求超时时间
    // 其他 Axios 配置项...
});

// 封装 GET 请求
export const get = url => {
    let params;
    return instance.get(url, { params });
};

// 封装 POST 请求
export const post = (url, data) => {
    return instance.post(url, data);
};


export default instance;