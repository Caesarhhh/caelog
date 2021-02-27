<template>
  <div class="box">
    <div id="info">
      <div id="title">{{datas.title}}</div>
      <div id="articleblock">{{datas.block}}</div>
      <div id="time">{{datas.time}}</div>
    </div>
    <div id="body">
      <div id="content">{{datas.content}}</div>
      <div id="buttonbox">
        <div class="button" @click="toglegood()"><img :src="this.good==1?goodsrc:nogoodsrc" alt="error"></div>
        <div class="button"><img :src="turnsrc" alt="error"></div>
        <div class="button" @click="readto"><img :src="readsrc" alt="error"></div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "bigarticleCard",
    data(){
      return {
        good:-1,
        goodsrc:"http://caesar216.usa3v.net/caelog/images/good.png",
        nogoodsrc:"http://caesar216.usa3v.net/caelog/images/nogood.png",
        turnsrc:"http://caesar216.usa3v.net/caelog/images/transmit.png",
        readsrc:"http://caesar216.usa3v.net/caelog/images/read.png"
      }
    },
    props:["datas"],
    methods:{
      toglegood:function (){
        this.good*=-1;
      },
      readto:function (){
        this.$router.push("/articleread/"+this.datas.title);
      }
    }
  }
</script>

<style scoped>
  .box{
    width:616px;
    height:247px;
    border-radius: 10px;
    border-style: ridge;
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
    width:auto;
    height: 37px;
    float:left;
    margin-top:16px;
    margin-left:25px;
    font-family: 华光楷体_CNKI;
    font-size: 28px;
    text-align: left;
    line-height: 100%;
  }
  #articleblock{
    width:96px;
    height:28px;
    float:left;
    margin-top:23px;
    margin-left:90px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
    text-align: left;
    line-height: 100%;
  }
  #time{
    width:85px;
    height:20px;
    float:right;
    margin-top:26px;
    margin-right:0px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
    text-align: left;
    line-height: 100%;
  }
  #body{
    width:600px;
    height: 179px;
    position: absolute;
    top:60px;
    left:7px;
  }
  #content{
    width: 552px;
    height: 170px;
    float:left;
    margin-top:8px;
    margin-left:2px;
    font-family: 华光楷体_CNKI;
    border-right-style: ridge;
    font-size: 18px;
    text-align: left;
    line-height: 100%;
    text-indent: 2em;
  }
  #buttonbox{
    width:36px;
    height: 119px;
    float:right;
    margin-top:5px;
    margin-right: 0px;
  }
  .button{
    width:30px;
    height:30px;
    margin-top:23px;
    margin-left:3px;
    float:left;
  }
  .button img{
    width:100%;
    height: 100%;
  }
</style>
