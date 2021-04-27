import mainpage from "./components/pages/mainpage/mainpage";
import backstage from "./components/pages/backstage/backstage";
import loginpage from "./components/pages/loginpage/loginpage";
import articleedit from "./components/pages/articleedit/articleedit";
import registerpage from "./components/pages/registepage/registerpage";
import articleshowpage from "./components/pages/blockshowpage/blockshowpage";
import articlereadpage from "./components/pages/articlereadpage/articlereadpage";
const routers=[
  {
    path:'/:userid/mainpage',name:'mainpage',component:mainpage
  },
  {
    path:'/:userid/backstage',name:'backstage',component:backstage
  },
  {
    path:'/',name:'loginpage',component: loginpage
  },
  {
    path:'',name:'loginpage',component: loginpage
  },
  {
    path:'/login',name:'loginpage',component: loginpage
  },
  {
    path:'/register',name:'registerpage',component:registerpage
  },
  {
    path:'/:userid/articleedit',name:'articleedit',component: articleedit
  },
  {
    path:'/:userid/articleedit/:articleid',name:'articleedit',component: articleedit
  },
  {
    path:'/:userid/articleshow/:type/:data',name:'articleshow',component: articleshowpage
  },
  {
    path:'/:userid/articleshow/:type',name:'articleshow',component: articleshowpage
  },
  {
    path:'/:userid/articleread/:articleid',name:'articleread',component: articlereadpage
  }
]
export default routers

