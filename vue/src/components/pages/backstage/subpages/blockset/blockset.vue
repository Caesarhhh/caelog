<template>
<div class="box2" :style="getcolor1()">
  <div id="settingbox">
    <form action="">
      <input id="pic" name="multipartFile" type="file" @change="uploadpic" enctype="multipart/form-data">
    </form>
    <img id="headchange" :src="settingpicsrc" alt="error">
    <div id="nametitle" :style="getcolor4()">板块名称</div>
    <input id="nameinput" v-model="inputname"></input>
    <div id="commentstitle" :style="getcolor4()">备注</div>
    <input id="commentsinput" v-model="inputremark"></input>
    <div id="addbutton" :style="getcolor3()" @click="submit">添加</div>
  </div>
  <div id="contentbox">
    <pn id="pagenums" :datas.sync="pagenumsinfo"></pn>
    <div id="line"></div>
    <bsb id="bsb1" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12]" v-if="(pagenumsinfo.pos-1)*12<sum"></bsb>
    <bsb id="bsb2" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+1]" v-if="(pagenumsinfo.pos-1)*12+1<sum"></bsb>
    <bsb id="bsb3" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+2]" v-if="(pagenumsinfo.pos-1)*12+2<sum"></bsb>
    <bsb id="bsb4" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+3]" v-if="(pagenumsinfo.pos-1)*12+3<sum"></bsb>
    <bsb id="bsb5" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+4]" v-if="(pagenumsinfo.pos-1)*12+4<sum"></bsb>
    <bsb id="bsb6" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+5]" v-if="(pagenumsinfo.pos-1)*12+5<sum"></bsb>
    <bsb id="bsb7" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+6]" v-if="(pagenumsinfo.pos-1)*12+6<sum"></bsb>
    <bsb id="bsb8" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+7]" v-if="(pagenumsinfo.pos-1)*12+7<sum"></bsb>
    <bsb id="bsb9" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+8]" v-if="(pagenumsinfo.pos-1)*12+8<sum"></bsb>
    <bsb id="bsb10" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+9]" v-if="(pagenumsinfo.pos-1)*12+9<sum"></bsb>
    <bsb id="bsb11" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+10]" v-if="(pagenumsinfo.pos-1)*12+10<sum"></bsb>
    <bsb id="bsb12" :datas="blocksetboxinfo[(pagenumsinfo.pos-1)*12+11]" v-if="(pagenumsinfo.pos-1)*12+11<sum"></bsb>
  </div>
</div>
</template>

<script>
  import pagenums from "../../../../tools/pagenums/pagenums";
  import blocksetbox from "./blocksetbox/blocksetbox";
  var pagenumsinfo={
    sum:1,
    pos:1,
    pagesize:12
  }
  var blocksetboxinfo=[]
  export default {
    name: "blockset",
    data(){
      return{
        settingpicsrc:this.common.getserveraddress+"images/tool/uploadpic.png",
        uploadurl:"",
        pagenumsinfo:pagenumsinfo,
        blocksetboxinfo:blocksetboxinfo,
        sum:0,
        inputname:"",
        inputremark:"",
        lock:true
      }
    },
    props:["datas"],
    components:{
      pn:pagenums,
      bsb:blocksetbox
    },
    methods:{
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3(){
        return {
          backgroundColor:this.$store.state.color3
        }
      },
      getcolor4(){
        return {
          backgroundColor:this.$store.state.color4
        }
      },
      refresh:function (){
        this.hrefcardinfo=[]
        this.hrefnum=0
        var valid=true
        if(valid){
          this.$axios.get(
            this.common.serveraddress+"/blocks/get?userid="+this.common.loginuserinfo.id).then(
            res=>{
              if(res.data.code==200){
                this.common.blocksinfo=res.data.data;
                for(var i=0;i<this.common.blocksinfo.length;i++){
                  var x=this.common.blocksinfo[i]
                  var temp={
                    imgsrc:x.imgsrc,
                    name:x.name_,
                    id:x.id,
                    comments: x.remark
                  }
                  this.$set(this.blocksetboxinfo,i,temp)
                }
                this.sum=this.common.blocksinfo.length;
                this.pagenumsinfo.sum=this.sum
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
        param.append('userid',this.common.loginuserinfo.id)
        this.uploadFile("/files/upload",param).then(res=>{
          this.uploadurl=res.data.data.url;
          this.settingpicsrc=this.uploadurl;})
      },
      submit:function (e){
        let param = new FormData()  // 创建form对象
        param.append('imgsrc', this.uploadurl)  // 通过append向form对象添加数据
        param.append('userid',this.common.loginuserinfo.id)
        param.append("name_",this.inputname)
        param.append("remark",this.inputremark)
        this.uploadFile("/blocks/add",param).then(res=>{
          console.log(res.data)
          this.refresh()})
      },
      deletebl:function (id){
        var valid=confirm("删除板块会导致删除板块下所有文章，确定删除吗？")
        if(valid){
          this.$axios.get(
            this.common.serveraddress+"/blocks/delete?userid="+this.common.loginuserinfo.id+"&id="+id).then(
            res=>{
              this.refresh();
              if(res.data.code==200){
              }
              else{
                alert(res.data.msg)
              }
            })
        }
        else{
        }
      },
    },
    mounted() {
      this.refresh()
    }
  }
</script>

<style scoped>
.box2{
  width:721px;
  height:631px;
}
#settingbox{
  width: 673px;
  height: 111px;
  position: absolute;
  top:18px;
  left: 22px;
}
#pic{
  width: 80px;
  height: 80px;
  position: absolute;
  top:26px;
  left:15px;
  opacity: 0;
  z-index: 10;
  background-color:transparent !important;
}
#headchange{
  width: 80px;
  height: 80px;
  position: absolute;
  top:16px;
  left:15px;
  border-radius: 50%;
}
#settingpic img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
#nametitle{
  width:40px;
  height: 48px;
  position: absolute;
  top:34px;
  left:100px;
  font-family: 华光楷体_CNKI;
  font-size: 17px;
  border-radius: 5px;
}
#nameinput{
  width:193px;
  height: 38px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:32px;
  left:145px;
  font-family: 华光楷体_CNKI;
  border-radius: 10px;
  border-width: thin;
  border-style: solid;
  outline: none;
}
#nameinput:focus{
  top:31px;
  left:144px;
  border-width: 2px;
}
#commentstitle{
  width:33px;
  height: 48px;
  position: absolute;
  top:35px;
  left:370px;
  font-family: 华光楷体_CNKI;
  font-size: 17px;
  border-radius: 10px;
}
#commentsinput{
  width:193px;
  height: 38px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:32px;
  left:406px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
  border-radius: 10px;
  border-width: thin;
  border-style: solid;
  outline: none;
}
#commentsinput:focus{
  top:31px;
  left:405px;
  border-width: 2px;
}
#addbutton{
  width: 97px;
  height: 28px;
  position: absolute;
  bottom:3px;
  right: 8px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  line-height: 28px;
  border-radius: 3px;
  cursor: pointer;
}
#contentbox{
  width: 673px;
  height: 446px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:156px;
  left: 22px;
}
#pagenums{
  position: absolute;
  left:5%;
  bottom: 10px;
  width: 90%;
}
#line{
  width:1px;
  height:340px;
  background-color: black;
  position: absolute;
  top:20px;
  left:340px;
}
#bsb1{
  position: absolute;
  top: 24px;
  left: 24px;
}
#bsb2{
  position: absolute;
  top: 80px;
  left: 24px;
}
#bsb3{
  position: absolute;
  top: 136px;
  left: 24px;
}
#bsb4{
  position: absolute;
  top: 192px;
  left: 24px;
}
#bsb5{
  position: absolute;
  top: 248px;
  left: 24px;
}
#bsb6{
  position: absolute;
  top: 314px;
  left: 24px;
}
#bsb7{
  position: absolute;
  top: 24px;
  left: 365px;
}
#bsb8{
  position: absolute;
  top: 80px;
  left: 365px;
}
#bsb9{
  position: absolute;
  top: 136px;
  left: 365px;
}
#bsb10{
  position: absolute;
  top: 192px;
  left: 365px;
}
#bsb11{
  position: absolute;
  top: 248px;
  left: 365px;
}
#bsb12{
  position: absolute;
  top: 314px;
  left: 365px;
}
</style>
