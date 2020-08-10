import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import axios, { AxiosStatic } from "axios";
import attachCognitoModule from "@vuetify/vuex-cognito-module";
import store from "./store";
import cognitoConfig from "./assets/cognitoConfig";

attachCognitoModule(
  store,
  {
    userPoolId: cognitoConfig.UserPoolId,
    identityPoolId: cognitoConfig.IdentityPoolId,
    userPoolWebClientId: cognitoConfig.ClientId,
    region: cognitoConfig.Region
  },
  "cognito"
);

require("@/assets/main.scss");
require("bulma-spacing/bulma-spacing.scss");

Vue.config.productionTip = false;

axios.defaults.baseURL = "http://localhost:8080";
Vue.prototype.$axios = axios;

declare module "vue/types/vue" {
  interface Vue {
    $axios: AxiosStatic;
  }
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
