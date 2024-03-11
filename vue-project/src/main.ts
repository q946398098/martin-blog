import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
// 在 Vue 中全局注册 vue-quill-editor 组件
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

const app = createApp(App)
app.component('QuillEditor', QuillEditor) //https://vueup.github.io/vue-quill/guide/options.html
app.use(ElementPlus)
app.use(createPinia())
app.use(router)
app.mount('#app')
