<template>
  <div id="mainpage">
    <pc id="personalCard" :datas="pcpos"></pc>
    <st id="searchtool"></st>
    <announce id="announce" :style="getcolor1()" :datas="announceInfo"></announce>
    <rs id="recommendsong" :style="getcolor1()" :datas="songInfo"></rs>
    <sorttool id="sorttool" :datas="sortinfo"></sorttool>
    <div id="articlebox">
      <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4]" v-if="(pagenumsinfo.pos-1)*4<articlenum"></ac>
      <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+1]" v-if="(pagenumsinfo.pos-1)*4+1<articlenum"></ac>
      <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+2]" v-if="(pagenumsinfo.pos-1)*4+2<articlenum"></ac>
      <ac class="articlecard" :datas="articleCardInfoprint[(pagenumsinfo.pos-1)*4+3]" v-if="(pagenumsinfo.pos-1)*4+3<articlenum"></ac>
    </div>
    <pn v-if="articlenum>3" class="pn" :style="getcolor1()" :datas.sync="pagenumsinfo"></pn>
    <div id="modibox" :style="getcolor1()">
      <div id="modititle">近期动态</div>
      <div id="modibody">
        <mc class="modicard" :style="getcolor1()" v-for="(i,index) in modificationcardInfo" :datas="i" v-if="index<6"></mc>
      </div>
    </div>
    <div id="blockbox" :style="getcolor1()">
      <lb id="blocks" :datas="blocksInfo"></lb>
      <lb id="labels" :datas="labelsInfo"></lb>
    </div>
    <div id="toback" @click="tobackstage" :style="getcolor3()" v-if="this.common.loginuserinfo.id==this.$route.params.userid">
      <div>前往后台</div>
    </div>
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
  var chatinfo={
    ifwin:false
  }
  var announceInfo={
    title:[],
    time:[],
    text:[],
    leftsrc:"http://caesar216.usa3v.net/caelog/images/left.png",
    rightsrc:"http://caesar216.usa3v.net/caelog/images/right.png",
    count:0
  }
  var songInfo={
    songname:"心要野",
    singer:"后海大鲨鱼",
    imgurl:"http://caesar216.usa3v.net/caelog/images/singer.png",
    playurl:"http://caesar216.usa3v.net/caelog/images/play.png",
    pauseurl:"http://caesar216.usa3v.net/caelog/images/pause.png",
    songurl:"http://caesar216.usa3v.net/caelog/images/心要野.mp3"
  }
  var sortinfo={
    timeslot:[{id:1,st:2015,et:2016}]
  }
  var modificationcardInfo=[{
    content:"修改了一些内容",
    time:"20-12-13"
  }]
  var blocksInfo={
    labels:{},
    title:"板块",
    type:'block'
  }
  var labelsInfo={
    labels:[],
    title:"标签",
    type:'label'
  }
  export default {
    name: 'mainpage',
    data(){
      return {
        announceInfo:announceInfo,
        songInfo:songInfo,
        sortinfo:sortinfo,
        articleCardInfo:[],
        articleCardInfoprint:[],
        modificationcardInfo:modificationcardInfo,
        blocksInfo:blocksInfo,
        labelsInfo:labelsInfo,
        pcpos:{pos:'left'},
        articlenum:0,
        sorttype:1,
        pagenumsinfo:{
          sum:1,
          pos:1
        }
      }
    },
    components: {
      st:searchtool,
      pc:personalCard,
      announce:announcement,
      rs:recommendsong,
      sorttool:sorttool,
      ac:articleCard,
      mc:modificationcard,
      lb:labelbox,
      pn:pagenums
    },
    mounted() {
      if(typeof this.$route.params.userid==='undefined'){
        this.router.push("/4/mainpage")
      }
      this.inituserinfo(this.$route.params.userid)
    },
    methods:{
      getColor4(){
        return {
          backgroundColor:this.$store.state.color4
        }
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
      tobackstage(){
        this.$router.push("/"+this.$route.params.userid+"/backstage")
      },
      addChange(){
        this.modificationcardInfo=[]
        this.$axios.get(
          this.common.serveraddress+"/change/get?userid="+this.common.userinfo.id).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
              var temp={
                content:res.data.data[i].content,
                time:res.data.data[i].time_.substring(2,10)
              }
              this.$set(this.modificationcardInfo,i,temp)
            }
          })
      },
      comparetime(time1,time2){
        let t1=[]
        t1.push(parseInt(time1.substring(0,4)))
        t1.push(parseInt(time1.substring(5,7)))
        t1.push(parseInt(time1.substring(8,10)))
        t1.push(parseInt(time1.substring(11,13)))
        t1.push(parseInt(time1.substring(14,16)))
        t1.push(parseInt(time1.substring(17,19)))
        t1.push(parseInt(time1.substring(20,22)))
        let t2=[]
        t2.push(parseInt(time2.substring(0,4)))
        t2.push(parseInt(time2.substring(5,7)))
        t2.push(parseInt(time2.substring(8,10)))
        t2.push(parseInt(time2.substring(11,13)))
        t2.push(parseInt(time2.substring(14,16)))
        t2.push(parseInt(time2.substring(17,19)))
        t2.push(parseInt(time2.substring(20,22)))
        for(var i=0;i<t1.length;i++){
          if(t1[i]!=t2[i]){
            return t1[i]-t2[i]
          }
        }
        return 0
      },
      sortArticlebyTime(){
        let len=this.articleCardInfoprint.length
        for(let i=0;i<len;i++){
          for(let j=0;j<len-1-i;j++){
            if(this.comparetime(this.articleCardInfoprint[j].time,this.articleCardInfoprint[j+1].time)<0){
              let temp=this.articleCardInfoprint[j]
              this.$set(this.articleCardInfoprint,j,this.articleCardInfoprint[j+1])
              this.$set(this.articleCardInfoprint,j+1,temp)
            }
          }
        }
        this.sorttype=1
      },
      sortArticlebyHot(){
        let len=this.articleCardInfoprint.length
        for(let i=0;i<len;i++){
          for(let j=0;j<len-1-i;j++){
            if(this.articleCardInfoprint[j].goodnum*2+this.articleCardInfoprint[j].viewnum<this.articleCardInfoprint[j+1].goodnum*2+this.articleCardInfoprint[j+1].viewnum){
              let temp=this.articleCardInfoprint[j]
              this.$set(this.articleCardInfoprint,j,this.articleCardInfoprint[j+1])
              this.$set(this.articleCardInfoprint,j+1,temp)
            }
          }
        }
        this.sorttype=0
      },
      selectbyTime:async function (s){
        let len=this.articleCardInfo.length
        let temp=[]
        this.pagenumsinfo={
          sum:0,
          pos:1
        }
        for(let i=0;i<len;i++){
          if(s=="all"||s==this.articleCardInfo[i].time.substring(2,7)){
            temp.push(this.articleCardInfo[i])
          }
        }
        this.articlenum=temp.length
        this.articleCardInfoprint=[]
        this.articleCardInfoprint=temp
        if(this.sorttype==0){
          this.sortArticlebyHot()
        }
        this.pagenumsinfo.sum=Math.ceil(this.articlenum/4)
      },
      getbls(){
        this.labelsInfo.labels=[]
        this.$axios.get(
          this.common.serveraddress+"/labels/get?userid="+this.common.userinfo.id).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
              var temp=res.data.data[i]
              this.$set(this.labelsInfo.labels,i,temp.name_)
            }
          })
        this.blocksInfo.labels=[]
        this.$axios.get(
          this.common.serveraddress+"/blocks/get?userid="+this.common.userinfo.id).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
              var temp=res.data.data[i]
              this.$set(this.blocksInfo.labels,temp.id,temp.name_)
            }
          })
      },
      refresh_announce(){
        this.announceInfo.title=[]
        this.announceInfo.time=[]
        this.announceInfo.text=[]
        this.announceInfo.count=0
        var randomcode=parseInt(new Date().getTime())%100000
        this.$axios.get(
          this.common.serveraddress+"/announcement/get?userid="+this.common.userinfo.id+"&randomcode="+randomcode).then(
          res=>{
            for(var i=0;i<res.data.data.length;i++){
              var temp={
                title:res.data.data[i].title,
                text:res.data.data[i].content,
                index:res.data.data[i].index_,
                id:res.data.data[i].id,
                time:res.data.data[i].times
              }
              this.$set(this.announceInfo.title,temp.index-1,temp.title)
              this.$set(this.announceInfo.time,temp.index-1,temp.time.substr(2,9))
              this.$set(this.announceInfo.text,temp.index-1,temp.text)
              this.announceInfo.count++
            }
          })
      },
      getarticles(){
        this.articleCardInfo=[]
        this.articlenum=0
        this.sortinfo.timeslot=[]
        this.pagenumsinfo={
          sum:0,
          pos:1
        }
        this.$axios.get(
          this.common.serveraddress+"/article/get?userid="+this.common.userinfo.id).then(
          res=>{
            if(res.data.data.length==0){
              var temp={
                title:res.data.data.title,
                time:res.data.data.time_,
                content:res.data.data.content,
                block:blocksInfo[res.data.data.blockid],
                id:res.data.data.id,
                goodnum:res.data.data.goodnum,
                viewnum:res.data.data.viewnum
              }
              this.articleCardInfo.push(temp)
              this.sortinfo.timeslot.push({
                id:this.sortinfo.timeslot.length,
                st:temp.time.substring(2,4),
                et:temp.time.substring(5,7)
              })
              this.articlenum++
            }
            for(var i =res.data.data.length-1;i>=0;i--){
              var temp={
                title:res.data.data[i].title,
                time:res.data.data[i].time_,
                content:res.data.data[i].content,
                block:blocksInfo[res.data.data[i].blockid],
                id:res.data.data[i].id,
                goodnum:res.data.data[i].goodnum,
                viewnum:res.data.data[i].viewnum
              }
              if(this.sortinfo.timeslot.length==0||this.sortinfo.timeslot[this.sortinfo.timeslot.length-1].st!=temp.time.substring(2,4)||this.sortinfo.timeslot[this.sortinfo.timeslot.length-1].et!=temp.time.substring(5,7)){
                this.sortinfo.timeslot.push({
                  id:this.sortinfo.timeslot.length,
                  st:temp.time.substring(2,4),
                  et:temp.time.substring(5,7)
                })
              }
              this.articleCardInfo.push(temp)
              this.articleCardInfoprint.push(temp)
              this.articlenum++
            }
            this.pagenumsinfo.sum=Math.ceil(this.articlenum/4)
          })
      },
      inituserinfo(userid){
        this.$axios({
          url:this.common.serveraddress+"/user/get?userid="+userid,
          method:"get"
        }).then(
          res=>{
            this.common.userinfo=res.data.data
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
    width:1024px;
  }
  #toback{
    width: 80px;
    height: 120px;
    background-color: #d7eee9;
    position: absolute;
    left:10px;
    cursor: pointer;
  }
  #toback div{
    position: absolute;
    top:20px;
    font-family: 华光楷体_CNKI;
    font-size: 28px;
    line-height: 40px;
  }
  .pn{
    position: absolute;
    left:265px;
    top:1143px;
  }
  #personalCard{
    position: absolute;
    top:20px;
    left:27px;
  }
  #searchtool{
    position: absolute;
    top:31px;
    left:345px;
  }
  #announce{
    position: absolute;
    top:29px;
    left:680px;
  }
  #recommendsong{
    position: absolute;
    left: 315px;
    top:91px;
  }
  #sorttool{
    position: absolute;
    top: 230px;
    left: 290px;
  }
  #articlebox{
    position: absolute;
    width:520px;
    height: 857px;
    top: 281px;
    left: 305px;
  }
  .articlecard{
    float:left;
    margin-top: 10px;
    margin-left: 10px;
  }
  #modibox{
    width:180px;
    height: 711px;
    position: absolute;
    top: 240px;
    left: 829px;
  }
  #modititle{
    width:160px;
    height:35px;
    position: relative;
    border-bottom-style: groove;
    margin-left:8px;
    margin-top:4px;
    text-align: left;
    font-size: 28px;
    font-family: 华光楷体_CNKI;
  }
  #modibody{
    width:180px;
    height: 671px;
    position: relative;
    margin-top: 2px;
  }
  .modicard{
    float:left;
    margin-left: 8px;
    margin-top:10px;
    border-bottom-style: groove;
    border-width: thin;
  }
  #blockbox{
    width:280px;
    height:600px;
    position: absolute;
    left:14px;
    top:315px;
  }
  #blocks{
    float:left;
    margin-top: 20px;
    margin-left: 15px;
  }
  #labels{
    float:left;
    margin-top: 25px;
    margin-left: 15px;
  }
</style>
