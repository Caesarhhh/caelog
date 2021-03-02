// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import routers from "./routers";
import VueRouter from "vue-router";
import axios from "axios";
import common from "./components/tools/common/common";
Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.prototype.$axios=axios;
Vue.prototype.common=common;
/* eslint-disable no-new */
const router=new VueRouter({
  mode:'history',
  routes:routers
})
new Vue({
  el: '#app',
  components:{
    app:App
  },
  router,
  template: '<app></app>'
})
const s = document.createElement('script');
s.type = 'text/javascript';
s.src = 'https://cdnjs.cloudflare.com/ajax/libs/showdown/1.9.0/showdown.min.js';
document.body.appendChild(s);
