<template>
<div class="box2">
  <div id="pagehrefbox" :style="getcolor1()">
    <div class="pagebutton" :style="getcolor1()" @click="topage('/mainpage')">首页</div>
    <div class="pagebutton" :style="getcolor1()" @click="topage('/backstage')" v-if="ifloginsame">后台</div>
  </div>
  <div id="readcontent" :style="getcolor1()">
    <div id="title">{{title}}</div>
    <div id="showarea">
      <GeminiScrollbar>
        <div id="gsd">
          <div id="content"></div>
        </div>
      </GeminiScrollbar>
    </div>
  </div>
  <pc id="personalcard" :datas="pcpos"></pc>
  <st id="searchtool_ar"></st>
  <div id="blockbox_ar" :style="getcolor1()">
    <lb id="blocks" :datas="blocklabels"></lb>
    <lb id="labels" :datas="labellabels"></lb>
  </div>
  <div id="buttonbox">
    <img :src="this.good==-1?nogoodimgsrc:goodimgsrc" alt="error" @click="toglegood">
    <img :src="transmitimgsrc" alt="error">
  </div>
  <div id="commentarea" :style="getcolor1()">
    <div id="editcomment">
      <div id="editImgsrc"><img :src="headimgsrc" alt="未登录"></div>
      <input id="editinput" v-model="inputtext"></input>
      <button id="editbutton" @click="addcard">发表</button>
    </div>
    <cc class="commentCard clearfix" :datas.sync="commentCardinfo[(pagenumsinfo.pos-1)*3]" :replyinfo.sync="replyinfo" :allcomments="commentCardinfo" v-if="(pagenumsinfo.pos-1)*3<commentnum"></cc>
    <cc class="commentCard clearfix" :datas.sync="commentCardinfo[(pagenumsinfo.pos-1)*3+1]" :replyinfo.sync="replyinfo" :allcomments="commentCardinfo" v-if="(pagenumsinfo.pos-1)*3+1<commentnum"></cc>
    <cc class="commentCard clearfix" :datas.sync="commentCardinfo[(pagenumsinfo.pos-1)*3+2]" :replyinfo.sync="replyinfo" :allcomments="commentCardinfo" v-if="(pagenumsinfo.pos-1)*3+2<commentnum"></cc>
    <pn id="pagenums" :datas.sync="pagenumsinfo"></pn>
    <rtl id="replytool" :commentinfo.sync="commentCardinfo" :datas="replyinfo" v-if="replyinfo.ifreply"></rtl>
  </div>
</div>
</template>

<script>
  import personalCard from "../../tools/personalCard/personalCard";
  import searchtool from "../../tools/searchtool/searchtool";
  import labelbox from "../../tools/labelbox/labelbox";
  import commentCard from "./commentCard/commentCard";
  import pagenums from "../../tools/pagenums/pagenums";
  import replytool from "../../wins/replytool/replytool";
  import GeminiScrollbar from 'vue-gemini-scrollbar';
  import Vue from "vue";
  Vue.use(GeminiScrollbar);
  var blocklabels={
    labels:{},
    title:"板块",
    type:'block'
  }
  var labellabels={
    labels:["机器学习","前端","后端","心情"],
    title:"标签",
    type:'label'
  }
  var commentCardinfo=[]
  var replyinfo={
    id:0,
    headimgsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
    nickname:"Luffy",
    name:"Luffy",
    ifreply:false,
    ccindex:0,
    commentid:0,
    targetid:0
  }
  const s = document.createElement('script');
  export default {
    name: "articlereadpage",
    props:["datas"],
    data(){
      return{
        iflog:false,
        blocklabels:blocklabels,
        labellabels:labellabels,
        transmitimgsrc:"http://caesar216.usa3v.net/caelog/images/transmit.png",
        goodimgsrc:"http://caesar216.usa3v.net/caelog/images/good.png",
        nogoodimgsrc:"http://caesar216.usa3v.net/caelog/images/nogood.png",
        title:"",
        good:-1,
        headimgsrc:"",
        commentCardinfo:commentCardinfo,
        commentCardinfo_:[],
        pagenumsinfo:{sum:1,pos:1},
        commentnum:0,
        commentnum_:0,
        replyinfo:replyinfo,
        nickname: "Caesar",
        inputtext:"",
        bigsum:4,
        pcpos:{pos:'right'},
        ifloginsame:false
      }
    },
    created() {
      this.title=this.$route.params.articleid;
    },
    methods:{
      topage:function (s){
        this.$router.push("/"+this.$route.params.userid+s);
      },
      toglegood:function (){
        if(!this.iflog){
          alert("请先登录")
          return
        }
        if(this.good==-1){
          this.$axios.get(
            this.common.serveraddress+"/action/add?actorid="+this.common.loginuserinfo.id+"&targetid="+this.common.userinfo.id+"&type_=articlegood"+"&objectid="+this.$route.params.articleid).then(
            res=>{
              this.$axios.get(this.common.serveraddress+"/article/changegood?userid="+this.common.userinfo.id+"&id="+this.$route.params.articleid+"&count=1").then(
                res=>{
                  this.good=1
                }
              )
            })
        }
        else{
          this.$axios.get(
            this.common.serveraddress+"/action/delete?actorid="+this.common.loginuserinfo.id+"&targetid="+this.common.userinfo.id+"&type_=articlegood"+"&objectid="+this.$route.params.articleid).then(
            res=>{
              this.$axios.get(this.common.serveraddress+"/article/changegood?userid="+this.common.userinfo.id+"&id="+this.$route.params.articleid+"&count=-1").then(
                res=>{
                  this.good=-1
                }
              )
            })
        }
      },
      refresh_replyinfo:function (){
        this.replyinfo.id=this.common.loginuserinfo.id
        this.replyinfo.headimgsrc=this.common.loginuserinfo.backimgsrc
        this.replyinfo.nickname=this.common.loginuserinfo.nickname
        this.replyinfo.name=this.common.loginuserinfo.nickname
        this.replyinfo.freply=false
        this.replyinfo.ccindex=0
        this.replyinfo.commentid=0

      },
      addcard:function (){
        if(!this.iflog){
          alert("请先登录")
          return
        }
        if (this.inputtext.match(/^[ ]*$/)||this.inputtext==null||this.inputtext==undefined) {
          alert("评论内容不能为空")
          return
        }
        let param = new FormData()
        param.append('userid',this.common.userinfo.id)
        param.append('content_',this.inputtext)
        param.append('articleid',this.$route.params.articleid)
        param.append('actorid',this.common.loginuserinfo.id)
        this.uploadFile("/comment/add",param).then(async res=>{
          await this.getcomments()
          this.commentCardinfo=this.commentCardinfo_
          this.commentnum=this.commentnum_
        })
      },
      showtexthtml: function (x) {
        var mdValue=x;
        var converter = new showdown.Converter();
        var html = converter.makeHtml(mdValue);
        document.getElementById("content").innerHTML = html;
        this.count*=-1;
      },
      deletecard:function (pos){
        var temp=confirm("确定删除吗？");
        if(temp){
          this.commentCardinfo.splice(pos,1);
          this.commentnum-=1;
          if(this.commentnum%3==0){
            this.pagenumsinfo.sum-=1;
          }
          if(this.pagenumsinfo.sum<this.pagenumsinfo.pos){
            this.pagenumsinfo.pos-=1;
          }
        }
      },
      deletecomment:function (indexx,pos){
        var temp=confirm("确定删除吗？");
        commentCardinfo.forEach(
          (item,index,array)=>{
            if(item.index==indexx){
              indexx=index;
            }
          }
        )
        if(temp){
          commentCardinfo[indexx].addedcontent.splice(pos,1);
          commentCardinfo[indexx].addednicknames.splice(pos,1);
          commentCardinfo[indexx].addedsrc.splice(pos,1);
          commentCardinfo[indexx].ifgood.splice(pos,1);
          commentCardinfo[indexx].goodnums.splice(pos,1);
          commentCardinfo[indexx].addednum-=1;
        }
      },
      getbls(){
        this.labellabels.labels=[]
        this.$axios.get(
          this.common.serveraddress+"/labels/get?userid="+this.$route.params.userid).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
              var temp=res.data.data[i]
              this.$set(this.labellabels.labels,i,temp.name_)
            }
          })
        this.blocklabels.labels=[]
        this.$axios.get(
          this.common.serveraddress+"/blocks/get?userid="+this.$route.params.userid).then(
          res=>{
            for(var i=0;i<res.data.data.length&&i<15;i++){
              var temp=res.data.data[i]
              this.$set(this.blocklabels.labels,temp.id,temp.name_)
            }
          })
      },
      getarticle(){
        this.$axios.get(
          this.common.serveraddress+"/article/getone?userid="+this.$route.params.userid+"&id="+this.$route.params.articleid).then(
          res=>{
              var temp={
                title:res.data.data.title,
                time:res.data.data.time_.substring(2,10),
                content:res.data.data.content,
                block:blocklabels[res.data.data.blockid],
                id:res.data.data.id
              }
              this.title=temp.title
              this.showtexthtml(temp.content)
          })
      },
      refreshcomment:async function (){
        await this.getcomments()
        this.commentCardinfo=this.commentCardinfo_
        this.commentnum=this.commentnum_
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
      ifgood:function (actorid,targetid,objectid){
        let param = new FormData()
        param.append('targetid',targetid)
        param.append('actorid',actorid)
        param.append('type_',"666")
        param.append('objectid',objectid)
        var result=1
        this.uploadFile("/action/ifin",param).then(res=>{
          if(res.data.code==400){
            result=-1
            return result
          }
          else{
            result=1
            return result
          }
        })
      },
      getcomments: function (){
        this.commentCardinfo_=[]
        this.commentnum_=0
        this.$axios.get(
          this.common.serveraddress+"/comment/get?userid="+this.common.userinfo.id+"&articleid="+this.$route.params.articleid).then(
          async res=> {
            for(var i =0;i<res.data.data.length;i++){
              var temp={
                id:res.data.data[i].id,
                actorid:res.data.data[i].actorid,
                maincontent:res.data.data[i].content,
                mainsrc:"",
                index:this.commentCardinfo.length,
                nickname:"",
                addednicknames:[],
                ifmaingood:1,
                maingoodnums:res.data.data[i].goodnum,
                time:res.data.data[i].time_,
                ifgood:[],
                goodnums:[],
                addedsrc:[],
                addedcontent:[],
                addednum:0,
                addedactorid:[],
                count:0,
                addid:[],
                addtime:[]
              }
              await this.$axios.get(this.common.serveraddress+"/user/getimgsrc?userid="+temp.actorid).then(
                ress=>{
                  temp.mainsrc=ress.data.data
                }
              )
              await this.$axios.get(this.common.serveraddress+"/user/getnickname?userid="+temp.actorid).then(
                ress=>{
                  temp.nickname=ress.data.data
                }
              )
              if(this.iflog){
                let param = new FormData()
                param.append('targetid',temp.actorid)
                param.append('actorid',this.common.loginuserinfo.id)
                param.append('type_',"commentgood")
                param.append('objectid',temp.id)
              await this.uploadFile("/action/ifin",param).then(ress=>{
                if(ress.data.code==400){
                  temp.ifmaingood=-1
                }
                else{
                  temp.ifmaingood=1
                }
              })
              }
            else{
              temp.ifmaingood=-1
              }
              await this.$axios.get(
                this.common.serveraddress+"/addcomment/get?userid="+this.common.userinfo.id+"&articleid="+this.$route.params.articleid+"&commentid="+temp.id).then(
                async ress=>{
                  for(var u=0;u<ress.data.data.length;u++){
                    temp.addedcontent.push(ress.data.data[u].content)
                    temp.addednicknames.push(ress.data.data[u].name_)
                    temp.goodnums.push(ress.data.data[u].goodnum)
                    temp.addid.push(ress.data.data[u].id)
                    temp.addedactorid.push(ress.data.data[u].actorid)
                    temp.addtime.push(ress.data.data[u].time_)
                    await this.$axios.get(this.common.serveraddress+"/user/getimgsrc?userid="+ress.data.data[u].actorid).then(
                      resss=>{
                        temp.addedsrc.push(resss.data.data)
                      }
                    )
                    let param = new FormData()
                    param.append('targetid',ress.data.data[u].actorid)
                    param.append('actorid',this.common.loginuserinfo.id)
                    param.append('type_',"addcommentgood")
                    param.append('objectid',temp.addid[u])
                    if(this.iflog){
                    await this.uploadFile("/action/ifin",param).then(resss=>{
                      if(resss.data.code==400){
                        temp.ifgood.push(-1)
                      }
                      else{
                        temp.ifgood.push(1)
                      }
                    })}
                  else{
                    temp.ifgood.push(-1)
                    }
                    temp.addednum++
                  }
                })
              this.commentCardinfo_.push(temp)
              this.commentnum_++
              if(i==res.data.data.length-1){
                this.commentnum=this.commentnum_
              }
              this.pagenumsinfo.sum=Math.ceil(this.commentnum/3)
            }
          })
      },
      getifgood:function (){
          this.$axios.get(
            this.common.serveraddress+"/action/ifin?actorid="+this.common.loginuserinfo.id+"&targetid="+this.common.userinfo.id+"&type_=articlegood"+"&objectid="+this.$route.params.articleid).then(
            res=>{
              if(res.data.code==400){
                this.good=-1
              }
              else{
                this.good=1
              }
            })
      },
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      inituserinfo(userid){
        this.$axios({
          url:this.common.serveraddress+"/user/get?userid="+userid,
          method:"get"
        }).then(
          async res=>{
            this.common.userinfo=res.data.data
            if(this.iflog){
              this.getifgood()
            }
            this.getbls()
            this.getarticle()
            await this.getcomments()
            this.commentCardinfo=this.commentCardinfo_
            if(!(JSON.stringify(this.common.loginuserinfo)==='{}'||this.common.loginuserinfo==null)) {
              this.headimgsrc = this.common.loginuserinfo.backimgsrc
              this.refresh_replyinfo()
            }
          }
        )
      }
    },
    components:{
      pc:personalCard,
      st:searchtool,
      lb:labelbox,
      cc:commentCard,
      pn:pagenums,
      rtl:replytool
    },
    async mounted() {
      if((JSON.stringify(this.common.loginuserinfo)==='{}')==false&&this.common.loginuserinfo!=null){
        this.iflog=true
        if(this.common.loginuserinfo.id==this.$route.params.userid){
          this.ifloginsame=true
        }
      }
      await this.inituserinfo(this.$route.params.userid)
      let data={
        userid:this.$route.params.userid,
        id:this.$route.params.articleid,
        count:1
      }
      this.$axios.post(this.common.serveraddress+"/article/addview",data).then(res=>{
      })
    }
  }
</script>

<style scoped>
  .box2 {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: relative;
    margin-top: 0px;
    width:1024px;
  }
  #pagehrefbox{
    width:720px;
    height:60px;
    position: absolute;
    top:26px;
    left:8px;
  }
  .pagebutton{
    width: 70px;
    height: 45px;
    border-right-style: groove;
    float: left;
    margin-top: 6px;
    margin-left: 15px;
    line-height: 45px;
    font-family: 华光楷体_CNKI;
    font-size: 25px;
    cursor: pointer;
  }
  #personalcard{
    position: absolute;
    top:26px;
    left: 7px;
  }
  #searchtool_ar{
    position: absolute;
    top:333px;
    left: 732px;
  }
  #blockbox_ar{
    position: absolute;
    top:398px;
    left:734px;
    width: 280px;
    height: 600px;
  }
  #blocks{
    float:left;
    margin-top: 20px;
    margin-left: 15px;
  }
  #labels{
    float:left;
    margin-top: 25px;
    margin-left: 15px;
  }
  #buttonbox{
    width:200px;
    height: 40px;
    position: absolute;
    top:1039px;
    left: 741px;
  }
  #buttonbox img{
    width:20%;
    height: 100%;
    float: left;
    margin-top: 0px;
    margin-left: 50px;
  }
  #title{
    width:682px;
    height: 61px;
    position: absolute;
    top:0px;
    left:0px;
    font-family: 华光楷体_CNKI;
    font-size: 50px;
    line-height: 61px;
    text-align: left;
  }
  #showarea{
    width: 720px;
    height: 860px;
    position: absolute;
    left:0px;
    top:87px;
  }
  #readcontent{
    width: 720px;
    height: 958px;
    position: absolute;
    left:8px;
    top:98px;
  }
  #gsd{
    width: 682px;
    height: 880px;
  }
  #content{
    width: 682px;
    height: 880px;
    position: absolute;
    left:21px;
    text-align: left;
  }
  #commentarea{
    width: 976px;
    height: auto;
    position: absolute;
    left:21px;
    top:1112px;
  }
  #editcomment{
    width: 942px;
    height: 55px;
    border-bottom-style:groove;
    border-width: thin;
    position: relative;
    margin-top:17px;
    margin-left:17px;
  }
  #editImgsrc{
    width:40px;
    height: 40px;
    position: absolute;
    left:6px;
    top:6px;
    border-radius: 50%;
  }
  #editImgsrc img{
    width:100%;
    height: 100%;
    border-radius: 50%;
  }
  #editinput{
    width:798px;
    height:42px;
    border-style: ridge;
    position: absolute;
    top:5px;
    left:55px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
  }
  #editbutton{
    width:70px;
    height: 42px;
    position: absolute;
    top:8px;
    right: 5px;
    font-size: 20px;
    font-family: 华光楷体_CNKI;
  }
  .commentCard{
    float:left;
    margin-top: 5px;
    margin-left: 15px;
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
  #pagenums{
    float: left;
    margin-top:6px;
    margin-left:180px;
  }
  #replytool{
    position: fixed;
    top:50%;
    left: 50%;
    margin-left: -300px;
    margin-top: -200px;
    z-index: 100;
  }
</style>
