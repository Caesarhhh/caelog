<template>
  <div id="app">
    <a-page-header
      style="border-bottom: 1px solid rgb(235, 237, 240)"
      title="Caelog.top"
      :sub-title="welcomeText"
    >
      <template slot="extra">
        <a-popover :title="adminName" class="popBox" style="background-color: white;border-style: none">
          <template slot="content">
            <div v-if="iflogin" class="userButton" @click="toBackStage">前往后台</div>
            <div v-if="iflogin" class="userButton" @click="logout">退出登录</div>
            <div v-if="!iflogin" class="userButton" @click="login">登录</div>
            <div v-if="!iflogin" class="userButton" @click="register">注册</div>
          </template>
          <a-button type="primary">
            <a-icon type="user" style="color: rgba(0,0,0,.25)"/>
          </a-button>
        </a-popover>
        <a-popover class="popBox" @click="toDocPage" style="background-color: white;border-style: none">
          <template slot="content">
            <div>项目文档</div>
          </template>
          <a-button type="primary">
            <a-icon type="question" style="color: rgba(0,0,0,.25)"/>
          </a-button>
        </a-popover>
      </template>
    </a-page-header>
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
      bcolor:"green",
      adminName:"",
      welcomeText:"",
      iflogin:false
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
    if(((JSON.stringify(this.common.loginuserinfo)==='{}')||this.common.loginuserinfo==null)){
      this.welcomeText="您好，未登录"
      this.adminName="未登录"
      this.iflogin=false
      return
    }
    this.adminName=this.common.loginuserinfo.nickname
    this.welcomeText="欢迎，"+this.adminName+"!"
    this.iflogin=true
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
    toBackStage(){
      this.$router.push("/"+this.common.loginuserinfo.id+"/backstage")
    },
    toDocPage: function () {
      window.open("https://github.com/Caesarhhh/caelog");
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
  beforeCreate() {
  },
  beforeMount() {
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
    if(typeof this.$route.params.userid=="undefined"){
      this.$store.commit('changecolorSelect',0)
      let i=0
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color1,1])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color2,2])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color3,3])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color4,4])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].colorFont,"Font"])
      this.$store.commit('changecolor',[this.$store.state.colorSelect[i].colorFont2,"Font2"])
      this.changeback()
      return
    }
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

}

.userButton {
  cursor: pointer;
  background-color: inherit;
  height: 30px;
}

.userButton :hover {
  background-color: coral;
  color: coral;
}


.popBox {
  background-color: white;
  color: white;
  border-style: none;
}

.popBox:hover {
  background-color: white;
}

.popBox:focus {
  background-color: inherit;
}
</style>
