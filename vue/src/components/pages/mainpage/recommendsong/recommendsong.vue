<template>
  <div class="box">
    <div id="pic">
      <img :src="datas.imgurl" alt="error">
    </div>
    <div id="info">
      <div id="songname">{{ datas.songname }}</div>
      <div id="singer">{{ datas.singer }}</div>
    </div>
    <div id="play" @click="changestate">
      <img :src="this.songstate==1?datas.pauseurl:datas.playurl" alt="error">
    </div>
  </div>
</template>

<script>
  export default {
    name: "recommendsong",
    props: ["datas"],
    data() {
      return {
        songstate: -1,
        audio: new Audio(this.common.getserveraddress + "images/心要野.mp3")
      }
    },
    methods: {
      changestate: function () {
        this.songstate *= -1;
        if (this.songstate == 1) {
          this.audio.play();
        } else {
          this.audio.pause();
        }
      }
    }
  }
</script>

<style scoped>
  .box {
    width: 115%;
    height: 121px;
    position: absolute;
  }

  #pic {
    width: 21%;
    border-radius: 50%;
    border-style: ridge;
    position: absolute;
    left: 8%;
    top: 9px;
  }

  #pic img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }

  #play {
    width: 15%;
    bottom: 10px;
    right: 3%;
    position: absolute;
  }

  #play img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }

  #info {
    width: 40%;
    height: 90px;
    position: absolute;
    left: 10%;
    top: 11px;
  }

  #songname {
    width: 100%;
    height: 25px;
    top: 9px;
    left: 70%;
    position: absolute;
    font-family: 华光楷体_CNKI;
    font-size: 25px;
    text-align: left;
  }

  #singer {
    width: 100%;
    height: 16px;
    top: 48px;
    left: 70%;
    position: absolute;
    text-align: left;
    font-family: 华光楷体_CNKI;
  }
</style>
