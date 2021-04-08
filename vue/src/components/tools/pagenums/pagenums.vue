<template>
<div class="box_pn clearfix">
  <div id="bigbox">
    <div id="first" @click="postostart">首页</div>
    <div id="backbutton" v-if="datas.pos>1" @click="redpos"><img :src="backsrc" alt="error"></div>
    <div id="numsbox">
      <div id="num1" class="num" v-if="datas.pos>3">...</div>
      <div id="num2" class="num" v-if="datas.pos>2" @click="updatepos(datas.pos-2)">{{datas.pos-2}}</div>
      <div id="num3" class="num" v-if="datas.pos>1" @click="updatepos(datas.pos-1)">{{datas.pos-1}}</div>
      <div id="num4" class="num">{{datas.pos}}</div>
      <div id="num5" class="num" v-if="datas.sum-datas.pos>0" @click="updatepos(datas.pos+1)">{{datas.pos+1}}</div>
      <div id="num6" class="num" v-if="datas.sum-datas.pos>1" @click="updatepos(datas.pos+2)">{{datas.pos+2}}</div>
      <div id="num7" class="num" v-if="datas.sum-datas.pos>2">...</div>
    </div>
    <div id="last" @click="postoend">尾页</div>
    <div id="aheadbutton" v-if="datas.sum-datas.pos>0" @click="addpos"><img :src="aheadsrc" alt="error"></div>
  </div>
</div>
</template>

<script>
  var aheadsrc="http://caesar216.usa3v.net/caelog/images/ahead.png"
  var backsrc="http://caesar216.usa3v.net/caelog/images/back.png"
  export default {
    name: "pagenums",
    props:["datas"],
    data(){
      return{
        aheadsrc:aheadsrc,
        backsrc:backsrc
      }
    },
    methods:{
      addpos:function (){
        if(this.datas.pos<this.datas.sum){
          var temp={
            pos:this.datas.pos+1,
            sum:this.datas.sum
          };
          this.$emit("update:datas",temp);
        }
      },
      redpos:function (){
        if(this.datas.pos>1){
          var temp={
            pos:this.datas.pos-1,
            sum:this.datas.sum
          };
          this.$emit("update:datas",temp);
        }
      },
      postostart:function (){
        var temp={
          pos:1,
          sum:this.datas.sum
        };
        this.$emit("update:datas",temp);
      },
      postoend:function (){
        var temp={
          pos:this.datas.sum,
          sum:this.datas.sum
        };
        this.$emit("update:datas",temp);
      },
      updatepos:function (num){
        var temp={
          pos:num,
          sum:this.datas.sum
        };
        this.$emit("update:datas",temp);
      }
    }
  }
</script>

<style scoped>
.box_pn{
  width:584px;
  height:50px;
  border-style: groove;
  border-width: thin;
}
#first{
  width: 65px;
  height: 34px;
  float:left;
  border-bottom-style: groove;
  border-width: thin;
  margin-top:6px;
  margin-left: 5px;
  line-height: 34px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
#backbutton{
  width: 40px;
  height: 40px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  left: 78px;
  top:3px;
}
#backbutton img{
  width: 100%;
  height: 100%;
}
#aheadbutton{
  width: 40px;
  height: 40px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  right: 78px;
  top:3px;
}
#aheadbutton img{
  width: 100%;
  height: 100%;
}
#bigbox{
  position: relative;
  margin-top:0px;
  margin-left:0px;
  width: 584px;
  height: 50px;
}
#numsbox{
  width:330px;
  height: 46px;
  position: absolute;
  top: 0px;
  left: 125px;
}
.num{
  width: 32px;
  height: 32px;
  border-bottom-style: groove;
  border-width: thin;
  position: absolute;
  top:6px;
  line-height: 32px;
}
#num1{
  right:280px;
}
#num2{
  right:235px;
}
#num3{
  right:190px;
}
#num4{
  right:145px;
  font-weight: bold;
}
#num5{
  right:100px;
}
#num6{
  right:55px;
}
#num7{
  right:10px;
}
#last{
  width: 65px;
  height: 34px;
  float:right;
  border-bottom-style: groove;
  border-width: thin;
  margin-top:6px;
  margin-right: 5px;
  line-height: 34px;
  font-family: 华光楷体_CNKI;
  font-size: 22px;
}
.clearfix:after{
  content: "";
  display: block;
  height: 0;
  clear:both;
  visibility: hidden;
}
.clearfix{
  *zoom: 1;
}
</style>
