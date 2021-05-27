<template>
<div class="searchbox" :style="getcolor1()">
<input id="searchinput" v-model="inputtext"></input>
  <div id="searchbutton" @click="searchto">
    <img :src="searchimg" alt="error">
  </div>
</div>
</template>

<script>
  export default {
    name: "searchbox",
    props:["datas"],
    data(){
      return{
        searchimg:this.common.getserveraddress+"/images/search.png",
        inputtext:""
      }
    },
    methods:{
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      searchto:function (){
      this.$router.push("/"+this.$route.params.userid+"/articleshow/search/"+this.inputtext)
    }
    }
  }
</script>

<style scoped>
  .searchbox{
    width:95%;
    height:40px;
    position: relative;
  }
  #searchinput{
    width:80%;
    height:30px;
    border-style: ridge;
    position: absolute;
    top:2px;
    left:4px;
    font-size:25px;
    font-family: 华光楷体_CNKI;
  }
  #searchbutton{
    width:30px;
    height:30px;
    position: absolute;
    top:4px;
    right:4px;
  }
  #searchbutton img{
    width: 100%;
    height: 100%;
  }
</style>
