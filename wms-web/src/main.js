import Vue from 'vue'
import App from './App.vue'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import VueRouter from 'vue-router'
import router from "@/router";
import store from '@/store';
import './assets/global.css';
import axios from "axios";

Vue.prototype.$axios=axios;
Vue.prototype.$httpsUrl='http://localhost:8090';
Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use(VueRouter);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
