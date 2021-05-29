<template>
  <div class="box">
    <a-row>
      <a-col :span="14">
        <div class="searchbox" :style="getcolor1()">
          <input id="searchinput" v-model="inputtext"></input>
          <div id="searchbutton" @click="searchto">
            <img :src="searchimgsrc" alt="error">
          </div>
        </div>
        <st :sortby-timef="sortArticlebyTime" :sortby-hotf="sortArticlebyHot" :selectby-timef="sortArticlebyTime"
            :datas="sorttoolinfo" id="sorttool_as"></st>
        <ac id="bac1" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4]" v-if="(pagenumsinfo.pos-1)*4<articlenum"></ac>
        <ac id="bac2" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+1]"
            v-if="(pagenumsinfo.pos-1)*4+1<articlenum"></ac>
        <ac id="bac3" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+2]"
            v-if="(pagenumsinfo.pos-1)*4+2<articlenum"></ac>
        <ac id="bac4" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+3]"
            v-if="(pagenumsinfo.pos-1)*4+3<articlenum"></ac>
        <pn v-if="articlenum!=0"
            :class="{pagenums_as_1:(pagenumsinfo.pos-1)*4+1==articlenum,pagenums_as_2:(pagenumsinfo.pos-1)*4+2==articlenum,pagenums_as_3:(pagenumsinfo.pos-1)*4+3==articlenum,pagenums_as_4:pagenumsinfo.pos*4<=articlenum}"
            :datas.sync="pagenumsinfo"></pn>
      </a-col>
      <a-col :span="10">
        <pc id="personalcard" :datas="pcpos"></pc>
        <div id="blockbox_as" :style="getcolor1()">
          <div id="blockstitle">板块</div>
          <div id="searchselect" @click="searchresult">
            <div>搜索结果</div>
          </div>
          <div id="blockselects">
            <div class="blockselect" v-for="item in blocks" @click="showto(item.name)">{{ item['name'] }}</div>
            <div style="float:left;margin-top:20px;width:190px;"></div>
          </div>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import sorttool from "../../tools/sorttool/sorttool";
  import articleCard from "../../tools/articleCard/articleCard";
  import pagenums from "../../tools/pagenums/pagenums";
  import personalCard from "../../tools/personalCard/personalCard";

  var sorttoolinfo = {
    timeslot: [
      {id: 0, st: 2015, et: 2016}
    ]
  }
  var articleCardInfo = []
  var pagenumsinfo = {
    pos: 1,
    sum: 1,
    pagesize:4
  }
  export default {
    name: "articleshowpage",
    props: ["datas"],
    data() {
      return {
        searchimgsrc: "http://caesar216.usa3v.net/caelog/images/search.png",
        sorttoolinfo: sorttoolinfo,
        articleCardInfo: articleCardInfo,
        articleCardInfoprint: [],
        pagenumsinfo: pagenumsinfo,
        blocks: {},
        pcpos:{pos:'right'},
        inputtext: "",
        searchdata: "搜索内容",
        articlenum: 0
      }
    },
    components: {
      st: sorttool,
      pn: pagenums,
      ac: articleCard,
      pc:personalCard
    },
    created() {
      this.searchdata = this.$route.params.data;
      this.inputtext = this.searchdata
      if (this.$route.params.type == "search") {
        if (this.$route.params.data == "") {
          this.$route.params.data == " "
        }
      }
    },
    methods: {
      getcolor1() {
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2() {
        return {backgroundColor: this.$store.state.color2}
      },
      searchto: function () {
        this.inputtext = this.inputtext.replace(/(^\s*)|(\s*$)/g, "")
        if (this.inputtext == "") {
          this.inputtext = " "
        }
        var temp = this.inputtext;
        this.inputtext = "";
        this.$router.push("/" + this.$route.params.userid + "/articleshow/search/" + temp);
        this.getarticleinfo()
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
        for (let i = 0; i < len; i++) {
          if (s == "all" || s == this.articleCardInfo[i].time.substring(2, 7)) {
            temp.push(this.articleCardInfo[i])
          }
        }
        this.articlenum = temp.length
        this.pagenumsinfo.pos = 1
        this.pagenumsinfo.sum = this.articlenum
        this.articleCardInfoprint = []
        this.articleCardInfoprint = temp
        if (this.sorttype == 0) {
          this.sortArticlebyHot()
        }
      },
      showto: function (s) {
        if (s == this.$route.params.data) {
          return
        }
        this.$router.push("/" + this.$route.params.userid + "/articleshow/block/" + s);
        this.getarticleinfo()
      },
      searchresult: function () {
        var temp = this.searchdata;
        this.$router.push("/" + this.$route.params.userid + "/articleshow/search/" + temp);
        this.getarticleinfo()
      },
      getarticleinfo: function () {
        var that = this
        this.pagenumsinfo = {
          pos: 1,
          sum: 0,
          pagesize: 4
        }
        this.articlenum = 0
        this.articleCardInfo = []
        this.articleCardInfoprint=[]
        this.blocks = {}
        this.sorttoolinfo.timeslot = []
        this.$axios.get(
          this.common.serveraddress + "/blocks/get?userid=" + this.$route.params.userid).then(
          res => {
            for (var i = 0; i < res.data.data.length && i < 15; i++) {
              var temp = res.data.data[i]
              this.$set(this.blocks, temp.id, {index: i, name: temp.name_})
            }
            if (this.$route.params.type == "search") {
              let searchdata = this.$route.params.data
              if (typeof this.$route.params.data === 'undefined') {
                searchdata = " "
              }
              this.$axios.get(
                this.common.serveraddress + "/article/getbysearch?userid=" + this.$route.params.userid + "&searchcontent=" + searchdata).then(
                ress => {
                  for (var i = 0; i < ress.data.data.length; i++) {
                    var temp = {
                      title: ress.data.data[i].title,
                      block: this.blocks[ress.data.data[i].blockid]['name'],
                      time: ress.data.data[i].time_,
                      content: ress.data.data[i].content,
                      id: ress.data.data[i].id,
                      goodnum: ress.data.data[i].goodnum,
                      viewnum: ress.data.data[i].viewnum
                    }
                    if (this.sorttoolinfo.timeslot.length == 0 || this.sorttoolinfo.timeslot[this.sorttoolinfo.timeslot.length - 1].st != temp.time.substring(2, 4) || this.sorttoolinfo.timeslot[this.sorttoolinfo.timeslot.length - 1].et != temp.time.substring(5, 7)) {
                      this.sorttoolinfo.timeslot.push({
                        id: this.sorttoolinfo.timeslot.length,
                        st: temp.time.substring(2, 4),
                        et: temp.time.substring(5, 7)
                      })
                    }
                    this.articleCardInfo.push(temp)
                    this.articleCardInfoprint.push(temp)
                    this.articlenum++
                  }
                  this.pagenumsinfo.sum = this.articlenum
                })
            } else if (this.$route.params.type == "label") {
              this.$axios.get(
                this.common.serveraddress + "/article/getbylabel?userid=" + this.$route.params.userid + "&labelname=" + this.$route.params.data).then(
                ress => {
                  for (var i = 0; i < ress.data.data.length; i++) {
                    var temp = {
                      title: ress.data.data[i].title,
                      block: that.blocks[ress.data.data[i].blockid]['name'],
                      time: ress.data.data[i].time_,
                      content: ress.data.data[i].content,
                      id: ress.data.data[i].id,
                      goodnum: ress.data.data[i].goodnum,
                      viewnum: ress.data.data[i].viewnum
                    }
                    if (this.sorttoolinfo.timeslot.length == 0 || this.sorttoolinfo.timeslot[this.sorttoolinfo.timeslot.length - 1].st != temp.time.substring(2, 4) || this.sorttoolinfo.timeslot[this.sorttoolinfo.timeslot.length - 1].et != temp.time.substring(5, 7)) {
                      this.sorttoolinfo.timeslot.push({
                        id: this.sorttoolinfo.timeslot.length,
                        st: temp.time.substring(2, 4),
                        et: temp.time.substring(5, 7)
                      })
                    }
                    this.articleCardInfo.push(temp)
                    this.articleCardInfoprint.push(temp)
                    this.articlenum++
                  }
                  this.pagenumsinfo.sum = this.articlenum
                })
            } else if (this.$route.params.type == "block") {
              this.$axios.get(
                this.common.serveraddress + "/article/getbyblock?userid=" + this.$route.params.userid + "&blockname=" + this.$route.params.data).then(
                ress => {
                  console.log(ress)
                  for (var i = 0; i < ress.data.data.length; i++) {
                    var temp = {
                      title: ress.data.data[i].title,
                      block: that.blocks[ress.data.data[i].blockid]['name'],
                      time: ress.data.data[i].time_,
                      content: ress.data.data[i].content,
                      id: ress.data.data[i].id,
                      goodnum: ress.data.data[i].goodnum,
                      viewnum: ress.data.data[i].viewnum
                    }
                    if (this.sorttoolinfo.timeslot.length == 0 || this.sorttoolinfo.timeslot[this.sorttoolinfo.timeslot.length - 1].st != temp.time.substring(2, 4) || this.sorttoolinfo.timeslot[this.sorttoolinfo.timeslot.length - 1].et != temp.time.substring(5, 7)) {
                      this.sorttoolinfo.timeslot.push({
                        id: this.sorttoolinfo.timeslot.length,
                        st: temp.time.substring(2, 4),
                        et: temp.time.substring(5, 7)
                      })
                    }
                    this.articleCardInfo.push(temp)
                    this.articleCardInfoprint.push(temp)
                    this.articlenum++
                  }
                  this.pagenumsinfo.sum = this.articlenum
                })
            }
          })
      }
    },
    async mounted() {
      this.getarticleinfo()
    }
  }
</script>

<style scoped>
  .box1 {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: relative;
    margin-top: 0px;
    width: 1024px;
  }

  .searchbox {
    width: 80%;
    height: 40px;
    position: absolute;
    left: 69px;
    top: 61px;
  }

  #searchinput {
    width: 85%;
    height: 30px;
    border-style: groove;
    border-width: thin;
    position: absolute;
    top: 4px;
    left: 4px;
    font-size: 25px;
    font-family: 华光楷体_CNKI;
  }

  #searchbutton {
    width: 30px;
    height: 30px;
    position: absolute;
    top: 4px;
    right: 2%;
  }

  #searchbutton img {
    width: 100%;
    height: 100%;
  }

  #sorttool_as {
    position: absolute;
    left: 44px;
    top: 125px;
  }

  #bac1 {
    position: absolute;
    top: 193px;
    left: 6%;
  }

  #bac2 {
    position: absolute;
    top: 407px;
    left: 6%;
  }

  #bac3 {
    position: absolute;
    top: 621px;
    left: 6%;
  }

  #bac4 {
    position: absolute;
    top: 835px;
    left: 6%;
  }

  .pagenums_as_1 {
    position: absolute;
    top: 413px;
    left: 42%;
  }

  .pagenums_as_2 {
    position: absolute;
    top: 627px;
    left: 42%;
  }

  .pagenums_as_3 {
    position: absolute;
    top: 841px;
    left: 42%;
  }

  .pagenums_as_4 {
    position: absolute;
    top: 1055px;
    left: 42%;
  }

  #personalcard{
    position: absolute;
    top:50px;
    left:27%;
    width: 54%;
  }

  #blockbox_as {
    width: 60%;
    height: auto;
    border-radius: 10px;;
    position: absolute;
    top: 347px;
    left: 26%;
  }

  #blockstitle {
    width: 96%;
    height: 74px;
    border-bottom-style: groove;
    float: left;
    margin-top: 11px;
    margin-left: 2%;
    line-height: 74px;
    font-family: 华光楷体_CNKI;
    font-size: 40px;
  }

  #searchselect {
    width: 90%;
    height: 40px;
    border-bottom-style: groove;
    border-width: thin;
    line-height: 40px;
    font-size: 24px;
    font-family: 华光楷体_CNKI;
    float: left;
    margin-top: 10px;
    margin-left: 5%;
    cursor: pointer;
  }

  #blockselects {
    width: 100%;
    height: auto;
    float: left;
    margin-top: 0px;
  }

  .blockselect {
    width: 90%;
    height: 40px;
    border-bottom-style: groove;
    border-width: thin;
    line-height: 40px;
    font-size: 24px;
    font-family: 华光楷体_CNKI;
    cursor: pointer;
    float: left;
    margin-top: 10px;
    margin-left: 5%;
  }
</style>
