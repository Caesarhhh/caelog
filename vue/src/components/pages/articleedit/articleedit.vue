<template>
  <div class="articleedit">
    <div id="empty">
      <div id="buttonbox">
        <div id="mainhref" @click="topage('/mainpage')">首页</div>
        <div id="backhref" @click="topage('/backstage')">后台</div>
      </div>
      <div id="markdowntitle">markdown</div>
      <textarea id="markdownarea" @keyup="mdSwitch"></textarea>
      <div id="showtitle">预览</div>
      <div :class="{showarea:count>-2}">
        <GeminiScrollbar>
          <div id="gsd">
            <div id="showhtml"></div>
          </div>
        </GeminiScrollbar>
      </div>
      <div id="bot">
        <div id="settingbox">
          <div id="blockbox">
            <div id="blocktitle">模块选择</div>
            <select id="blockselect" v-model="selected">
              <option v-for="(item,index) in blocks" :value="index">{{ item }}</option>
            </select>
            <div id="createblockarea">
              <div id="createtitle">新建板块</div>
              <input id="newinput" type="text">
              <div id="newconfirm">确认</div>
            </div>
            <div id="resultblock">{{ blocks[selected] }}</div>
            <div id="blockconfirm">确认</div>
          </div>
          <div id="labelsetting">
            <div id="labeltitle">标签定义</div>
            <input id="labelinput" v-model="labelinput"></input>
            <div id="labelconfirm" @click="addlabel">添加</div>
          </div>
          <div id="labels" class="clearfix">
            <lc class="labelcard" v-for="item in labelcardinfo" :datas="item"></lc>
          </div>
        </div>
        <div id="submit">发表</div>
      </div>
    </div>
  </div>
</template>

<script>
  import labelcard from "./labelcard/labelcard";
  import GeminiScrollbar from 'vue-gemini-scrollbar'
  import Vue from "vue";
  Vue.use(GeminiScrollbar);
  var labelcardinfo = [
    {label: "编程语言"},
    {label: "随笔"},
    {label: "杂谈"},
    {label: "人工智能"},
    {label: "数据挖掘"}
  ]
  export default {
    name: "articleedit",
    props: ["datas"],
    data() {
      return {
        blocks: ["JAVA", "C++", "Python", "PHP", "JS", "随笔", "opencv"],
        selected: "",
        labelcardinfo: labelcardinfo,
        labelinput: "",
        count:-1
      }
    },
    components: {
      lc: labelcard
    },
    methods: {
      topage(s){
        this.$router.push({path:s})
      },
      addlabel: function () {
        var temp = {
          label: this.labelinput
        }
        this.labelcardinfo.push(temp);
        this.labelinput = "";
      },
      mdSwitch: function () {
        var mdValue = document.getElementById("markdownarea").value;
        var converter = new showdown.Converter();
        var html = converter.makeHtml(mdValue);
        document.getElementById("showhtml").innerHTML = html;
        this.count*=-1;
      }
    },
    mounted() {
      const s = document.createElement('script');
      s.type = 'text/javascript';
      s.src = 'https://cdnjs.cloudflare.com/ajax/libs/showdown/1.9.0/showdown.min.js';
      document.body.appendChild(s);
    }
  }
</script>

<style scoped>
  @import 'github-markdown.css';

  .articleedit {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: relative;
    margin-top: 0px;
    width: 1024px;
  }

  .clearfix:after {
    display: block;
    clear: both;
    content: "";
    visibility: hidden;
    height: 0;
  }

  .clearfix {
    zoom: 1
  }

  #empty {
    position: relative;
    margin-top: 0px;
    margin-left: 0px;
  }

  #buttonbox {
    width: 720px;
    height: 60px;
    border-style: ridge;
    position: absolute;
    top: 13px;
    left: 152px;
  }

  #mainhref {
    width: 70px;
    height: 45px;
    position: absolute;
    top: 6px;
    border-style: ridge;
    line-height: 45px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    left: 15px;
  }

  #backhref {
    width: 70px;
    height: 45px;
    position: absolute;
    top: 6px;
    border-style: ridge;
    line-height: 45px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    left: 100px;
  }

  #markdowntitle {
    width: 480px;
    height: 40px;
    position: absolute;
    border-style: ridge;
    line-height: 40px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    font-weight: bold;
    top: 80px;
    left: 25px;
  }

  #showtitle {
    width: 480px;
    height: 40px;
    position: absolute;
    border-style: ridge;
    line-height: 40px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    font-weight: bold;
    top: 80px;
    left: 519px;
  }

  #markdownarea {
    width: 478px;
    height: 777px;
    position: absolute;
    top: 130px;
    left: 25px;
    font-size: 18px;
  }

  .showarea {
    width: 480px;
    height: 777px;
    border-style: ridge;
    position: absolute;
    top: 130px;
    left: 519px;
  }

  #showhtml {
    width: 682px;
    height: auto;
    position: absolute;
    text-align: left;
    top: 0px;
  }

  .gs {
    width:480px;
  }

  #gsd {
    width: 480px;
    height: 777px;
  }

  #bot {
    width: 1000px;
    height: auto;
    position: absolute;
    left: 25px;
    top: 936px;
  }

  #settingbox {
    width: 974px;
    height: auto;
    position: relative;
    border-style: ridge;
    left: 0px;
    top: 0px;
  }

  #blockbox {
    width: 946px;
    height: 103px;
    border-style: ridge;
    position: absolute;
    left: 14px;
    top: 18px;
  }

  #blocktitle {
    width: 112px;
    height: 33px;
    border-style: ridge;
    line-height: 33px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
    position: absolute;
    left: 13px;
    top: 12px;
  }

  #blockselect {
    width: 505px;
    height: 30px;
    position: absolute;
    top: 14px;
    left: 171px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #createblockarea {
    width: 518px;
    height: 39px;
    border-style: ridge;
    position: absolute;
    left: 164px;
    top: 51px;
  }

  #resultblock {
    width: 120px;
    height: 60px;
    border-style: ridge;
    line-height: 60px;
    font-family: 华光楷体_CNKI;
    font-size: 24px;
    position: absolute;
    top: 32px;
    right: 110px;
  }

  #blockconfirm {
    width: 80px;
    height: 48px;
    border-style: ridge;
    position: absolute;
    bottom: 5px;
    right: 5px;
    line-height: 48px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
  }

  #createtitle {
    width: 74px;
    height: 32px;
    border-style: ridge;
    line-height: 32px;
    position: absolute;
    top: 2px;
    left: 2px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #newinput {
    width: 360px;
    height: 30px;
    position: absolute;
    top: 2px;
    left: 84px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #newconfirm {
    width: 60px;
    height: 32px;
    border-style: ridge;
    line-height: 32px;
    position: absolute;
    top: 2px;
    right: 2px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #labelsetting {
    width: 946px;
    height: 56px;
    border-style: ridge;
    position: relative;
    margin-top: 138px;
    margin-left: 14px;
  }

  #labeltitle {
    width: 112px;
    height: 33px;
    border-style: ridge;
    line-height: 33px;
    position: absolute;
    left: 11px;
    top: 10px;
    font-family: 华光楷体_CNKI;
    font-size: 22px;
  }

  #labelinput {
    width: 719px;
    height: 40px;
    position: absolute;
    left: 132px;
    top: 5px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
  }

  #labelconfirm {
    width: 80px;
    height: 48px;
    border-style: ridge;
    line-height: 48px;
    position: absolute;
    right: 3px;
    top: 3px;
    font-family: 华光楷体_CNKI;
    font-size: 20px;
  }

  #labels {
    width: 946px;
    height: auto;
    position: relative;
    margin-top: 10px;
    margin-left: 14px;
    margin-bottom: 5px;
  }

  .labelcard {
    float: left;
    margin-left: 20px;
    margin-top: 10px;
    margin-bottom: 10px;
  }

  #submit {
    width: 140px;
    height: 70px;
    border-style: ridge;
    line-height: 70px;
    font-family: 华光楷体_CNKI;
    font-size: 35px;
    position: relative;
    margin-top: 50px;
    margin-left: 420px;
    margin-bottom: 50px;
  }
</style>
