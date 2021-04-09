<template>
<div class="sortbox">
  <div id="sort1" @click="sortbyHot" :class="{shadow:sorttype==0,nonshadow:sorttype==1}">热度</div>
  <div id="sort2" @click="sortbyTime" :class="{shadow:sorttype==1,nonshadow:sorttype==0}">时间</div>
  <div id="select">
    <select name="selecttime" v-model="timeselect">
      <option v-for="item in datas.timeslot" :value="item.id">{{item.st}}-{{item.et}}</option>
    </select>
  </div>
</div>
</template>

<script>
  export default {
    name: "sorttool",
    data(){
      return {
        timeselect:{id:0,st:2015,et:2016},
        sorttype:1
      }
    },
    props:["datas"],
    methods:{
      setsorttype:function (index){
        this.sorttype=index;
      },
      sortbyTime:function (){
        this.$parent.sortArticlebyTime()
        this.setsorttype(1)
      },
      sortbyHot:function (){
        this.$parent.sortArticlebyHot()
        this.setsorttype(0)
      }
    }
  }
</script>

<style scoped>
  .shadow{
    top:5px;
    box-shadow: 5px 5px 5px #888888;
  }
  .nonshadow{
    top:10px;
    box-shadow: 0px 0px 0px #888888;
  }
  .sortbox{
    width:390px;
    height:51px;
    position: relative;
  }
  #sort1{
    width:67px;
    height:26px;
    left:38px;
    background-color: white;
    position: absolute;
    border-style: groove;
    border-width: thin;
  }
  #sort2{
    width:67px;
    height:26px;
    left:155px;
    background-color: white;
    position: absolute;
    border-style: groove;
    border-width: thin;
  }
  #select{
    width:102px;
    height:25px;
    top:16px;
    left:272px;
    position: absolute;
  }
  #select select{
    width:100%;
    height:100%;
  }
</style>
