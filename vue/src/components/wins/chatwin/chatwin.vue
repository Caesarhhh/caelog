<template>
<div class="box11" :style="getcolor1()">
  <div id="empty">
    <div id="close" @click="close" class="unselect"><img :src="closeimgsrc" alt="error"></div>
    <div id="chatarea">
      <GeminiScrollbar>
        <div id="gsd">
          <div id="chats" ref="chats" class="clearfix">
            <cb v-for="(item,index) in chatinfo" :key="index" :datas="item" :class="{unselect:ifunselect}"></cb>
          </div>
<!--        <div id="scrollarea" ref="scrollpos">-->
<!--          <div id="scroll" @mousedown="move"></div>-->
<!--        </div>-->
        </div>
    </GeminiScrollbar>
    </div>
    <div id="buttonarea">
      <div id="submit" :style="getcolor3()" @click="submitmsg">发送</div>
    </div>
    <textarea id="textinput" v-model="inputtext"></textarea>
  </div>
</div>
</template>

<script>
  import chatbox from "./chatbox/chatbox";
  import GeminiScrollbar from 'vue-gemini-scrollbar';
  import Vue from "vue";
  Vue.use(GeminiScrollbar);
  var chatinfo=[{
    headimgsrc: "http://caesar216.usa3v.net/caelog/images/head.jpg",
    content:"只看一个人的着作，结果是不大好的：你就得不到多方面的优点。必须如蜜蜂一样，采过许多花，这才能酿出蜜来。倘若叮在一处，所得就非常有限，枯燥了。",
    ifleft:false,
    chatterid:0
  }]
  export default {
    name: "chatwin",
    props:["datas"],
    computed:{
    },
    data(){
      return{
        closeimgsrc:"http://caesar216.usa3v.net/caelog/images/close2.png",
        chatinfo:chatinfo,
        sheight:420,
        shpos:0,
        inputtext:"",
        currentId:1,
        ifunselect:false,
        timer:"",
        chatid:0
      }
    },
    mounted() {
      this.init()
      this.getChatinfo(this.datas.targetid)
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
      gettime(){
        let x=new Date().toLocaleString()
        let ar=x.split(/,|:| |-|\/|[\u4e00-\u9fa5]./)
        for(let i=1;i<6;i++){
          if(ar[i].length==1){
            ar[i]="0"+ar[i]
          }
        }
        return ar[0]+"-"+ar[1]+"-"+ar[2]+" "+ar[3]+":"+ar[4]+":"+ar[5]
      },
      close:function (){
        var temp=this.datas;
        temp.ifwin=false;
        this.$emit("update:datas",temp);
      },
      getChatinfo:function (targetid){
        var that=this
        this.chatinfo=[]
        this.$axios({
          method:"get",
          url:that.common.serveraddress+"/chat/get?person1id="+that.common.loginuserinfo.id+"&person2id="+targetid
        }).then(
          res=>{
            if(res.data.code==400){
              this.$axios({
                method:"get",
                url:that.common.serveraddress+"/chat/add?person1id="+that.common.loginuserinfo.id+"&person2id="+targetid
              }).then(ress=>{
                this.chatid=ress.data.data.id
              })
            }
            else{
              this.chatid=res.data.data.id
              this.$axios({
                method:'get',
                url:that.common.serveraddress+"/letter/get?chatid="+this.chatid}).then(async function (ress){
                var temp=ress.data.data
                for(var i=temp.length-1;i>-1;i--){
                  var temp_chatinfo={
                    headimgsrc: that.common.loginuserinfo.backimgsrc,
                    content:temp[i].content,
                    ifleft:false,
                    time:temp[i].time_,
                    chatterid:that.$route.params.userid
                  }
                  if(temp[i].actorid!=that.common.loginuserinfo.id){
                   await that.$axios({
                      url:that.common.serveraddress+"/user/getimgsrc?userid="+targetid,
                      method:"get"
                    }).then(
                      resss=>{
                        temp_chatinfo.ifleft=true
                        temp_chatinfo.headimgsrc=resss.data.data
                        temp_chatinfo.chatterid=that.datas.targetid
                        that.chatinfo.push(temp_chatinfo)
                      }
                    )
                  }
                  else{
                    that.chatinfo.push(temp_chatinfo)
                  }
                }
              })
            }
          }
        )
      },
      init:function (){
        if(this.getscrollheight()>0){
          this.setscrollheight(this.getscrollheight());
          var top=420-this.getscrollheight();
          this.setscrollpos(top);
          this.setchatspos(-1*top*(this.chatsheight()-424)/(420-this.getscrollheight()));
          this.shpos=top;
        }
        window.addEventListener('mousewheel', this.debounce(this.handleScroll,100), true)||window.addEventListener("DOMMouseScroll",this.debounce(this.handleScroll,100),false)
      },
      waittime:function (fun,time){
        this.timer = setTimeout(()=>{fun
        },time);
      },
      setscrollpos:function (pos){
        this.$refs.scrollpos.style.setProperty("--scrollpos",String(pos)+"px");
      },
      setscrollheight:function (height){
        this.$refs.scrollpos.style.setProperty("--scrollheight",String(height)+"px");
      },
      setchatspos:function (pos){
        this.$refs.chats.style.setProperty("--chattop",String(pos)+"px");
      },
      chatsheight(){
        return this.$refs.chats.offsetHeight+10;
      },
      getscrollheight(){
        if(this.chatsheight()<424){
          return 0;
        }
        var temp=424*420/this.chatsheight();
        this.setscrollheight(temp);
        return temp;
      },
      debounce(func, wait) {
        let timeout;
        return function () {
          let context = this;
          let args = arguments;
          if (timeout) clearTimeout(timeout);
          timeout = setTimeout(() => {
            func.apply(context, args)
          }, wait);
        }
      },
      handleScroll(e){
        let direction = e.deltaY > 0 ? 'down' : 'up'
        if(this.getscrollheight()>0){
          if(direction=='down'){
            this.shpos+=60;
            if(this.shpos>this.chatsheight()-424){
              this.shpos=this.chatsheight()-424;
            }
            this.setscrollpos(this.shpos*(420-this.getscrollheight())/(this.chatsheight()-424));
            this.setchatspos(-1*this.shpos);
          }
          else{
            this.shpos-=60;
            if(this.shpos<0){
              this.shpos=0;
            }
            this.setscrollpos(this.shpos*(420-this.getscrollheight())/(this.chatsheight()-424));
            this.setchatspos(-1*this.shpos);
          }
        }
      },
      move(e){
        let odiv = e.target;
        let disY = e.clientY - odiv.offsetTop;
        document.onmousemove = (e)=>{
          let top = e.clientY - disY;
          if(top+this.getscrollheight()<420&&top>0){
          this.setscrollpos(top);
          this.setchatspos(-1*top*(this.chatsheight()-424)/(420-this.getscrollheight()));
          odiv.style.top = top + 'px';
          this.ifunselect=true;
          this.shpos=top;
          }
        };
        document.onmouseup = (e) => {
          document.onmousemove = null;
          document.onmouseup = null;
          this.ifunselect=false;
        };
      },
      submitmsg:function (){
        if(this.inputtext!=""){
          var temptext=this.inputtext
          this.$axios({
            method:'get',
            url:this.common.serveraddress+"/letter/add?actorid="+this.common.loginuserinfo.id+"&targetid="+this.datas.targetid+"&content="+temptext
          }).then(res=>{
            if(res.data.code==200){
              var temp_chatinfo={
                headimgsrc: this.common.loginuserinfo.backimgsrc,
                content:temptext,
                ifleft:false,
                time:this.gettime()
              }
              this.chatinfo.push(temp_chatinfo)
            }
            this.inputtext="";
          })
        }
        else{
          alert("私信内容不能为空")
        }
      }
    },
    components:{
      cb:chatbox
    }
  }
</script>

<style scoped>
.box11{
  width:700px;
  height:691px;
  border-style: ridge;
  position: fixed;
  top:50%;
  left: 50%;
  margin-top: -350px;
  margin-left: -350px;
  z-index: 999;
}
#empty{
  width:700px;
  height: 691px;
  position: relative;
  margin-top: 0px;
  margin-left: 0px;
}
.unselect{

  -webkit-user-select:none;

  -moz-user-select:none;

  -ms-user-select:none;

  user-select:none;

}
#close{
  position: absolute;
  top:5px;
  right: 5px;
  width:40px;
  height: 40px;
  cursor: pointer;
}
#close img{
  width: 100%;
  height: 100%;
}
#chatarea{
  width:676px;
  height: 424px;
  border-style: ridge;
  position: absolute;
  top:42px;
  left:10px;
  --chattop:0px;
}
#gsd{
  width:676px;
  height: 424px;
}
#chats{
  position: absolute;
  top:0px;
}
#buttonarea{
  width: 662px;
  height: 43px;
  position: absolute;
  top:475px;
  left:17px;
}
#submit{
  width: 75px;
  height: 37px;
  position: absolute;
  border-radius: 5px;
  top:1px;
  right: 5px;
  font-family: 华光楷体_CNKI;
  font-size: 24px;
  line-height: 37px;
  cursor: pointer;
}
#textinput {
  width: 628px;
  height: 141px;
  position: absolute;
  top: 525px;
  left: 33px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
#scrollarea{
  width:3px;
  height: 420px;
  background-color: #d0d0d0;
  position: absolute;
  top:0px;
  right: 0px;
  --scrollpos:0px;
  --scrollheight:0px;
}
#scroll{
  width:5px;
  height: var(--scrollheight);
  background-color: #888888;
  position: absolute;
  top:var(--scrollpos);
}
.clearfix:after{
  display:block;
  clear:both;
  content:"";
  visibility:hidden;
  height:0;
}
.clearfix{
  zoom:1
}
</style>
