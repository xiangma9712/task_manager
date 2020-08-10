<template>
  <div class="container">
    <h2 class="is-size-3 has-margin-bottom-10">Sign Up Page</h2>
    <div class="box">
      <div v-if="isInitial">
        <div class="columns">
          <div class="column">
            <div class="field">
              <label for="username">ユーザー名</label>
              <div class="control">
                <input
                  type="text"
                  class="input"
                  v-model="signupRequest.username"
                />
              </div>
            </div>
          </div>
          <div class="column">
            <div class="field">
              <label for="username">メールアドレス</label>
              <div class="control">
                <input
                  type="text"
                  class="input"
                  v-model="signupRequest.attributes.email"
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
                  v-model="signupRequest.password"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="columns">
          <div class="column">
            <button class="button is-primary" @click="register">登録</button>
          </div>
        </div>
      </div>
      <div v-else>
        <div class="columns">
          <div class="column">
            <div class="field">検証コード</div>
            <div class="contro">
              <input type="text" class="input" v-model="code" />
            </div>
          </div>
        </div>
        <div class="columns">
          <div class="column">
            <button class="button" @click="register">確定</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
enum Staus {
  REGISTER,
  CONFIRM
}

@Component
export default class Login extends Vue {
  public signupRequest: {
    username: string;
    password: string;
    attributes: {};
  } = { username: "", password: "", attributes: { email: "" } };
  public state = Staus.REGISTER;
  public code = "";

  get isInitial() {
    return this.state === Staus.REGISTER;
  }

  public async register() {
    await this.$store.dispatch("cognito/registerUser", this.signupRequest);
    this.state = Staus.CONFIRM;
  }

  public async confirm() {
    await this.$store.dispatch("cognito/confirmUser", {
      username: this.signupRequest.username,
      code: this.code
    });
    this.$router.push("/");
  }
}
</script>

<style></style>
