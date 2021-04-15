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
        <div class="button" @click="toglegood()"><img :src="this.good==1?goodsrc:nogoodsrc" alt="error"></div>
        <div class="button" @click="share()"><img :src="turnsrc" alt="error"></div>
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
      getcolor4(){
        return {backgroundColor: this.$store.state.color4}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      share(){
        this.$copyText(this.common.siteaddress+"/"+this.$route.params.userid+"/articleread/"+this.datas.id).then(res=>{
          alert("已复制文章连接到剪切板")
        })
      },
      toglegood:function (){
        if((JSON.stringify(this.common.loginuserinfo)==='{}')||this.common.loginuserinfo==null){
          alert("请先登录")
          return
        }
        if(this.good==-1){
          this.$axios.get(
            this.common.serveraddress+"/action/add?actorid="+this.common.loginuserinfo.id+"&targetid="+this.common.userinfo.id+"&type_=articlegood"+"&objectid="+this.datas.id).then(
            res=>{
              this.$axios.get(this.common.serveraddress+"/article/changegood?userid="+this.common.userinfo.id+"&id="+this.datas.id+"&count=1").then(
                res=>{
                  this.good=1
                }
              )
            })
        }
        else{
          this.$axios.get(
            this.common.serveraddress+"/action/delete?actorid="+this.common.loginuserinfo.id+"&targetid="+this.common.userinfo.id+"&type_=articlegood"+"&objectid="+this.datas.id).then(
            res=>{
              this.$axios.get(this.common.serveraddress+"/article/changegood?userid="+this.common.userinfo.id+"&id="+this.datas.id+"&count=-1").then(
                res=>{
                  this.good=-1
                }
              )
            })
        }
      },
      readto:function (){
        this.$router.push("/"+this.$route.params.userid+"/articleread/"+this.datas.id);
      }
    },
      mounted() {
          if((JSON.stringify(this.common.loginuserinfo)==='{}')==false&&this.common.loginuserinfo!=null){
              this.$axios.get(
                  this.common.serveraddress+"/action/ifin?actorid="+this.common.loginuserinfo.id+"&targetid="+this.$route.params.userid+"&type_=articlegood"+"&objectid="+this.datas.id).then(
                  res=>{
                      if(res.data.code==400){
                          this.good=-1
                      }
                      else{
                          this.good=1
                      }
                  })
          }
      }
  }
</script>

<style scoped>
  .box{
    width:616px;
    height:247px;
    border-radius: 10px;
    border-bottom-style: groove;
    position: relative;
  }
  #info{
    width: auto;
    height: 39px;
    position: absolute;
    top:5px;
    left:7px;
  }
  #title{
    width:320px;
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
    width:136px;
    height:28px;
    float:left;
    margin-top:26px;
    margin-left:5px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
    text-align: left;
    line-height: 100%;
  }
  #time{
    width:105px;
    height:20px;
    float:right;
    margin-top:31px;
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
    overflow: hidden;
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
