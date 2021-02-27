<template>
<div :class="{box3l:datas.ifleft,box3r:!datas.ifleft}">
  <div id="empty" class="clearfix">
    <div class="unselect" :class="{headimg:datas.ifleft,headimg_:!datas.ifleft}"><img :src="datas.headimgsrc" alt="error"></div>
    <div :class="{content:datas.ifleft,content_:!datas.ifleft}" class="chattext"><div>{{datas.content}}</div></div>
  </div>
</div>
</template>

<script>
  export default {
    name: "chatbox",
    props:["datas"]
  }
</script>

<style scoped>
.box3l{
  width:650px;
  height:auto;
  float: left;
  margin-left: 10px;
  margin-top:1px;
  margin-bottom: 4px;
}
.box3r{
  width:650px;
  height:auto;
  float: right;
  margin-right: 10px;
  margin-top:1px;
  margin-bottom: 4px;
}
#empty{
  width:650px;
  height: auto;
  position: relative;
  margin-left: 0px;
  margin-top: 0px;
}
.headimg{
  width:45px;
  height: 45px;
  float:left;
  margin-top: 2px;
  margin-left: 5px;
}
.headimg img{
  width:100%;
  height: 100%;
  border-radius: 50%;
}
.headimg_{
  width:45px;
  height: 45px;
  float:right;
  margin-top: 2px;
  margin-right: 5px;
}
.headimg_ img{
  width:100%;
  height: 100%;
  border-radius: 50%;
}
.content{
  width:auto;
  height: auto;
  min-width: 100px;
  max-width: 500px;
  float: left;
  background-color: #f0ffff;
  border-radius: 10px;
  margin-left: 15px;
  margin-top: 10px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  text-align: left;
}
.content div{
  position: relative;
  margin-top:4px;
  margin-left: 4px;
  margin-right: 4px;
  margin-bottom: 4px;
}
.content_{
  width:auto;
  height: auto;
  min-width: 20px;
  max-width: 500px;
  min-height: 50px;
  float: right;
  background-color: #f0ffff;
  border-radius: 10px;
  margin-right: 15px;
  margin-top: 10px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  text-align: left;
}
.content_ div{
  position: relative;
  margin-top:4px;
  margin-left: 4px;
  margin-right: 4px;
  margin-bottom: 4px;
}
.clearfix:after{
  display:block;
  clear:both;
  content:"";
  visibility:hidden;
  height:0;
}
.chattext{
  word-wrap:break-word;
  word-break:break-all;
}
.clearfix{
  zoom:1
}
.unselect{

  -webkit-user-select:none;

  -moz-user-select:none;

  -ms-user-select:none;

  user-select:none;

}
</style>
