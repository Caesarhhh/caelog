<template>
<div class="boxhhwin">
  <div id="close_hwin" @click="closewin">
    <img :src="closeimg" alt="error">
  </div>
  <div id="title_hwin" class="unselect">{{datas.comments}}</div>
  <div id="href_hwin">{{datas.href}}</div>
</div>
</template>

<script>
  export default {
    name: "hrefshowwin",
    props:["datas"],
    data(){
      return{
        closeimg:"http://caesar216.usa3v.net/caelog/images/close2.png"
      }
    },
    methods:{
      closewin:function (){
        var temp=this.datas;
        temp.ifwin=false;
        this.$emit("update:datas",temp);
      }
    }
  }
</script>

<style scoped>
.boxhhwin{
  width:auto;
  height: 40px;
  border-style: ridge;
  background-color: white;
  z-index: 10;
}
#close_hwin{
  width:32px;
  height:32px;
  position: absolute;
  right:-7px;
  top:-7px;
}
#close_hwin img{
  width: 100%;
  height: 100%;
}
#title_hwin{
  width:auto;
  height: 30px;
  float: left;
  border-right-style: ridge;
  margin-top: 5px;
  margin-left: 3px;
  text-align: left;
  line-height: 30px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
#href_hwin{
  width:auto;
  height: 30px;
  float: left;
  margin-top: 5px;
  margin-left: 3px;
  margin-right:18px;
  text-align: left;
  line-height: 30px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
.unselect{

  -webkit-user-select:none;

  -moz-user-select:none;

  -ms-user-select:none;

  user-select:none;

}
</style>
