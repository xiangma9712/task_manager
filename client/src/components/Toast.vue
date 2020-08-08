<template>
  <div>
    <div
      v-if="isOpen"
      transition="fade"
      :class="`float-center notification is-light ${color}`"
    >
      {{ message }}
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Watch, Vue } from "vue-property-decorator";
const sleep = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));

@Component
export default class Toast extends Vue {
  @Prop()
  public message!: string;

  @Prop({ default: 2000 })
  public duration: number;

  @Prop({ default: "is-success" })
  public color: string;

  public isOpen: true | false = false;

  public async open() {
    this.isOpen = true;
    await sleep(this.duration);
    this.isOpen = false;
  }

  @Watch("message")
  public async messageChanged() {
    if (this.message === "") {
      return;
    }
    this.open();
  }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave {
  opacity: 0;
}
</style>
