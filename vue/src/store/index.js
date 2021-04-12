import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
    userinfo:localStorage.getItem('userinfo')?localStorage.getItem("userinfo"):{},
    token_emailcode:localStorage.getItem("token_emailcode")?localStorage.getItem("token_emailcode"):{code:"",time:""},
    colorNum:0,
    color1:"rgb(255,255,255)",//主标签色
    color2:"rgb(222,225,230)",//背景色
    color3:"rgb(223,249,252)",//tag色
    color4:"rgb(241,243,244)",//辅标签色
    colorFont:"black",//主字体色
    colorFont2:"blue",//辅字体色
    colorSelect:[
      {
      color1: "rgb(4,91,98)",
      color2:"rgb(2,57,62)",
      color3:"rgb(13,72,78)",
      color4:"rgb(16,128,135)",
      colorFont:"rgb(255,255,255)",
      colorFont2:"yellow"
    },{
      color1:"rgb(255,255,255)",
      color2:"rgb(222,225,230)",
      color3:"rgb(223,249,252)",
      color4:"rgb(241,243,244)",
      colorFont:"black",
      colorFont2:"blue"
    },{
      color1:"rgb(183,225,228)",
      color2:"rgb(90,180,193)",
      color3:"rgb(255,255,255)",
      color4:"rgb(225,225,225)",
      colorFont:"rgb(93,188,194)",
      colorFont2:"blue"
    },{
        color1:"rgb(245,225,218)",
        color2:"rgb(227,171,154)",
        color3:"rgb(210,194,188)",
        color4:"rgb(255,217,207)",
        colorFont:"rgb(215,136,109)",
        colorFont2:"black"
      },{
        color1:"rgb(203,220,247)",
        color2:"rgb(132,173,234)",
        color3:"rgb(255,255,255)",
        color4:"rgb(196,224,224)",
        colorFont:"rgb(86,141,229)",
        colorFont2:"green"
      },{
        color1:"rgb(68,87,96)",
        color2:"rgb(43,55,61)",
        color3:"rgb(56,69,76)",
        color4:"rgb(186,193,193)",
        colorFont:"rgb(255,255,255)",
        colorFont2:"white"
      },{
        color1:"rgb(109,65,161)",
        color2:"rgb(70,42,104)",
        color3:"rgb(86,55,124)",
        color4:"rgb(145,78,193)",
        colorFont:"rgb(255,255,255)",
        colorFont2:"white"
      }
    ]
  },
  mutations: {
    // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      state.Authorization = user.Authorization;
      localStorage.setItem('Authorization', user.Authorization);
    },
    changecolor(state,aa){
      let a=aa[0]
      let i=aa[1]
      if(i===1){
        state.color1=a
      }
      else if(i===2){
        state.color2=a
      }
      else if(i===3){
        state.color3=a
      }
      else if(i===4){
        state.color4=a
      }
      else if(i==="Font"){
        state.colorFont=a
      }
      else if(i==="Font2"){
        state.colorFont2=a
      }
    },
    changecolorSelect(state,a){
      state.colorNum=a
    }
  }
});
export default store;
