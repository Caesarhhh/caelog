<template>
  <div class="articleedit">
    <div id="empty">
      <div id="buttonbox">
        <div id="mainhref" @click="topage('/mainpage')">首页</div>
        <div id="backhref" @click="topage('/backstage')">后台</div>
      </div>
      <div id="markdowntitle">markdown</div>
      <textarea id="markdownarea" v-model="innerhtmlinput" @keyup="mdSwitch"></textarea>
      <div id="showtitle">预览</div>
      <div :class="{showarea:count>-2}">
        <GeminiScrollbar>
          <div id="gsd">
            <div id="showhtml"></div>
          </div>
        </GeminiScrollbar>
      </div>
      <div id="bot">
        <div id="settingbox">
          <div id="titlesetting">
            <div id="titletitle">标题定义</div>
            <input type="text" id="titleinput" v-model="titleinput"></input>
          </div>
          <div id="blockbox">
            <div id="blocktitle">模块选择</div>
            <select id="blockselect" v-model="selected">
              <option v-for="(item,index) in blocks" :value="index">{{ item }}</option>
            </select>
            <div id="createblockarea">
              <div id="createtitle">新建板块</div>
              <form action="">
                <input id="newblockinput" name="multipartFile" type="file" @change="uploadpic" enctype="multipart/form-data">
              </form>
              <img :src="newimgsrc" id="newblockimg" />
              <input id="newinput" type="text" v-model="newblockname">
              <input id="newinputcomment" type="text" v-model="newblockcomment">
              <div id="newconfirm" @click="addblock">确认</div>
            </div>
            <div id="resultblock">{{ blocks[selected] }}</div>
          </div>
          <div id="labelsetting">
            <div id="labeltitle">标签定义</div>
            <input id="labelinput" v-model="labelinput"></input>
            <div id="labelconfirm" @click="addlabel">添加</div>
          </div>
          <div id="labels" class="clearfix">
            <lc class="labelcard" v-for="item in labelcardinfo" :datas="item"></lc>
          </div>
        </div>
        <div class="submit" @click="submit" v-if="ifnew">发表</div>
        <div class="submit" @click="submit_old" v-if="!ifnew">完成修改</div>
      </div>
    </div>
  </div>
</template>

<script>
  import labelcard from "./labelcard/labelcard";
  import GeminiScrollbar from 'vue-gemini-scrollbar'
  import Vue from "vue";
  Vue.use(GeminiScrollbar);
  var labelcardinfo = [
  ]
  export default {
    name: "articleedit",
    props: ["datas"],
    data() {
      return {
        blocks:[],
        originlabels:[],
        id:[],
        selected: "",
        labelcardinfo: labelcardinfo,
        labelss:{},
        labelinput: "",
        count:-1,
        ifnew:true,
        innerhtmlinput:"",
        newblockname:"板块名称",
        newblockcomment:"在此输入板块备注",
        titleinput:"",
        newimgsrc:"http://caesar216.usa3v.net/caelog/images/tool/uploadpic.png",
      }
    },
    components: {
      lc: labelcard
    },
    methods: {
      topage(s){
        this.$router.push({path:"/"+this.common.loginuserinfo.id+s})
      },
      addlabel: function () {
        var temp = {
          label: this.labelinput,
          index: this.labelcardinfo.length
        }
        this.labelcardinfo.push(temp);
        this.labelinput = "";
      },
      mdSwitch: function () {
        var mdValue = document.getElementById("markdownarea").value;
        var converter = new showdown.Converter();
        var html = converter.makeHtml(mdValue);
        document.getElementById("showhtml").innerHTML = html;
        this.count*=-1;
      },
      refresh_blocks:function (){
        this.blocks=[{label:"",id:""}]
        this.$axios.get(
          this.common.serveraddress+"/blocks/get?userid="+this.common.loginuserinfo.id).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
                var label=res.data.data[i].name_
                var id_=res.data.data[i].id
              this.$set(this.blocks,i,label)
              this.$set(this.id,i,id_)
            }
          })
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
          this.newimgsrc=this.common.getserveraddress+res.data.data})
      },
      addblock:function (){
        let param = new FormData()  // 创建form对象
        param.append('imgsrc', this.newimgsrc)  // 通过append向form对象添加数据
        param.append('userid',this.common.userinfo.id)
        param.append("name_",this.newblockname)
        param.append("remark",this.newblockcomment)
        this.uploadFile("/blocks/add",param).then(res=>{
          console.log(res.data)
          this.refresh_blocks()})
      },
      subset(a,b){
        return a.filter(function(i) {return b.indexOf(i) < 0;})
      },
      addarlabel:function (articleid,pos){
        if(pos>=this.labelcardinfo.length){
          return
        }
        let param = new FormData()
        param.append('name_', this.labelcardinfo[pos].label)
        param.append('userid',this.common.userinfo.id)
        param.append('articleid',articleid)
        this.uploadFile("/labels/add",param).then(res=>{
          if(res.data.code==200){
            this.addarlabel(articleid,pos+1)
          }})
      },
      addarlabel_old:function (articleid,pos){
        if(pos>=this.originlabels.length){
          return
        }
        let param = new FormData()
        param.append('name_', this.originlabels[pos].label)
        param.append('userid',this.common.userinfo.id)
        param.append('articleid',articleid)
        this.uploadFile("/labels/add",param).then(res=>{
          if(res.data.code==200){
            this.addarlabel_old(articleid,pos+1)
          }})
      },
      deletelabel:function (set){
        let param = new FormData()
        for(var i=0;i<set.length;i++){
        param.append('userid',this.common.userinfo.id)
        param.append('id',this.labelss[set[i].label])
        this.uploadFile("/labels/delete",param).then(res=>{
          if(res.data.code==200){
          }}
          )
        }
      },
      submit:function (){
        let param = new FormData()
        param.append('content', this.innerhtmlinput)
        param.append('userid',this.common.loginuserinfo.id)
        param.append('title',this.titleinput)
        param.append('blockid',this.id[this.selected])
        this.uploadFile("/article/add",param).then(res=>{
          this.addarlabel(res.data.data.id,0)
          if(res.data.code==200){
            alert("发表成功！")
          }})
        let params = new FormData()
        let change_content=this.common.loginuserinfo.nickname+"发布了文章《"+this.titleinput+"》"
        params.append('content_',change_content )
        params.append('userid',this.common.loginuserinfo.id)
        this.uploadFile("/change/add",params).then(res=>{
          if(res.data.code==200){
          }})
      },
      submit_old:function (){
        let param = new FormData()
        param.append('content', this.innerhtmlinput)
        param.append('userid',this.common.loginuserinfo.id)
        param.append('title',this.titleinput)
        param.append('blockid',this.id[this.selected])
        param.append('id',this.$route.params.articleid)
        this.uploadFile("/article/update",param).then(res=>{
          this.deletelabel(this.subset(this.originlabels,this.labelcardinfo))
          this.originlabels=this.subset(this.labelcardinfo,this.originlabels)
          this.addarlabel_old(this.$route.params.articleid,0)
          if(res.data.code==200){
            alert("修改成功！")
          }})
        let params = new FormData()
        let change_content=this.common.loginuserinfo.nickname+"修改了文章《"+this.titleinput+"》"
        params.append('content_',change_content )
        params.append('userid',this.common.loginuserinfo.id)
        this.uploadFile("/change/add",params).then(res=>{
          if(res.data.code==200){
          }})
      },
      closelb:function (index){
        this.labelcardinfo.splice(index,1)
        for(var i=index;i<this.labelcardinfo.length-1;i++){
          this.labelcardinfo[i].index--;
        }
      },
      isEmptyObject:function (obj){
        for (var n in obj) {
          return false
        }
        return true;
      },
      getlabels:function (){
        this.labelcardinfo=[]
        this.originlabels=[]
        if(!this.isEmptyObject(this.$route.params)){
          this.ifnew=false
          this.$axios.get(
            this.common.serveraddress+"/article/getone?userid="+this.common.loginuserinfo.id+"&id="+this.$route.params.articleid).then(
            res=>{
              var temp=res.data.data[0]
              this.innerhtmlinput=temp.content
              this.titleinput=temp.title
              this.selected=temp.blockid
              this.mdSwitch()
            })
          this.$axios.get(
            this.common.serveraddress+"/labels/getar?userid="+this.common.loginuserinfo.id+"&articleid="+this.$route.params.articleid).then(
            res=>{
              for(var i =0;i<res.data.data.length;i++){
                var temp={
                  label:res.data.data[i].name_,
                  index: this.labelcardinfo.length
                }
                this.labelcardinfo.push(temp)
                this.labelss[res.data.data[i].name_]=res.data.data[i].id
                this.originlabels.push(temp)
              }
            })
        }
      }
    },
    mounted() {
      if(((JSON.stringify(this.common.loginuserinfo)==='{}')||this.common.loginuserinfo==null)){
        this.$router.push("/login")
      }
      if(this.common.loginuserinfo.id!=this.$route.params.userid){
        alert("您无权访问该用户编辑文章页！")
        location.go(-1)
      }
      this.refresh_blocks()
      this.getlabels()
    }
  }
</script>

<style scoped>
  @import 'github-markdown.css';

  .articleedit {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: relative;
    margin-top: 0px;
    width: 1024px;
  }

  .clearfix:after {
    display: block;
    clear: both;
    content: "";
    visibility: hidden;
    height: 0;
  }

  .clearfix {
    zoom: 1
  }

  #empty {
    position: relative;
    margin-top: 0px;
    margin-left: 0px;
  }

  #buttonbox {
    width: 720px;
    height: 60px;
    border-style: ridge;
    position: absolute;
    top: 13px;
    left: 152px;
  }

  #mainhref {
    width: 70px;
    height: 45px;
    position: absolute;
    top: 6px;
    border-style: ridge;
    line-height: 45px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    left: 15px;
  }

  #backhref {
    width: 70px;
    height: 45px;
    position: absolute;
    top: 6px;
    border-style: ridge;
    line-height: 45px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    left: 100px;
  }

  #markdowntitle {
    width: 480px;
    height: 40px;
    position: absolute;
    border-style: ridge;
    line-height: 40px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    font-weight: bold;
    top: 80px;
    left: 25px;
  }

  #showtitle {
    width: 480px;
    height: 40px;
    position: absolute;
    border-style: ridge;
    line-height: 40px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    font-weight: bold;
    top: 80px;
    left: 519px;
  }

  #markdownarea {
    width: 478px;
    height: 777px;
    position: absolute;
    top: 130px;
    left: 25px;
    font-size: 18px;
  }

  .showarea {
    width: 480px;
    height: 777px;
    border-style: ridge;
    position: absolute;
    top: 130px;
    left: 519px;
  }

  #showhtml {
    width: 682px;
    height: auto;
    position: absolute;
    text-align: left;
    top: 0px;
  }

  .gs {
    width:480px;
  }

  #gsd {
    width: 480px;
    height: 777px;
  }

  #bot {
    width: 1000px;
    height: auto;
    position: absolute;
    left: 25px;
    top: 936px;
  }

  #settingbox {
    width: 974px;
    height: auto;
    position: relative;
    border-style: ridge;
    left: 0px;
    top: 0px;
  }

  #titlesetting{
    width: 946px;
    height: 58px;
    border-style: ridge;
    position: absolute;
    left: 14px;
    top: 8px;
  }

  #blockbox {
    width: 946px;
    height: 103px;
    border-style: ridge;
    position: absolute;
    left: 14px;
    top: 78px;
  }

  #titletitle {
    width: 112px;
    height: 33px;
    border-style: ridge;
    line-height: 33px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
    position: absolute;
    left: 13px;
    top: 10px;
  }

  #titleinput {
    width: 680px;
    height: 40px;
    position: absolute;
    left: 142px;
    top: 5px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
  }

  #blocktitle {
    width: 112px;
    height: 33px;
    border-style: ridge;
    line-height: 33px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
    position: absolute;
    left: 13px;
    top: 12px;
  }

  #blockselect {
    width: 505px;
    height: 30px;
    position: absolute;
    top: 14px;
    left: 171px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #createblockarea {
    width: 518px;
    height: 39px;
    border-style: ridge;
    position: absolute;
    left: 164px;
    top: 51px;
  }

  #resultblock {
    width: 120px;
    height: 60px;
    border-style: ridge;
    line-height: 60px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    position: absolute;
    top: 32px;
    right: 110px;
  }

  #createtitle {
    width: 74px;
    height: 32px;
    border-style: ridge;
    line-height: 32px;
    position: absolute;
    top: 2px;
    left: 2px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #newblockinput{
    position: absolute;
    top: 0px;
    width:32px;
    height: 32px;
    border-radius: 50%;
    left:84px;
    z-index: 10;
    opacity: 0;
  }

  #newblockimg{
    position: absolute;
    top: 3px;
    width:32px;
    height: 32px;
    left:84px;
  }
  #newinputcomment{
    width: 220px;
    height: 30px;
    position: absolute;
    top: 2px;
    left: 221px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
    z-index: 12;
  }

  #newinput {
    width: 90px;
    height: 30px;
    position: absolute;
    top: 2px;
    left: 124px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
    z-index: 12;
  }

  #newconfirm {
    width: 60px;
    height: 32px;
    border-style: ridge;
    line-height: 32px;
    position: absolute;
    top: 2px;
    right: 2px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #labelsetting {
    width: 946px;
    height: 56px;
    border-style: ridge;
    position: relative;
    margin-top: 198px;
    margin-bottom:10px;
    margin-left: 14px;
  }

  #labeltitle {
    width: 112px;
    height: 33px;
    border-style: ridge;
    line-height: 33px;
    position: absolute;
    left: 11px;
    top: 10px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
  }

  #labelinput {
    width: 719px;
    height: 40px;
    position: absolute;
    left: 132px;
    top: 5px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
  }

  #labelconfirm {
    width: 80px;
    height: 48px;
    border-style: ridge;
    line-height: 48px;
    position: absolute;
    right: 3px;
    top: 3px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
  }

  #labels {
    width: 946px;
    height: auto;
    position: relative;
    margin-top: 10px;
    margin-left: 14px;
    margin-bottom: 5px;
  }

  .labelcard {
    float: left;
    margin-left: 20px;
    margin-top: 10px;
    margin-bottom: 10px;
  }

  .submit {
    width: 140px;
    height: 70px;
    border-style: ridge;
    line-height: 70px;
    font-family: 华光楷体_CNKI;
    font-size: 35px;
    position: relative;
    margin-top: 80px;
    margin-left: 420px;
    margin-bottom: 50px;
  }
</style>
