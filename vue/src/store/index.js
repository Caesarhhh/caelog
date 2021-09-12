import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
const store = new Vuex.Store({
    state: {
        // 存储token
        Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
        userinfo: localStorage.getItem('userinfo') ? localStorage.getItem("userinfo") : {},
        token_emailcode: localStorage.getItem("token_emailcode") ? localStorage.getItem("token_emailcode") : { code: "", time: "" },
        colorNum: 0,
        color1: "rgb(255,255,255)", //主标签色
        color2: "rgb(222,225,230)", //背景色
        color3: "rgb(223,249,252)", //tag色
        color4: "rgb(241,243,244)", //辅标签色
        colorFont: "black", //主字体色
        colorFont2: "blue", //辅字体色
        colorSelect: [{
            color1: "rgb(4,91,98)",
            color2: "rgb(2,57,62)",
            color3: "rgb(13,72,78)",
            color4: "rgb(16,128,135)",
            colorFont: "rgb(255,255,255)",
            colorFont2: "yellow"
        }, {
            color1: "rgb(255,255,255)",
            color2: "rgb(222,225,230)",
            color3: "rgb(223,249,252)",
            color4: "rgb(241,243,244)",
            colorFont: "black",
            colorFont2: "blue"
        }, {
            color1: "rgb(183,225,228)",
            color2: "rgb(90,180,193)",
            color3: "rgb(255,255,255)",
            color4: "rgb(225,225,225)",
            colorFont: "rgb(93,188,194)",
            colorFont2: "blue"
        }, {
            color1: "rgb(245,225,218)",
            color2: "rgb(227,171,154)",
            color3: "rgb(210,194,188)",
            color4: "rgb(255,217,207)",
            colorFont: "rgb(215,136,109)",
            colorFont2: "black"
        }, {
            color1: "rgb(203,220,247)",
            color2: "rgb(132,173,234)",
            color3: "rgb(255,255,255)",
            color4: "rgb(196,224,224)",
            colorFont: "rgb(86,141,229)",
            colorFont2: "green"
        }, {
            color1: "rgb(68,87,96)",
            color2: "rgb(43,55,61)",
            color3: "rgb(56,69,76)",
            color4: "rgb(186,193,193)",
            colorFont: "rgb(255,255,255)",
            colorFont2: "white"
        }, {
            color1: "rgb(109,65,161)",
            color2: "rgb(70,42,104)",
            color3: "rgb(86,55,124)",
            color4: "rgb(145,78,193)",
            colorFont: "rgb(255,255,255)",
            colorFont2: "white"
        }, {
            color1: "#05668D",
            color2: "#028090",
            color3: "#B2DBBF",
            color4: "#02C39A",
            colorFont: "white",
            colorFont2: "#F0F3BD"
        }, {
            color1: "#607D8B",
            color2: "#455A64",
            color3: "#757575",
            color4: "#FFFFFF",
            colorFont: "#212121",
            colorFont2: "#BDBDBD"
        }, {
            color1: "#4CAF50",
            color2: "#689F38",
            color3: "#8BC34A",
            color4: "#FFFFFF",
            colorFont: "#212121",
            colorFont2: "#BDBDBD"
        }, {
            color1: "#03A9F4",
            color2: "#0288D1",
            color3: "#448AFF",
            color4: "#FFFFFF",
            colorFont: "#212121",
            colorFont2: "#BDBDBD"
        }, {
            color1: "#9DC8C8",
            color2: "#519D9E",
            color3: "#8CD790",
            color4: "#58C9B9",
            colorFont: "#212121",
            colorFont2: "#BDBDBD"
        }, {
            color1: "#278E9F",
            color2: "#10606B",
            color3: "#444678",
            color4: "#73C2B4",
            colorFont: "white",
            colorFont2: "#67797D"
        }, {
            color1: "#E4CD9B",
            color2: "#5E4F45",
            color3: "#BBA180",
            color4: "#FFEED0",
            colorFont: "green",
            colorFont2: "#67797D"
        }, {
            color1: "#34526C",
            color2: "#04403F",
            color3: "#203D3C",
            color4: "#483F38",
            colorFont: "white",
            colorFont2: "#BDBDBD"
        }]
    },
    mutations: {
        // 修改token，并将token存入localStorage
        changeLogin(state, user) {
            state.Authorization = user.Authorization;
            localStorage.setItem('Authorization', user.Authorization);
        },
        changecolor(state, aa) {
            let a = aa[0]
            let i = aa[1]
            if (i === 1) {
                state.color1 = a
            } else if (i === 2) {
                state.color2 = a
            } else if (i === 3) {
                state.color3 = a
            } else if (i === 4) {
                state.color4 = a
            } else if (i === "Font") {
                state.colorFont = a
            } else if (i === "Font2") {
                state.colorFont2 = a
            }
        },
        changecolorSelect(state, a) {
            state.colorNum = a
        }
    }
});
export default store;