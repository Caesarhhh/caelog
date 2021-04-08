<template>
<div class="box1">
  <div id="contentbox">
    <div id="title">{{datas.title}}</div>
    <div id="content">{{datas.content}}</div>
  </div>
  <div id="indexpic">{{datas.index}}</div>
  <div id="uppic" @click="up(datas.index)"><img :src="upsrc" alt="error"></div>
  <div id="deletepic" @click="del(datas.id)"><img :src="delsrc" alt="error"></div>
</div>
</template>

<script>
  export default {
    name: "announcesetCard",
    props:["datas"],
    data(){
      return{
        upsrc:"http://caesar216.usa3v.net/caelog/images/up.png",
        delsrc:"http://caesar216.usa3v.net/caelog/images/delete.png"
      }
    },
    methods:{
      del:function (id){
        this.$parent.deletean(id)
      },
      up:function (index){
        this.$parent.change(index)
      }
    }
  }
</script>

<style scoped>
.box1{
  width: 656px;
  height: 166px;
  border-style: groove;
  border-width: thin;
}
#contentbox{
  width: 596px;
  height: 148px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:7px;
  left: 10px;
}
#title{
  width:576px;
  height: 32px;
  border-bottom-style: groove;
  border-width: thin;
  position: absolute;
  left:9px;
  top:5px;
  text-align: center;
  line-height: 32px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#content{
  width:576px;
  height: 95px;
  position: absolute;
  left:9px;
  top:44px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  text-align: left;
  text-indent: 2em;
}
#indexpic{
  width: 30px;
  height: 30px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:17px;
  right: 6px;
  font-family: 华光楷体_CNKI;
  font-size: 25px;
  line-height: 30px;
  text-align: center;
  border-radius: 50%;
}
#uppic{
  width: 32px;
  height: 32px;
  position: absolute;
  top:69px;
  right: 6px;
}
#uppic img{
  width: 100%;
  height: 100%;
}
#deletepic{
  width: 32px;
  height: 32px;
  position: absolute;
  top:120px;
  right: 6px;
}
#deletepic img{
  width: 100%;
  height: 100%;
}
</style>
