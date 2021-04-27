<template>
<div class="letterset">
  <div id="title" :style="getcolor4()"><div>私信管理</div></div>
  <div id="letterbox">
    <lc id="lc1" v-if="(pagenumsinfo.pos-1)*6<letternum" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6]"></lc>
    <lc id="lc2" v-if="(pagenumsinfo.pos-1)*6+1<letternum" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+1]"></lc>
    <lc id="lc3" v-if="(pagenumsinfo.pos-1)*6+2<letternum" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+2]"></lc>
    <lc id="lc4" v-if="(pagenumsinfo.pos-1)*6+3<letternum" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+3]"></lc>
    <lc id="lc5" v-if="(pagenumsinfo.pos-1)*6+4<letternum" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+4]"></lc>
    <lc id="lc6" v-if="(pagenumsinfo.pos-1)*6+5<letternum" :datas="letterCardinfo[(pagenumsinfo.pos-1)*6+5]"></lc>
    <div id="line"></div>
    <pn id="pagenums" :datas.sync="pagenumsinfo"></pn>
  </div>
</div>
</template>

<script>
  import pagenums from "../../../../../tools/pagenums/pagenums";
  import lettercard from "./lettercard/lettercard";
  var pagenumsinfo={
    pos:1,
    sum:2
  }
  export default {
    name: "letterset",
    props:["datas"],
    data(){
      return{
        pagenumsinfo:pagenumsinfo,
        letternum:0,
        letterCardinfo:[{
          nickname:"Jack",
          time:"21-04-12",
          headsrc:this.common.getserveraddress+"/userdata/-1/1617959159018505.png",
          targetid:1,
          id:0
        }]
      }
    },
    components:{
      pn:pagenums,
      lc:lettercard
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
      comparetime(time1,time2){
        let t1=[]
        t1.push(parseInt(time1.substring(0,4)))
        t1.push(parseInt(time1.substring(5,7)))
        t1.push(parseInt(time1.substring(8,10)))
        t1.push(parseInt(time1.substring(11,13)))
        t1.push(parseInt(time1.substring(14,16)))
        t1.push(parseInt(time1.substring(17,19)))
        t1.push(parseInt(time1.substring(20,22)))
        let t2=[]
        t2.push(parseInt(time2.substring(0,4)))
        t2.push(parseInt(time2.substring(5,7)))
        t2.push(parseInt(time2.substring(8,10)))
        t2.push(parseInt(time2.substring(11,13)))
        t2.push(parseInt(time2.substring(14,16)))
        t2.push(parseInt(time2.substring(17,19)))
        t2.push(parseInt(time2.substring(20,22)))
        for(var i=0;i<t1.length;i++){
          if(t1[i]!=t2[i]){
            return t1[i]-t2[i]
          }
        }
        return 0
      },
      getchatinfo:function (){
        this.letterCardinfo=[]
        this.letternum=0
        this.$axios({
          method:'get',
          url:this.common.serveraddress+"/chat/getbysort?userid="+this.common.loginuserinfo.id
        }).then(res=>{
          let len=res.data.data.length
          this.letternum=0
          for(let i=0;i<len;i++){
            let tempdict={
              nickname:"",
              time:"",
              headsrc:"",
              targetid:res.data.data[i].person1id,
              id:res.data.data[i].id
            }
            var tempid=res.data.data[i].person1id
            if(tempid==this.common.loginuserinfo.id){
              tempid=res.data.data[i].person2id
              tempdict.targetid=tempid
            }
            this.$axios({
              method:'get',
              url:this.common.serveraddress+"/user/get?userid="+tempid
            }).then(async ress=>{
              this.$axios({
                method:'get',
                async:false,
                url:this.common.serveraddress+"/letter/get?chatid="+tempdict.id
              }).then(resss=>{{
                tempdict.nickname=ress.data.data.nickname
                tempdict.headsrc=ress.data.data.backimgsrc
                this.letterCardinfo.push(tempdict)
                tempdict.time=resss.data.data[0].time_
                this.letternum++
                this.pagenumsinfo.sum=Math.ceil(this.letternum/6)
                if(this.letternum==len){
                  for(let pi=0;pi<len;pi++){
                    for(let pj=0;pj<len-pi-1;pj++){
                      if(this.comparetime(this.letterCardinfo[pj].time,this.letterCardinfo[pj+1].time)<0){
                        let templetter=this.letterCardinfo[pj]
                        this.$set(this.letterCardinfo,pj,this.letterCardinfo[pj+1])
                        this.$set(this.letterCardinfo,pj+1,templetter)
                      }
                    }
                  }
                }
              }
              })
            })
          }
        }).catch((e)=>{})
      }
    },
    mounted() {
      this.pagenumsinfo={
        pos:1,
        sum:0
      }
      this.getchatinfo()
    }
  }
</script>

<style scoped>
.letterset{
  width:696px;
  height: 281px;
  border-style: groove;
  border-width: thin;
}
#title{
  width:47px;
  height: 256px;
  border-radius: 10px;
  border-width: thin;
  position: absolute;
  top:10px;
  left:15px;
}
#title div{
  font-size: 24px;
  font-family: 华光楷体_CNKI;
  position: absolute;
  top:70px;
}
#letterbox{
  width:614px;
  height: 256px;
  border-style: groove;
  border-width: thin;
  position: absolute;
  top:10px;
  left:71px;
}
#lc1{
  position: absolute;
  top:7px;
  left:14px
}
#lc2{
  position: absolute;
  top:70px;
  left:14px
}
#lc3{
  position: absolute;
  top:133px;
  left:14px
}
#lc4{
  position: absolute;
  top:7px;
  left:324px
}
#lc5{
  position: absolute;
  top:70px;
  left:324px
}
#lc6{
  position: absolute;
  top:133px;
  left:324px
}
#line{
  height:180px;
  width:1px;
  position: absolute;
  background-color: black;
  top:8px;
  left:300px;
}
#pagenums{
  position: absolute;
  top:197px;
  left:15px;
}
</style>
