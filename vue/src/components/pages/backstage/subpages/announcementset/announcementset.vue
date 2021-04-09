<template>
<div class="box2">
  <div id="settingtitle">
    <div id="titletitle">标题</div>
    <input type="text" id="titlecontent" v-model="title">
    <div id="contenttitle"><div>公告内容</div></div>
    <textarea cols="30" rows="10" id="contentcontent" v-model="content"></textarea>
    <button id="addbutton" @click="submit">添加</button>
  </div>
  <div id="announcement">公告最多四条</div>
  <ac id="ac1" :datas="announcesetcardinfo[0]" v-if="sum>0"></ac>
  <ac id="ac2" :datas="announcesetcardinfo[1]" v-if="sum>1"></ac>
  <ac id="ac3" :datas="announcesetcardinfo[2]" v-if="sum>2"></ac>
  <ac id="ac4" :datas="announcesetcardinfo[3]" v-if="sum>3"></ac>
</div>
</template>

<script>
  import announcesetCard from "./announcesetCard/announcesetCard";
  var announcesetcardinfo=[]
  export default {
    name: "announcementset",
    props:["datas"],
    data(){
      return{
        announcesetcardinfo:announcesetcardinfo,
        sum:0,
        title:"",
        content:""
      }
    },
    components:{
      ac:announcesetCard,
    },
    methods:{
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
          headers: {'Content-Type': "multipart/form-data",
          'Cache-Control':'no-cache'},
          withCredentials: true,
          responseType: 'json',
        };
        return this.$axios.post(url, data, config);
      },
      submit:function (){
        if(this.announcesetcardinfo.length>3){
          alert("公告最多为四个！")
          return
        }
        let param = new FormData()
        param.append('title', this.title)
        param.append('userid',this.common.loginuserinfo.id)
        param.append('content',this.content)
        this.uploadFile("/announcement/add",param).then(res=>{
          setTimeout(this.refresh,300)
      })
        let params = new FormData()
        let change_content=this.common.loginuserinfo.nickname+"增加了公告《"+this.title+"》"
        params.append('content_',change_content )
        params.append('userid',this.common.loginuserinfo.id)
        this.uploadFile("/change/add",params).then(res=>{
          if(res.data.code==200){
          }})
    },
      refresh:function (){
        this.sum=0
        this.announcesetcardinfo=[]
        var valid=true
        if(valid){
          var randomcode=parseInt(new Date().getTime())%100000
          this.$axios.get(
            this.common.serveraddress+"/announcement/get?userid="+this.common.loginuserinfo.id+"&randomcode="+randomcode).then(
            res=>{
              for(var i=0;i<res.data.data.length;i++){
                var temp={
                  title:res.data.data[i].title,
                  content:res.data.data[i].content,
                  index:res.data.data[i].index_,
                  id:res.data.data[i].id,
                  time:res.data.data[i].times
                }
                this.$set(this.announcesetcardinfo,temp.index-1,temp)
                this.sum++
              }
            })
        }
        else{

        }
      },
      deletean:function (id){
        let param = new FormData()
        param.append('userid',this.common.loginuserinfo.id)
        param.append('id',id)
        this.uploadFile("/announcement/delete",param).then(res=>{
          console.log(res.data)
        })
        setTimeout(this.refresh,300)
      },
      change:function (index){
        if(index<2){
          return
        }
        let param = new FormData()
        param.append('userid',this.common.loginuserinfo.id)
        param.append('index1',index)
        param.append('index2',index-1)
        this.uploadFile("/announcement/changeindex",param).then(res=>{
          console.log(res.data)
          setTimeout(this.refresh,300)
        })
      }
    },
    mounted() {
      this.refresh()
    }
  }
</script>

<style scoped>
.box2{
  width:721px;
  height:1070px;
  background-color: white;
}
#settingtitle{
  width:680px;
  height: 217px;
  position: absolute;
  border-style: groove;
  border-width: thin;
  top:14px;
  left:20px;
}
#titletitle{
  width:41px;
  height:32px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:10px;
  left:12px;
  line-height: 32px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
#titlecontent{
  width:491px;
  height:32px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:10px;
  left:64px;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#contenttitle{
  width:41px;
  height:105px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:50px;
  left:12px;
  line-height: 32px;
}
#contenttitle div{
  position: absolute;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
  width:20px;
  left:10px;
  line-height: 26px;
}
#contentcontent{
  width:491px;
  height:143px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:50px;
  left:63px;
  text-align: left;
  font-family: 华光楷体_CNKI;
  font-size: 18px;
}
#addbutton{
  width:105px;
  height: 35px;
  position: absolute;
  top:177px;
  left: 566px;
  font-family: 华光楷体_CNKI;
  font-size: 21px;
  line-height: 35px;
  background-color: white;
}
#announcement{
  width:680px;
  height: 48px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:242px;
  left:20px;
  line-height: 48px;
  font-family: 华光楷体_CNKI;
  font-size: 24px;
}
#ac1{
  position: absolute;
  top:322px;
  left: 32px;
}
#ac2{
  position: absolute;
  top:510px;
  left: 32px;
}
#ac3{
  position: absolute;
  top:698px;
  left: 32px;
}
#ac4{
  position: absolute;
  top:886px;
  left: 32px;
}
</style>
