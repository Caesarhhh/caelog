<template>
<div class="backstage">
  <sb id="selectbox" :datas="selectboxinfo" :pageindex_.sync="pageindex"></sb>
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
  var selectboxinfo={
    headsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
    introduction:"帅气的人",
    nickname:"Caesar"
  }
  var personalCenterinfo={
    headsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
    introduction:"帅气的人",
    nickname:"Caesar"
  }
  var articlearrangeinfo={}
  var socialhrefinfo={}
  var anouncementsetinfo={}
  var blocksetinfo={}
  export default {
    name: "backstage",
    data(){
      return{
        pageindex:1,
        selectboxinfo:selectboxinfo,
        personalCenterinfo:personalCenterinfo,
        articlearrangeinfo:articlearrangeinfo,
        socialhrefinfo:socialhrefinfo,
        anouncementsetinfo:anouncementsetinfo,
        blocksetinfo:blocksetinfo
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
    mounted() {
      if(((JSON.stringify(this.common.loginuserinfo)==='{}')||this.common.loginuserinfo==null)){
        this.$router.push("/login")
      }
      if(this.common.loginuserinfo.id!=this.$route.params.userid){
        alert("您无权访问该用户后台！")
        location.go(-1)
      }
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
