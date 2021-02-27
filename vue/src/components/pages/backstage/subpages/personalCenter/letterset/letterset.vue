<template>
<div class="letterset">
  <div id="title"><div>私信管理</div></div>
  <div id="letterbox">
    <lc id="lc1" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6]"></lc>
    <lc id="lc2" v-if="!(pagenumsinfo.pos==pagenumsinfo.sum&&letternum%6<2)" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+1]"></lc>
    <lc id="lc3" v-if="!(pagenumsinfo.pos==pagenumsinfo.sum&&letternum%6<3)" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+2]"></lc>
    <lc id="lc4" v-if="!(pagenumsinfo.pos==pagenumsinfo.sum&&letternum%6<4)" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+3]"></lc>
    <lc id="lc5" v-if="!(pagenumsinfo.pos==pagenumsinfo.sum&&letternum%6<5)" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+4]"></lc>
    <lc id="lc6" v-if="!(pagenumsinfo.pos==pagenumsinfo.sum&&letternum%6<6)" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+5]"></lc>
    <div id="line"></div>
    <pn id="pagenums" :datas.sync="pagenumsinfo"></pn>
  </div>
</div>
</template>

<script>
  import pagenums from "../../../../../tools/pagenums/pagenums";
  import lettercard from "./lettercard/lettercard";
  var pagenumsinfo={
    pos:1,
    sum:2
  }
  var letterCardinfo=
  [{nickname:"Luffy",
    time:"20-12-17 18:30",
    headsrc:"http://caesar216.usa3v.net/caelog/images/luffy.png"},
    {nickname:"Zoro",
      time:"20-12-19 17:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/zoro.png"},
    {nickname:"Nami",
      time:"20-12-18 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/nami.png"},
    {nickname:"Sanji",
      time:"20-12-15 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/sanji.png"},
    {nickname:"Wusopu",
      time:"20-12-14 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/wusopu.png"},
    {nickname:"Joba",
      time:"20-12-13 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/joba.png"},
    {nickname:"Robin",
      time:"20-12-12 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/robin.png"},
    {nickname:"Franky",
      time:"20-12-14 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/franky.png"},
    {nickname:"Blook",
      time:"20-12-15 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/blook.png"},
    {nickname:"Shinpin",
      time:"20-12-19 18:30",
      headsrc:"http://caesar216.usa3v.net/caelog/images/shinpin.png"}]
  export default {
    name: "letterset",
    props:["datas"],
    data(){
      return{
        pagenumsinfo:pagenumsinfo,
        letternum:10,
        letterCardinfo:letterCardinfo
      }
    },
    components:{
      pn:pagenums,
      lc:lettercard
    },
    methods:{
    }
  }
</script>

<style scoped>
.letterset{
  width:696px;
  height: 281px;
  border-style: ridge;
}
#title{
  width:47px;
  height: 256px;
  border-style: ridge;
  position: absolute;
  top:10px;
  left:15px;
}
#title div{
  font-size: 24px;
  font-family: 华光楷体_CNKI;
  position: absolute;
  top:70px;
}
#letterbox{
  width:614px;
  height: 256px;
  border-style: ridge;
  position: absolute;
  top:10px;
  left:71px;
}
#lc1{
  position: absolute;
  top:7px;
  left:14px
}
#lc2{
  position: absolute;
  top:70px;
  left:14px
}
#lc3{
  position: absolute;
  top:133px;
  left:14px
}
#lc4{
  position: absolute;
  top:7px;
  left:324px
}
#lc5{
  position: absolute;
  top:70px;
  left:324px
}
#lc6{
  position: absolute;
  top:133px;
  left:324px
}
#line{
  height:180px;
  width:1px;
  position: absolute;
  background-color: black;
  top:8px;
  left:300px;
}
#pagenums{
  position: absolute;
  top:197px;
  left:15px;
}
</style>
