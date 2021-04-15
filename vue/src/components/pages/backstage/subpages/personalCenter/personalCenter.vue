<template>
<div class="boxper" :style="getcolor1()">
  <div id="infocard">
    <input type="file"id="inputhead" @change="uploadpic">
    <div id="headimg"><img :src="headimgsrc" alt="error"></div>
    <div id="nickname">
      <input type="text" v-model="nickname">
    </div>
    <div id="intro">
      <textarea cols="30" rows="10" v-model="introduction">{{introduction}}</textarea>
    </div>
    <div class="button" @click="submit" :style="getcolor3()">保存修改</div>
  </div>
  <safeset :datas="safetysettinginfo" id="safetysetting"></safeset>
  <div id="backgroundset">
    <div id="backgroundshow">
      <div @click="selectColor(index)" v-for="(item,index) in colorsSelect" :class="{colorSelect:colorNum==index,colorSelectnon:colorNum!=index}">
        <div :class="{color1select:colorNum==index,color1selectnon:colorNum!=index}" :style="getcolor(index,1)"></div>
        <div :class="{color2select:colorNum==index,color2selectnon:colorNum!=index}" :style="getcolor(index,2)"></div>
      </div>
    </div>
    <div :style="getcolor3()" id="backgroundbutton" @click="changebc()">更换主题</div>
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
        colorsSelect:[],
        colorNum:0,
        backgroundsrc:"http://caesar216.usa3v.net/caelog/images/background.png"
      }
    },
    props:["datas"],
    components:{
      safeset:safetysetting,
      ls:letterset
    },
    computed:{
    },
    watch:{
      getcolor1(val){
        console.log(val)
      }
    },
    mounted() {
      this.inituserinfo(this.$route.params.userid)
      this.colorsSelect=this.$store.state.colorSelect
      this.colorNum=this.$store.state.colorNum
    },
    methods:{
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3(){
        return {backgroundColor: this.$store.state.color3}
      },
      getcolor(num,i){
        let color="red"
        if(i==1){
          color=this.colorsSelect[num].color1
        }
        if(i==2){
          color=this.colorsSelect[num].color2
        }
        return {backgroundColor: color}
      },
      selectColor(i){
        this.$store.commit("changecolorSelect",i)
        this.colorNum=i
        this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color1,1])
        this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color2,2])
        this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color3,3])
        this.$store.commit('changecolor',[this.$store.state.colorSelect[i].color4,4])
        this.$store.commit('changecolor',[this.$store.state.colorSelect[i].colorFont,"Font"])
        this.$store.commit('changecolor',[this.$store.state.colorSelect[i].colorFont2,"Font2"])
        this.$parent.changeback()
      },
      changebc(){
        let params={
          userid:this.common.loginuserinfo.id,
          colorNum:this.$store.state.colorNum
        }
        this.$axios.post(this.common.serveraddress+"/user/changebc",params).then(res=>{
          if(res.data.code==200){
            alert("背景更换成功！")
          }
          else{
            alert("背景更换失败!")
          }
        })
      },
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
        let params={
          userid:this.common.loginuserinfo.id,
          headimgsrc:this.headimgsrc,
          nickname:this.nickname,
          introduction:this.introduction
        }
        this.$axios.post(
          this.common.serveraddress+"/user/changebasedata",params
        ).then(
          res=>{
            if(res.data.code==200){
              this.inituserinfo(this.$route.params.userid)
              alert("修改成功！")
            }
            else{
              alert(res.data.msg)
            }
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
          var uploadurl=this.common.getserveraddress+res.data.data;
          this.headimgsrc=uploadurl;
        })
      }
    }
  }
</script>

<style scoped>
.boxper{
  width:721px;
  height:1051px;
}
.clearfix:after {
  visibility: hidden;
  display: block;
  font-size: 0;
  content: " ";
  clear: both;
  height: 0;
}
.colorSelectnon{
  border-radius: 18px;
  width: 90px;
  height: 35px;
  float: left;
  margin-left:20px;
  margin-top: 10px;
  overflow: hidden;
}
.colorSelect{
  border-style: solid;
  border-width: 2px;
  border-radius: 16px;
  width: 86px;
  height: 31px;
  float: left;
  margin-left:20px;
  margin-top: 10px;
  overflow: hidden;
}
.color1selectnon{
  width:45px;
  float: left;
  margin-left: 0px;
  margin-top: 0px;
  height: 35px;
}
.color2selectnon{
  width:45px;
  float: left;
  margin-left: 0px;
  margin-top: 0px;
  height: 35px;
}
.color1select{
  width:43px;
  float: left;
  margin-left: 0px;
  margin-top: 0px;
  height: 35px;
}
.color2select{
  width:43px;
  float: left;
  margin-left: 0px;
  margin-top: 0px;
  height: 35px;
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
  border-radius: 10px;
  border-style: solid;
  border-width: thin;
  outline: none;
  position: absolute;
  left:0px;
  top:0px;
}
#nickname input:focus{
  border-width: 2px;
  top:-1px;
  left:-1px;
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
  border-radius: 10px;
  outline: none;
  font-family: 华光楷体_CNKI;
  border-width: thin;
  border-style: solid;
  position: absolute;
  top:0px;
  left:0px;
}
#intro textarea:focus{
  border-width: 2px;
  left:-1px;
  top:-1px;
}
.button{
  width:98px;
  height:43px;
  position: absolute;
  top:162px;
  left:582px;
  line-height: 43px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  cursor: pointer;
  border-radius: 5px;
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
  overflow: hidden;
  top:3px;
  left:7px;
}
#backgroundbutton{
  width:75px;
  height: 42px;
  position: absolute;
  top:125px;
  left:615px;
  line-height: 42px;
  font-family: 华光楷体_CNKI;
  cursor: pointer;
  font-size: 18px;
  background-color: #e6f0f5;
  border-radius: 5px;
}
#letterarange{
  position: absolute;
  top:756px;
  left:13px;
}
</style>
