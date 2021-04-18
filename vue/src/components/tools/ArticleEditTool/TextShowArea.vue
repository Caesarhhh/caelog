<template>
<div class="TextShowArea" :style="[getcolor4(),getcolorFont()]">
    <div class="textShow" name="textShow" :style="[getcolor4(),getcolorFont()]">
      <GeminiScrollbar>
          <div id="showContent" v-html="datas">
          </div>
      </GeminiScrollbar>
    </div>
</div>
</template>

<script>
  import Vue from 'vue';
  import Prism from 'prismjs';
  import GeminiScrollbar from 'vue-gemini-scrollbar';
  Vue.use(GeminiScrollbar)
  Vue.use(Prism)
  let content,codeNodes;
  export default {
    name: "textShowArea",
    props:["datas"],
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
      }
    },
    mounted() {
      const languages = ['html', 'css', 'js', 'php', 'dart', 'bash', 'nginx', 'sql'];
      content = document.querySelector('#showContent');
      codeNodes = content.getElementsByTagName('code');
      this.observer = new MutationObserver(() => {
        Prism.highlightAll()
      });

      this.observer.observe(content, {
        childList: true,
        characterData: true,
      });
    }
  }
</script>

<style scoped>
.TextShowArea{
}
.textShow{
  width:96%;
  height: 94%;
  position: absolute;
  top:3%;
  left:3%;
  border:none;
  outline: none;
  text-align: left;
}
#showContent{
  width:98%;
  height: 100%;
  position: absolute;
  left:0px;
  top:0px;
  border:none;
  outline: none;
}
</style>
