<template>
<div class="box1">
  <div class="searchbox">
    <input id="searchinput" v-model="inputtext"></input>
    <div id="searchbutton" @click="searchto">
      <img :src="searchimgsrc" alt="error">
    </div>
  </div>
  <st :datas="sorttoolinfo" id="sorttool_as"></st>
  <div id="blockbox_as">
    <div id="blockstitle">板块</div>
    <div id="searchselect" @click="searchresult"><div>搜索结果</div></div>
    <div id="blockselects">
      <div class="blockselect" v-for="item in blocks" @click="showto(item.name)">{{item['name']}}</div>
      <div style="float:left;margin-top:20px;width:190px;"></div>
    </div>
  </div>
  <bac id="bac1" :datas="articleCardInfo[(pagenumsinfo.pos-1)*4]" v-if="(pagenumsinfo.pos-1)*4<articlenum"></bac>
  <bac id="bac2" :datas="articleCardInfo[(pagenumsinfo.pos-1)*4+1]" v-if="(pagenumsinfo.pos-1)*4+1<articlenum"></bac>
  <bac id="bac3" :datas="articleCardInfo[(pagenumsinfo.pos-1)*4+2]" v-if="(pagenumsinfo.pos-1)*4+2<articlenum"></bac>
  <bac id="bac4" :datas="articleCardInfo[(pagenumsinfo.pos-1)*4+3]" v-if="(pagenumsinfo.pos-1)*4+3<articlenum"></bac>
  <pn v-if="articlenum!=0" :class="{pagenums_as_1:(pagenumsinfo.pos-1)*4+1==articlenum,pagenums_as_2:(pagenumsinfo.pos-1)*4+2==articlenum,pagenums_as_3:(pagenumsinfo.pos-1)*4+3==articlenum,pagenums_as_4:pagenumsinfo.pos*4<=articlenum}" :datas="pagenumsinfo"></pn>
</div>
</template>

<script>
  import sorttool from "../../tools/sorttool/sorttool";
  import bigarticleCard from "./bigarticleCard/bigarticleCard";
  import pagenums from "../../tools/pagenums/pagenums";
  var sorttoolinfo={
    timeslot:[
      {id:0,st:2015,et:2016},
      {id:1,st:2016,et:2017},
      {id:2,st:2018,et:2019},
      {id:3,st:2020,et:"now"}
    ]
  }
  var articleCardInfo=[]
  var pagenumsinfo={
    pos:1,
    sum:1
  }
  export default {
    name: "articleshowpage",
    props:["datas"],
    data(){
      return{
        searchimgsrc:"http://caesar216.usa3v.net/caelog/images/search.png",
        sorttoolinfo:sorttoolinfo,
        articleCardInfo:articleCardInfo,
        pagenumsinfo:pagenumsinfo,
        blocks:{},
        inputtext:"",
        searchdata:"搜索内容",
        articlenum:0
      }
    },
    components:{
      st:sorttool,
      pn:pagenums,
      bac:bigarticleCard
    },
    created() {
      this.searchdata=this.$route.params.data;
      this.inputtext=this.searchdata
    },
    methods:{
      searchto:function (){
        var temp=this.inputtext;
        this.inputtext="";
        this.$router.push("/"+this.$route.params.userid+"/articleshow/search/"+temp);
        this.getarticleinfo()
      },
      showto:function (s){
        if(s==this.$route.params.data){
          return
        }
        this.$router.push("/"+this.$route.params.userid+"/articleshow/block/"+s);
        this.getarticleinfo()
      },
      searchresult:function (){
        var temp=this.searchdata;
        this.$router.push("/"+this.$route.params.userid+"/articleshow/search/"+temp);
        this.getarticleinfo()
      },
      getarticleinfo:function (){
        this.pagenumsinfo={
          pos:1,
          sum:0
        }
        this.articlenum=0
        this.articleCardInfo=[]
        this.blocks={}
        this.$axios.get(
          this.common.serveraddress+"/blocks/get?userid="+this.$route.params.userid).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
              var temp=res.data.data[i]
              this.$set(this.blocks,temp.id,{index:i,name:temp.name_})
            }
            if(this.$route.params.type=="search"){
              this.$axios.get(
                this.common.serveraddress+"/article/getbysearch?userid="+this.$route.params.userid+"&searchcontent="+this.$route.params.data).then(
                res=>{
                  for(var i=0;i<res.data.data.length;i++){
                    var temp={
                      title:res.data.data[i].title,
                      block:this.blocks[res.data.data[i].blockid]['name'],
                      time:res.data.data[i].time_.substring(0,10),
                      content:res.data.data[i].content,
                      id:res.data.data[i].id
                    }
                    this.articleCardInfo.push(temp)
                    this.articlenum++
                  }
                  this.pagenumsinfo.sum=Math.ceil(this.articlenum/4)
                })
            }
            else if(this.$route.params.type=="label"){
              this.$axios.get(
                this.common.serveraddress+"/article/getbylabel?userid="+this.$route.params.userid+"&labelname="+this.$route.params.data).then(
                res=>{
                  for(var i=0;i<res.data.data.length;i++){
                    var temp={
                      title:res.data.data[i].title,
                      block:this.blocks[res.data.data[i].blockid]['name'],
                      time:res.data.data[i].time_.substring(0,10),
                      content:res.data.data[i].content,
                      id:res.data.data[i].id
                    }
                    this.articleCardInfo.push(temp)
                    this.articlenum++
                  }
                  this.pagenumsinfo.sum=Math.ceil(this.articlenum/4)
                })
            }
            else if(this.$route.params.type=="block"){
              this.$axios.get(
                this.common.serveraddress+"/article/getbyblock?userid="+this.$route.params.userid+"&blockname="+this.$route.params.data).then(
                res=>{
                  for(var i=0;i<res.data.data.length;i++){
                    var temp={
                      title:res.data.data[i].title,
                      block:this.blocks[res.data.data[i].blockid]['name'],
                      time:res.data.data[i].time_.substring(0,10),
                      content:res.data.data[i].content,
                      id:res.data.data[i].id
                    }
                    this.articleCardInfo.push(temp)
                    this.articlenum++
                  }
                  this.pagenumsinfo.sum=Math.ceil(this.articlenum/4)
                })
            }
          })
      }
    },
    mounted() {
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
    width:1024px;
  }
  .searchbox{
    width:450px;
    height:40px;
    background-color: white;
    position: absolute;
    left: 69px;
    top:61px;
  }
  #searchinput{
    width:400px;
    height:30px;
    border-style: groove;
    border-width: thin;
    position: absolute;
    top:2px;
    left:4px;
    font-size:25px;
    font-family: 华光楷体_CNKI;
  }
  #searchbutton{
    width:30px;
    height:30px;
    position: absolute;
    top:4px;
    right:4px;
  }
  #searchbutton img{
    width: 100%;
    height: 100%;
  }
  #sorttool_as{
    position: absolute;
    left:44px;
    top:125px;
    background-color: white;
  }
  #bac1{
    position: absolute;
    top:193px;
    left: 33px;
  }
  #bac2{
    position: absolute;
    top:467px;
    left: 33px;
  }
  #bac3{
    position: absolute;
    top:741px;
    left: 33px;
  }
  #bac4{
    position: absolute;
    top:1015px;
    left: 33px;
  }
  .pagenums_as_1{
    position: absolute;
    top:463px;
    left: 49px;
    background-color: white;
  }
  .pagenums_as_2{
    position: absolute;
    top:737px;
    left: 49px;
    background-color: white;
  }
  .pagenums_as_3{
    position: absolute;
    top:1011px;
    left: 49px;
    background-color: white;
  }
  .pagenums_as_4{
    position: absolute;
    top:1285px;
    left: 49px;
    background-color: white;
  }
  #blockbox_as{
    width:250px;
    height: auto;
    border-style: groove;
    border-width: thin;
    position: absolute;
    top:127px;
    left:711px;
    background-color: white;
  }
  #blockstitle{
    width:230px;
    height: 74px;
    border-bottom-style: groove;
    float:left;
    margin-top:11px;
    margin-left:9px;
    line-height: 74px;
    font-family: 华光楷体_CNKI;
    font-size: 40px;
  }
  #searchselect{
    width:190px;
    height: 40px;
    border-bottom-style: groove;
    border-width: thin;
    line-height: 40px;
    font-size: 24px;
    font-family: 华光楷体_CNKI;
    float: left;
    margin-top:10px;
    margin-left:30px;
  }
  #blockselects{
    width:248px;
    height:auto;
    float: left;
    margin-top:0px;
  }
  .blockselect{
    width:190px;
    height: 40px;
    border-bottom-style: groove;
    border-width: thin;
    line-height: 40px;
    font-size: 24px;
    font-family: 华光楷体_CNKI;
    float:left;
    margin-top:10px;
    margin-left:30px;
  }
</style>
