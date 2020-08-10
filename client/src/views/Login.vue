<template>
  <div class="container">
    <h2 class="is-size-3 has-margin-bottom-10">Login Page</h2>
    <div class="box">
      <div class="columns">
        <div class="column">
          <div class="field">
            <label for="username">ユーザー名</label>
            <div class="control">
              <input
                type="text"
                class="input"
                v-model="loginRequest.username"
              />
            </div>
          </div>
        </div>
        <div class="column">
          <div class="field">
            <label for="password">パスワード</label>
            <div class="contorl">
              <input
                type="password"
                class="input"
                v-model="loginRequest.password"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column">
          <button class="button is-primary" @click="login">ログイン</button>
        </div>
      </div>
      <p>
        ユーザー登録が済んでいない？
        <router-link tag="a" to="/signup">Sign Up</router-link>
      </p>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Login extends Vue {
  public loginRequest: { username: string; password: string } = {
    username: "",
    password: ""
  };

  public async login() {
    await this.$store.dispatch("cognito/signInUser", this.loginRequest);
    if (this.$store.getters["cognito/isLoggedIn"]) {
      this.$router.push("/");
    }
  }
}
</script>

<style></style>
