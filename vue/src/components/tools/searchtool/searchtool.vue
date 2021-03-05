<template>
<div class="searchbox">
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
        searchimg:"http://caesar216.usa3v.net/caelog/images/search.png",
        inputtext:""
      }
    },
    methods:{
      searchto:function (){
      this.$router.push("/"+this.$route.params.userid+"/articleshow/search/"+this.inputtext)
    }
    }
  }
</script>

<style scoped>
  .searchbox{
    width:280px;
    height:40px;
    border-style: ridge;
    position: relative;
  }
  #searchinput{
    width:230px;
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
