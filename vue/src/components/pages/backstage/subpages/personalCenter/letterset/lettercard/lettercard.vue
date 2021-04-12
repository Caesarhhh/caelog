<template>
<div class="boxlc" @click="toChatterPage" :style="getcolor4()">
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
    ifwin:false,
    targetid:0
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
      toChatterPage(){
        this.$router.push("/"+this.datas.targetid+"/mainpage")
      },
      getcolor4(){
        return {backgroundColor: this.$store.state.color4}
      },
      openchat:function (){
        this.chatinfo.targetid=this.datas.targetid
        this.chatinfo.ifwin=true;
      },
      getletterinfo:function (){
      }
    },
    mounted() {
      this.chatinfo.targetid=this.datas.targetid
    }
  }
</script>

<style scoped>
.boxlc{
  width:251px;
  height: 47px;
  border-style: groove;
  border-width: thin;
  border-radius: 5px;
  cursor: pointer;
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
  border-bottom-style: groove;
  border-width: thin;
  position: absolute;
  top:2px;
  left:47px;
  border-radius: 2px;
  cursor: pointer;
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
  height: 20px;
  position: absolute;
  top:22px;
  left:85px;
  line-height: 20px;
  font-size: 16px;
  font-family: 华光楷体_CNKI;
  background-color: unset;
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
