<template>
<div class="box">
<div id="announcetitle">{{datas.title[announceindex]}}</div>
<div id="leftbutton" @click="redindex"><img :src="datas.leftsrc" alt="error"></div>
<div id="rightbutton" @click="addindex"><img :src="datas.rightsrc" alt="error"></div>
<div id="announcetime">{{datas.time[announceindex]}}</div>
<div id="announcetext">{{datas.text[announceindex]}}</div>
<div id="rate">
  <div v-for="(item,i) in datas.time" :class="{rat:i!=announceindex,ratdot:i==announceindex}"></div>
</div>
</div>
</template>

<script>
  export default {
    data(){
      return{
        announceindex:0,
        timmer:''
      }
    },
    name: "announcement",
    props:["datas"],
    mounted(){
      this.timmer=setInterval(this.addindex,10000)
    },
    methods:{
      addindex:function (){
        if(this.announceindex==this.datas.count-1){
          this.announceindex=0;
        }
        else {
          this.announceindex++;
        }
      },
      redindex:function (){
        if(this.announceindex==0){
          this.announceindex=3;
        }
        else {
          this.announceindex--;
        }
      }
    }
  }
</script>

<style scoped>
.box{
  width: 315px;
  height: 183px;
  position: relative;
  border-style: ridge;
}
#announcetitle{
  width:200px;
  height:37px;
  position: absolute;
  top:5px;
  left:18px;
  font-size: 30px;
  font-family: 华光楷体_CNKI;
}
#leftbutton{
  width:30px;
  height:60px;
  position: absolute;
  left:-20px;
  top:60px;
  display: none;
}
#leftbutton img{
  width:100%;
  height:100%;
}
#rightbutton{
  width:30px;
  height:60px;
  position: absolute;
  right:-20px;
  top:60px;
  display: none;
}
#rightbutton img{
  width:100%;
  height:100%;
}
.box:hover #rightbutton{
  display: initial;
}
.box:hover #leftbutton{
  display: initial;
}
.box:hover #rate{
  display: initial;
}
#leftbutton:hover{
  visibility: visible;
}
#announcetime{
  width:72px;
  height:21px;
  position: absolute;
  top:19px;
  right:5px;
  font-size: 16px;
  font-family: 华光楷体_CNKI;
}
#announcetext{
  width:300px;
  height:120px;
  position: absolute;
  top:52px;
  left:14px;
  word-wrap:break-word;
  word-break:break-all;
  font-size: 18px;
  font-family: 华光楷体_CNKI;
  text-indent: 2em;
  text-align: left;
}
#rate{
  width:80px;
  height:20px;
  position: absolute;
  left:110px;
  bottom: 0px;
  text-align: center;
  display: none;
}
.rat{
  width:10px;
  height: 10px;
  display: inline-block;
  border-radius: 50%;
  background-color: silver;
}
.ratdot{
  width:10px;
  height: 10px;
  display: inline-block;
  border-radius: 50%;
  background-color: #888888;
}
</style>
