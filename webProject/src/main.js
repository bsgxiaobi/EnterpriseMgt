import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueAxios from "vue-axios";
import axios from "axios"
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';


//createApp(App).use(store).use(router).use(axios).use(ElementPlus).mount('#app')
const app = createApp(App);
app.config.globalProperties.$axios=axios;
app.use(store).use(router).use(ElementPlus).mount('#app');

// axios.defaults.baseURL = 'http://localhost:8082';//后端开发环境地址
// axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';//配置请求头信息。
axios.defaults.headers.common['accessToken'] = 'FA4C308D5E8F6409E01344ADDAEB4C71';