<template>
<div class="box">
  <div id="title"><div>安全设置</div></div>
  <div id="modify">
    <div id="secretmodifyTitle">修改密码</div>
    <div id="originaltitle">原始密码</div>
    <input class="input" v-model="originalpassword" id="originalcontent" type="password"></input>
    <div id="newtitle">新密码</div>
    <input class="input" v-model="newinputpassword" id="newcontent" type="password"></input>
    <div id="confirmtitle">确认密码</div>
    <input class="input" v-model="confirmpassword" id="confirmcontent" type="password"></input>
    <div id="qusetiontitle">密保问题</div>
    <div class="input" id="questioncontent">{{safetyquestion}}</div>
    <input class="input" v-model="safetyanswerinput" id="questironanswer"></input>
    <div id="emailtitle">邮箱验证码</div>
    <input class="input" id="emailinput" v-model="codeinput" v-if="ifcode"></input>
    <div id="emailget" @click="getrandomcode" v-if="!ifcode">获取验证码</div>
    <button id="confirmbutton" @click="changepassword">提交</button>
  </div>
  <div id="safety">
    <div id="safesettitle">密保安全</div>
    <div id="secretenter" v-if="safetyenter==0">
      <div id="secrettitle"><div>输入密码</div></div>
      <input id="secretinput" type="password" v-model="safetyinput"></input>
      <button id="secretbutton" @click="entersecret">确认</button>
    </div>
    <div id="questionset" v-if="safetyenter==1">
    <div id="questiontitle"><div>密保问题</div></div>
    <input id="question" v-model="safetyquestion"></input>
    <input id="answer" v-model="safetyanswer"></input>
    </div>
    <div id="emailset" v-if="safetyenter==1">
      <div id="emailsettitle">邮箱</div>
      <input id="emailsetinput" v-model="safetyemail"></input>
    </div>
    <button id="savebutton" v-if="safetyenter==1" @click="changesafetyset">保存修改</button>
  </div>
</div>
</template>

<script>
  export default {
    name: "safetysetting",
    props:["datas"],
    data(){
      return{
        safetyenter:0,
        safetyinput:"",
        user:{},
        code:"",
        originalpassword:"",
        newinputpassword:"",
        confirmpassword:"",
        safetyanswerinput:"",
        codeinput:"",
        ifcode:false,
        safetyquestion: "",
        safetyanswer: "",
        safetyemail:""
      }
    },
    methods:{
      entersecret:function (){
        if(this.safetyinput==this.common.loginuserinfo.password){
          this.safetyenter=1;
        }
        else{
          alert("密码错误");
        }
      },
      changepassword:function (){
        if(this.originalpassword!=this.common.loginuserinfo.password){
          alert("原始密码错误")
          return
        }
        if(this.newinputpassword!=this.confirmpassword){
          alert("新密码和确认密码不一致！")
          return
        }
        if(this.safetyanswerinput!=this.common.loginuserinfo.securityAnswer){
          alert("密保问题回答错误")
          return
        }
        if(this.codeinput!=this.code){
          alert("验证码错误")
          return
        }
        this.$axios.get(
          this.common.serveraddress+"/user/changepassword?userid="+this.common.loginuserinfo.id+"&password="+this.newinputpassword).then(
          res=>{
            if(res.data.code==200){
              console.log(res.data.msg)
            }
            else{
              alert(res.data.msg)
            }
          })
      },
      getrandomcode:function (){
        this.$axios.get(
          this.common.serveraddress+"/email/send?address="+this.common.loginuserinfo.securityEmail).then(
          res=>{
            if(res.data.code==200){
              this.ifcode=true
              this.code=res.data.data
              alert("修改成功!")
              this.originalpassword=""
              this.newinputpassword=""
              this.confirmpassword=""
              this.safetyanswerinput=""
              this.codeinput=""
            }
            else{
              alert(res.data.msg)
            }
          })
      },
      changesafetyset:function (){
        this.$axios.get(
          this.common.serveraddress+"/user/changesafetyset?userid="+this.common.loginuserinfo.id+"&safetyquestion="+this.safetyquestion+"&safetyanswer="+this.safetyanswer+"&safetyemail="+this.safetyemail
        ).then(
          res=>{
            console.log(res.data.data)
          }
        )
      }
    },
    mounted() {
      var valid=true
      if(valid){
        this.$axios.get(
          this.common.serveraddress+"/user/get?userid="+this.common.loginuserinfo.id).then(
          res=>{
            if(res.data.code==200){
              this.common.loginuserinfo=res.data.data;
            }
            else{
              alert(res.data.msg)
            }
          })
      }
      this.safetyquestion=this.common.loginuserinfo.securityQuestion
      this.safetyanswer=this.common.loginuserinfo.securityAnswer
      this.safetyemail=this.common.loginuserinfo.securityEmail
    }
  }
</script>

<style scoped>
.box{
  width:695px;
  height:330px;
  border-style: ridge;
}
.input{
  width: 205px;
  height: 22px;
  position: absolute;
  font-family: 华光楷体_CNKI;
  font-size: 16px;
}
#title{
  width:47px;
  height: 300px;
  position: absolute;
  top:15px;
  left:15px;
  border-style: ridge;
}
#title div{
  width:30px;
  height: 100px;
  position: absolute;
  left:7px;
  top:93px;
  text-align: center;
  font-size: 24px;
  font-family: 华光楷体_CNKI;
}
#modify {
  width: 313px;
  height: 300px;
  border-style: ridge;
  position: absolute;
  top: 15px;
  left: 85px;
}
#safety{
  width:266px;
  height: 300px;
  border-style: ridge;
  position: absolute;
  top:15px;
  left:412px;
}
#secretmodifyTitle{
  width:287px;
  height:34px;
  border-style: ridge;
  position: absolute;
  top:14px;
  left: 14px;
  line-height: 34px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
#originaltitle{
  width:70px;
  height: 26px;
  border-style: ridge;
  position: absolute;
  left:14px;
  top:56px;
  font-family: 华光楷体_CNKI;
  line-height: 26px;
  font-size: 16px;
}
#originalcontent{
  top:57px;
  left:95px;
}
#newtitle{
  width:70px;
  height: 26px;
  border-style: ridge;
  position: absolute;
  left:14px;
  top:92px;
  font-family: 华光楷体_CNKI;
  line-height: 26px;
  font-size: 16px;
}
#newcontent{
  top:93px;
  left:95px;
}
#confirmtitle{
  width:70px;
  height: 26px;
  border-style: ridge;
  position: absolute;
  left:14px;
  top:129px;
  font-family: 华光楷体_CNKI;
  line-height: 26px;
  font-size: 16px;
}
#confirmcontent{
  top:130px;
  left:95px;
}
#qusetiontitle{
  width:70px;
  height: 55px;
  border-style: ridge;
  position: absolute;
  left:14px;
  top:167px;
  line-height: 55px;
  font-family: 华光楷体_CNKI;
  line-height: 55px;
  font-size: 16px;
}
#questioncontent{
  top:168px;
  left:95px;
  border-style: ridge;
  line-height: 22px;
}
#questironanswer{
  top:197px;
  left:95px;
}
#emailtitle{
  width:70px;
  height: 49px;
  border-style: ridge;
  position: absolute;
  left:14px;
  top:234px;
  font-family: 华光楷体_CNKI;
  line-height: 26px;
  font-size: 16px;
}
#emailinput{
  top:236px;
  left:95px;
}
#emailget{
  top:240px;
  left:140px;
  font-size: 20px;
  font-family: 华光楷体_CNKI;
  position: absolute;
}
#confirmbutton{
  width: 45px;
  height: 22px;
  position: absolute;
  top:269px;
  left:255px;
}
#safesettitle{
  width:236px;
  height: 31px;
  border-style: ridge;
  position: absolute;
  left:15px;
  top:14px;
  line-height: 31px;
  font-size: 20px;
  font-family: 华光楷体_CNKI;
}
#questionset{
  width: 236px;
  height: 63px;
  border-style: ridge;
  position: absolute;
  left:15px;
  top:57px;
}
#secretenter{
  width: 236px;
  height: 63px;
  border-style: ridge;
  position: absolute;
  left:15px;
  top:57px;
}
#questiontitle{
  width:42px;
  height: 55px;
  border-style: ridge;
  position: absolute;
  top:2px;
  left:5px;
  font-family: 华光楷体_CNKI;
  font-size: 16px;
}
#questiontitle div{
  position: absolute;
  top:8px;
}
#secrettitle{
  width:42px;
  height: 55px;
  border-style: ridge;
  position: absolute;
  top:2px;
  left:5px;
  font-family: 华光楷体_CNKI;
  font-size: 16px;
}
#secrettitle div{
  position: absolute;
  top:8px;
}
#question{
  width:174px;
  height:26px;
  position: absolute;
  border-style: ridge;
  top:2px;
  left:55px;
  font-family: 华光楷体_CNKI;
  font-size: 15px;
}
#secretinput{
  width:174px;
  height:26px;
  position: absolute;
  border-style: ridge;
  top:2px;
  left:55px;
  font-family: 华光楷体_CNKI;
  font-size: 15px;
}
#answer{
  width: 174px;
  height: 24px;
  border-style: ridge;
  position: absolute;
  top:33px;
  left:55px;
  font-family: 华光楷体_CNKI;
  font-size: 15px;
}
#secretbutton{
  width:50px;
  height: 24px;
  position: absolute;
  right:10px;
  top:37px;
}
#emailset{
  width:236px;
  height: 34px;
  border-style: ridge;
  position: absolute;
  left:15px;
  top:127px;
}
#emailsettitle{
  width:42px;
  height: 27px;
  border-style: ridge;
  position: absolute;
  left:5px;
  top:2px;
  font-family: 华光楷体_CNKI;
  font-size: 16px;
  line-height: 27px;
}
#emailsetinput{
  width: 174px;
  height: 24px;
  border-style: ridge;
  position: absolute;
  left:55px;
  top:2px;
}
#savebutton{
  width: 98px;
  height: 43px;
  position: absolute;
  top:248px;
  left:155px;
}
</style>
