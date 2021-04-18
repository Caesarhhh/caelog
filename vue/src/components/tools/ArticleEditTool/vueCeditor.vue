<template>
  <div class="vueCeditor" @keydown.s="savebyKey">
    <selectors id="selectors"></selectors>
    <textInput id="textInput" :datas.sync="inputText" v-model="inputText"></textInput>
    <textShow id="textShow" :datas.sync="innerHtml"></textShow>
  </div>
</template>

<script>
  import TextInputArea from "./TextInputArea";
  import TextShowArea from "./TextShowArea";
  import Prism from "prismjs";
  import showdown from "showdown";
  import Selectors from "./Selectors";
  import Vue from 'vue'
  Vue.use(Prism)
  let converter=new showdown.Converter();
  converter.setOption('tables',true)
  export default {
    name: "vueCeditor",
    components:{
      textInput:TextInputArea,
      textShow:TextShowArea,
      selectors:Selectors
    },
    props:["value"],
    data(){
        return{
           inputText:"",
           innerHtml:"",
        }
    },
    methods:{
      changeDatas(){
        this.$emit("input",this.inputText)
      },
      makeHtml:async function (){
        this.innerHtml=converter.makeHtml(this.inputText)
        Prism.highlightAll()
      },
      save:function (){
        localStorage.setItem("draft",this.inputText)
      },
      savebyKey:function (e){
        if(e.ctrlKey==false){
          return
        }
        localStorage.setItem("draft",this.inputText)
        alert("已保存到本地缓存")
        e.preventDefault()
      }
    },
    watch:{
      value(val,oldVal){
        this.inputText=this.value
        this.innerHtml=converter.makeHtml(this.inputText)
        Prism.highlightAll()
      }
    },
    mounted() {
    },
    beforeMount() {
    }
  }
</script>

<style scoped>
.vueCeditor{
  position: absolute;
  margin: 0 auto;
}
#textInput{
  width: 49%;
  height: 94%;
  position: absolute;
  left:0px;
  top:6%;
  border-radius: 20px;
}
#textShow{
  width: 49%;
  height: 94%;
  position: absolute;
  right:0px;
  top:6%;
  border-radius: 20px;
  word-wrap:break-word;
}
#selectors{
  position: absolute;
  width: 100%;
  height: 5%;
  top: 0.4%;
  left: 0px;
}
</style>
