<template>
<div class="box1">
  <div id="pic"><img :src="datas.imgsrc" alt=""></div>
  <div id="name">{{datas.name}}</div>
  <div id="comments">{{datas.comments}}</div>
  <div id="delpic" @click="del(datas.id)"><img :src="delimgsrc" alt="error"></div>
</div>
</template>

<script>
  export default {
    name: "blocksetbox",
    data(){
      return{
        delimgsrc:"http://caesar216.usa3v.net/caelog/images/delete.png"
      }
    },
    props:["datas"],
    methods:{
      del:function (id){
          this.$parent.deletebl(id)
      }
    }
  }
</script>

<style scoped>
.box1{
  width:280px;
  height: 36px;
  border-style: groove;
  border-width: thin;
}
#pic{
  width:28px;
  height: 28px;
  position: absolute;
  top:4px;
  left:4px;
}
#pic img{
  width: 100%;
  height: 100%;
}
#name{
  width:94px;
  height: 28px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:5px;
  left:38px;
  line-height: 28px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#comments{
  width:102px;
  height: 28px;
  border-left-style: ridge;
  border-right-style: ridge;
  border-width: thin;
  position: absolute;
  top:5px;
  left:138px;
  line-height: 28px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#delpic{
  width:28px;
  height: 28px;
  position: absolute;
  top:4px;
  right:3px;
}
#delpic img{
  width: 100%;
  height: 100%;
}
</style>
