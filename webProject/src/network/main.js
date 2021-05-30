import axios from "axios";
import qs from 'qs';

axios.defaults.baseURL = 'http://localhost:8082';//后端开发环境地址
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';//配置请求头信息。