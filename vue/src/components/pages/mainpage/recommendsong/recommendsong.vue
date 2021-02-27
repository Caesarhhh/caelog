<template>
<div class="box">
  <div id="pic">
    <img :src="datas.imgurl" alt="error">
  </div>
  <div id="info">
    <div id="songname">{{datas.songname}}</div>
    <div id="singer">{{datas.singer}}</div>
  </div>
  <div id="play" @click="changestate">
    <img :src="this.songstate==1?datas.pauseurl:datas.playurl" alt="error">
  </div>
</div>
</template>

<script>
  export default {
    name: "recommendsong",
    props:["datas"],
    data(){
      return{
        songstate:-1,
        audio:new Audio("http://caesar216.usa3v.net/caelog/images/心要野.mp3")
      }
    },
    methods:{
      changestate:function (){
        this.songstate*=-1;
        if(this.songstate==1){
          this.audio.play();
        }
        else{
          this.audio.pause();
        }
      }
    }
  }
</script>

<style scoped>
.box{
  width:312px;
  height:121px;
  border-style: ridge;
  position: relative;
}
#pic{
  width: 90px;
  height:90px;
  margin-left: 13px;
  margin-top:9px;
  border-style: ridge;
  float:left;
  border-radius: 50%;
}
#pic img{
  width:100%;
  height:100%;
  border-radius: 50%;
}
#info{
  width: 135px;
  height:90px;
  float:left;
  margin-left: 11px;
  margin-top: 11px;
}
#songname{
  width:110px;
  height:25px;
  margin-top:9px;
  margin-left:8px;
  font-family: 华光楷体_CNKI;
  font-size:25px;
  text-align:left;
}
#singer{
  width:90px;
  height:16px;
  margin-top: 12px;
  margin-left:8px;
  text-align: left;
  font-family: 华光楷体_CNKI;
}
#play{
  width: 50px;
  height: 50px;
  float:left;
  margin-top:61px;
  margin-left:0px;
}
#play img{
  width:100%;
  height:100%;
  border-radius: 50%;
}
</style>
