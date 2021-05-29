<template>
<div class="box123">
  <div class="window" :style="getcolor4()">
    <div id="upper">
      <div id="head"><img :src="datas.headimgsrc" alt="error"></div>
      <div id="nickname">{{datas.nickname}}</div>
      <div id="close" @click="changestate(false)">
        <img :src="closeimgsrc" alt="error">
      </div>
    </div>
    <div id="lower">
      <textarea id="inputtext" v-model="inputtext"></textarea>
      <div @click="submit" id="submit" :style="[getcolor3(),getcolorFont()]">提交</div>
    </div>
  </div>
</div>
</template>

<script>
  export default {
    name: "replytool",
    props:["datas","commentinfo","addComment"],
    data(){
      return{
        closeimgsrc:this.common.getserveraddress+"images/close2.png",
        inputtext:""
      }
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
      getcolorFont(){
        return {color: this.$store.state.colorFont}
      },
      getcolorFont2(){
        return {color: this.$store.state.colorFont2}
      },
      changestate:function (state){
        var temp=this.datas;
        temp.ifreply=state;
        this.$emit("update:datas",temp);
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
      submit:function (){
        let param = new FormData()
        param.append('userid',this.$route.params.userid)
        param.append('content_',this.inputtext)
        param.append('articleid',this.$route.params.articleid)
        param.append('actorid',this.common.loginuserinfo.id)
        param.append('commentid',this.datas.commentid)
        param.append('name_',this.datas.nickname)
        this.uploadFile("/addcomment/add",param).then(async res=>{
          console.log(res)
          var temp=this.datas;
          temp.ifreply=false;
          this.$emit("update:datas",temp);
          this.addComment(this.datas.commentid,this.datas.index)
        })
      }
    }
  }
</script>

<style scoped>
.box123{
  width: 300px;
  height: 200px;
}
.window{
  width:550px;
  height: 310px;
  position: relative;
  margin-top: 0px;
  margin-left: 0px;
  border-style: solid;
  border-width: thin;
}
#upper{
  width: 530px;
  height: 45px;
  border-bottom-style: ridge;
  position: absolute;
  top:5px;
  left:9px;
}
#head{
  width:35px;
  height: 35px;
  position: absolute;
  top:4px;
  left:4px;
}
#head img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
#nickname{
  font-family: 华光楷体_CNKI;
  font-size: 26px;
  position: absolute;
  top:10px;
  left:50px;
}
#close{
  width:30px;
  height: 30px;
  position: absolute;
  top:3px;
  right: 3px;
}
#close img{
  width: 100%;
  height: 100%;
}
#lower{
  width:535px;
  height: 250px;
  position: absolute;
  top:56px;
  left:9px;
}
#inputtext{
  height: 236px;
  width: 460px;
  position: absolute;
  top:4px;
  left:5px;
  font-family: 华光楷体_CNKI;
  font-size: 20px;
}
#submit{
  width:50px;
  height: 32px;
  position: absolute;
  right: 2px;
  bottom: 2px;
  background-color: beige;
  line-height: 32px;
  font-family: 华光楷体_CNKI;
  font-size: 21px;
  color:black;
  cursor: pointer;
  border-radius: 5px;
}
</style>
