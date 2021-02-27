<template>
<div class="box">
    <div id="titlebox">
    <form action="">
      <input id="pic" name="multipartFile" type="file" @change="uploadpic" enctype="multipart/form-data">
    </form>
    <img id="headchange" :src="picsrc" alt="error">
    <div id="hreftitle">链接</div>
    <input id="hrefcontent" v-model="hrefcontent"></input>
    <div id="commentstitle">备注</div>
    <input id="commentscontent" v-model="commentcontent"></input>
    <button id="confirm" @click="submit">添加</button>
  </div>
  <div id="announcement">可见的链接最多只能有四个</div>
  <hc id="hc1" :datas.sync="hrefcardinfo[(pagenumsinfo.pos-1)*6]" v-if="(pagenumsinfo.pos-1)*6<hrefnum"></hc>
  <hc id="hc2" :datas.sync="hrefcardinfo[(pagenumsinfo.pos-1)*6+1]" v-if="(pagenumsinfo.pos-1)*6+1<hrefnum"></hc>
  <hc id="hc3" :datas.sync="hrefcardinfo[(pagenumsinfo.pos-1)*6+2]" v-if="(pagenumsinfo.pos-1)*6+2<hrefnum"></hc>
  <hc id="hc4" :datas.sync="hrefcardinfo[(pagenumsinfo.pos-1)*6+3]" v-if="(pagenumsinfo.pos-1)*6+3<hrefnum"></hc>
  <hc id="hc5" :datas.sync="hrefcardinfo[(pagenumsinfo.pos-1)*6+4]" v-if="(pagenumsinfo.pos-1)*6+4<hrefnum"></hc>
  <hc id="hc6" :datas.sync="hrefcardinfo[(pagenumsinfo.pos-1)*6+5]" v-if="(pagenumsinfo.pos-1)*6+5<hrefnum"></hc>
  <pn id="pagenums" :datas.sync="pagenumsinfo"></pn>
</div>
</template>

<script>
  import hrefcard from "./hrefcard/hrefcard";
  import pagenums from "../../../../tools/pagenums/pagenums";
  var hrefcardinfo=[]
  var pagenumsinfo={
    pos:1,
    sum:1
  }
  export default {
    name: "socialhrefset",
    props:["datas"],
    data(){
      return{
        picsrc:"http://caesar216.usa3v.net/caelog/images/tool/uploadpic.png",
        hrefcardinfo:hrefcardinfo,
        pagenumsinfo:pagenumsinfo,
        hrefcontent:" ",
        commentcontent:" ",
        hrefnum:0,
        uploadurl:"",
        fil:"",
        upimg:{}
      }
    },
    methods:{
      refresh:function (){
        this.hrefcardinfo=[]
        this.hrefnum=0
        var valid=true
        if(valid){
          this.$axios.get(
            this.common.serveraddress+"/socialhref/get?userid="+this.common.userinfo.id).then(
            res=>{
              if(res.data.code==200){
                this.common.hrefinfo=res.data.data;
                for(var i=0;i<this.common.hrefinfo.length;i++){
                  var x=this.common.hrefinfo[i]
                  var temp=true;
                  var cc="http://caesar216.usa3v.net/caelog/images/tool/selected.png";
                  if(x.ifuse==0){
                    temp=false
                    var cc="http://caesar216.usa3v.net/caelog/images/tool/nonselected.png";
                  }
                  this.hrefcardinfo.push(
                    {
                      picsrc:x.imgsrc,
                      href:x.href,
                      comments:x.comment,
                      checked:temp,
                      hrefid:x.hrefid,
                      checksrc:cc
                    }
                  )
                }
                this.hrefnum=this.common.hrefinfo.length;
                this.pagenumsinfo.sum=Math.ceil(this.hrefnum/6)
              }
              else{
                alert(res.data.msg)
              }
            })
        }
        else{

        }
      },
      deletehr:function (id){
        console.log(id)
        var valid=true
        if(valid){
          this.$axios.get(
            this.common.serveraddress+"/socialhref/delete?userid="+this.common.userinfo.id+"&hrefid="+id).then(
            res=>{
              if(res.data.code==200){
                console.log(res.data.msg)
                this.refresh();
              }
              else{
                alert(res.data.msg)
              }
            })
        }
        else{
        }
      },
      changevisable:function (id,ifuse){
        console.log(id)
        var valid=true
        if(valid){
          this.$axios.get(
            this.common.serveraddress+"/socialhref/changevisable?userid="+this.common.userinfo.id+"&hrefid="+id+"&ifuse="+ifuse).then(
            res=>{
              if(res.data.code==200){
                console.log(res.data.msg)
                this.refresh();
              }
              else{
                alert(res.data.msg)
              }
            })
        }
        else{
        }
      },
      uploadFile:function (url, data) {
        let config = {
          url: url,
          baseURL: this.common.serveraddress,
          transformResponse: [function (data1) {
            var data = data1;
            if (typeof data1 == "string") {
              data = JSON.parse(data1);
            }
            if (data.message && (data.data === 'login.invalid.token')) {
              window.localStorage.removeItem("access-user");
              alert("超时请重新登陆");
              window.location.href = '/';
            }
            return data;
          }],
          headers: {'Content-Type': "multipart/form-data"},
          withCredentials: true,
          responseType: 'json',
        };
        return this.$axios.post(url, data, config);
      },
      uploadpic:function(e){
        let file = e.target.files[0]
        let param = new FormData()  // 创建form对象
        param.append('file', file)  // 通过append向form对象添加数据
        param.append('userid',this.common.userinfo.id)
        this.uploadFile("/files/upload",param).then(res=>{
          console.log(res.data)
          this.uploadurl=this.common.getserveraddress+res.data.data;
          this.picsrc=this.uploadurl;})
      },
      getObjectURL(file) {
        var url = null ;
        if (window.createObjectURL!=undefined) { // basic
          url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
          url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
          url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
      },
      submit:function (e){
        let param = new FormData()  // 创建form对象
        param.append('imgsrc', this.uploadurl)  // 通过append向form对象添加数据
        param.append('userid',this.common.userinfo.id)
        param.append("href",this.hrefcontent)
        param.append("comment",this.commentcontent)
        this.uploadFile("/socialhref/add",param).then(res=>{
          console.log(res.data)
          this.refresh()}
      )
      }
    },
    components:{
      hc:hrefcard,
      pn:pagenums
    },
    mounted() {
      this.refresh()
    }
  }
</script>

<style scoped>
.box{
  width:721px;
  height:650px;
  border-style: ridge;
}
#titlebox{
  width:673px;
  height: 111px;
  border-style: ridge;
  position: absolute;
  left: 17px;
  top:18px;
}
#pic{
  width: 80px;
  height: 80px;
  position: absolute;
  top:26px;
  left:35px;
  opacity: 0;
  z-index: 10;
  background-color:transparent !important;
}
#headchange{
  width: 80px;
  height: 80px;
  position: absolute;
  top:16px;
  left:25px;
  border-radius: 50%;
}
#hreftitle{
  width:40px;
  height: 25px;
  position: absolute;
  top:42px;
  left:140px;
  line-height: 25px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#hrefcontent{
  width:220px;
  height: 30px;
  position: absolute;
  top:37px;
  left:190px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
#commentstitle{
  width:40px;
  height: 25px;
  position: absolute;
  top:42px;
  left:440px;
  line-height: 25px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#commentscontent{
  width:160px;
  height: 30px;
  position: absolute;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  top:37px;
  left:490px;
}
#confirm{
  width:60px;
  height: 25px;
  position: absolute;
  top:80px;
  right:10px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#announcement{
  width: 673px;
  height: 47px;
  border-style: ridge;
  position: absolute;
  top:154px;
  left:17px;
  line-height: 47px;
  font-size: 22px;
  font-family: 华光楷体_CNKI;
}
#hc1{
  position: absolute;
  top:233px;
  left:17px;
}
#hc2{
  position: absolute;
  top:290px;
  right:17px;
}
#hc3{
  position: absolute;
  top:347px;
  left:17px;
}
#hc4{
  position: absolute;
  top:404px;
  right:17px;
}
#hc5{
  position: absolute;
  top:461px;
  left:17px;
}
#hc6{
  position: absolute;
  top:518px;
  right:17px;
}
#pagenums{
  position: absolute;
  top:585px;
  left:65px;
}
</style>
