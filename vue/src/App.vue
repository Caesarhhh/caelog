<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import mainpage from "./components/pages/mainpage/mainpage";
import backstage from "./components/pages/backstage/backstage";
import articleshowpage from "./components/pages/blockshowpage/blockshowpage";
import articlereadpage from "./components/pages/articlereadpage/articlereadpage";
import loginpage from "./components/pages/loginpage/loginpage";
import articleedit from "./components/pages/articleedit/articleedit";
export default {
  name: 'App',
  data(){
    return {
    }
  },
  components: {
    mg:mainpage,
    bg:backstage,
    asg:articleshowpage,
    arg:articlereadpage,
    lg:loginpage,
    ae:articleedit
  }
}
</script>

<style>

  *{
    background-color: white;
  }
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
  margin-top: 0px;
  width:1024px;
  margin:auto;
}
</style>
