<template>
<div class="box">
  <div id="topbox">
    <select>
      <option v-for="item in this.blocks" v-model="blockselect">{{item.name}}</option>
    </select>
    <st id="sorttool" :datas="sorttoolinfo"></st>
  </div>
  <div id="punisharticle" @click="toedit">发布文章</div>
  <div id="articlesbox">
    <ac id="ac1" :datas="acinfo[0]" v-if="(pginfo.pos-1)*4<articlenum"></ac>
    <ac id="ac2" :datas="acinfo[0]" v-if="(pginfo.pos-1)*4+1<articlenum"></ac>
    <ac id="ac3" :datas="acinfo[0]" v-if="(pginfo.pos-1)*4+2<articlenum"></ac>
    <ac id="ac4" :datas="acinfo[0]" v-if="(pginfo.pos-1)*4+3<articlenum"></ac>
  </div>
  <pn id="pagenums" :datas.sync="pginfo"></pn>
</div>
</template>

<script>
  import sorttool from "../../../../tools/sorttool/sorttool";
  import articleshowCard from "./articleshowCard/articleshowCard";
  import pagenums from "../../../../tools/pagenums/pagenums";
  var sorttoolinfo={
    timeslot: [{id:0,st:2014,et:2015},{id:0,st:2016,et:2017},{id:0,st:2018,et:2019},{id:0,st:2020,et:"now"}]
  }
  var acinfo=[{
    time:"20-12-13",
    title:"我以我血荐轩辕",
    block:"随笔",
    content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
  },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    },
    {
      time:"20-12-13",
      title:"我以我血荐轩辕",
      block:"随笔",
      content:"所写的事迹，大抵有一点见过或者听过的缘由，但决不会用这事实，只是采取一端，加以改造，或者生发开去，到足以几乎完全发表我的意见为止。人物的模特儿也一样，没有专用一个人，往往嘴在浙江，脸在北京，衣服在山西，是一个拼凑起来的角色。"
    }]
  var pginfo={
    pos:1,
    sum:3
  }
  export default {
    name: "articlearrange",
    data(){
      return{
        sorttoolinfo:sorttoolinfo,
        blocks:[{id:0,name:"C++"},{id:1,name:"JAVA"},{id:2,name:"Linux"},{id:3,name:"ML"}],
        blockselect:{id:0,name:"C++"},
        acinfo:acinfo,
        pginfo:pginfo,
        articlenum:9
      }
    },
    props:["datas"],
    components:{
      st:sorttool,
      ac:articleshowCard,
      pn:pagenums
    },
    methods:{
      toedit:function (){
        this.$router.push("/articleedit");
      }
    }
  }
</script>

<style scoped>
.box{
  width:721px;
  height:1150px;
  border-style: ridge;
}
#topbox{
  width: 677px;
  height:74px;
  border-style: ridge;
  position: absolute;
  top:23px;
  left: 22px;
}
#topbox select{
  width:222px;
  height: 37px;
  border-style: ridge;
  position: absolute;
  top:18px;
  left:22px;
}
#sorttool{
  position: absolute;
  top:12px;
  right:10px;
}
#punisharticle{
  width: 140px;
  height: 40px;
  position: absolute;
  border-style: ridge;
  line-height: 40px;
  top:116px;
  right:50px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#articlesbox{
  width:656px;
  height: 910px;
  position: absolute;
  top:160px;
  left:30px;
}
#ac1{
  position: absolute;
  top:15px;
  left:76px;
}
#ac2{
  position: absolute;
  top:240px;
  left:76px;
}
#ac3{
  position: absolute;
  top:465px;
  left:76px;
}
#ac4{
  position: absolute;
  top:690px;
  left:76px;
}
#pagenums{
  position: absolute;
  top:1070px;
  left:65px;
}
</style>
