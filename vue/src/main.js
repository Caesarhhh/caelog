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
import 'prismjs/themes/prism-okaidia.css';
import 'ant-design-vue/dist/antd.css';
import { Button,FormModel,Pagination,DatePicker,Input,Icon,Form,Select,Checkbox,Row,Col,PageHeader,Popover,Menu,Tag,Table,Divider } from 'ant-design-vue';
import Prism from "prismjs"
Prism.highlightAll()
Vue.config.productionTip = false
Vue.component(Button.name,Button)
Vue.use(Pagination)
Vue.use(VueClipboard)
Vue.use(VueRouter);
Vue.use(Table);
Vue.use(DatePicker);
Vue.use(Select);
Vue.use(Divider);
Vue.use(Form);
Vue.use(Menu);
Vue.use(Row);
Vue.use(Col);
Vue.use(Popover);
Vue.use(FormModel);
Vue.use(Input);
Vue.use(Icon);
Vue.use(Checkbox);
Vue.use(PageHeader);
Vue.use(Tag)
Vue.prototype.$axios=axios;
Vue.prototype.common=common;
/* eslint-disable no-new */
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
s.src = common.getserveraddress+'images/tool/showdown-master/dist/showdown.min.js';
document.body.appendChild(s);
