<template>
<div class="box">
  <div id="infocard">
    <input type="file"id="inputhead" @change="uploadpic">
    <div id="headimg"><img :src="headimgsrc" alt="error"></div>
    <div id="nickname">
      <input type="text" v-model="nickname">
    </div>
    <div id="intro">
      <textarea cols="30" rows="10" v-model="introduction">{{introduction}}</textarea>
    </div>
    <button @click="submit">保存修改</button>
  </div>
  <safeset :datas="safetysettinginfo" id="safetysetting"></safeset>
  <div id="backgroundset">
    <div id="backgroundshow"><img :src="backgroundsrc" alt="error"></div>
    <button id="backgroundbutton">更换背景</button>
  </div>
  <ls id="letterarange"></ls>
</div>
</template>

<script>
  import safetysetting from "./safetysetting/safetysetting";
  import letterset from "./letterset/letterset";
  var safetysettinginfo={
    safetyquestion:"我的政治面貌是什么？",
    safetyanswer:"预备党员",
    email:"yujkl5487@qq.com",
    secret:"123456"
  }
  var lettersetinfo={
    letters:[{
      nickname:"Luffy",
      time:"20-12-17 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/luffy.png"
    }]
  }
  export default {
    name: "personalCentor",
    data(){
      return{
        headimgsrc:"",
        nickname:"",
        introduction:"",
        safetysettinginfo:safetysettinginfo,
        lettersetinfo:lettersetinfo,
        backgroundsrc:"http://caesar216.usa3v.net/caelog/images/background.png"
      }
    },
    props:["datas"],
    components:{
      safeset:safetysetting,
      ls:letterset
    },
    mounted() {
      this.inituserinfo(this.$route.params.userid)
    },
    methods:{
      inituserinfo(userid){
        this.$axios({
          url:this.common.serveraddress+"/user/get?userid="+userid,
          method:"get"
        }).then(
          res=>{
            localStorage.setItem("userinfo",JSON.stringify(res.data.data))
            this.common.userinfo=res.data.data
            this.common.loginuserinfo=res.data.data
            this.headimgsrc=this.common.loginuserinfo.backimgsrc
            this.nickname=this.common.loginuserinfo.nickname
            this.introduction=this.common.loginuserinfo.introduction
          }
        )
      },
      submit:function (){
        this.$axios.get(
          this.common.serveraddress+"/user/changebasedata?userid="+this.common.loginuserinfo.id+"&headimgsrc="+this.headimgsrc+"&nickname="+this.nickname+"&introduction="+this.introduction
        ).then(
          res=>{
            this.inituserinfo(this.$route.params.userid)
          }
        )
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
            if (data.message && (data.data === 'login.invalid.token')) {
              window.localStorage.removeItem("access-user");
              alert("超时请重新登陆");
              window.location.href = '/';
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
        let param = new FormData()  // 创建form对象
        param.append('file', file)  // 通过append向form对象添加数据
        param.append('userid',this.common.loginuserinfo.id)
        this.uploadFile("/files/upload",param).then(res=>{
          console.log(res.data)
          var uploadurl=this.common.getserveraddress+res.data.data;
          this.headimgsrc=uploadurl;})
      }
    }
  }
</script>

<style scoped>
.box{
  width:721px;
  height:1051px;
  background-color: white;
}
#infocard{
  width: 696px;
  height: 219px;
  border-bottom-style: groove;
  border-width: thin;
  position: absolute;
  top:8px;
  left:12px;
}
#inputhead{
  width: 60px;
  height: 60px;
  position: absolute;
  top:10px;
  left:300px;
  z-index: 10;
  opacity: 0;
}
#headimg{
  width: 60px;
  height: 60px;
  position: absolute;
  top:10px;
  left:300px;
}
#headimg img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
#nickname{
  width:100px;
  height:25px;
  position: absolute;
  left:380px;
  top:28px;
}
#nickname input{
  width:100%;
  height: 100%;
  font-size: 18px;
  font-family: 华光楷体_CNKI;
  text-align: center;
}
#intro{
  width: 300px;
  height: 120px;
  position: absolute;
  top:80px;
  left:185px;
}
#intro textarea{
  width: 100%;
  height: 100%;
  text-align: left;
}
#infocard button{
  width:98px;
  height:43px;
  position: absolute;
  top:162px;
  left:582px;
}
#safetysetting{
  position: absolute;
  top:241px;
  left: 13px;
}
#backgroundset{
  width:696px;
  height: 160px;
  position: absolute;
  left:13px;
  top:581px;
}
#backgroundshow{
  width:596px;
  height: 150px;
  position: absolute;
  width:100%;
  overflow: hidden;
  top:3px;
  left:7px;
}
#backgroundbutton{
  width:75px;
  height: 42px;
  position: absolute;
  top:113px;
  left:615px;
}
#letterarange{
  position: absolute;
  top:756px;
  left:13px;
}
</style>
