<template>
<div class="box">
  <div id="topbox">
    <select>
      <option v-for="key in this.blocks" v-model="blockselect">{{key}}</option>
    </select>
    <st id="sorttool" :datas="sorttoolinfo"></st>
  </div>
  <div id="punisharticle" @click="toedit">发布文章</div>
  <div id="articlesbox">
    <ac id="ac1" :datas="acinfo[(pginfo.pos-1)*4]" v-if="(pginfo.pos-1)*4<articlenum"></ac>
    <ac id="ac2" :datas="acinfo[(pginfo.pos-1)*4+1]" v-if="(pginfo.pos-1)*4+1<articlenum"></ac>
    <ac id="ac3" :datas="acinfo[(pginfo.pos-1)*4+2]" v-if="(pginfo.pos-1)*4+2<articlenum"></ac>
    <ac id="ac4" :datas="acinfo[(pginfo.pos-1)*4+3]" v-if="(pginfo.pos-1)*4+3<articlenum"></ac>
  </div>
  <pn id="pagenums" :datas.sync="pginfo"></pn>
</div>
</template>

<script>
  import sorttool from "../../../../tools/sorttool/sorttool";
  import articleshowCard from "./articleshowCard/articleshowCard";
  import pagenums from "../../../../tools/pagenums/pagenums";
  var sorttoolinfo={
    timeslot: [{id:0,st:2014,et:2015},{id:0,st:2016,et:2017},{id:0,st:2018,et:2019},{id:0,st:2020,et:"now"}]
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
    sum:0
  }
  export default {
    name: "articlearrange",
    data(){
      return{
        sorttoolinfo:sorttoolinfo,
        blocks: {},
        blockselect:{id:0,name:"C++"},
        acinfo:acinfo,
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
      this.refresh_article()
    },
    methods:{
      toedit:function (){
        this.$router.push("/articleedit");
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
      getblocks:function (){
        this.blocks={}
        this.$axios.get(
          this.common.serveraddress+"/blocks/get?userid="+this.common.userinfo.id).then(
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
        this.articlenum=0
        let param = new FormData()
        param.append('userid',this.common.userinfo.id)
        this.uploadFile("/article/get",param).then(res=>{
          if(res.data.code==200){
            for(var i =0;i<res.data.data.length;i++){
              var temp={time:res.data.data[i].time_.substring(2,10),
                        title:res.data.data[i].title,
                        block:this.blocks[res.data.data[i].blockid],
                        content:res.data.data[i].content.substring(0,300),
                        articleid:res.data.data[i].id}
              // this.$set(this.acinfo,this.articlenum,temp)
              this.acinfo.push(temp)
              this.articlenum++
            }
          }
          this.pginfo.sum=Math.floor(this.articlenum/4)
          console.log(this.acinfo)
        })
      },
      deleteac:function (arid){
        this.$axios.get(
          this.common.serveraddress+"/article/delete?userid="+this.common.userinfo.id+"&id="+arid).then(
          res=>{
            if(res.data.code==200){
              alert("删除成功！")
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
  border-style: ridge;
}
#topbox{
  width: 677px;
  height:74px;
  border-style: ridge;
  position: absolute;
  top:23px;
  left: 22px;
}
#topbox select{
  width:222px;
  height: 37px;
  border-style: ridge;
  position: absolute;
  top:18px;
  left:22px;
}
#sorttool{
  position: absolute;
  top:12px;
  right:10px;
}
#punisharticle{
  width: 140px;
  height: 40px;
  position: absolute;
  border-style: ridge;
  line-height: 40px;
  top:116px;
  right:50px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
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
  left:65px;
}
</style>
