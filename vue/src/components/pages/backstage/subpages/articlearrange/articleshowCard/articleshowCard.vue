<template>
  <div class="box" :style="getcolor4()">
    <div id="info">
      <div id="title">{{datas.title}}</div>
      <div id="articleblock">{{datas.block}}</div>
      <div id="time">{{datas.time.substring(2,10)}}</div>
    </div>
    <div id="body">
      <div id="content">{{datas.content}}</div>
      <div id="buttonbox">
        <div class="button" @click="tomodify(datas.articleid)"><img :src="modifysrc" alt="error"></div>
        <div class="button" @click="deleteac(datas.articleid)"><img :src="delsrc" alt="error"></div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "articleshowCard",
    data(){
      return {
        good:-1,
        modifysrc:this.common.getserveraddress+"images/modify.png",
        delsrc:this.common.getserveraddress+"images/delete.png"
      }
    },
    props:["datas"],
    methods:{
      getcolor4(){
        return{
          backgroundColor:this.$store.state.color4
        }
      },
      deleteac:function (aid){
        this.$parent.deleteac(aid)
      },
      tomodify(s){
        this.$router.push({path:"/"+this.common.loginuserinfo.id+"/articleedit/"+s})
      }
    }
  }
</script>

<style scoped>
  .box{
    width:500px;
    height:200px;
    border-radius: 10px;
    border-style: groove;
    border-width: thin;
    position: relative;
  }
  #info{
    width: 479px;
    height: 39px;
    position: absolute;
    top:5px;
    left:7px;
  }
  #title{
    width:300px;
    height: 37px;
    position: absolute;
    overflow: hidden;
    top:1px;
    left:3px;
    font-family: 华光楷体_CNKI;
    font-size: 26px;
    text-align: left;
    line-height: 100%;
  }
  #articleblock{
    width:96px;
    height:28px;
    overflow: hidden;
    position: absolute;
    top:3px;
    left:310px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
    text-align: left;
    line-height: 100%;
  }
  #time{
    width:85px;
    height:20px;
    position: absolute;
    top:26px;
    right:0px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
    text-align: left;
    line-height: 100%;
  }
  #body{
    width:484px;
    height: 147px;
    position: absolute;
    top:45px;
    left:7px;
  }
  #content{
    width: 436px;
    height: 138px;
    float:left;
    margin-top:8px;
    margin-left:2px;
    font-family: 华光楷体_CNKI;
    border-right-style: ridge;
    font-size: 18px;
    text-align: left;
    line-height: 100%;
    text-indent: 2em;
    overflow: hidden;
  }
  #buttonbox{
    width:36px;
    height: 134px;
    float:right;
    margin-top:5px;
    margin-right: 0px;
  }
  .button{
    width:30px;
    height:30px;
    margin-top:28px;
    margin-left:3px;
    float:left;
  }
  .button img{
    width:100%;
    height: 100%;
  }
</style>
