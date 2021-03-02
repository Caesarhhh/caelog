<template>
  <div id="mainpage">
    <pc id="personalCard" :datas="pcpos"></pc>
    <st id="searchtool"></st>
    <announce id="announce" :datas="announceInfo"></announce>
    <rs id="recommendsong" :datas="songInfo"></rs>
    <sorttool id="sorttool" :datas="sortinfo"></sorttool>
    <div id="articlebox">
      <ac class="articlecard" :datas="articleCardInfo[0]" v-if="articlenum>0"></ac>
      <ac class="articlecard" :datas="articleCardInfo[1]" v-if="articlenum>1"></ac>
      <ac class="articlecard" :datas="articleCardInfo[2]" v-if="articlenum>2"></ac>
      <ac class="articlecard" :datas="articleCardInfo[3]" v-if="articlenum>3"></ac>
    </div>
    <div id="modibox">
      <div id="modititle">近期动态</div>
      <div id="modibody">
        <mc class="modicard" v-for="i in modificationcardInfo" :datas="i"></mc>
      </div>
    </div>
    <div id="blockbox">
      <lb id="blocks" :datas="blocksInfo"></lb>
      <lb id="labels" :datas="labelsInfo"></lb>
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
    timeslot:[{id:1,st:2015,et:2016},{id:2,st:2017,et:2018},{id:3,st:2019,et:2020}]
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
        modificationcardInfo:modificationcardInfo,
        blocksInfo:blocksInfo,
        labelsInfo:labelsInfo,
        pcpos:{pos:'left'},
        articlenum:0
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
      lb:labelbox
    },
    mounted() {
      this.refresh_announce()
      this.getbls()
      this.addChange()
      this.getarticles()
      },
    methods:{
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
        this.$axios.get(
          this.common.serveraddress+"/article/get?userid="+this.common.userinfo.id).then(
          res=>{
            for(var i =0;i<res.data.data.length;i++){
              var temp={
                title:res.data.data[i].title,
                time:res.data.data[i].time_.substring(2,10),
                content:res.data.data[i].content,
                block:blocksInfo[res.data.data[i].blockid],
                id:res.data.data[i].id
              }
              this.articleCardInfo.push(temp)
              this.articlenum++
            }
          })
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
    color: #2c3e50;
    position: relative;
    margin-top: 0px;
    width:1024px;
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
    left:680px
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
    border-style: ridge;
    position: absolute;
    top: 240px;
    left: 829px;
  }
  #modititle{
    width:160px;
    height:35px;
    position: relative;
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
    margin-top:16px;
  }
  #blockbox{
    width:280px;
    height:600px;
    position: absolute;
    border-style: ridge;
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
