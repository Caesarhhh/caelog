<template>
<div class="loginpage">
  <div id="empty">
    <div id="close">
      <img :src="closeimgsrc" alt="error">
    </div>
    <div id="body">
      <div id="nametitle">用户名</div>
      <div id="passwordtitle">密码</div>
      <input type="text" id="nameinput" v-model="inputmsg.textinput">
      <input type="password" id="passwordinput" v-model="inputmsg.passwordinput">
      <div id="login" class="unselect" @click="submitForm">登录</div>
    </div>
  </div>
</div>
</template>

<script>
  import {mapMutations} from "vuex";
  export default {
    name: "loginpage",
    props:["datas"],
    data(){
      return{
        closeimgsrc:"http://caesar216.usa3v.net/caelog/images/close2.png",
        inputmsg:{
          textinput:"",
          passwordinput:""
        },
        userToken:'',
      }
    },
    methods:{
      ...mapMutations(['changeLogin']),
      submitForm(){
        let v=this
        let param = new FormData()
        param.append('nickname',this.inputmsg.textinput)
        param.append('password',this.inputmsg.passwordinput)
        this.$axios(
          {
            method:'post',
            url:this.common.serveraddress+"/user/login",
            data:{
              nickname:this.inputmsg.textinput,
              password:this.inputmsg.passwordinput
            }
          }
        ).then(res=>{
          if(res.data.code==400){
            alert('密码或用户名错误');
            return
          }
          this.common.userinfo=res.data.userinfo.data
          this.common.loginuserinfo=res.data.userinfo.data
          v.userToken = res.data.token;
          v.changeLogin({ Authorization:v.userToken });
          localStorage.setItem("userinfo",JSON.stringify(res.data.userinfo.data))
          v.$router.push('/'+this.common.loginuserinfo.id+'/mainpage');
          alert('登录成功');
          location.reload()
        }).catch(function(err){
          alert('密码或用户名错误');
        })
      }
    }
  }
</script>

<style scoped>
.loginpage{
  width:424px;
  height: 240px;
  border-style: ridge;
  position: fixed;
  top:50%;
  left: 50%;
  margin-top: -324px;
  margin-left: -212px;
  background-color: white;
}
#empty{
  width:424px;
  height: 249px;
  position: relative;
  margin-top: 0px;
  margin-left: 0px;
}
#close{
  width:45px;
  height: 45px;
  position: absolute;
  top:3px;
  right: 3px;
}
#close img{
  width:100%;
  height: 100%;
}
#body{
  width:414px;
  height: 182px;
  position: absolute;
  top:48px;
  left:3px;
}
#nametitle{
  position: absolute;
  top:30px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#nameinput{
  position: absolute;
  top:30px;
  left:120px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#passwordtitle{
  position: absolute;
  top:80px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#passwordinput{
  position: absolute;
  top:80px;
  left:120px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#login{
  border-style: ridge;
  position: absolute;
  top:145px;
  right:25px;
  font-family: 华光楷体_CNKI;
  font-size: 25px;
}
.unselect{

  -webkit-user-select:none;

  -moz-user-select:none;

  -ms-user-select:none;

  user-select:none;

}
</style>
