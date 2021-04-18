<template>
<div class="Selectors">
  <div id="selectBox">
    <div class="button" :style="getcolor3()" @click="openPicwin">
      <img src="http://caesar216.usa3v.net/caelog/images/picture.png" alt="error">
    </div>
    <div class="button" :style="getcolor3()" @click="save">
      <img src="http://caesar216.usa3v.net/caelog/images/save.png" alt="error">
    </div>
    <div id="ImgBox" v-if="ifwin" :style="[getcolorFont(),getcolor4()]">
      <div id="close" @click="closePicwin">
        <img :src="closeImgSrc" alt="error">
      </div>
      <div id="copy" @click="copyPic">
        复制选取的图片
      </div>
      <div id="ImgSelectArea">
        <GeminiScrollbar>
          <div v-for="(value,index) in imgs" :class="{imgSelects:index!=selectedIndex,imgSelected:index==selectedIndex}" @dblclick="copyPic()" @click="selectPic(index)">
            <img :src="value" alt="error">
          </div>
        </GeminiScrollbar>
      </div>
      <div id="upload">点击或拖拽上传本地图片</div>
      <input type="file" id="uploadByClick" @change="uploadFilebyClick">
    </div>
  </div>
</div>
</template>

<script>
  import Vue from 'vue'
  import axios from 'axios'
  import GeminiScrollbar from 'vue-gemini-scrollbar';
  import VueClipboard from 'vue-clipboard2'
  Vue.use(VueClipboard)
  Vue.use(GeminiScrollbar)
  export default {
    name: "Selectors",
    data(){
      return{
        imgs:[
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png",
          "http://caesar216.usa3v.net/caelog/images/picture.png"
        ],
        closeImgSrc:"http://caesar216.usa3v.net/caelog/images/close2.png",
        ifwin:false,
        selectedIndex:99999
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
      openPicwin:function (){
        this.ifwin=true
        this.$nextTick(()=>{
          let upload=document.getElementById("ImgSelectArea")
          upload.addEventListener('dragenter',this.onDrag,false)
          upload.addEventListener('dragover',this.onDrag,false)
          upload.addEventListener('drop',this.onDrop,false)
        })
      },
      closePicwin:function (){
        this.ifwin=false
        this.selectedIndex=9999
      },
      selectPic:function (index){
        this.selectedIndex=index
      },
      copyPic:function (){
        if(this.selectedIndex==99999){
          alert ("未选择图片！")
          return
        }
        let text="<img src=\""+this.imgs[this.selectedIndex]+"\" width=\"200px\" />"
        this.$copyText(text)
        this.closePicwin()
      },
      onDrag (e) {
        e.stopPropagation();
        e.preventDefault();
      },
      onDrop (e) {
        e.stopPropagation();
        e.preventDefault();
        //this.imgPreview(e.dataTransfer.files);
        this.uploadFile(e.dataTransfer.files)
      },
      uploadFile (files) {
        let params = new FormData();
        params.append('file', files[0]);
        params.append('userid',this.common.loginuserinfo.id);
        var that=this
        axios.post(this.common.serveraddress+'/files/upload', params).then(res=>{
          that.imgs.push(this.common.getserveraddress+res.data.data)
        })
      },
      uploadFilebyClick (e) {
        let files=e.target.files
        let params = new FormData();
        params.append('file', files[0]);
        params.append('userid',this.common.loginuserinfo.id);
        var that=this
        axios.post(this.common.serveraddress+'/files/upload', params).then(res=>{
          that.imgs.push(this.common.getserveraddress+res.data.data)
        })
      },
      save:function (){
        this.$parent.save()
        alert("已保存到本地缓存")
      }
    },
    mounted() {
    }
  }
</script>

<style scoped>
.Selectors{
}
#selectBox{
  width:98%;
  height: 100%;
  position: absolute;
  left:1%;
  top:0;
}
.button{
  width:3%;
  height: 90%;
  border-radius: 50%;
  float:left;
  margin-left: 3%;
  cursor: pointer;
}
.button img{
  width: 100%;
  height: 100%;
}
#ImgBox{
  width:50%;
  height: 1000%;
  border-style: solid;
  position: absolute;
  z-index: 2;
  border-radius: 20%;
}
#close{
  border-style: solid;
  width:5%;
  height: 8%;
  border-radius: 50%;
  position: absolute;
  right:5%;
  cursor: pointer;
  background-color: white;
  overflow: hidden;
}
#close img{
  width: 100%;
  height: 100%;
}
#copy{
  position: absolute;
  width:40%;
  height: 10%;
  left:30%;
  top:1.2%;
  font-family: 华光楷体_CNKI;
  line-height: 180%;
  font-size: 150%;
  cursor: pointer;
}
#ImgSelectArea{
  border-radius: 10%;
  width:96%;
  height: 70%;
  position: absolute;
  left:1.8%;
  top:13%;
}
.imgSelects{
  width: auto;
  max-width: 21%;
  height: 45%;
  float: left;
  margin-left: 2%;
  margin-top: 1%;
  margin-bottom: 1%;
  cursor: pointer;
  border-style: solid;
  border-radius: 30%;
  border-width: 1px;
  border-color: transparent;
}
.imgSelected{
   width: auto;
   max-width: 21%;
   height: 45%;
   float: left;
   margin-left: 2%;
   margin-top: 1%;
  margin-bottom: 1%;
   cursor: pointer;
   border-style: solid;
  border-radius: 30%;
  border-width: 1px;
  border-color: lightskyblue;
 }
.imgSelects img{
  height: 100%;
}
.imgSelected img{
  height: 100%;
}
#upload{
  position: absolute;
  bottom:4%;
  font-family: 华光楷体_CNKI;
  font-size: 180%;
  left:25%;
  cursor: pointer;
}
#uploadByClick{
  position: absolute;
  bottom:4%;
  font-family: 华光楷体_CNKI;
  font-size: 180%;
  left:15%;
  cursor: pointer;
  z-index: 4;
  width:60%;
  opacity: 0;
}
</style>
