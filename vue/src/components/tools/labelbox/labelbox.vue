<template>
<div class="box">
  <div id="title">
    {{datas.title}}
  </div>
  <div id="body">
    <div v-for="i in datas.labels" class="label" @click="showto(i)">{{i}}</div>
  </div>
</div>
</template>

<script>
  export default {
    name: "labelbox",
    props:["datas"],
    methods:{
      showto:function (s){
        this.$router.push("/articleshow/"+this.datas.type+"/"+s);
      }
    }
  }
</script>

<style scoped>
.box{
  width:250px;
  height: 250px;
  position: relative;
}
#title{
  width:100px;
  height:35px;
  position: absolute;
  top:5px;
  left:10px;
  line-height: 35px;
  font-size: 28px;
  font-family: 华光楷体_CNKI;
}
#body{
  width:230px;
  height:190px;
  position: absolute;
  border-style: ridge;
  left:10px;
  top:50px;
}
.label{
  width:auto;
  height:21px;
  float:left;
  margin-top: 10px;
  margin-left:14px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
</style>
