<template>
<div class="box1" :style="getcolor4()">
  <div id="empty">
    <div id="value">{{datas.label}}</div>
    <div id="close" @click="close">
      <img :src="closeimgsrc" alt="error">
    </div>
  </div>
</div>
</template>

<script>
  export default {
    name: "labelcard",
    props:["datas"],
    methods:{
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor4(){
        return {backgroundColor: this.$store.state.color4}
      },
      close:function (){
        this.$parent.closelb(this.datas.index)
      }
    },
    data(){
      return{
        closeimgsrc:this.common.getserveraddress+"images/close.png"
      }
    }
  }
</script>

<style scoped>
.box1{
  width: auto;
  min-width:80px;
  height: auto;
  min-height: 40px;
  border-radius: 10px;
}
.box1:hover #close{
  display: block;
}
#empty{
  width: auto;
  min-width:80px;
  height: auto;
  min-height: 30px;
  position: relative;
}
#value{
  font-family: 华光楷体_CNKI;
  font-size: 21px;
  position: relative;
  margin-top: 10px;
  margin-left: 3px;
  margin-right: 3px;
}
#close{
  width: 28px;
  height: 28px;
  position: absolute;
  right:-17px;
  top:-22px;
  display: none;
}
#close img{
  width: 100%;
  height: 100%;
}
</style>
