<template>
  <div id="app">
    <div class="log" v-if="state">欢迎，{{username}}</div>
    <div class="logout" v-if="state" @click="logout">logout</div>
    <div class="logout" v-if="!state" @click="login">login</div>
    <router-view></router-view>
  </div>
</template>

<script>
import mainpage from "./components/pages/mainpage/mainpage";
import backstage from "./components/pages/backstage/backstage";
import articleshowpage from "./components/pages/blockshowpage/blockshowpage";
import articlereadpage from "./components/pages/articlereadpage/articlereadpage";
import loginpage from "./components/pages/loginpage/loginpage";
import articleedit from "./components/pages/articleedit/articleedit";
import axios from "axios";
export default {
  name: 'App',
  data(){
    return {
    }
  },
  computed:{
    state:function (){
      return !(JSON.stringify(this.common.loginuserinfo)==='{}'||this.common.loginuserinfo==null)
    },
    username:function (){
      return this.common.loginuserinfo.nickname
    }
  },
  components: {
    mg:mainpage,
    bg:backstage,
    asg:articleshowpage,
    arg:articlereadpage,
    lg:loginpage,
    ae:articleedit
  },
  methods:{
    logout:function (){
      localStorage.removeItem("userinfo")
      localStorage.removeItem("Authorization")
      this.common.loginuserinfo={}
      location.reload()
    },
    login:function (){
      this.$router.push("/login")
    }
  },
  beforeMount() {
    const s = document.createElement('script');
    s.type = 'text/javascript';
    s.src = 'http://caesar216.usa3v.net/caelog/images/tool/showdown-master/dist/showdown.min.js';
    document.body.appendChild(s);
    this.$axios.interceptors.request.use(
      config => {
        if (localStorage.getItem('Authorization')) {
          config.headers.Authorization = localStorage.getItem('Authorization');
        }

        return config;
      },
      error => {
        return Promise.reject(error);
      });
    this.common.loginuserinfo=JSON.parse(localStorage.getItem("userinfo"));
  }
}
</script>

<style>
  body{
    background-color: #f5f6f7;
  }
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  position: relative;
  margin-top: 0px;
  width:1024px;
  margin:auto;
  height:100%;
  height: auto;
  min-height: 1100px;
}
.log{
  position: absolute;
  width:120px;
  height: 20px;
  right:100px;
  top:0px;
  text-align: center;
  z-index: 100;
}
.logout{
  position: absolute;
  width:30px;
  height: 20px;
  right:60px;
  top:0px;
  color: #0000ff;
  text-decoration: underline;
  cursor: pointer;
  z-index: 100;
}
</style>
