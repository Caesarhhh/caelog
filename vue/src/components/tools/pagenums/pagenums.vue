<template>
<div class="box_pns clearfix">
  <div>
    <a-pagination
      v-model="datas.pos"
      :total="datas.sum"
      :page-size="datas.pagesize"
      @showSizeChange="onShowSizeChange"
    />
    <br />
  </div>
</div>
</template>

<script>
  export default {
    name: "pagenums",
    props:["datas"],
    data(){
      return{
        aheadsrc:this.common.getserveraddress+"images/ahead.png",
        backsrc:this.common.getserveraddress+"images/back.png",
        current: 0,
        sum:0,
        pageSize:0
      }
    },
    mounted() {
      this.sum=this.datas.sum
      this.current=this.datas.pos
      this.pageSize=this.datas.pagesize
    },
    methods:{
      onShowSizeChange(current, pageSize) {
        console.log(current, pageSize);
      },
      getColor4(){
        return{
          backgroundColor:this.$store.state.color4
        }
      }
    }
  }
</script>

<style scoped>
.box_pns{
  height:50px;
  border-width: thin;
  border-radius: 10px;
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
</style>
