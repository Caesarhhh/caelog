// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex' //引入状态管理
import App from './App'
import routers from "./routers";
import VueRouter from "vue-router";
import axios from "axios";
import common from "./components/tools/common/common";
import store from "./store";
Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.prototype.$axios=axios;
Vue.prototype.common=common;
/* eslint-disable no-new */
const router=new VueRouter({
  mode:'history',
  routes:routers
})
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next();
  } else {
    let token = localStorage.getItem('Authorization');
    if (token === 'null' || token === '') {

      next('/login');
    } else {
      next();
    }
  }
});
new Vue({
  el: '#app',
  components:{
    app:App
  },
  router,
  store:store,
  template: '<app></app>'
})
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('Authorization')) {
      config.headers.Authorization = localStorage.getItem('Authorization');
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  });
const s = document.createElement('script');
s.type = 'text/javascript';
s.src = 'http://caesar216.usa3v.net/caelog/images/tool/showdown-master/dist/showdown.min.js';
document.body.appendChild(s);
