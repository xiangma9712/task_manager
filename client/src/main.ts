import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import axios, { AxiosStatic } from "axios";

require("@/assets/main.scss");

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
  render: h => h(App)
}).$mount("#app");
