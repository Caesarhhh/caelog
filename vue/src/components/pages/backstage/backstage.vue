<template>
  <!--<div class="backstage">-->
  <!--  <div @click="tomainpage" id="tomain" :style="getcolor3()">首页</div>-->
  <!--  <div @click="tomainpage" :style="getcolor3_()" id="tri"></div>-->
  <!--  <sb id="selectbox" ref="sb" :datas="selectboxinfo" :pageindex_.sync="pageindex"></sb>-->
  <!--  <pc id="personalcentor" :datas="personalCenterinfo" v-if="this.pageindex==1"></pc>-->
  <!--  <ar id="articlearrange" :datas="articlearrangeinfo" v-if="this.pageindex==2"></ar>-->
  <!--  <shs id="socialhrefset" :datas="socialhrefinfo" v-if="this.pageindex==3"></shs>-->
  <!--  <as id="announcementset" :datas="anouncementsetinfo" v-if="this.pageindex==4"></as>-->
  <!--  <bs id="blockset" :datas="blocksetinfo" v-if="this.pageindex==5"></bs>-->
  <!--</div>-->
  <div class="aph">
    <a-row type="flex">
      <a-col :span="6">
        <div id="idbox" :style="getcolor1()">
          <div id="head"><img :src="userimgsrc" alt="error"></div>
          <div id="nickname">{{username}}</div>
          <div id="idintroduction">
            <GeminiScrollbar>
              <div id="content">
                {{userintro}}
              </div>
            </GeminiScrollbar>
          </div>
        </div>
        <div id="line"></div>
        <a-menu :style="getcolor1()" mode="inline" :open-keys="openKeys" @openChange="onOpenChange">
          <a-sub-menu key="sub1">
            <span slot="title"><a-icon type="mail"/><span>个人中心</span></span>
            <a-menu-item key="1">
              个人设置
              <router-link :to="'/'+userid+subpaths[0]"></router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <span slot="title"><a-icon type="appstore"/><span>文章管理</span></span>
            <a-menu-item key="2">
              <router-link :to="'/'+userid+subpaths[1]">
                文章管理
              </router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub3">
            <span slot="title"><a-icon type="setting"/><span>友链管理</span></span>
            <a-menu-item key="3">
              <router-link :to="'/'+userid+subpaths[2]">
                友链管理
              </router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub4">
            <span slot="title"><a-icon type="setting"/><span>公告管理</span></span>
            <a-menu-item key="4">
              <router-link :to="'/'+userid+subpaths[3]">
                公告管理
              </router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub5">
            <span slot="title"><a-icon type="setting"/><span>板块管理</span></span>
            <a-menu-item key="5">
              <router-link :to="'/'+userid+subpaths[4]">
                板块管理
              </router-link>
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-col>
      <a-col :span="3"></a-col>
      <a-col :span="15">
        <router-view></router-view>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import selectbox from "../../tools/selectbox/selectbox";
  import personalCenter from "./subpages/personalCenter/personalCenter";
  import articlearrange from "./subpages/articlearrange/articlearrange";
  import socialhrefset from "./subpages/socialhrefset/socialhrefset";
  import announcementset from "./subpages/announcementset/announcementset";
  import blockset from "./subpages/blockset/blockset";

  var articlearrangeinfo = {}
  var socialhrefinfo = {}
  var anouncementsetinfo = {}
  var blocksetinfo = {}
  export default {
    name: "backstage",
    data() {
      return {
        pageindex: 1,
        selectboxinfo: {
          headsrc: this.common.getserveraddress + "images/head.jpg",
          introduction: "帅气的人",
          nickname: "Caesar"
        },
        personalCenterinfo: {
          headsrc: this.common.getserveraddress + "images/head.jpg",
          introduction: "帅气的人",
          nickname: "Caesar"
        },
        openKeys: ['sub1'],
        subpaths: [
          '/backstage/personalCentor',
          '/backstage/articleArrange',
          '/backstage/socialhrefArrange',
          '/backstage/announceArrange',
          "/backstage/blockArrange"
        ],
        userid: 0,
        username:"",
        userimgsrc:"",
        userintro:"",
        rootSubmenuKeys: ['sub1', 'sub2', 'sub3', 'sub4', 'sub5'],
        articlearrangeinfo: articlearrangeinfo,
        socialhrefinfo: socialhrefinfo,
        anouncementsetinfo: anouncementsetinfo,
        blocksetinfo: blocksetinfo
      }
    },
    methods: {
      onOpenChange(openKeys) {
        const latestOpenKey = openKeys.find(key => this.openKeys.indexOf(key) === -1);
        if (this.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
          this.openKeys = openKeys;
        } else {
          this.openKeys = latestOpenKey ? [latestOpenKey] : [];
        }
      },
      getcolor1() {
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2() {
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3() {
        return {
          backgroundColor: this.$store.state.color3
        }
      },
      getcolor3_() {
        return {
          borderWidth: "20px",
          borderStyle: "solid",
          borderTopColor: this.$store.state.color3,
          borderBottomColor: "transparent",
          borderLeftColor: "transparent",
          borderRightColor: "transparent"
        }
      },
      changeback() {
        this.$parent.changeback()
      },
      tomainpage() {
        this.$router.push("/" + this.common.loginuserinfo.id + "/mainpage")
      }
    },
    components: {
      sb: selectbox,
      pc: personalCenter,
      ar: articlearrange,
      shs: socialhrefset,
      as: announcementset,
      bs: blockset
    },
    beforeCreate() {
      if (((JSON.stringify(this.common.loginuserinfo) === '{}') || this.common.loginuserinfo == null)) {
        this.$router.push("/login")
      }
      if (this.common.loginuserinfo.id != this.$route.params.userid) {
        this.$router.push("/" + this.common.loginuserinfo.id + "/mainpage")
        alert("您无权访问该用户后台！")
      }
    },
    beforeMount() {
    },
    mounted() {
      this.userid = this.common.loginuserinfo.id
      this.username=this.common.loginuserinfo.nickname
      this.userimgsrc=this.common.loginuserinfo.backimgsrc
      this.userintro=this.common.loginuserinfo.introduction
      console.log(this.userimgsrc)
      this.common.loginuserinfo = JSON.parse(localStorage.getItem("userinfo"))
      this.selectboxinfo.headsrc = this.common.loginuserinfo.backimgsrc
      this.selectboxinfo.introduction = this.common.loginuserinfo.introduction
      this.selectboxinfo.nickname = this.common.loginuserinfo.nickname
    }
  }
</script>

<style scoped>
  .backstage {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: relative;
    margin-top: 0px;
    margin-left: 10%;
  }

  #idbox{
    width:100%;
    height:199px;
    position: relative;
    margin-top:7px;
    margin-left:0px;
  }
  #head{
    width:70px;
    position: absolute;
    top:5px;
    left:2%;
  }
  #head img{
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }
  #nickname{
    width:35%;
    height: 25px;
    position: absolute;
    top:33px;
    left:45%;
    font-family: 华光楷体_CNKI;
    font-weight: bold;
    font-size: 20px;
  }
  #idintroduction{
    width:96%;
    height:95px;
    position: absolute;
    top:95px;
    left:2%;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }
  #content{
    width: 100%;
    height:90px;
    position: absolute;
    word-wrap: break-word;
    word-break: break-all;
    left:0px;
    text-align: left;
    font-family: 华光楷体_CNKI;
    border-top-style: dashed;
  }

  #line{
    width:100%;
    height: 1px;
    float: left;
    margin-top:0px;
    margin-left:0;
    background-color: black;
  }

  #tomain {
    position: absolute;
    cursor: pointer;
    width: 40px;
    font-family: 华光楷体_CNKI;
    height: 60px;
    top: 0px;
    font-size: 22px;
    line-height: 35px;
    z-index: 99;
    border-radius: 2px;
  }

  #tri {
    width: 0px;
    height: 0px;
    left: 0px;
    top: 59px;
    position: absolute;
    z-index: 98;
    border-radius: 2px;
    cursor: pointer;
  }

  #selectbox {
    position: absolute;
    top: 39px;
    left: 23px;
  }

  #personalcentor {
    position: absolute;
    top: 134px;
    left: 272px;
  }

  #articlearrange {
    position: absolute;
    top: 134px;
    left: 272px;
  }

  #socialhrefset {
    position: absolute;
    top: 134px;
    left: 272px;
  }

  #announcementset {
    position: absolute;
    top: 134px;
    left: 272px;
  }

  #blockset {
    position: absolute;
    top: 134px;
    left: 272px;
  }
</style>
