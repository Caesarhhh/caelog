<template>
<div class="box2">
  <div id="pagehrefbox">
    <div class="pagebutton" @click="topage('/mainpage')">首页</div>
    <div class="pagebutton" @click="topage('/backstage')">后台</div>
  </div>
  <div id="title">{{title}}</div>
  <div id="content"></div>
  <pc id="personalcard" :datas="pcpos"></pc>
  <st id="searchtool_ar"></st>
  <div id="blockbox_ar">
    <lb id="blocks" :datas="blocklabels"></lb>
    <lb id="labels" :datas="labellabels"></lb>
  </div>
  <div id="buttonbox">
    <img :src="this.good==-1?nogoodimgsrc:goodimgsrc" alt="error" @click="toglegood">
    <img :src="transmitimgsrc" alt="error">
  </div>
  <div id="commentarea">
    <div id="editcomment">
      <div id="editImgsrc"><img :src="headimgsrc" alt="error"></div>
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
  var blocklabels={
    labels:["随笔","java","c++","linux","windows"],
    title:"板块",
    type:'block'
  }
  var labellabels={
    labels:["机器学习","前端","后端","心情"],
    title:"标签",
    type:'label'
  }
  var commentCardinfo=[{
    maincontent:"革命是痛苦，其中也必然混有污秽和血，决不是如诗人所想象的那般有趣，那般完美；革命尤其是现实的故事，需要各种卑贱的，麻烦的工作，决不如诗人想象的那般浪漫；革命当然有破坏，然而更需要建设，破坏是痛快的，但建设却是麻烦的事。所以对于革命抱着浪漫谛克的幻想的人，一和革命接近，一到革命进行，便容易失望。",
    mainsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
    index:0,
    nickname:"Caesar",
    addednicknames:["Ben","Janet","Jhon"],
    ifmaingood:-1,
    maingoodnums:5,
    ifgood:[-1,-1,1],
    goodnums:[5,7,9],
    addedsrc:[
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg"
    ],
    addedcontent:[
      "只看一个人的着作，结果是不大好的：你就得不到多方面的优点。必须如蜜蜂一样，采过许多花，这才能酿出蜜来。倘若叮在一处，所得就非常有限，枯燥了。",
      "愿中国青年都摆脱冷气，只是向上走，不必听自暴自弃者流的话。能做事的做事，能发声的发声。有一分热，发一分光。就令萤火一般，也可以在黑暗里发一点光，不必等候炬火。",
      "人有生存的权力，同样也应该拥有死的权力。我这种想法并无什么新意，只是人们不敢直截了当地把如此平常且起码的事情说出来而已。"
    ],
    addednum:3,
    count:1
  },{
    maincontent:"革命是痛苦，其中也必然混有污秽和血，决不是如诗人所想象的那般有趣，那般完美；革命尤其是现实的故事，需要各种卑贱的，麻烦的工作，决不如诗人想象的那般浪漫；革命当然有破坏，然而更需要建设，破坏是痛快的，但建设却是麻烦的事。所以对于革命抱着浪漫谛克的幻想的人，一和革命接近，一到革命进行，便容易失望。",
    mainsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
    index:1,
    nickname:"Caesar",
    addednicknames:["Ben","Janet","Jhon"],
    ifmaingood:-1,
    maingoodnums:5,
    ifgood:[-1,-1,1],
    goodnums:[5,7,9],
    addedsrc:[
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg"
    ],
    addedcontent:[
      "只看一个人的着作，结果是不大好的：你就得不到多方面的优点。必须如蜜蜂一样，采过许多花，这才能酿出蜜来。倘若叮在一处，所得就非常有限，枯燥了。",
      "愿中国青年都摆脱冷气，只是向上走，不必听自暴自弃者流的话。能做事的做事，能发声的发声。有一分热，发一分光。就令萤火一般，也可以在黑暗里发一点光，不必等候炬火。",
      "人有生存的权力，同样也应该拥有死的权力。我这种想法并无什么新意，只是人们不敢直截了当地把如此平常且起码的事情说出来而已。"
    ],
    addednum:3,
    count:1
  },{
    maincontent:"革命是痛苦，其中也必然混有污秽和血，决不是如诗人所想象的那般有趣，那般完美；革命尤其是现实的故事，需要各种卑贱的，麻烦的工作，决不如诗人想象的那般浪漫；革命当然有破坏，然而更需要建设，破坏是痛快的，但建设却是麻烦的事。所以对于革命抱着浪漫谛克的幻想的人，一和革命接近，一到革命进行，便容易失望。",
    mainsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
    index:2,
    nickname:"Caesar",
    addednicknames:["Ben","Janet","Jhon"],
    ifmaingood:-1,
    maingoodnums:5,
    ifgood:[-1,-1,1],
    goodnums:[5,7,9],
    addedsrc:[
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg"
    ],
    addedcontent:[
      "只看一个人的着作，结果是不大好的：你就得不到多方面的优点。必须如蜜蜂一样，采过许多花，这才能酿出蜜来。倘若叮在一处，所得就非常有限，枯燥了。",
      "愿中国青年都摆脱冷气，只是向上走，不必听自暴自弃者流的话。能做事的做事，能发声的发声。有一分热，发一分光。就令萤火一般，也可以在黑暗里发一点光，不必等候炬火。",
      "人有生存的权力，同样也应该拥有死的权力。我这种想法并无什么新意，只是人们不敢直截了当地把如此平常且起码的事情说出来而已。"
    ],
    addednum:3,
    count:1
  },{
    maincontent:"革命是痛苦，其中也必然混有污秽和血，决不是如诗人所想象的那般有趣，那般完美；革命尤其是现实的故事，需要各种卑贱的，麻烦的工作，决不如诗人想象的那般浪漫；革命当然有破坏，然而更需要建设，破坏是痛快的，但建设却是麻烦的事。所以对于革命抱着浪漫谛克的幻想的人，一和革命接近，一到革命进行，便容易失望。",
      mainsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
      index:3,
      nickname:"Caesar",
      addednicknames:["Ben","Janet","Jhon"],
      ifmaingood:-1,
      maingoodnums:5,
      ifgood:[-1,-1,1],
      goodnums:[5,7,9],
      addedsrc:[
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg",
      "http://caesar216.usa3v.net/caelog/images/head.jpg"
    ],
      addedcontent:[
      "只看一个人的着作，结果是不大好的：你就得不到多方面的优点。必须如蜜蜂一样，采过许多花，这才能酿出蜜来。倘若叮在一处，所得就非常有限，枯燥了。",
      "愿中国青年都摆脱冷气，只是向上走，不必听自暴自弃者流的话。能做事的做事，能发声的发声。有一分热，发一分光。就令萤火一般，也可以在黑暗里发一点光，不必等候炬火。",
      "人有生存的权力，同样也应该拥有死的权力。我这种想法并无什么新意，只是人们不敢直截了当地把如此平常且起码的事情说出来而已。"
    ],
      addednum:3,
      count:1
  }]
  var pagenumsinfo={
    sum:2,
    pos:1
  }
  var replyinfo={
    headimgsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
    nickname:"Luffy",
    name:"Luffy",
    ifreply:false,
    ccindex:0
  }
  export default {
    name: "articlereadpage",
    props:["datas"],
    data(){
      return{
        blocklabels:blocklabels,
        labellabels:labellabels,
        transmitimgsrc:"http://caesar216.usa3v.net/caelog/images/transmit.png",
        goodimgsrc:"http://caesar216.usa3v.net/caelog/images/good.png",
        nogoodimgsrc:"http://caesar216.usa3v.net/caelog/images/nogood.png",
        title:"狂人日记杂谈",
        good:-1,
        headimgsrc:"http://caesar216.usa3v.net/caelog/images/head.jpg",
        commentCardinfo:commentCardinfo,
        pagenumsinfo:pagenumsinfo,
        commentnum:4,
        replyinfo:replyinfo,
        nickname: "Caesar",
        inputtext:"",
        bigsum:4,
        pcpos:{pos:'right'}
      }
    },
    created() {
      this.title=this.$route.params.articleid;
    },
    methods:{
      topage:function (s){
        this.$router.push(s);
      },
      toglegood:function (){
        this.good*=-1;
      },
      addcard:function (){
        var temp={
          maincontent:this.inputtext,
          mainsrc:this.headimgsrc,
          index:this.bigsum,
          nickname:replyinfo.name,
          addednicknames:[],
          ifmaingood:-1,
          maingoodnums:0,
          ifgood:[],
          goodnums:[],
          addedsrc:[],
          addedcontent:[],
          addednum:3,
          count:1
        };
        this.commentCardinfo.push(temp);
        this.bigsum+=1;
        this.commentnum+=1;
        if((this.commentnum-1)%3==0){
          this.pagenumsinfo.sum+=1;
        }
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
      }
    },
    components:{
      pc:personalCard,
      st:searchtool,
      lb:labelbox,
      cc:commentCard,
      pn:pagenums,
      rtl:replytool
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
    border-style: groove;
    position: absolute;
    top:26px;
    left:8px;
  }
  .pagebutton{
    width: 70px;
    height: 45px;
    border-style: ridge;
    float: left;
    margin-top: 6px;
    margin-left: 15px;
    line-height: 45px;
    font-family: 华光楷体_CNKI;
    font-size: 25px;
  }
  #personalcard{
    position: absolute;
    top:26px;
    left: 7px;
  }
  #searchtool_ar{
    position: absolute;
    top:333px;
    left: 742px;
  }
  #blockbox_ar{
    position: absolute;
    top:398px;
    left:734px;
    width: 280px;
    height: 600px;
    border-style: ridge;
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
    width:648px;
    height: 61px;
    border-bottom-style: ridge;
    position: absolute;
    top:109px;
    left:21px;
    font-family: 华光楷体_CNKI;
    font-size: 50px;
    line-height: 61px;
    text-align: left;
  }
  #content{
    width: 682px;
    height: 880px;
    border-style: ridge;
    position: absolute;
    left:21px;
    top:196px;
  }
  #commentarea{
    width: 976px;
    height: auto;
    border-style: ridge;
    position: absolute;
    left:21px;
    top:1112px;
  }
  #editcomment{
    width: 942px;
    height: 55px;
    border-style:ridge;
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
  }
</style>
