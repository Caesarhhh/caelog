<template>
<div class="box">
  <div id="idbox">
    <div id="head"><img :src="datas.headsrc" alt="error"></div>
    <div id="nickname">{{datas.nickname}}</div>
    <div id="idintroduction">{{datas.introduction}}</div>
  </div>
  <div id="line"></div>
  <div class="selectbin" @click="changeindex(1)">个人中心</div>
  <div class="selectbin" @click="changeindex(2)">文章管理</div>
  <div class="selectbin" @click="changeindex(3)">友链管理</div>
  <div class="selectbin" @click="changeindex(4)">公告管理</div>
  <div class="selectbin" @click="changeindex(5)">版块管理</div>
</div>
</template>

<script>
  export default {
    name: "selectbox",
    props:["datas","pageindex_"],
    data(){
      return{
        pageindex:0
      }
    },
    mounted() {
      this.pageindex=this.pageindex_;
    },
    methods:{
      changeindex:function (num){
        this.pageindex=num;
        this.$emit("update:pageindex_",num);
      }
    }
  }
</script>

<style scoped>
.box{
  width:236px;
  height:528px;
  border-style: ridge;
}
#idbox{
  width:222px;
  height:199px;
  position: absolute;
  top:7px;
  left:6px;
}
#head{
  width:70px;
  height: 70px;
  position: absolute;
  top:5px;
  left:5px;
}
#head img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
#nickname{
  width:70px;
  height: 25px;
  position: absolute;
  top:33px;
  left:100px;
  font-family: 华光楷体_CNKI;
  font-weight: bold;
  font-size: 20px;
}
#idintroduction{
  width:205px;
  height:95px;
  border-style: ridge;
  position: absolute;
  top:95px;
  left:7px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#line{
  width:230px;
  height: 1px;
  float: left;
  margin-top:216px;
  margin-left:3px;
  background-color: black;
}
.selectbin{
  width:195px;
  height:40px;
  border-bottom-style: ridge;
  float:left;
  margin-top:10px;
  margin-left:20px;
  font-family: 华光楷体_CNKI;
  font-size: 28px;
  text-align: center;
  line-height: 46px;
}
</style>
