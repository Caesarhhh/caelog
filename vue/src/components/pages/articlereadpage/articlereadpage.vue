<template>
<div class="box2">
  <a-row>
    <a-col :span="18">
      <div id="readcontent" :style="getcolor4()">
        <div id="title">{{title}}</div>
        <div id="showarea">
          <GeminiScrollbar>
            <div id="gsd">
              <div id="content" v-html="content"></div>
            </div>
          </GeminiScrollbar>
        </div>
      </div>
    </a-col>
    <a-col :span="6">
      <pc id="personalcard" :datas="pcpos"></pc>
      <div id="blockbox_ar" :style="getcolor1()">
        <lb id="blocks" :datas="blocklabels"></lb>
        <lb id="labels" :datas="labellabels"></lb>
      </div>
      <div id="buttonbox">
        <img :src="this.good==-1?nogoodimgsrc:goodimgsrc" alt="error" @click="toglegood">
        <img :src="transmitimgsrc" @click="share()" alt="error">
      </div>
    </a-col>
    <div id="commentarea" :style="getcolor1()">
      <div id="editcomment">
        <div id="editImgsrc"><img :src="headimgsrc" alt="未登录"></div>
        <input id="editinput" v-model="inputtext"></input>
        <div id="editbutton" :style="getcolor3()" @click="addcard">发表</div>
      </div>
      <cc class="commentCard clearfix" :datas.sync="commentCardinfo[(pagenumsinfo.pos-1)*3]" :replyinfo.sync="replyinfo" :allcomments="commentCardinfo" v-if="(pagenumsinfo.pos-1)*3<commentnum"></cc>
      <cc class="commentCard clearfix" :datas.sync="commentCardinfo[(pagenumsinfo.pos-1)*3+1]" :replyinfo.sync="replyinfo" :allcomments="commentCardinfo" v-if="(pagenumsinfo.pos-1)*3+1<commentnum"></cc>
      <cc class="commentCard clearfix" :datas.sync="commentCardinfo[(pagenumsinfo.pos-1)*3+2]" :replyinfo.sync="replyinfo" :allcomments="commentCardinfo" v-if="(pagenumsinfo.pos-1)*3+2<commentnum"></cc>
      <pn id="pagenums" :datas.sync="pagenumsinfo"></pn>
      <rtl :add-comment="addaddedcomment" id="replytool" :commentinfo.sync="commentCardinfo" :datas="replyinfo" v-if="replyinfo.ifreply"></rtl>
    </div>
  </a-row>
</div>
</template>

<script>
  import personalCard from "../../tools/personalCard/personalCard";
  import searchtool from "../../tools/searchtool/searchtool";
  import labelbox from "../../tools/labelbox/labelbox";
  import commentCard from "./commentCard/commentCard";
  import pagenums from "../../tools/pagenums/pagenums";
  import replytool from "../../wins/replytool/replytool";
  import marked from 'marked';
  import showdown from "showdown";
  import Prism from "prismjs";
  import GeminiScrollbar from 'vue-gemini-scrollbar';
  import Vue from "vue";
  Vue.use(Prism)
  Vue.use(GeminiScrollbar);
  Prism.highlightAll()
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
  const s = document.createElement('script');
  export default {
    name: "articlereadpage",
    props:["datas"],
    data(){
      return{
        iflog:false,
        blocklabels:blocklabels,
        labellabels:labellabels,
        transmitimgsrc:this.common.getserveraddress+"images/transmit.png",
        goodimgsrc:this.common.getserveraddress+"images/good.png",
        nogoodimgsrc:this.common.getserveraddress+"images/nogood.png",
        title:"",
        good:-1,
        headimgsrc:"",
        commentCardinfo:commentCardinfo,
        commentCardinfo_:[],
        pagenumsinfo:{sum:1,pos:1,pagesize:3},
        commentnum:0,
        commentnum_:0,
        replyinfo:{
          id:0,
          headimgsrc:this.common.getserveraddress+"images/head.jpg",
          nickname:"Luffy",
          name:"Luffy",
          ifreply:false,
          ccindex:0,
          commentid:0,
          targetid:0
        },
        nickname: "Caesar",
        inputtext:"",
        bigsum:4,
        pcpos:{pos:'right'},
        ifloginsame:false,
        index:0,
        content:""
      }
    },
    created() {
      this.title=this.$route.params.articleid;
    },
    methods:{
      getcolor1(){
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2(){
        return {backgroundColor: this.$store.state.color2}
      },
      getcolor3(){
        return {backgroundColor: this.$store.state.color3}
      },
      getcolor4(){
        return {backgroundColor: this.$store.state.color4}
      },
      topage:function (s){
        this.$router.push("/"+this.$route.params.userid+s);
      },
      share(){
        this.$copyText(this.common.siteaddress+"/"+this.$route.params.userid+"/articleread/"+this.$route.params.articleid).then(res=>{
          alert("已复制文章连接到剪切板")
        })
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
      addaddedcomment(commentid){
        var index=0
        for(var i=0;i<this.commentCardinfo.length;i++){
          if(this.commentCardinfo[i].id==commentid){
            index=i
            break
          }
        }
        let temp=this.commentCardinfo[index]
        temp.addednum++
        this.$axios.get(
          this.common.serveraddress+"/addcomment/get?userid="+this.common.userinfo.id+"&articleid="+this.$route.params.articleid+"&commentid="+commentid).then(
          res=>{
            temp.addednicknames.unshift(this.common.loginuserinfo.nickname)
            temp.ifgood.unshift(-1)
            temp.addedsrc.unshift(this.common.loginuserinfo.backimgsrc)
            temp.goodnums.unshift(0)
            temp.addedcontent.unshift(res.data.data[0].content)
            temp.addedactorid.unshift(this.common.loginuserinfo.userid)
            temp.addid.unshift(res.data.data[0].id)
            temp.addtime.unshift(res.data.data[0].time_)
            this.$set(this.commentCardinfo,i,temp);
          })
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
          this.commentCardinfo=this.commentCardinfo_
          this.commentnum=this.commentnum_
          if(res.data.code==200){
            this.inputtext=""
            this.$axios.get(
              this.common.serveraddress+"/comment/get?userid="+this.common.userinfo.id+"&articleid="+this.$route.params.articleid).then(
                res=>{
                  var temp={
                    id:res.data.data[0].id,
                    actorid:res.data.data[0].actorid,
                    maincontent:res.data.data[0].content,
                    mainsrc:this.common.loginuserinfo.backimgsrc,
                    index:0,
                    nickname:this.common.loginuserinfo.nickname,
                    addednicknames:[],
                    ifmaingood:-1,
                    maingoodnums:res.data.data[0].goodnum,
                    time:res.data.data[0].time_,
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
                  this.commentnum++;
                  this.commentCardinfo.unshift(temp)
                  this.pagenumsinfo.pos=1
                  this.pagenumsinfo.sum=this.commentnum
                }
            )
          }
          else{
            alert(res.data.msg)
          }
        })
      },
      showtexthtml: function (x) {
        var mdValue=x;
        var converter = new showdown.Converter();
        this.content=converter.makeHtml(mdValue)
        this.count*=-1;
        this.$nextTick(()=>{
          Prism.highlightAll()})
      },
      deletecard:function (pos){
        var temp=confirm("确定删除吗？");
        if(temp){
          this.commentCardinfo.splice(pos,1);
          this.commentnum-=1;
          this.pagenumsinfo.sum-=1;
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
        console.log(this.common.serveraddress+"/article/getone?userid="+this.$route.params.userid+"&id="+this.$route.params.articleid)
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
              Prism.highlightAll()
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
           res=> {
            for(let i =0;i<res.data.data.length;i++){
                let temp={
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
              this.$axios.get(this.common.serveraddress+"/user/getimgsrc?userid="+temp.actorid).then(
                ress=>{
                  temp.mainsrc=ress.data.data
                }
              )
              this.$axios.get(this.common.serveraddress+"/user/getnickname?userid="+temp.actorid).then(
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
              this.uploadFile("/action/ifin",param).then(ress=>{
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
              this.$axios.get(
                this.common.serveraddress+"/addcomment/get?userid="+this.common.userinfo.id+"&articleid="+this.$route.params.articleid+"&commentid="+temp.id).then(
                 ress=>{
                  for(let u=0;u<ress.data.data.length;u++){
                    temp.addedcontent.push(ress.data.data[u].content)
                    temp.addednicknames.push(ress.data.data[u].name_)
                    temp.goodnums.push(ress.data.data[u].goodnum)
                    temp.addid.push(ress.data.data[u].id)
                    temp.addedactorid.push(ress.data.data[u].actorid)
                    temp.addtime.push(ress.data.data[u].time_)
                    this.$axios.get(this.common.serveraddress+"/user/getimgsrc?userid="+ress.data.data[u].actorid).then(
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
                    this.uploadFile("/action/ifin",param).then(resss=>{
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
                this.pagenumsinfo.sum=this.commentnum
              }
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
    top:320px;
    left:13%;
    width: 78%;
    height: 600px;
    border-radius: 15px;
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
    top:929px;
    left: 2%;
  }
  #buttonbox img{
    width:20%;
    height: 100%;
    float: left;
    margin-top: 0px;
    margin-left: 50px;
  }
  #title{
    width:80%;
    height: 61px;
    position: absolute;
    top:0px;
    left:1%;
    font-family: 华光楷体_CNKI;
    font-size: 50px;
    line-height: 61px;
    text-align: left;
  }
  #showarea{
    width: 100%;
    height: 860px;
    position: absolute;
    left:0px;
    top:87px;
  }
  #readcontent{
    width: 100%;
    height: 958px;
    position: absolute;
    left:2%;
    top:10px;
    border-radius: 10px;
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
    width: 94%;
    height: auto;
    position: absolute;
    left:3%;
    top:980px;
    border-radius: 20px;
  }
  #editcomment{
    width: 96%;
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
    width:79%;
    height:42px;
    border-style: solid;
    border-width: thin;
    border-radius: 10px;
    outline: none;
    position: absolute;
    top:5px;
    left:55px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
  }
  #editinput:focus{
    border-width: 2px;
    left:54px;
    top:4px;
  }
  #editbutton{
    width:70px;
    height: 42px;
    position: absolute;
    top:8px;
    border-radius: 6px;
    line-height: 42px;
    right: 2%;
    font-size: 20px;
    cursor: pointer;
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
    margin-left:40%;
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
