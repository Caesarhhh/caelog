<template>
<div class="box">
  <div id="content">
    <GeminiScrollbar>
      {{datas.content}}
    </GeminiScrollbar>
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
  width:96%;
  height:90px;
  border-radius: 10px;
  position: relative;
  margin-left: 2%;
}
#content{
  width:100%;
  height: 68px;
  word-break: break-all;
  word-wrap: break-word;
  overflow: hidden;
  position: absolute;
  text-align: left;
  left:8px;
  top:5px;
  font-size: 21px;
  font-family: 华光楷体_CNKI;
}
#time{
  width: 50%;
  height:16px;
  position: absolute;
  right:2%;
  bottom:2px;
  font-family: 华光楷体_CNKI;
}
</style>
