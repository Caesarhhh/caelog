<template>
  <div class="loginPage">
    <h1>Caelog Login</h1>
    <a-form
      id="components-form-demo-normal-login"
      :form="form"
      class="login-form"
    >
      <a-form-item>
        <a-input
          v-decorator="[
            'userName',
            {
              rules: [
                { required: true, message: 'Please input your username!' },
              ],
            },
          ]"
          placeholder="Username"
          v-model="inputName"
        >
          <a-icon
            slot="prefix"
            type="user"
            style="color: rgba(0, 0, 0, 0.25)"
          />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-input
          v-decorator="[
            'password',
            {
              rules: [
                { required: true, message: 'Please input your Password!' },
              ],
            },
          ]"
          type="password"
          placeholder="Password"
          v-model="inputPassword"
        >
          <a-icon
            slot="prefix"
            type="lock"
            style="color: rgba(0, 0, 0, 0.25)"
          />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-button
          @click="Submit"
          type="primary"
          html-type="submit"
          class="login-form-button"
        >
          Log in
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  name: "loginpage",
  props: ["datas"],
  data() {
    return {
      closeimgsrc: this.common.getserveraddress + "images/close2.png",
      inputName: "",
      inputPassword: "",
      userToken: "",
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
  },
  methods: {
    ...mapMutations(["changeLogin"]),
    getcolor1() {
      return { backgroundColor: this.$store.state.color1 };
    },
    getcolor2() {
      return { backgroundColor: this.$store.state.color2 };
    },
    getcolor3() {
      return { backgroundColor: this.$store.state.color3 };
    },
    getcolor4() {
      return { backgroundColor: this.$store.state.color4 };
    },
    Submit() {
      let v = this;
      let param = new FormData();
      param.append("nickname", this.inputName);
      param.append("password", this.inputPassword);
      this.$axios({
        method: "post",
        url: this.common.serveraddress + "/user/login",
        data: {
          nickname: this.inputName,
          password: this.inputPassword,
        },
      })
        .then((res) => {
          console.log(res);
          if (res.data.code == 400) {
            alert("密码或用户名错误");
            return;
          }
          this.common.userinfo = res.data.userinfo.data;
          this.common.loginuserinfo = res.data.userinfo.data;
          v.userToken = res.data.token;
          v.changeLogin({ Authorization: v.userToken });
          localStorage.setItem(
            "userinfo",
            JSON.stringify(res.data.userinfo.data)
          );
          v.$router.push("/" + this.common.loginuserinfo.id + "/mainpage");
          alert("登录成功");
          location.reload();
        })
        .catch(function (err) {
          alert("密码或用户名错误");
        });
    },
  },
};
</script>

<style scoped>
.loginPage {
  margin: 0 auto;
  margin-top: 60px;
  width: 50%;
}
#components-form-demo-normal-login {
  margin: 0 auto;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
</style>
