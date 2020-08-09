<template>
  <div class="modal-card">
    <header class="modal-card-head">
      <p class="modal-card-title">作業記録</p>
      <button class="delete" aria-label="close" @click="close"></button>
    </header>
    <section class="modal-card-body">
      <div>対象タスク : {{ task.title }}</div>
      <div class="field">
        <label for="logDate">作業日時</label>
        <div class="control">
          <input type="date" class="input" v-model="requeset.logDate" />
        </div>
      </div>
      <div class="field">
        <label for="usedHour">作業時間</label>
        <input type="number" class="input" v-model="requeset.usedHour" />
      </div>
      <div class="field">
        <label for="progress">進捗</label>
        <input type="number" class="input" v-model="requeset.progress" />
      </div>
      <div class="field">
        <label for="memo">メモ</label>
        <textarea class="textarea" v-model="requeset.memo" />
      </div>
    </section>
    <footer class="modal-card-foot">
      <button class="button is-warning is-light" @click="close">
        キャンセル
      </button>
      <button class="button is-primary is-light" @click="save">
        記録
      </button>
    </footer>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Emit, Watch, Vue } from "vue-property-decorator";
import { LogAddRequest, createLogWithTask } from "@/helper/log";
import { Task } from "@/helper/task";

type InputError = {
  code?: string;
  message: string;
};

@Component
export default class LogInput extends Vue {
  @Prop()
  public task!: Task;

  public requeset: LogAddRequest = createLogWithTask(this.task.id);
  public errors: Array<InputError> = [];

  @Emit("close")
  public close() {
    this.errors.splice(0);
  }

  @Emit("closeWithSuccessMessage")
  public closeWithSuccessMessage(payload: string) {
    return payload;
  }

  public async save() {
    try {
      this.requeset.taskId = this._getNum(this.task.id);
      await this.$axios.post("/log/", this.requeset);
      const message = "記録に成功しました。";
      this.$set(this, "request", createLogWithTask(this.task.id));
      this.closeWithSuccessMessage(message);
    } catch (error) {
      this.errors.push({ code: error.code, message: error.message });
    }
  }

  private _getNum(id: number | undefined) {
    return id ? id : 0;
  }

  @Watch("task")
  public argChanged() {
    this.$set(this, "request", createLogWithTask(this.task.id));
  }
}
</script>

<style></style>
