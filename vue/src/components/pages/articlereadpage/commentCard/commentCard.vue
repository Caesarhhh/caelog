<template>
<div class="box1">
  <div class="maincomment clearfix">
    <div id="mainsrc"><img :src="datas.mainsrc" alt="error"></div>
    <div id="maincontent"><div>{{datas.nickname}} : {{datas.maincontent}}</div></div>
    <div class="buttonbox">
      <div class="goodnums">{{datas.maingoodnums}}</div>
      <img @click="toglegood(-1)" :src="datas.ifmaingood==-1?nogoodimgsrc:goodimgsrc" alt="error" class="good">
      <div class="reply" @click="reply(-1)"><img :src="relpyimgsrc" alt="error"></div>
      <div class="delete" @click="deletecard(-1)" v-if="replyinfo.name==datas.nickname"><img :src="deleteimgsrc" alt="error"></div>
    </div>
  </div>
  <div v-for="(item,index) in datas.addedsrc" class="addedcomment clearfix">
    <div class="addedsrc"><img :src="datas.addedsrc[index]" alt="error"></div>
    <div class="addedcontent"><div>{{datas.addednicknames[index]}} : {{datas.addedcontent[index]}}</div></div>
    <div class="buttonbox" v-if="datas.count>-2">
      <div class="goodnums">{{datas.goodnums[index]}}</div>
      <img @click="toglegood(index)" :src="datas.ifgood[index]==-1?nogoodimgsrc:goodimgsrc" alt="error" class="good">
      <div class="reply" @click="reply(index)"><img :src="relpyimgsrc" alt="error"></div>
      <div class="delete" @click="deletecard(index)" v-if="replyinfo.name==getname(datas.addednicknames[index])"><img :src="deleteimgsrc" alt="error"></div>
    </div>
  </div>
</div>
</template>

<script>
  export default {
    name: "commentCard",
    props:["datas","replyinfo"],
    data(){
      return{
        goodimgsrc:"http://caesar216.usa3v.net/caelog/images/good.png",
        nogoodimgsrc:"http://caesar216.usa3v.net/caelog/images/nogood.png",
        relpyimgsrc:"http://caesar216.usa3v.net/caelog/images/reply.png",
        deleteimgsrc:"http://caesar216.usa3v.net/caelog/images/delete.png"
      }
    },
    methods:{
      getname:function (s){
        var pos=s.indexOf("@");
        if(pos==-1){
          return s;
        }
        else{
          return s.substr(0,pos);
        }
      },
      toglegood:function (num){
        if(num==-1){
          var temp=this.datas;
          temp.ifmaingood*=-1;
          temp.maingoodnums+=temp.ifmaingood;
          this.$emit("update:datas",temp);
        }
        else{
          var temp=this.datas;
          temp.ifgood[num]*=-1;
          temp.goodnums[num]+=temp.ifgood[num];
          temp.count*=-1;
          this.$emit("update:datas",temp);
        }
      },
      reply:function (target){
        if(target==-1){
          var temp=this.replyinfo;
          temp.ifreply=true;
          temp.nickname=temp.name;
          temp.ccindex=this.datas.index;
          this.$emit("update:replyinfo",temp);
        }
        else{
          var temp=this.replyinfo;
          temp.ifreply=true;
          temp.nickname=temp.name;
          temp.nickname+="@";
          temp.ccindex=this.datas.index;
          temp.nickname+=this.getname(this.datas.addednicknames[target]);
          this.$emit("update:replyinfo",temp);
        }
      },
      deletecard:function (num){
        if(num==-1){
        this.$parent.deletecard(this.datas.index);
        }
        else{
          this.$parent.deletecomment(this.datas.index,num);
        }
      }
    }
  }
</script>

<style scoped>
.box1{
  width:942px;
  border-radius: 10px;
  height: auto;
  border-style: ridge;
}
.maincomment{
  position: relative;
  margin-left: 5px;
  margin-top:10px;
  border-bottom-style: ridge;
  width:930px;
  height: auto;
}
#mainsrc{
  width:40px;
  height: 40px;
  float:left;
  margin-left: 5px;
  margin-top: 5px;
}
#mainsrc img{
  width:100%;
  height: 100%;
  border-radius: 50%;
}
#maincontent{
  width:860px;
  float:left;
  margin-top:5px;
  margin-left:10px;
  height: auto;
}
#maincontent div{
  text-align: left;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
.buttonbox{
  width:152px;
  height: 30px;
  float: right;
  margin-right: 10px;
  margin-bottom: 2px;
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
.delete{
  width:30px;
  height: 30px;
  float: right;
  margin-right: 17px;
  margin-top: 3px;
}
.delete img{
  width: 100%;
  height: 100%;
}
.reply{
  float: right;
  margin-right:17px;
  margin-top: 4px;
  width: 30px;
  height: 30px;
}
.reply img{
  width: 100%;
  height: 100%;
}
.good{
  width:30px;
  height: 30px;
  float:right;
  margin-right:12px;
}
.goodnums{
  float:right;
  margin-right:5px;
  margin-top:6px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
.addedcomment{
  float:right;
  margin-right: 5px;
  margin-top:2px;
  border-bottom-style: ridge;
  width:880px;
  height: auto;
}
.addedsrc{
  width:40px;
  height: 40px;
  float:left;
  margin-left: 5px;
  margin-top: 5px;
}
.addedsrc img{
  width:100%;
  height: 100%;
  border-radius: 50%;
}
.addedcontent{
  width:810px;
  float:left;
  margin-top:5px;
  margin-left:20px;
  height: auto;
}
.addedcontent div{
  text-align: left;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
</style>
