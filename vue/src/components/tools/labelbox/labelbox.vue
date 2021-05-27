<template>
<div class="box" :style="getcolor1()">
  <div id="title">
    {{datas.title}}
  </div>
  <div id="body">
    <GeminiScrollbar>
      <div v-for="i in datas.labels" class="label" @click="showto(i)">{{i}}</div>
    </GeminiScrollbar>
  </div>
</div>
</template>

<script>
  export default {
    name: "labelbox",
    props:["datas"],
    data(){
      return{
      }
    },
    methods:{
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      showto:function (s){
        this.$router.push("/"+this.$route.params.userid+"/articleshow/"+this.datas.type+"/"+s);
      }
    }
  }
</script>

<style scoped>
.box{
  width:86%;
  height: 250px;
  position: relative;
}
#title{
  width:94%;
  height:35px;
  position: absolute;
  top:5px;
  left:3%;
  line-height: 35px;
  font-size: 28px;
  font-family: 华光楷体_CNKI;
}
#body{
  width:94%;
  height:190px;
  position: absolute;
  border-bottom-style: ridge;
  left:3%;
  top:50px;
}
.label{
  width:auto;
  height:21px;
  float:left;
  margin-top: 10px;
  margin-left:4%;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  cursor: pointer;
}
</style>
