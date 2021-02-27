<template>
<div class="box1">
  <div class="window">
    <div id="upper">
      <div id="head"><img :src="datas.headimgsrc" alt="error"></div>
      <div id="nickname">{{datas.nickname}}</div>
      <div id="close" @click="changestate(false)">
        <img :src="closeimgsrc" alt="error">
      </div>
    </div>
    <div id="lower">
      <textarea id="inputtext" v-model="inputtext"></textarea>
      <div @click="submit" id="submit">提交</div>
    </div>
  </div>
</div>
</template>

<script>
  export default {
    name: "replytool",
    props:["datas","commentinfo"],
    data(){
      return{
        closeimgsrc:"http://caesar216.usa3v.net/caelog/images/close2.png",
        inputtext:""
      }
    },
    methods:{
      changestate:function (state){
        var temp=this.datas;
        temp.ifreply=state;
        this.$emit("update:datas",temp);
      },
      submit:function (){
        var temp=this.commentinfo;
        var pos=this.datas.ccindex;
        temp[pos].addednum+=1;
        temp[pos].goodnums.push(0);
        temp[pos].ifgood.push(-1);
        temp[pos].count*=-1;
        temp[pos].addedsrc.push(this.datas.headimgsrc);
        temp[pos].addedcontent.push(this.inputtext);
        temp[pos].addednicknames.push(this.datas.nickname);
        this.$emit("update:commentinfo",temp);
        var tt=this.datas;
        tt.ifreply=false;
        this.$emit("update:datas",tt);
      }
    }
  }
</script>

<style scoped>
.box1{
  width: 300px;
  height: 200px;
}
.window{
  width:550px;
  height: 310px;
  border-style: ridge;
  background-color: white;
  position: relative;
  margin-top: 0px;
  margin-left: 0px;
}
#upper{
  width: 530px;
  height: 45px;
  border-bottom-style: ridge;
  position: absolute;
  top:5px;
  left:9px;
}
#head{
  width:35px;
  height: 35px;
  position: absolute;
  top:4px;
  left:4px;
}
#head img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
#nickname{
  font-family: 华光楷体_CNKI;
  font-size: 26px;
  position: absolute;
  top:10px;
  left:50px;
}
#close{
  width:30px;
  height: 30px;
  position: absolute;
  top:3px;
  right: 3px;
}
#close img{
  width: 100%;
  height: 100%;
}
#lower{
  width:535px;
  height: 250px;
  position: absolute;
  top:56px;
  left:9px;
}
#inputtext{
  height: 236px;
  width: 460px;
  position: absolute;
  top:4px;
  left:5px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
#submit{
  width:50px;
  height: 32px;
  border-style: ridge;
  position: absolute;
  right: 2px;
  bottom: 2px;
  background-color: beige;
  line-height: 32px;
  font-family: 华光楷体_CNKI;
  font-size: 21px;
}
</style>
