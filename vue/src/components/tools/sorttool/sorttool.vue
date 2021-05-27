<template>
  <div class="sortbox">
    <div id="sort1" :style="getcolor1()" @click="sortbyHot" :class="{shadow:sorttype==0,nonshadow:sorttype==1}">热度</div>
    <div id="sort2" :style="getcolor1()" @click="sortbyTime" :class="{shadow:sorttype==1,nonshadow:sorttype==0}">时间
    </div>
    <div id="select">
      <select @change="selectbyTime" name="selecttime" v-model="timeselect">
        <option value="-1">全部</option>
        <option v-for="item in datas.timeslot" :value="item.id">{{ item.st }}-{{ item.et }}</option>
      </select>
    </div>
  </div>
</template>

<script>
  export default {
    name: "sorttool",
    data() {
      return {
        timeselect: {id: 0, st: 2015, et: 2016},
        sorttype: 1
      }
    },
    props: ["datas"],
    mounted() {
      this.timeselect = -1
    },
    methods: {
      getcolor1() {
        return {backgroundColor: this.$store.state.color1}
      },
      getcolor2() {
        return {backgroundColor: this.$store.state.color2}
      },
      setsorttype: function (index) {
        this.sorttype = index;
      },
      sortbyTime: function () {
        this.$parent.$parent.$parent.$parent.$parent.sortArticlebyTime()
        this.setsorttype(1)
      },
      sortbyHot: function () {
        this.$parent.$parent.$parent.$parent.$parent.sortArticlebyHot()
        this.setsorttype(0)
      },
      selectbyTime: function () {
        if (this.timeselect == -1) {
          this.$parent.$parent.$parent.$parent.$parent.selectbyTime("all")
          return
        }
        this.$parent.$parent.$parent.$parent.$parent.selectbyTime(this.datas.timeslot[this.timeselect].st + "-" + this.datas.timeslot[this.timeselect].et)
      }
    }
  }
</script>

<style scoped>
  .shadow {
    top: 5px;
    box-shadow: 5px 5px 5px #888888;
  }

  .nonshadow {
    top: 10px;
    box-shadow: 0px 0px 0px #888888;
  }

  .sortbox {
    width: 390px;
    height: 51px;
    position: relative;
  }

  #sort1 {
    width: 67px;
    height: 26px;
    left: 38px;
    position: absolute;
    border-radius: 10px;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
    line-height: 26px;
    cursor: pointer;
  }

  #sort2 {
    width: 67px;
    height: 26px;
    left: 155px;
    position: absolute;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
    line-height: 26px;
    border-radius: 10px;
    cursor: pointer;
  }

  #select {
    width: 102px;
    height: 25px;
    top: 16px;
    left: 272px;
    position: absolute;
  }

  #select select {
    border-radius: 10px;
    border-width: thin;
    border-style: solid;
    position: absolute;
    top: 0px;
    left: 0px;
    outline: none;
    width: 100%;
    height: 100%;
    font-family: 华光楷体_CNKI;
    font-size: 18px;
  }

  #select select:focus {
    border-width: 2px;
  }
</style>
