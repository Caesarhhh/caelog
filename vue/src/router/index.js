import Vue from 'vue'
import VueRouter from 'vue-router'
import mainpage from "../components/pages/mainpage/mainpage";
import backstage from "../components/pages/backstage/backstage";
import loginpage from "../components/pages/loginpage/loginpage";
import articleedit from "../components/pages/articleedit/articleedit";
import articleshowpage from "../components/pages/blockshowpage/articleshowpage";
Vue.use(VueRouter);
const routes=[
  {
    path:'/mainpage',name:'mainpage',component:mainpage
  },
  {
    path:'/backstage',component:backstage
  },
  {
    path:'/loginpage',component: loginpage
  },
  {
    path:'/articleedit',component: articleedit
  },
  {
    path:'articleshow',component: articleshowpage
  }
]
export default new VueRouter({
  routes:routes
})
