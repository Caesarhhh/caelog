import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    // 存储token
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
    userinfo:localStorage.getItem('userinfo')?localStorage.getItem("userinfo"):{},
    token_emailcode:localStorage.getItem("token_emailcode")?localStorage.getItem("token_emailcode"):{code:"",time:""}
  },
  mutations: {
    // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      state.Authorization = user.Authorization;
      localStorage.setItem('Authorization', user.Authorization);
    }
  }
});

export default store;
