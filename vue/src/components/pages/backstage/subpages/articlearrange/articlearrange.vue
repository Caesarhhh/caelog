<template>
<div class="box" :style="getcolor1()">
  <div id="topbox" :style="getcolor4()">
    <select @change="selectbyBlock_Time" v-model="blockselect">
      <option value="all">全部</option>
      <option v-for="key in this.blocks" :value="key">{{key}}</option>
    </select>
    <st id="sorttool" :selectby-timef="selectbyTime" :sortby-hotf="sortArticlebyHot" :sortby-timef="sortArticlebyTime" :datas="sortinfo"></st>
  </div>
  <div id="punisharticle" :style="getcolor3()" @click="toedit">发布文章</div>
  <div id="articlesbox">
    <ac id="ac1" :datas="acinfoprint[(pginfo.pos-1)*4]" v-if="(pginfo.pos-1)*4<articlenum"></ac>
    <ac id="ac2" :datas="acinfoprint[(pginfo.pos-1)*4+1]" v-if="(pginfo.pos-1)*4+1<articlenum"></ac>
    <ac id="ac3" :datas="acinfoprint[(pginfo.pos-1)*4+2]" v-if="(pginfo.pos-1)*4+2<articlenum"></ac>
    <ac id="ac4" :datas="acinfoprint[(pginfo.pos-1)*4+3]" v-if="(pginfo.pos-1)*4+3<articlenum"></ac>
  </div>
  <pn id="pagenums" :datas.sync="pginfo"></pn>
</div>
</template>

<script>
  import sorttool from "../../../../tools/sorttool/sorttool";
  import articleshowCard from "./articleshowCard/articleshowCard";
  import pagenums from "../../../../tools/pagenums/pagenums";
  var sortinfo={
    timeslot: [{id:0,st:2014,et:2015}]
  }
  var acinfo=[
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    }]
  var pginfo={
    pos:1,
    sum:0,
    pagesize:4
  }
  export default {
    name: "articlearrange",
    data(){
      return{
        sortinfo:sortinfo,
        blocks: {},
        blockselect:{id:0,name:"C++"},
        timeselect:"all",
        acinfo:acinfo,
        acinfoprint:acinfo,
        pginfo:pginfo,
        articlenum:0,
      }
    },
    props:["datas"],
    components:{
      st:sorttool,
      ac:articleshowCard,
      pn:pagenums
    },
    mounted() {
      this.blockselect="all"
      this.refresh_article()
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
      getcolor4(){
        return {backgroundColor: this.$store.state.color4}
      },
      toedit:function (){
        this.$router.push("/"+this.$route.params.userid+"/articleedit/-1");
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
        let len=this.acinfoprint.length
        for(let i=0;i<len;i++){
          for(let j=0;j<len-1-i;j++){
            if(this.comparetime(this.acinfoprint[j].time,this.acinfoprint[j+1].time)<0){
              let temp=this.acinfoprint[j]
              this.$set(this.acinfoprint,j,this.acinfoprint[j+1])
              this.$set(this.acinfoprint,j+1,temp)
            }
          }
        }
        this.sorttype=1
      },
      sortArticlebyHot(){
        let len=this.acinfoprint.length
        for(let i=0;i<len;i++){
          for(let j=0;j<len-1-i;j++){
            if(this.acinfoprint[j].goodnum*2+this.acinfoprint[j].viewnum<this.acinfoprint[j+1].goodnum*2+this.acinfoprint[j+1].viewnum){
              let temp=this.acinfoprint[j]
              this.$set(this.acinfoprint,j,this.acinfoprint[j+1])
              this.$set(this.acinfoprint,j+1,temp)
            }
          }
        }
        this.sorttype=0
      },
      selectbyBlock_Time:async function (){
        let s=this.blockselect
        let len=this.acinfo.length
        let temp=[]
        this.pginfo={
          sum:0,
          pos:1,
          pagesize: 4
        }
        for(let i=0;i<len;i++){
          if((this.blockselect=="all"||s==this.acinfo[i].block)&&(this.timeselect=="all"||this.timeselect==this.acinfo[i].time.substring(2,7))){
            temp.push(this.acinfo[i])
          }
        }
        this.articlenum=temp.length
        this.acinfoprint=[]
        this.acinfoprint=temp
        if(this.sorttype==0){
          this.sortArticlebyHot()
        }
        else{
          this.sortArticlebyTime()
        }
        this.pginfo.sum=this.articlenum
      },
      selectbyTime:async function (s){
        this.timeselect=s
        this.selectbyBlock_Time()
      },
      getblocks:function (){
        this.blocks={}
        this.$axios.get(
          this.common.serveraddress+"/blocks/get?userid="+this.common.loginuserinfo.id).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
              var label=res.data.data[i].name_
              var id_=res.data.data[i].id
              this.blocks[id_]=label
            }
          })
      },
      refresh_article:function (){
        this.getblocks()
        this.acinfo=[]
        this.acinfoprint=[]
        this.articlenum=0
        this.sortinfo.timeslot=[]
        let param = new FormData()
        param.append('userid',this.common.loginuserinfo.id)
        this.uploadFile("/article/get",param).then(res=>{
          if(res.data.code==200){
            for(var i =0;i<res.data.data.length;i++){
              var temp={time:res.data.data[i].time_,
                        title:res.data.data[i].title,
                        block:this.blocks[res.data.data[i].blockid],
                        content:res.data.data[i].content.substring(0,300),
                        articleid:res.data.data[i].id,
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
              // this.$set(this.acinfo,this.articlenum,temp)
              this.acinfo.push(temp)
              this.acinfoprint.push(temp)
              this.articlenum++
            }
          }
          this.pginfo.sum=this.articlenum
          this.sortArticlebyTime()
        })
      },
      deleteac:function (arid){
        var valid=confirm("确定删除文章吗？")
        if(!valid){
          return
        }
        this.$axios.get(
          this.common.serveraddress+"/article/delete?userid="+this.common.loginuserinfo.id+"&id="+arid).then(
          res=>{
            if(res.data.code==200){
              this.refresh_article()
            }
            else{
              alert(res.data.msg)
            }
          })
      }
    }
  }
</script>

<style scoped>
.box{
  width:721px;
  height:1150px;
  position: absolute;
}
#topbox{
  width: 677px;
  height:74px;
  border-style: solid;
  border-width: thin;
  position: absolute;
  top:23px;
  left: 22px;
  border-radius: 15px;
}
#topbox select{
  width:222px;
  height: 37px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:18px;
  left:22px;
}
#sorttool{
  position: absolute;
  top:85px;
  left:10px;
}
#punisharticle{
  width: 140px;
  height: 40px;
  position: absolute;
  border-style: groove;
  border-width: thin;
  line-height: 40px;
  top:116px;
  left:500px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
  border-radius: 5px;
  cursor: pointer;
}
#articlesbox{
  width:656px;
  height: 910px;
  position: absolute;
  top:160px;
  left:30px;
}
#ac1{
  position: absolute;
  top:15px;
  left:76px;
}
#ac2{
  position: absolute;
  top:240px;
  left:76px;
}
#ac3{
  position: absolute;
  top:465px;
  left:76px;
}
#ac4{
  position: absolute;
  top:690px;
  left:76px;
}
#pagenums{
  position: absolute;
  top:1070px;
  left:5%;
  width:90%;
}
</style>
