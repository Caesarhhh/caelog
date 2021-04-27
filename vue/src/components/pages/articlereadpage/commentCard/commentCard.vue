<template>
<div class="box1">
  <div class="maincomment clearfix">
    <div @click="tomainpage(datas.actorid)" id="mainsrc"><img :src="datas.mainsrc" alt="error"></div>
    <div id="maincontent"><div>{{datas.nickname}} : {{datas.maincontent}}</div></div>
    <div class="buttonbox">
      <div class="goodnums">{{datas.maingoodnums}}</div>
      <img @click="toglegood(-1)" :src="datas.ifmaingood==-1?nogoodimgsrc:goodimgsrc" alt="error" class="good">
      <div class="reply" @click="reply(-1)"><img :src="relpyimgsrc" alt="error"></div>
      <div class="delete" @click="deletecard(-1)" v-if="replyinfo.name==datas.nickname"><img :src="deleteimgsrc" alt="error"></div>
    </div>
    <div class="time">{{datas.time}}</div>
  </div>
  <div v-for="(item,index) in datas.addedsrc" class="addedcomment clearfix">
    <div @click="tomainpage(datas.addedactorid[index])" class="addedsrc"><img :src="datas.addedsrc[index]" alt="error"></div>
    <div class="addedcontent"><div>{{datas.addednicknames[index]}} : {{datas.addedcontent[index]}}</div></div>
    <div class="buttonbox" v-if="datas.count>-2">
      <div class="goodnums">{{datas.goodnums[index]}}</div>
      <img @click="toglegood(index)" :src="ifgood[index]==-1?nogoodimgsrc:goodimgsrc" alt="error" class="good">
      <div class="reply" @click="reply(index)"><img :src="relpyimgsrc" alt="error"></div>
      <div class="delete" @click="deletecard(index)" v-if="replyinfo.id==datas.addedactorid[index]"><img :src="deleteimgsrc" alt="error"></div>
    </div>
    <div class="time">{{datas.addtime[index]}}</div>
  </div>
</div>
</template>

<script>
  export default {
    name: "commentCard",
    props:["datas","replyinfo"],
    data(){
      return{
        goodimgsrc:this.common.getserveraddress+"images/good.png",
        nogoodimgsrc:this.common.getserveraddress+"images/nogood.png",
        relpyimgsrc:this.common.getserveraddress+"images/reply.png",
        deleteimgsrc:this.common.getserveraddress+"images/delete.png",
        ifgood:[],
        iflog:false
      }
    },
    methods:{
      tomainpage(id){
        this.$router.push("/"+id+"/mainpage")
      },
      getname:function (s){
        var pos=s.indexOf("@");
        if(pos==-1){
          return s;
        }
        else{
          return s.substr(0,pos);
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
      toglegood:function (num){
        if(!this.iflog){
          alert("请先登录")
          return
        }
        var temp=this.datas;
        if(num==-1){
          if(temp.ifmaingood==-1){
            this.$axios.get(
              this.common.serveraddress+"/action/add?actorid="+this.common.loginuserinfo.id+"&targetid="+this.datas.actorid+"&type_=commentgood"+"&objectid="+this.datas.id).then(
              res=>{
                this.$axios.get(this.common.serveraddress+"/comment/changegood?userid="+this.$route.params.userid+"&id="+this.datas.id+"&count=1"+"&articleid="+this.$route.params.articleid).then(
                  res=>{
                    temp.ifmaingood*=-1;
                    temp.maingoodnums+=temp.ifmaingood;
                    this.$emit("update:datas",temp);
                  }
                )
              })
          }
          else{
            this.$axios.get(
              this.common.serveraddress+"/action/delete?actorid="+this.common.loginuserinfo.id+"&targetid="+this.datas.actorid+"&type_=commentgood"+"&objectid="+this.datas.id).then(
              res=>{
                this.$axios.get(this.common.serveraddress+"/comment/changegood?userid="+this.$route.params.userid+"&id="+this.datas.id+"&count=-1"+"&articleid="+this.$route.params.articleid).then(
                  res=>{
                    temp.ifmaingood*=-1;
                    temp.maingoodnums+=temp.ifmaingood;
                    this.$emit("update:datas",temp);
                  }
                )
              })
          }
        }
        else{
          if(temp.ifgood[num]==-1){
            this.$axios.get(
              this.common.serveraddress+"/action/add?actorid="+this.common.loginuserinfo.id+"&targetid="+this.datas.addedactorid[num]+"&type_=addcommentgood"+"&objectid="+this.datas.addid[num]).then(
              res=>{
                this.$axios.get(this.common.serveraddress+"/addcomment/changegoodnum?userid="+this.$route.params.userid+"&id="+this.datas.addid[num]+"&count=1"+"&articleid="+this.$route.params.articleid).then(
                  res=>{
                    temp.ifgood[num]*=-1;
                    temp.goodnums[num]+=temp.ifgood[num];
                    temp.count*=-1;
                    this.ifgood=temp.ifgood
                    this.ifgood.push(0)
                    this.ifgood.pop()
                    this.$emit("update:datas",temp);
                  }
                )
              })
          }
          else{
            this.$axios.get(
              this.common.serveraddress+"/action/delete?actorid="+this.common.loginuserinfo.id+"&targetid="+this.datas.addedactorid[num]+"&type_=addcommentgood"+"&objectid="+this.datas.addid[num]).then(
              res=>{
                this.$axios.get(this.common.serveraddress+"/addcomment/changegoodnum?userid="+this.$route.params.userid+"&id="+this.datas.addid[num]+"&count=-1"+"&articleid="+this.$route.params.articleid).then(
                  res=>{
                    temp.ifgood[num]*=-1;
                    temp.goodnums[num]+=temp.ifgood[num];
                    temp.count*=-1;
                    this.ifgood=temp.ifgood
                    this.ifgood.push(0)
                    this.ifgood.pop()
                    this.$emit("update:datas",temp);
                  }
                )
              })
          }
        }
      },
      reply:function (target){
        if(!this.iflog){
          alert("请先登录")
          return
        }
        if(target==-1){
          console.log("in")
          var temp=this.replyinfo;
          temp.ifreply=true;
          temp.nickname=temp.name;
          temp.ccindex=this.datas.index;
          temp.commentid=this.datas.id
          temp.targetid=this.datas.actorid
          this.$emit("update:replyinfo",temp);
        }
        else{
          var temp=this.replyinfo;
          temp.ifreply=true;
          temp.nickname=temp.name;
          temp.nickname+="@";
          temp.ccindex=this.datas.index;
          temp.commentid=this.datas.id
          temp.targetid=this.datas.actorid
          temp.nickname+=this.getname(this.datas.addednicknames[target]);
          temp.index=this.datas.index
          this.$emit("update:replyinfo",temp);
        }
      },
      deletecard:function (num){
        if(!confirm("确定删除吗")){
          return
        }
        if(num==-1){
          console.log(this.datas.commmentid)
          let param = new FormData()
          param.append('userid',this.common.userinfo.id)
          param.append('id',this.datas.id)
          param.append('articleid',this.$route.params.articleid)
          this.uploadFile("/comment/delete",param).then(async res=>{
            await this.$parent.refreshcomment()
          })
        }
        else{
          let param = new FormData()
          param.append('userid',this.common.userinfo.id)
          param.append('id',this.datas.addid[num])
          param.append('articleid',this.$route.params.articleid)
          this.uploadFile("/addcomment/delete",param).then(async res=>{
            await this.$parent.refreshcomment()
          })
        }
      }
    },
    mounted() {
      this.ifgood=this.datas.ifgood
      if((JSON.stringify(this.common.loginuserinfo)==='{}')==false&&this.common.loginuserinfo!=null){
        this.iflog=true
      }
    }
  }
</script>

<style scoped>
.box1{
  width:942px;
  border-radius: 10px;
  height: auto;
  border-bottom-style: ridge;
  border-width: thin;
}
.maincomment{
  position: relative;
  margin-left: 5px;
  margin-top:10px;
  border-bottom-style: ridge;
  border-width: thin;
  width:930px;
  height: auto;
}
#mainsrc{
  width:40px;
  height: 40px;
  float:left;
  margin-left: 5px;
  margin-top: 5px;
  cursor: pointer;
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
.time{
  width:160px;
  height: 30px;
  float: right;
  line-height: 40px;
  text-align: right;
  margin-bottom: 0px;
  margin-right: 15px;
  font-family: 华光楷体_CNKI;
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
  border-width: thin;
  width:880px;
  height: auto;
}
.addedsrc{
  width:40px;
  height: 40px;
  float:left;
  margin-left: 5px;
  margin-top: 5px;
  cursor: pointer;
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
