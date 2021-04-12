<template>
  <div id="app">
    <div class="log" :style="getcolorFont2()" v-if="state">欢迎，{{username}}</div>
    <div class="logout" :style="getcolorFont2()" v-if="state" @click="logout">logout</div>
    <div class="logout" :style="getcolorFont2()" v-if="!state" @click="login">login</div>
    <div class="register" v-if="!state" :style="getcolorFont2()" @click="register">register</div>
    <router-view :style="stylevar"></router-view>
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
      bcolor:"green"
    }
  },
  computed:{
    state:function (){
      return !(JSON.stringify(this.common.loginuserinfo)==='{}'||this.common.loginuserinfo==null)
    },
    username:function (){
      return this.common.loginuserinfo.nickname
    },
    stylevar:function (){
      return this.getcolors()
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
  mounted() {
  },
  methods:{
    getcolor1(){
      return {backgroundColor: this.$store.state.color1}
    },
    getcolor2(){
      return {backgroundColor: this.$store.state.color2}
    },
    getcolors(){
      return {
        backgroundColor: this.$store.state.color2,
        color:this.$store.state.colorFont
      }
    },
    getcolorFont2(){
      return {
        color: this.$store.state.colorFont2
      }
    },
    changeback(){
      document.querySelector('body').setAttribute('style', 'background-color:'+this.$store.state.color2+";")
    },
    logout:function (){
      localStorage.removeItem("userinfo")
      localStorage.removeItem("Authorization")
      this.common.loginuserinfo={}
      location.reload()
    },
    login:function (){
      this.$router.push("/login")
    },
    register:function (){
      this.$router.push("/register")
    },
    getFontColor(){
      return {color:this.$store.state.colorFont}
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
    let params={
      userid:this.$route.params.userid
    }
    this.$axios.post(this.common.serveraddress+"/user/getbc",params).then(res=>{
      this.$store.commit('changecolorSelect',res.data.data)
      let i=this.$store.state.colorNum
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color1,1])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color2,2])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color3,3])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color4,4])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].colorFont,"Font"])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].colorFont2,"Font2"])
      this.changeback()
    })
  }
}
</script>

<style vars="{ stylevar }">
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
  text-decoration: underline;
  cursor: pointer;
  z-index: 100;
}
.register{
  position: absolute;
  width:30px;
  height: 20px;
  right:130px;
  top:0px;
  text-decoration: underline;
  cursor: pointer;
  z-index: 100;
}
</style>
