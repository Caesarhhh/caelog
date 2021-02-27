import mainpage from "./components/pages/mainpage/mainpage";
import backstage from "./components/pages/backstage/backstage";
import loginpage from "./components/pages/loginpage/loginpage";
import articleedit from "./components/pages/articleedit/articleedit";
import articleshowpage from "./components/pages/blockshowpage/articleshowpage";
import articlereadpage from "./components/pages/articlereadpage/articlereadpage";
const routers=[
  {
    path:'/mainpage',name:'mainpage',component:mainpage
  },
  {
    path:'/',name:'mainpage',component:mainpage
  },
  {
    path:'/backstage',name:'backstage',component:backstage
  },
  {
    path:'/login',name:'loginpage',component: loginpage
  },
  {
    path:'/articleedit',name:'articleedit',component: articleedit
  },
  {
    path:'/articleshow/search/:searchdata',name:'articleshow',component: articleshowpage
  },
  {
    path:'/articleshow/label/:labeldata',name:'articleshow',component: articleshowpage
  },
  {
    path:'/articleshow/block/:blockdata',name:'articleshow',component: articleshowpage
  },
  {
    path:'/articleread/:articleid',name:'articleread',component: articlereadpage
  }
]
export default routers
