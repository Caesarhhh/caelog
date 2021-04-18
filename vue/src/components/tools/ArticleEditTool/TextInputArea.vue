<template>
  <div :style="[getcolor4(),getcolorFont()]" class="TextInputArea">
    <textarea :style="[getcolor4(),getcolorFont()]" @keydown="changeText()" @keyup="changeText()" v-model="inputText"></textarea>
  </div>
</template>

<script>
  import Prism from "prismjs"
  export default {
    name: "textInputArea",
    props:["value"],
    data(){
      return{
        inputText:""
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
      changeText:function (e){
        this.$emit("update:datas",this.inputText)
        this.$parent.changeDatas()
        this.$parent.makeHtml()
      }
    },
    watch:{
      value(){
        this.inputText=this.value
      }
    },
    mounted() {
      // document.addEventListener('paste',function (event){
      //   console.log(event.clipboardData.items[0])
      //   var items = event.clipboardData && event.clipboardData.items;
      //   var file = null;
      //   if (items && items.length) {
      //     // 检索剪切板items
      //     for (var i = 0; i < items.length; i++) {
      //       console.log(items[i].type)
      //       if (items[i].type.indexOf('image') !== -1) {
      //         file = items[i].getAsFile();
      //         break;
      //       }
      //     }
      //   }
      // })
    }
  }
</script>

<style scoped>
.TextInputArea{
}
textarea{
  width:94%;
  height: 94%;
  position: absolute;
  top:3%;
  left:3%;
  font-size: 20px;
  font-family: 华光楷体_CNKI;
  border:none;
  outline: none;
}
textarea:focus{
  border-width: 2px;
}
</style>
