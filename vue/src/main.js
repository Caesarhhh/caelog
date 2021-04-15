// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import routers from "./routers";
import VueRouter from "vue-router";
import axios from "axios";
import common from "./components/tools/common/common";
import store from "./store";
import VueClipboard from 'vue-clipboard2';
import 'prismjs/themes/prism.css';
import hljs from "highlight.js"
import 'highlight.js/styles/stackoverflow-dark.css';
Vue.config.productionTip = false
Vue.use(VueClipboard)
Vue.use(VueRouter)
Vue.prototype.$axios=axios;
Vue.prototype.common=common;
/* eslint-disable no-new */
Vue.directive('highlight',function (el) {
  let blocks = el.querySelectorAll('pre code');
  blocks.forEach((block)=>{
    hljs.highlightBlock(block)
  })
})
const router=new VueRouter({
  mode:'history',
  routes:routers,
  base:'/caelog/'
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
router.afterEach(() => {
  window.scrollTo(0,0);
})
new Vue({
  el: '#app',
  components:{
    app:App
  },
  router,
  store:store,
  template: '<app></app>'
})
const s = document.createElement('script');
s.type = 'text/javascript';
s.src = 'http://caesar216.usa3v.net/caelog/images/tool/showdown-master/dist/showdown.min.js';
document.body.appendChild(s);
