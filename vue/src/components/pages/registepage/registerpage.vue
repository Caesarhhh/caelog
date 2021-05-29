<template>
<div class="loginpage" :style="getcolor1()">
  <div id="empty" :style="getcolor1()">
    <div id="close">
      <img :src="closeimgsrc" alt="error">
    </div>
    <input id="pic" name="multipartFile" type="file" @change="uploadpic" enctype="multipart/form-data">
    <div id="backimgsrc">
      <img :src="inputmsg.backgroundimgsrc" alt="error">
    </div>
    <div id="body">
      <div :class="namevalid?'tips_valid':'tips'">{{nametips}}</div>
      <div id="nametitle">用户名</div>
      <div id="passwordtitle">密码</div>
      <div id="confirmpasswordtitle">确认密码</div>
      <div id="introductiontitle">个人介绍</div>
      <div id="securityQuestiontitle">密保问题</div>
      <div id="securityAnswertitle">密保回答</div>
      <div :class="emailvalid?'tips_valid_email':'tips_email'">{{emailtips}}</div>
      <div id="securityEmailtitle">密保邮箱</div>
      <div id="emailbutton" v-if="!ifcode" :style="getcolorFont2()" @click="getrandomcode">验证</div>
      <div id="securityEmailcodetitle">验证码</div>
      <input type="text" @keyup="testname" id="nameinput" v-model="inputmsg.textinput">
      <input type="password" id="passwordinput" v-model="inputmsg.passwordinput">
      <input type="password" id="confirmpasswordinput" v-model="inputmsg.confirmpasswordinput">
      <textarea id="introductioninput" v-model="inputmsg.introduction" />
      <input type="text" id="securityQuestioninput" v-model="inputmsg.securityQuestion">
      <input type="text" id="securityAnswerinput" v-model="inputmsg.securityAnswer">
      <input @keyup="testemail" type="text" id="securityEmailinput" v-model="inputmsg.securityEmail">
      <input type="text" id="securityEmailcodeinput" v-model="inputmsg.codeinput">
      <div id="register" :style="getcolor3()" @click="submitForm">注册</div>
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
        closeimgsrc:this.common.getserveraddress+"images/close2.png",
        inputmsg:{
          textinput:"",
          passwordinput:"",
          confirmpasswordinput:"",
          introduction:"",
          backgroundimgsrc:this.common.getserveraddress+"images/tool/uploadpic.png",
          securityQuestion:"",
          securityAnswer:"",
          securityEmail:"",
          codeinput:""
        },
        nametips:"",
        namevalid:false,
        emailtips:"",
        emailvalid:false,
        ifcode:false,
        userToken:'',
      }
    },
    computed:{
    },
    methods:{
      ...mapMutations(['changeLogin']),
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3(){
        return {backgroundColor: this.$store.state.color3}
      },
      getcolor4(){
        return {backgroundColor: this.$store.state.color4}
      },
      getcolorFont2(){
        return {color:this.$store.state.colorFont2}
      },
      testname:function(){
        let data={
          username:this.inputmsg.textinput
        }
        if(data.username==""){
          this.nametips="Please input nickname!"
          this.namevalid=false
          return
        }
        this.$axios.post(this.common.serveraddress+"/user/testname",data).then(res=>{
          if(res.data.code==200){
            this.nametips="Nickname is effective!"
            this.namevalid=true
          }
          else{
            this.nametips=res.data.msg
            this.namevalid=false
          }
        })
      },
      testemail:function(){
        let data={
          emailaddress:this.inputmsg.securityEmail
        }
        if(data.emailaddress==""){
          this.emailtips="Please input email!"
          this.emailvalid=false
          return
        }
        var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        if(!pattern.test(data.emailaddress)){
          this.emailtips=""
          this.emailvalid=false
          return
        }
        this.$axios.post(this.common.serveraddress+"/user/testemail",data).then(res=>{
          if(res.data.code==200){
            this.emailtips="email is effective!"
            this.emailvalid=true
          }
          else{
            this.emailtips=res.data.msg
            this.emailvalid=false
          }
        })
      },
      uploadFile:function (url, data) {
        let config = {
          url: url,
          baseURL: this.common.serveraddress,
          transformResponse: [function (data1) {
            var data = data1;
            if (typeof data1 == "string") {
              data = JSON.parse(data1);
            }
            return data;
          }],
          headers: {'Content-Type': "multipart/form-data"},
          withCredentials: true,
          responseType: 'json',
        };
        return this.$axios.post(url, data, config);
      },
      uploadpic:function(e){
        let file = e.target.files[0]
        let param = new FormData()
        param.append('file', file)
        this.$axios.post(this.common.serveraddress+"/files/uploadtemp",param).then(res=>{
          this.uploadurl=res.data.data.url
          this.inputmsg.backgroundimgsrc=this.uploadurl
        })
        // this.uploadFile("/files/uploadtemp",param).then(res=>{
        //   console.log(res)
        //   this.uploadurl=this.common.getserveraddress+res.data.data;
        //   this.inputmsg.backgroundimgsrc=this.uploadurl;})
      },
      getrandomcode:function (){
        // if(!this.emailvalid){
        //   alert("请输入有效的密保邮箱！")
        //   return
        // }
        let data={
          'address':this.inputmsg.securityEmail
        }
        this.$axios.post(
          this.common.serveraddress+"/email/sendtemp",data).then(
          res=>{
            if(res.data.code==200){
              this.ifcode=true
              let temp={
                code:res.data.data.code,
                time:Date.parse(new Date())+120000,
                email:res.data.data.email
              }
              localStorage.setItem("token_emailcode",JSON.stringify(temp))
              this.ifcode=true
            }
            else{
              alert(res.data.msg)
            }
          })
      },
      submitForm(){
        if(this.inputmsg.textinput==""){
          alert("请输入用户名")
          return
        }
        if(this.namevalid==false){
          alert("请输入有效的用户名")
          return
        }
        if(this.inputmsg.passwordinput!=this.inputmsg.confirmpasswordinput){
          alert("确认密码与原始密码不符！")
          return
        }
        if(this.inputmsg.introduction==""){
          alert("请完成个人介绍！")
          return
        }
        if(this.inputmsg.securityQuestion==""){
          alert("请设置安全问题！")
          return
        }
        if(this.inputmsg.securityAnswer==""){
          alert("请设置密保回答！")
          return
        }
        if(this.inputmsg.securityAnswer==""){
          alert("请设置密保邮箱！")
          return
        }
        // if(!this.emailvalid){
        //   alert("请输入有效的密保邮箱！")
        //   return
        // }
        var codedata=JSON.parse(localStorage.getItem("token_emailcode"))
        var timestamp = Date.parse(new Date());
        if(codedata.time+300000<timestamp){
          this.ifcode=false
          alert("验证码失效，请重新发送！")
          return
        }
        let param={
          nickname:this.inputmsg.textinput,
          password:this.inputmsg.passwordinput,
          introduction:this.inputmsg.introduction,
          backgroundimgsrc:this.inputmsg.backgroundimgsrc,
          securityQuestion:this.inputmsg.securityQuestion,
          securityAnswer:this.inputmsg.securityAnswer,
          securityEmail:this.inputmsg.securityEmail,
          emailcode:codedata.code,
          verifyemail:codedata.email,
          inputcode:this.inputmsg.codeinput
        }
        this.$axios.post(this.common.serveraddress+"/user/register",param).then(
          res=>{
            if(res.data.code==200){
                 alert("注册成功")
                 this.$router.push("/login")
            }
            else{
              alert(res.data.msg)
            }
          }
        )
      }
    }
  }
</script>

<style scoped>
.loginpage{
  width:424px;
  height: 620px;
  border-style: ridge;
  position: fixed;
  top:55%;
  left: 50%;
  margin-top: -324px;
  margin-left: -212px;
}
.tips{
  position: absolute;
  left:200px;
  top:10px;
  font-size: 15px;
  font-family: 华光楷体_CNKI;
  color: red;
}
.tips_valid{
  position: absolute;
  left:200px;
  top:10px;
  font-size: 15px;
  font-family: 华光楷体_CNKI;
  color: green;
}
.tips_email{
  position: absolute;
  left:200px;
  top:414px;
  font-size: 15px;
  font-family: 华光楷体_CNKI;
  color: red;
}
.tips_valid_email{
  position: absolute;
  left:200px;
  top:414px;
  font-size: 15px;
  font-family: 华光楷体_CNKI;
  color: green;
}
#empty{
  width:424px;
  height: 620px;
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
  height: 562px;
  position: absolute;
  top:48px;
  left:3px;
}
#pic{
  width: 80px;
  height: 80px;
  position: absolute;
  top:2px;
  left:20px;
  opacity: 0;
  z-index: 100;
}
#backimgsrc{
  width: 65px;
  height: 65px;
  position: absolute;
  top:6px;
  left:25px;
  border-radius: 50%;
  overflow: hidden;
}
#backimgsrc img{
  width:100%;
  height: 100%;
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
#confirmpasswordinput{
  position: absolute;
  top:130px;
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
#confirmpasswordtitle{
  position: absolute;
  top:130px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#introductiontitle{
  position: absolute;
  top:180px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#introductioninput{
  position: absolute;
  top:180px;
  left:120px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
  height:120px;
}
#securityQuestiontitle{
  position: absolute;
  top:330px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#securityQuestioninput{
  position: absolute;
  top:330px;
  left:120px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#securityAnswertitle{
  position: absolute;
  top:380px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#securityAnswerinput{
  position: absolute;
  top:380px;
  left:120px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#emailbutton{
  position: absolute;
  top:430px;
  right:5px;
  line-height: 30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
  cursor: pointer;
}
#securityEmailtitle{
  position: absolute;
  top:430px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#securityEmailcodetitle{
  position: absolute;
  top:480px;
  left:30px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#securityEmailinput{
  position: absolute;
  top:430px;
  left:120px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#securityEmailcodeinput{
  position: absolute;
  top:480px;
  left:120px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#register{
  position: absolute;
  border-radius: 5px;
  bottom:0px;
  right:25px;
  font-family: 华光楷体_CNKI;
  font-size: 25px;
  cursor: pointer;
}
</style>
