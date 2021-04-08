<template>
<div class="box">
  <div id="content">
    {{datas.content}}
  </div>
  <div id="time">
    {{datas.time}}
  </div>
</div>
</template>

<script>
  export default {
    name: "modificationcard",
    props:["datas"]
  }
</script>

<style scoped>
.box{
  width:160px;
  height:90px;
  border-radius: 10px;
  position: relative;
}
#content{
  width:153px;
  height: 60px;
  position: absolute;
  text-align: left;
  left:8px;
  top:5px;
  font-size: 22px;
  font-family: 华光楷体_CNKI;
}
#time{
  width: 70px;
  height:16px;
  position: absolute;
  right:2px;
  bottom:2px;
  font-family: 华光楷体_CNKI;
}
</style>
