<template>
<div class="box" :style="getcolor4()">
  <div id="pic"><img :src="datas.picsrc" alt="error"></div>
  <div id="href">{{datas.href}}</div>
  <div id="comments">{{datas.comments}}</div>
  <img :src="datas.checksrc" id="checked" alt="error" @click="changechecked" />
  <div id="delete" @click="deletehref(datas.hrefid)"><img :src="deletesrc" alt="error"></div>
</div>
</template>

<script>
  export default {
    name: "hrefcard",
    props:["datas"],
    data(){
      return{
        imgsrc:"http://caesar216.usa3v.net/caelog/images/delete.png",
        deletesrc:"http://caesar216.usa3v.net/caelog/images/delete.png",
        selectsrc:"http://caesar216.usa3v.net/caelog/images/tool/selected.png",
        nonselectsrc:"http://caesar216.usa3v.net/caelog/images/tool/nonselected.png",
        checked:true
      }
    },
    mounted() {
      this.checked=this.datas.checked;
      this.confirmsrc()
    },
    methods:{
      getcolor4(){
        return {
          backgroundColor:this.$store.state.color4
        }
      },
      changechecked:function (event){
        this.changehref();
      },
      deletehref:function (id){
        this.$parent.deletehr(id);
      },
      confirmsrc:function (){
        if(this.checked==true){
          this.imgsrc=this.selectsrc
        }
        else{
          this.imgsrc=this.nonselectsrc
        }
      },
      changehref:function (){
        var tt=0
        if(this.checked==true){
          tt=1
        }
        this.$parent.changevisable(this.datas.hrefid,1-tt);
        this.confirmsrc()
      }
    }
  }
</script>

<style scoped>
.box{
  width: auto;
  height: 36px;
  border-style: groove;
  border-width: thin;
  border-radius: 10px;
}
#pic{
  width: 28px;
  height: 28px;
  float:left;
  margin-top:4px;
  margin-left:4px;
}
#pic img{
  width:100%;
  height: 100%;
}
#href{
  width:auto;
  height: 30px;
  border-left-style: ridge;
  border-width: thin;
  float:left;
  margin-left:5px;
  margin-top:3px;
  line-height: 30px;
}
#comments{
  width:auto;
  height: 30px;
  border-left-style: ridge;
  border-width: thin;
  float:left;
  margin-left:3px;
  margin-top:3px;
  line-height: 30px;
}
#checked{
  width: 25px;
  height: 25px;
  float:left;
  margin-top:8px;
  margin-left:4px;
}
#delete{
  width:30px;
  height: 30px;
  float:left;
  margin-top:4px;
  margin-left: 1px;
}
#delete img{
  width:100%;
  height:100%;
}
</style>
