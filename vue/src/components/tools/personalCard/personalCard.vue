<template>
  <div class="empty_pc">
    <div :class="{box:datas.pos=='left',box_:datas.pos=='right'}" :style="getcolor1()">
      <cw :datas.sync="chatinfo" v-if="chatinfo.ifwin"></cw>
      <div id="pic" @click="toMainPage">
        <img v-bind:src="this.common.userinfo.backimgsrc" alt="error">
      </div>
      <div id="name">
        {{ this.common.userinfo.nickname }}
      </div>
      <div id="introduction">
        <GeminiScrollbar>
          <div id="content_pc">
          </div>
        </GeminiScrollbar>
      </div>
      <div id="buttonbox">
        <hsw :style="getcolor3()" :class="{hrefwin:datas.pos=='left',hrefwin_:datas.pos=='right'}" :datas.sync="hrefwininfo"
             v-if="hrefwininfo.ifwin"></hsw>
        <GeminiScrollbar>
          <div v-for="(item,index) in hrefdatas" class="hrefbox" @click="openhrefwin(index)">
            <img :src="item.hrefimgsrc" alt="error">
          </div>
          <div id="chatbox" @click="openchat" v-if="!ifsame">
            <img v-bind:src="chatpicsrc" alt="error">
          </div>
        </GeminiScrollbar>
      </div>
    </div>
  </div>
</template>

<script>
  import chatwin from "../../wins/chatwin/chatwin";
  import hrefshowwin from "../../wins/hrefshowwin/hrefshowwin";
  import showdown from "showdown"

  var chatinfo = {
    ifwin: false,
    targetid: 0
  }
  var hrefwininfo = {
    ifwin: false,
    href: "www.github.com/Caesarhhh",
    comments: "github网址",
  }
  var hrefdatas = []
  export default {
    name: "personalCard",
    props: ['datas'],
    data() {
      return {
        headpicsrc: this.common.getserveraddress + "images/head.jpg",
        personalIntroduction: "帅气的人",
        chatpicsrc: this.common.getserveraddress + "images/personalchat.png",
        hrefdatas: hrefdatas,
        name: "Caesar",
        chatinfo: chatinfo,
        hrefwininfo: hrefwininfo,
        ifsame: false,
        userid:0
      }
    },
    components: {
      cw: chatwin,
      hsw: hrefshowwin
    },
    methods: {
      getcolor1() {
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2() {
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3() {
        return {backgroundColor: this.$store.state.color3}
      },
      getcolor4() {
        return {backgroundColor: this.$store.state.color4}
      },
      toMainPage(){
        this.$router.push("/"+this.userid+"/mainpage")
      },
      getintro() {
        this.$axios({
          url: this.common.serveraddress + "/user/get?userid=" + this.$route.params.userid,
          method: "get"
        }).then(res => {
          this.userid=res.data.data.id
          var mdValue = res.data.data.introduction;
          var converter = new showdown.Converter();
          var html = converter.makeHtml(mdValue);
          document.getElementById("content_pc").innerHTML = html;
        })
      },
      openchat: function () {
        if (!this.hrefwininfo.ifwin) {
          this.chatinfo.ifwin = true;
        }
        ;
      },
      openhrefwin: function (id) {
        if (!this.chatinfo.ifwin && !this.hrefwininfo.ifwin) {
          this.hrefwininfo.href = this.hrefdatas[id].href;
          this.hrefwininfo.comments = this.hrefdatas[id].comments;
          this.hrefwininfo.ifwin = true;
        }
      },
      inithrefinfo() {
        this.hrefdatas = []
        this.$axios.get(
          this.common.serveraddress + "/socialhref/get?userid=" + this.common.userinfo.id).then(
          res => {
            for (var i = 0; i < res.data.data.length; i++) {
              if (res.data.data[i].ifuse == 1) {
                var temp = {
                  hrefimgsrc: res.data.data[i].imgsrc,
                  href: res.data.data[i].href,
                  comments: res.data.data[i].comment,
                  ifuse: res.data.data[i].ifuse
                }
                this.$set(this.hrefdatas, i, temp)
              }
            }
          })
      },
      inituserinfo(userid) {
        this.$axios({
          url: this.common.serveraddress + "/user/get?userid=" + userid,
          method: "get"
        }).then(
          res => {
            this.common.userinfo = res.data.data
            this.inithrefinfo()
            this.getintro()
          }
        )
      }
    },
    mounted() {
      this.chatinfo.targetid = this.$route.params.userid
      this.inituserinfo(this.$route.params.userid)
      if (((JSON.stringify(this.common.loginuserinfo) === '{}') || this.common.loginuserinfo == null)) {
        this.ifsame = true
      } else {
        if (this.common.loginuserinfo.id == this.$route.params.userid) {
          this.ifsame = true
        }
      }
    }
  }
</script>

<style scoped>
  .box {
    width: 90%;
    height: 279px;
    position: relative;
    border-radius: 20px;
  }

  .box_ {
    width: 90%;
    height: 279px;
    position: absolute;
    right: 0px;
    border-radius: 20px;
  }

  .empty_pc{
    width:90%;
    height: 100%;
  }

  #pic {
    width: 32%;
    border-radius: 50%;
    border-style: ridge;
    position: absolute;
    top: 7px;
    left: 34%;
  }

  #pic :hover{
    cursor: pointer;
  }

  #pic img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }

  #name {
    width: 100%;
    height: 26px;
    position: absolute;
    top: 100px;
    left: 0;
    font-weight: bolder;
    font-size: 20px;
  }

  #introduction {
    width: 94%;
    height: 97px;
    border-bottom-style: ridge;
    position: absolute;
    top: 127px;
    left: 3%;
  }

  #content_pc {
    width: 230px;
    height: 90px;
    position: absolute;
    word-wrap: break-word;
    word-break: break-all;
    left: 0px;
    text-align: left;
    font-family: 华光楷体_CNKI;
    border-top-style: dashed;
  }

  #buttonbox {
    width: 100%;
    height: 45px;
    position: absolute;
    top: 228px;
    left: 2px;
  }

  .hrefbox {
    width: 30px;
    height: 30px;
    margin-top: 8px;
    margin-left: 12px;
    float: left;
  }

  .hrefbox img {
    width: 100%;
    height: 100%;
  }

  #chatbox {
    width: 38px;
    height: 38px;
    border-radius: 50%;
    float: right;
    margin-right: 5px;
    margin-top: 8px;
  }

  #chatbox img {
    width: 100%;
    height: 100%;
  }

  .hrefwin {
    position: absolute;
    bottom: 4px;
    left: 2px;
    z-index: 999;
  }

  .hrefwin_ {
    position: absolute;
    bottom: 4px;
    right: 2px;
    z-index: 999;
  }
</style>
