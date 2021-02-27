<template>
<div class="box1">
  <cw :datas.sync="chatinfo" v-if="chatinfo.ifwin"></cw>
  <div id="head"><img :src="datas.headsrc" alt="error"></div>
  <div id="info" @click="openchat">
    <div id="nickname">{{datas.nickname}}</div>
    <div id="time">{{datas.time}}</div>
  </div>
  <div id="close" class="close">
    <img :src="closesrc" alt="error">
  </div>
</div>
</template>

<script>
  import chatwin from "../../../../../../wins/chatwin/chatwin";
  var chatinfo={
    ifwin:false
  }
  export default {
    name: "letterCard",
    data(){
      return{
        closesrc:"http://caesar216.usa3v.net/caelog/images/close.png",
        chatinfo:chatinfo,
      }
    },
    props:["datas"],
    components:{
      cw:chatwin
    },
    methods:{
      openchat:function (){
        this.chatinfo.ifwin=true;
      }
    }
  }
</script>

<style scoped>
.box1{
  width:251px;
  height: 47px;
  border-style: ridge;
  border-radius: 5px;
}
.box1:hover #close{
  display: block;
}
#head{
  width: 38px;
  height: 38px;
  position: absolute;
  top:4px;
  left:4px;
}
#head img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
#info{
  width: 195px;
  height: 38px;
  border-style: ridge;
  position: absolute;
  top:2px;
  left:47px;
  border-radius: 2px;
}
#nickname{
  width: 70px;
  height: 30px;
  position: absolute;
  top:5px;
  left:4px;
  line-height: 30px;
  text-align: left;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  font-weight: bolder;
}
#time{
  width: 110px;
  height: 30px;
  position: absolute;
  top:16px;
  left:85px;
  line-height: 30px;
  font-size: 16px;
  font-family: 华光楷体_CNKI;
}
#close{
  width: 25px;
  height: 25px;
  position: absolute;
  top:-12px;
  right:-12px;
  display: none;
}
#close img{
  width:100%;
  height: 100%;
}
</style>
