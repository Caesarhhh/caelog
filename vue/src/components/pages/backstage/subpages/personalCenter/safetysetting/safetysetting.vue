<template>
<div class="box">
  <div id="title" :style="getcolor4()"><div>安全设置</div></div>
  <div id="modify">
    <div id="secretmodifyTitle" :style="getcolor4()">修改密码</div>
    <div id="originaltitle" :style="getcolor4()">原始密码</div>
    <input class="input" v-model="originalpassword" id="originalcontent" type="password"></input>
    <div id="newtitle" :style="getcolor4()">新密码</div>
    <input class="input" v-model="newinputpassword" id="newcontent" type="password"></input>
    <div id="confirmtitle" :style="getcolor4()">确认密码</div>
    <input class="input" v-model="confirmpassword" id="confirmcontent" type="password"></input>
    <div id="qusetiontitle" :style="getcolor4()">密保问题</div>
    <div class="input" id="questioncontent" :style="getcolor4()">{{safetyquestion}}</div>
    <input class="input" v-model="safetyanswerinput" id="questironanswer"></input>
    <div id="emailtitle" :style="getcolor4()">邮箱验证码</div>
    <input class="input" id="emailinput" v-model="codeinput" v-if="ifcode"></input>
    <div id="emailget" @click="getrandomcode" v-if="!ifcode">点击获取验证码</div>
    <div id="confirmbutton" :style="getcolor3()" @click="changepassword">提交</div>
  </div>
  <div id="safety">
    <div id="safesettitle" :style="getcolor4()">密保安全</div>
    <div id="safetysetenter" v-if="safetyenter==0">
      <div id="qusetiontitle_" :style="getcolor4()"><div id="questiontitle_div">密保问题</div></div>
      <div class="input" id="questioncontent_" :style="getcolor4()">{{safetyquestion}}</div>
      <input class="input" v-model="safetyanswerinputright" id="questironanswer_"></input>
    </div>
    <div id="emailtitle_" :style="getcolor4()" v-if="safetyenter==0">邮箱验证码</div>
    <input id="emailinput_" v-model="codeinput_" v-if="ifcode_&&safetyenter==0"></input>
    <div id="emailget_" @click="getrandomcode_()" v-if="safetyenter==0&&!ifcode_">点击获取验证码</div>
    <div id="secretenter" v-if="safetyenter==0">
      <div id="secrettitle" :style="getcolor4()"><div>输入密码</div></div>
      <input id="secretinput" type="password" v-model="safetyinput"></input>
      <div :style="getcolor3()" id="secretbutton" @click="entersecret">确认</div>
    </div>
    <div id="questionset" v-if="safetyenter==1">
    <div id="questiontitle" :style="getcolor4()"><div>密保问题</div></div>
    <input id="question" v-model="safetyquestion_"></input>
    <input id="answer_s" v-model="safetyanswer"></input>
    </div>
    <div id="emailset" v-if="safetyenter==1">
      <div id="emailsettitle" :style="getcolor4()">邮箱</div>
      <div id="verify" @click="verifyemail()" :style="getcolorFont2()" v-if="!ifverify">验证</div>
      <input id="emailsetinput" v-model="safetyemail"></input>
    </div>
    <div id="emailcodeset" v-if="safetyenter==1">
      <div id="emailcodesettitle" :style="getcolor4()">验证码</div>
      <input class="input_border emailcodesetinput" v-model="emailcodeinput"></input>
    </div>
    <div :style="getcolor3()" id="savebutton" v-if="safetyenter==1" @click="changesafetyset">保存修改</div>
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
        safetyanswerinputright:"",
        codeinput:"",
        codeinput_:"",
        ifcode:false,
        ifcode_:false,
        safetyquestion: "",
        safetyquestion_: "",
        safetyanswer: "",
        safetyemail:"",
        emailcodeinput:"",
        ifverify:false,
        originemail:""
      }
    },
    methods:{
      getcolor4(){
        return {backgroundColor: this.$store.state.color4}
      },
      getcolorFont2(){
        return {color: this.$store.state.colorFont2}
      },
      verifyemail(){
        if(this.originemail==this.safetyemail){
          alert("邮箱不变无需验证！")
          return
        }
        let data={
          'address':this.safetyemail
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
              this.ifverify=true
            }
            else{
              alert(res.data.msg)
            }
          })
      },
      entersecret:function (){
        var codedata=JSON.parse(localStorage.getItem("token_emailcode_"))
        if(codedata.time<Date.parse(new Date())){
          alert("验证码时效已过，请重新发送")
          this.ifcode_=false
          return
        }
        let temp={
          userid:this.common.loginuserinfo.id,
          oldpassword: this.safetyinput,
          safetyanswerinput:this.safetyanswerinputright,
          codeinput:this.codeinput_,
          emailcode:codedata.code
        }
        this.$axios({
          method:'post',
          url:this.common.serveraddress+"/user/safetysetinput",
          data:temp
        }).then(
          res=>{
            if(res.data.code==200){
              this.safetyenter=1
            }
            else{
              alert(res.data.msg)
            }
          }
        )
      },
      changepassword:function (){
        var codedata=JSON.parse(localStorage.getItem("token_emailcode"))
        if(codedata.time<Date.parse(new Date())){
          alert("验证码时效已过，请重新发送")
          this.ifcode=false
          return
        }
        if(this.newinputpassword!=this.confirmpassword){
          alert("新密码和确认密码不一致！")
          return
        }
        let temp_data={
          userid:this.common.loginuserinfo.id,
          oldpassword:this.originalpassword,
          newpassword:this.newinputpassword,
          safetyanswerinput:this.safetyanswerinput,
          codeinput:this.codeinput,
          emailcode:codedata.code
        }
        this.$axios(
          {
            url:this.common.serveraddress+"/user/changepassword",
            data:temp_data,
            method:'post'
          }).then(
          res=>{
            if(res.data.code==200){
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
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3(){
        return {backgroundColor: this.$store.state.color3}
      },
      getrandomcode:function (){
        let data={
          'userid':this.common.loginuserinfo.id
        }
        this.$axios.post(
          this.common.serveraddress+"/email/send",data).then(
          res=>{
            if(res.data.code==200){
              this.ifcode=true
              let temp={
                code:res.data.data,
                time:Date.parse(new Date())+300000
              }
              localStorage.setItem("token_emailcode",JSON.stringify(temp))
              setTimeout(()=>{
                this.ifcode=false
              },180000)
            }
            else{
              alert(res.data.msg)
            }
          })
      },
      getrandomcode_:function (){
        let data={
          'userid':this.common.loginuserinfo.id
        }
        this.$axios.post(
          this.common.serveraddress+"/email/send",data).then(
          res=>{
            if(res.data.code==200){
              this.ifcode_=true
              let temp={
                code:res.data.data,
                time:Date.parse(new Date())+300000
              }
              localStorage.setItem("token_emailcode_",JSON.stringify(temp))
              setTimeout(()=>{
                this.ifcode_=false
              },180000)
            }
            else{
              alert(res.data.msg)
            }
          })
      },
      changesafetyset:function (){
        if(this.originemail!=this.safetyemail){
          var codedata=JSON.parse(localStorage.getItem("token_emailcode"))
          if(codedata.time<Date.parse(new Date())){
            alert("验证码时效已过，请重新发送")
            this.ifverify=false
            return
          }
        }
        let params={
          userid:this.common.loginuserinfo.id,
          safetyquestion:this.safetyquestion_,
          safetyanswer:this.safetyanswer,
          safetyemail:this.safetyemail,
          emailcode:"ss",
          verifyemail:"ss",
          inputcode:"ss",
          ifemailchange:this.originemail==this.safetyemail?0:1
        }
        if(params.ifemailchange==1){
          params.verifyemail=codedata.email
          params.inputcode=this.emailcodeinput
          params.emailcode=codedata.code
        }
        if(params.safetyanswer==""){
          alert("请输入密保答案！")
          return
        }
        var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        if(!pattern.test(params.safetyemail)){
          alert("请输入正确的邮箱地址！")
          return
        }
        let data={
          emailaddress:this.safetyemail
        }
        this.$axios.post(this.common.serveraddress+"/user/testemail",data).then(res=>{
          if(res.data.code==200){
            this.$axios.post(this.common.serveraddress+"/user/changesafetyset",params).then(
              res=>{
                if(res.data.code==200){
                  this.originemail=this.safetyemail
                  this.safetyquestion=this.safetyquestion_
                  alert("修改成功！")
                }
                else{
                  alert(res.data.msg)
                }
              }
            )
          }
          else{
            alert("已存在重复邮箱地址！")
          }
        })
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
              this.$axios.get(
                this.common.serveraddress+"/user/getemail?userid="+this.common.loginuserinfo.id).then(
                ress=>{
                  this.originemail=ress.data.data,
                    this.safetyemail=ress.data.data
                })
            }
            else{
              alert(res.data.msg)
            }
          })
      }
      this.safetyquestion=this.common.loginuserinfo.securityQuestion
      this.safetyquestion_=this.common.loginuserinfo.securityQuestion
    }
  }
</script>

<style scoped>
.box{
  width:695px;
  height:330px;
  border-style: groove;
  border-width: thin;
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
  border-radius: 10px;
  border-width: thin;
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
  border-style: groove;
  border-width: thin;
  position: absolute;
  top: 15px;
  left: 85px;
}
#safety{
  width:266px;
  height: 300px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:15px;
  left:412px;
}
#secretmodifyTitle{
  width:287px;
  height:34px;
  border-radius: 10px;
  border-width: thin;
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
  border-radius: 10px;
  border-width: thin;
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
  border-style: solid;
  border-width: thin;
  border-radius: 10px;
  outline: none;
}
#originalcontent:focus{
  border-width:2px;
  border-style: solid;
  top:56px;
  left:94px;
}
#newtitle{
  width:70px;
  height: 26px;
  border-radius: 10px;
  border-width: thin;
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
  border-style: solid;
  border-width: thin;
  border-radius: 10px;
  outline:none;
}
#newcontent:focus{
  border-width:2px;
  border-style: solid;
  top:92px;
  left:94px;
}
#confirmtitle{
  width:70px;
  height: 26px;
  border-radius: 10px;
  border-width: thin;
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
  border-radius: 10px;
  border-width: thin;
  border-style: solid;
  outline:none;
}
#confirmcontent:focus{
  border-width:2px;
  border-style: solid;
  top:129px;
  left:94px;
}
#qusetiontitle{
  width:70px;
  height: 55px;
  border-radius: 10px;
  border-width: thin;
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
  border-radius: 10px;
  border-width: thin;
  line-height: 22px;
}
#questironanswer{
  top:197px;
  left:95px;
  border-style: solid;
  border-radius: 10px;
  border-width: thin;
  outline: none;
}
#questironanswer:focus{
  border-width:2px;
  border-style: solid;
  top:196px;
  left:94px;
}
#emailtitle{
  width:70px;
  height: 49px;
  border-radius: 10px;
  border-width: thin;
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
  border-radius: 10px;
  outline:none;
  border-style: solid;
  border-width: thin;
}
#emailinput:focus{
  border-width:2px;
  border-style: solid;
  top:235px;
  left:94px;
}
#emailget{
  top:240px;
  left:128px;
  font-size: 20px;
  font-family: 华光楷体_CNKI;
  position: absolute;
  cursor: pointer;
}
#confirmbutton{
  width: 50px;
  height: 22px;
  position: absolute;
  top:272px;
  left:255px;
  font-family: 华光楷体_CNKI;
  line-height: 22px;
  border-radius: 8px;
  cursor: pointer;
}
#safesettitle{
  width:236px;
  height: 31px;
  border-radius: 10px;
  border-width: thin;
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
  border-width: thin;
  position: absolute;
  left:15px;
  top:57px;
}
#secretenter{
  width: 236px;
  height: 63px;
  border-width: thin;
  position: absolute;
  left:15px;
  top:185px;
}
#safetysetenter{
  width: 236px;
  height: 63px;
  border-width: thin;
  position: absolute;
  left:15px;
  top:57px;
}
#questiontitle{
  width:42px;
  height: 55px;
  border-radius: 10px;
  border-width: thin;
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
#emailtitle_{
  width:50px;
  height: 49px;
  border-radius: 10px;
  border-width: thin;
  position: absolute;
  left:15px;
  top:125px;
  font-family: 华光楷体_CNKI;
  line-height: 26px;
  font-size: 16px;
}
#emailinput_{
  position: absolute;
  height: 26px;
  top:135px;
  left:72px;
  border-radius: 10px;
  outline:none;
  border-style: solid;
  border-width: thin;
}
#emailinput_:focus{
  border-width:2px;
  border-style: solid;
  top:134px;
  left: 71px;
}
#emailget_{
  top:138px;
  left:86px;
  font-size: 20px;
  font-family: 华光楷体_CNKI;
  position: absolute;
  cursor: pointer;
}
#secrettitle{
  width:42px;
  height: 55px;
  border-radius: 10px;
  border-width: thin;
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
  border-style: groove;
  border-width: thin;
  top:2px;
  left:55px;
  font-family: 华光楷体_CNKI;
  font-size: 15px;
  border-radius: 10px;
  outline: none;
}
#question:focus{
  border-width:2px;
  border-style: solid;
  top:1px;
  left:54px;
}
#secretinput{
  width:169px;
  height:26px;
  position: absolute;
  border-style: solid;
  border-width: thin;
  top:2px;
  left:55px;
  font-family: 华光楷体_CNKI;
  font-size: 15px;
  border-radius: 10px;
  outline: none;
}
#secretinput:focus{
  border-width:2px;
  border-style: solid;
  top:1px;
  left:54px;
}
#qusetiontitle_{
  width:42px;
  height: 55px;
  border-radius: 10px;
  border-width: thin;
  position: absolute;
  top:2px;
  left:5px;
  font-family: 华光楷体_CNKI;
  font-size: 16px;
}
#questiontitle_div{
  position: absolute;
  top:8px;
}
#questioncontent_{
  width:174px;
  height:22px;
  position: absolute;
  top:4px;
  left:55px;
  line-height: 22px;
  border-radius: 10px;
}
#questironanswer_{
  width: 169px;
  height: 25px;
  top:30px;
  left:55px;
  border-width: thin;
  border-style: solid;
  border-radius: 10px;
  outline: none;
}
#questironanswer_:focus{
  border-width:2px;
  border-style: solid;
  top:29px;
  left:54px;
}
#answer_s{
  width: 174px;
  height: 24px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:33px;
  left:55px;
  font-family: 华光楷体_CNKI;
  font-size: 15px;
  border-radius: 10px;
  outline: none;
}
#answer_s:focus{
  border-width:2px;
  border-style: solid;
  top:32px;
  left:54px;
}
#secretbutton{
  width:50px;
  height: 24px;
  position: absolute;
  right:10px;
  top:37px;
  border-radius: 10px;
  font-family: 华光楷体_CNKI;
  line-height: 24px;
  cursor: pointer;
}
#emailset{
  width:236px;
  height: 34px;
  border-width: thin;
  position: absolute;
  left:15px;
  top:127px;
}
#emailsettitle{
  width:42px;
  height: 27px;
  border-radius: 10px;
  border-width: thin;
  position: absolute;
  left:5px;
  top:3px;
  font-family: 华光楷体_CNKI;
  font-size: 16px;
  line-height: 27px;
}
#emailsetinput{
  width: 134px;
  height: 24px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  left:55px;
  top:2px;
  border-radius: 10px;
  outline: none;
}
#emailsetinput:focus{
  border-width:2px;
  border-style: solid;
  top:1px;
  left:54px;
}
#emailcodeset{
  width:236px;
  height: 34px;
  position: absolute;
  left:15px;
  top:167px;
}
#emailcodesettitle{
  width:42px;
  height: 40px;
  border-radius: 10px;
  border-width: thin;
  position: absolute;
  left:5px;
  top:-3px;
  font-family: 华光楷体_CNKI;
  font-size: 16px;
  line-height: 20px;
}
#verify{
  position: absolute;
  top:6px;
  left:195px;
  width:50px;
  font-size: 18px;
  font-family: 华光楷体_CNKI;
  cursor: pointer;
}
.emailcodesetinput{
  width: 94px;
  height: 24px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  left:55px;
  top:2px;
  border-radius: 10px;
  outline: none;
}
.emailcodesetinput:focus{
  border-width:2px;
  border-style: solid;
  top:1px;
  left:54px;
}
#savebutton{
  width: 98px;
  height: 43px;
  line-height: 43px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  cursor: pointer;
  position: absolute;
  border-radius: 10px;
  top:248px;
  left:155px;
}
</style>
