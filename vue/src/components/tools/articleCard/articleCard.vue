<template>
<div class="boxac" :style="getColor4()">
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
    name: "articleCard",
    data(){
      return {
        good:-1,
        goodsrc:this.common.getserveraddress+"images/good.png",
        nogoodsrc:this.common.getserveraddress+"images/nogood.png",
        turnsrc:this.common.getserveraddress+"images/transmit.png",
        readsrc:this.common.getserveraddress+"images/read.png"
      }
    },
    props:["datas"],
    methods:{
        getColor4(){
            return {
                backgroundColor:this.$store.state.color4
            }
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
.boxac{
  width:100%;
  height:200px;
  border-radius: 10px;
  border-bottom-style: ridge;
  position: relative;
}
#info{
  width: 94%;
  height: 39px;
  position: absolute;
  top:5px;
  left:3%;
}
#title{
  width:70%;
  height: 37px;
  float:left;
  overflow: hidden;
  margin-top:1px;
  margin-left:2%;
  font-family: 华光楷体_CNKI;
  font-size: 28px;
  text-align: left;
  line-height: 100%;
}
#articleblock{
  width:96px;
  height:28px;
  float:left;
  margin-top:9px;
  margin-left:56px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
  text-align: left;
  line-height: 100%;
}
#time{
  width:25%;
  height:20px;
  position: absolute;
  top:15px;
  right:0%;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  text-align: left;
  line-height: 100%;
}
#body{
  width:98%;
  height: 147px;
  position: absolute;
  top:45px;
  left:1%;
}
#content{
  width: 94%;
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
  width:5%;
  height: 134px;
  float:right;
  margin-top:5px;
  margin-right: 0px;
}
.button{
  width:90%;
  margin-top:14px;
  margin-left:3px;
  float:left;
}
.button img{
  width:100%;
  height: 100%;
}
</style>
