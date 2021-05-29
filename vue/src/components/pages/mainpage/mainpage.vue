<template>
  <div id="mainpage">
    <a-row type="flex">
      <a-col :span="5">
        <pc id="personalCard" :datas="pcpos"></pc>
        <div id="blockbox" :style="getcolor1()">
          <lb id="blocks" :datas="blocksInfo"></lb>
          <lb id="labels" :datas="labelsInfo"></lb>
        </div>
      </a-col>
      <a-col :span="19">
        <a-row>
          <a-col :span="8">
            <st id="searchtool"></st>
            <rs id="recommendsong" :style="getcolor1()" :datas="songInfo"></rs>
          </a-col>
          <a-col :span="16">
            <announce id="announce" :style="getcolor1()" :datas="announceInfo"></announce>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="20">
            <sorttool :sortbyTimef="sortArticlebyTime" :sortbyHotf="sortArticlebyHot" :selectbyTimef="selectbyTime" id="sorttool" :datas="sortinfo"></sorttool>
            <div id="articlebox">
              <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4]"
                  v-if="(pagenumsinfo.pos-1)*4<articlenum"></ac>
              <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+1]"
                  v-if="(pagenumsinfo.pos-1)*4+1<articlenum"></ac>
              <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+2]"
                  v-if="(pagenumsinfo.pos-1)*4+2<articlenum"></ac>
              <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+3]"
                  v-if="(pagenumsinfo.pos-1)*4+3<articlenum"></ac>
            </div>
            <pn v-if="articlenum>3" class="pn" :datas.sync="pagenumsinfo"></pn>
          </a-col>
          <a-col :span="4">
            <div id="modibox" :style="getcolor1()">
              <div id="modititle">近期动态</div>
              <div id="modibody">
                <mc class="modicard" :style="getcolor1()" v-for="(i,index) in modificationcardInfo" :key="index"
                    :datas="i" v-if="index<6"></mc>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import personalCard from "../../tools/personalCard/personalCard";
  import searchtool from "../../tools/searchtool/searchtool";
  import announcement from "./announcement/announcement";
  import recommendsong from "./recommendsong/recommendsong";
  import sorttool from "../../tools/sorttool/sorttool";
  import articleCard from "../../tools/articleCard/articleCard";
  import modificationcard from "../../tools/modificationcard/modificationcard";
  import labelbox from "../../tools/labelbox/labelbox";
  import pagenums from "../../tools/pagenums/pagenums";

  var chatinfo = {
    ifwin: false
  }
  var sortinfo = {
    timeslot: [{id: 1, st: 2015, et: 2016}]
  }
  var modificationcardInfo = [{
    content: "修改了一些内容",
    time: "20-12-13"
  }]
  var blocksInfo = {
    labels: {},
    title: "板块",
    type: 'block'
  }
  var labelsInfo = {
    labels: [],
    title: "标签",
    type: 'label'
  }
  export default {
    name: 'mainpage',
    data() {
      return {
        announceInfo: {
          title: [],
          time: [],
          text: [],
          leftsrc: this.common.getserveraddress + "images/left.png",
          rightsrc: this.common.getserveraddress + "images/right.png",
          count: 0
        },
        songInfo: {
          songname: "心要野",
          singer: "后海大鲨鱼",
          imgurl: this.common.getserveraddress + "images/singer.png",
          playurl: this.common.getserveraddress + "images/play.png",
          pauseurl: this.common.getserveraddress + "images/pause.png",
          songurl: this.common.getserveraddress + "images/心要野.mp3"
        },
        sortinfo: {
          timeslot: [{id: 1, st: 2015, et: 2016}]
        },
        articleCardInfo: [],
        articleCardInfoprint: [],
        modificationcardInfo: modificationcardInfo,
        blocksInfo: blocksInfo,
        labelsInfo: labelsInfo,
        pcpos: {pos: 'left'},
        articlenum: 0,
        sorttype: 1,
        ifback: false,
        pagenumsinfo: {
          sum: 1,
          pos: 1,
          pagesize:4
        }
      }
    },
    components: {
      st: searchtool,
      pc: personalCard,
      announce: announcement,
      rs: recommendsong,
      sorttool: sorttool,
      ac: articleCard,
      mc: modificationcard,
      lb: labelbox,
      pn: pagenums
    },
    mounted() {
      if (typeof this.$route.params.userid === 'undefined') {
        this.router.push("/4/mainpage")
      }
      let tmp = this.common.loginuserinfo
      if (!(!tmp && typeof (tmp) != "undefined" && tmp != 0)) {
        if (this.common.loginuserinfo.id == this.$route.params.userid) {
          this.ifback = true
        }
      }
      this.inituserinfo(this.$route.params.userid)
    },
    methods: {
      getColor4() {
        return {
          backgroundColor: this.$store.state.color4
        }
      },
      getcolor1() {
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2() {
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3() {
        return {backgroundColor: this.$store.state.color3}
      },
      tobackstage() {
        this.$router.push("/" + this.$route.params.userid + "/backstage")
      },
      addChange() {
        this.modificationcardInfo = []
        this.$axios.get(
          this.common.serveraddress + "/change/get?userid=" + this.common.userinfo.id).then(
          res => {
            for (var i = 0; i < res.data.data.length && i < 15; i++) {
              var temp = {
                content: res.data.data[i].content,
                time: res.data.data[i].time_.substring(2, 10)
              }
              this.$set(this.modificationcardInfo, i, temp)
            }
          })
      },
      comparetime(time1, time2) {
        let t1 = []
        t1.push(parseInt(time1.substring(0, 4)))
        t1.push(parseInt(time1.substring(5, 7)))
        t1.push(parseInt(time1.substring(8, 10)))
        t1.push(parseInt(time1.substring(11, 13)))
        t1.push(parseInt(time1.substring(14, 16)))
        t1.push(parseInt(time1.substring(17, 19)))
        t1.push(parseInt(time1.substring(20, 22)))
        let t2 = []
        t2.push(parseInt(time2.substring(0, 4)))
        t2.push(parseInt(time2.substring(5, 7)))
        t2.push(parseInt(time2.substring(8, 10)))
        t2.push(parseInt(time2.substring(11, 13)))
        t2.push(parseInt(time2.substring(14, 16)))
        t2.push(parseInt(time2.substring(17, 19)))
        t2.push(parseInt(time2.substring(20, 22)))
        for (var i = 0; i < t1.length; i++) {
          if (t1[i] != t2[i]) {
            return t1[i] - t2[i]
          }
        }
        return 0
      },
      sortArticlebyTime() {
        let len = this.articleCardInfoprint.length
        for (let i = 0; i < len; i++) {
          for (let j = 0; j < len - 1 - i; j++) {
            if (this.comparetime(this.articleCardInfoprint[j].time, this.articleCardInfoprint[j + 1].time) < 0) {
              let temp = this.articleCardInfoprint[j]
              this.$set(this.articleCardInfoprint, j, this.articleCardInfoprint[j + 1])
              this.$set(this.articleCardInfoprint, j + 1, temp)
            }
          }
        }
        this.sorttype = 1
      },
      sortArticlebyHot() {
        let len = this.articleCardInfoprint.length
        for (let i = 0; i < len; i++) {
          for (let j = 0; j < len - 1 - i; j++) {
            if (this.articleCardInfoprint[j].goodnum * 2 + this.articleCardInfoprint[j].viewnum < this.articleCardInfoprint[j + 1].goodnum * 2 + this.articleCardInfoprint[j + 1].viewnum) {
              let temp = this.articleCardInfoprint[j]
              this.$set(this.articleCardInfoprint, j, this.articleCardInfoprint[j + 1])
              this.$set(this.articleCardInfoprint, j + 1, temp)
            }
          }
        }
        this.sorttype = 0
      },
      selectbyTime: async function (s) {
        let len = this.articleCardInfo.length
        let temp = []
        this.pagenumsinfo = {
          sum: 0,
          pos: 1,
          pagesize:4
        }
        for (let i = 0; i < len; i++) {
          if (s == "all" || s == this.articleCardInfo[i].time.substring(2, 7)) {
            temp.push(this.articleCardInfo[i])
          }
        }
        this.articlenum = temp.length
        this.articleCardInfoprint = []
        this.articleCardInfoprint = temp
        if (this.sorttype == 0) {
          this.sortArticlebyHot()
        }
        this.pagenumsinfo.sum = this.articlenum
      },
      getbls() {
        this.labelsInfo.labels = []
        this.$axios.get(
          this.common.serveraddress + "/labels/get?userid=" + this.common.userinfo.id).then(
          res => {
            for (var i = 0; i < res.data.data.length && i < 15; i++) {
              var temp = res.data.data[i]
              this.$set(this.labelsInfo.labels, i, temp.name_)
            }
          })
        this.blocksInfo.labels = []
        this.$axios.get(
          this.common.serveraddress + "/blocks/get?userid=" + this.common.userinfo.id).then(
          res => {
            for (var i = 0; i < res.data.data.length && i < 15; i++) {
              var temp = res.data.data[i]
              this.$set(this.blocksInfo.labels, temp.id, temp.name_)
            }
          })
      },
      refresh_announce() {
        this.announceInfo.title = []
        this.announceInfo.time = []
        this.announceInfo.text = []
        this.announceInfo.count = 0
        var randomcode = parseInt(new Date().getTime()) % 100000
        this.$axios.get(
          this.common.serveraddress + "/announcement/get?userid=" + this.common.userinfo.id + "&randomcode=" + randomcode).then(
          res => {
            for (var i = 0; i < res.data.data.length; i++) {
              var temp = {
                title: res.data.data[i].title,
                text: res.data.data[i].content,
                index: res.data.data[i].index_,
                id: res.data.data[i].id,
                time: res.data.data[i].times
              }
              this.$set(this.announceInfo.title, temp.index - 1, temp.title)
              this.$set(this.announceInfo.time, temp.index - 1, temp.time.substr(2, 9))
              this.$set(this.announceInfo.text, temp.index - 1, temp.text)
              this.announceInfo.count++
            }
          })
      },
      getarticles() {
        this.articleCardInfo = []
        this.articleCardInfoprint=[]
        this.articlenum = 0
        this.sortinfo.timeslot = []
        this.pagenumsinfo = {
          sum: 0,
          pos: 1,
          pagesize:4
        }
        this.$axios.get(
          this.common.serveraddress + "/article/get?userid=" + this.common.userinfo.id).then(
          res => {
            if (res.data.data.length == 0) {
              // var temp={
              //   title:res.data.data.title,
              //   time:res.data.data.time_,
              //   content:res.data.data.content,
              //   block:blocksInfo[res.data.data.blockid],
              //   id:res.data.data.id,
              //   goodnum:res.data.data.goodnum,
              //   viewnum:res.data.data.viewnum
              // }
              // this.articleCardInfo.push(temp)
              // this.sortinfo.timeslot.push({
              //   id:this.sortinfo.timeslot.length,
              //   st:temp.time.substring(2,4),
              //   et:temp.time.substring(5,7)
              // })
              // this.articlenum++
            } else {
              for (var i = res.data.data.length - 1; i >= 0; i--) {
                var temp = {
                  title: res.data.data[i].title,
                  time: res.data.data[i].time_,
                  content: res.data.data[i].content,
                  block: blocksInfo.labels[res.data.data[i].blockid],
                  id: res.data.data[i].id,
                  goodnum: res.data.data[i].goodnum,
                  viewnum: res.data.data[i].viewnum
                }
                if (this.sortinfo.timeslot.length == 0 || this.sortinfo.timeslot[this.sortinfo.timeslot.length - 1].st != temp.time.substring(2, 4) || this.sortinfo.timeslot[this.sortinfo.timeslot.length - 1].et != temp.time.substring(5, 7)) {
                  this.sortinfo.timeslot.push({
                    id: this.sortinfo.timeslot.length,
                    st: temp.time.substring(2, 4),
                    et: temp.time.substring(5, 7)
                  })
                }
                console.log(blocksInfo)
                this.articleCardInfo.push(temp)
                this.articleCardInfoprint.push(temp)
                this.articlenum++
              }
              this.pagenumsinfo.sum = this.articlenum
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
            this.refresh_announce()
            this.getbls()
            this.addChange()
            this.getarticles()
          }
        )
      }
    }
  }
</script>

<style>
  #mainpage {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    position: relative;
    margin-top: 0px;
  }

  #toback div {
    position: absolute;
    top: 20px;
    font-family: 华光楷体_CNKI;
    font-size: 28px;
    line-height: 40px;
  }

  .pn {
    position: absolute;
    width: 73%;
    left: 10%;
    top: 1143px;
  }

  #personalCard {
    position: absolute;
    top: 20px;
    left: 20%;
  }

  #searchtool {
    position: absolute;
    top: 31px;
    left: 35%;
  }

  #announce {
    position: absolute;
    top: 29px;
    left: 25%;
    width: 70%;
    border-radius: 10px;
  }

  #recommendsong {
    position: absolute;
    left: 25%;
    top: 91px;
    border-radius: 20px;
  }

  #sorttool {
    position: absolute;
    top: 230px;
    left: 5%;
  }

  #articlebox {
    position: absolute;
    width: 75%;
    height: 857px;
    top: 281px;
    left: 7%;
  }

  .articlecard {
    float: left;
    margin-top: 10px;
    margin-left: 10px;
  }

  #modibox {
    width: 160%;
    height: 711px;
    position: absolute;
    top: 240px;
    right: 10%;
    border-radius: 20px;
  }

  #modititle {
    width: 90%;
    height: 35px;
    position: relative;
    border-bottom-style: groove;
    margin-left: 8px;
    margin-top: 4px;
    text-align: left;
    font-size: 28px;
    font-family: 华光楷体_CNKI;
  }

  #modibody {
    width: 100%;
    height: 671px;
    position: relative;
    margin-top: 2px;
  }

  .modicard {
    float: left;
    margin-left: 8px;
    margin-top: 10px;
    border-bottom-style: groove;
    border-width: thin;
  }

  #blockbox {
    width: 105%;
    height: 600px;
    position: absolute;
    left: 9%;
    top: 315px;
    border-radius: 20px;
  }

  #blocks {
    float: left;
    margin-top: 20px;
    margin-left: 15px;
  }

  #labels {
    float: left;
    margin-top: 25px;
    margin-left: 15px;
  }
</style>
