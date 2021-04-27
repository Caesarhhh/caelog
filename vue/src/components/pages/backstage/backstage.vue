<template>
<div class="backstage">
  <div @click="tomainpage" id="tomain" :style="getcolor3()">首页</div>
  <div @click="tomainpage" :style="getcolor3_()" id="tri"></div>
  <sb id="selectbox" ref="sb" :datas="selectboxinfo" :pageindex_.sync="pageindex"></sb>
  <pc id="personalcentor" :datas="personalCenterinfo" v-if="this.pageindex==1"></pc>
  <ar id="articlearrange" :datas="articlearrangeinfo" v-if="this.pageindex==2"></ar>
  <shs id="socialhrefset" :datas="socialhrefinfo" v-if="this.pageindex==3"></shs>
  <as id="announcementset" :datas="anouncementsetinfo" v-if="this.pageindex==4"></as>
  <bs id="blockset" :datas="blocksetinfo" v-if="this.pageindex==5"></bs>
</div>
</template>

<script>
  import selectbox from "../../tools/selectbox/selectbox";
  import personalCenter from "./subpages/personalCenter/personalCenter";
  import articlearrange from "./subpages/articlearrange/articlearrange";
  import socialhrefset from "./subpages/socialhrefset/socialhrefset";
  import announcementset from "./subpages/announcementset/announcementset";
  import blockset from "./subpages/blockset/blockset";
  var articlearrangeinfo={}
  var socialhrefinfo={}
  var anouncementsetinfo={}
  var blocksetinfo={}
  export default {
    name: "backstage",
    data(){
      return{
        pageindex:1,
        selectboxinfo:{
          headsrc:this.common.getserveraddress+"images/head.jpg",
          introduction:"帅气的人",
          nickname:"Caesar"
        },
        personalCenterinfo:{
          headsrc:this.common.getserveraddress+"images/head.jpg",
          introduction:"帅气的人",
          nickname:"Caesar"
        },
        articlearrangeinfo:articlearrangeinfo,
        socialhrefinfo:socialhrefinfo,
        anouncementsetinfo:anouncementsetinfo,
        blocksetinfo:blocksetinfo
      }
    },
    methods:{
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3(){
        return {
          backgroundColor: this.$store.state.color3
        }
      },
      getcolor3_(){
        return {
          borderWidth:"20px",
          borderStyle:"solid",
          borderTopColor:this.$store.state.color3,
          borderBottomColor:"transparent",
          borderLeftColor:"transparent",
          borderRightColor:"transparent"
        }
      },
      changeback(){
        this.$parent.changeback()
      },
      tomainpage(){
        this.$router.push("/"+this.common.loginuserinfo.id+"/mainpage")
      }
    },
    components:{
      sb:selectbox,
      pc:personalCenter,
      ar:articlearrange,
      shs:socialhrefset,
      as:announcementset,
      bs:blockset
    },
    beforeCreate() {
      if(((JSON.stringify(this.common.loginuserinfo)==='{}')||this.common.loginuserinfo==null)){
        this.$router.push("/login")
      }
      if(this.common.loginuserinfo.id!=this.$route.params.userid){
        this.$router.push("/"+this.common.loginuserinfo.id+"/mainpage")
        alert("您无权访问该用户后台！")
      }
    },
    beforeMount() {
    },
    mounted() {
      this.common.loginuserinfo=JSON.parse(localStorage.getItem("userinfo"))
      this.selectboxinfo.headsrc=this.common.loginuserinfo.backimgsrc
      this.selectboxinfo.introduction=this.common.loginuserinfo.introduction
      this.selectboxinfo.nickname=this.common.loginuserinfo.nickname
    }
  }
</script>

<style scoped>
.backstage{
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
  margin-top: 0px;
  width:1024px;
}
#tomain{
  position: absolute;
  cursor: pointer;
  width:40px;
  font-family: 华光楷体_CNKI;
  height: 60px;
  top:0px;
  font-size: 22px;
  line-height: 35px;
  z-index: 99;
  border-radius: 2px;
}
#tri{
  width: 0px;
  height: 0px;
  left:0px;
  top:59px;
  position: absolute;
  z-index: 98;
  border-radius: 2px;
  cursor: pointer;
}
#selectbox{
  position: absolute;
  top:39px;
  left:23px;
}
#personalcentor{
  position: absolute;
  top:134px;
  left:272px;
}
#articlearrange{
  position: absolute;
  top:134px;
  left:272px;
}
#socialhrefset{
  position: absolute;
  top:134px;
  left:272px;
}
#announcementset{
  position: absolute;
  top:134px;
  left:272px;
}
#blockset{
  position: absolute;
  top:134px;
  left:272px;
}
</style>
