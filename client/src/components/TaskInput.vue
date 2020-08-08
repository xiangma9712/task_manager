<template>
  <div class="modal-card">
    <header class="modal-card-head">
      <p class="modal-card-title">タスク{{ isNew ? "追加" : "編集" }}</p>
      <button class="delete" aria-label="close" @click="close"></button>
    </header>
    <section class="modal-card-body">
      <div class="field">
        <label for="title">タイトル</label>
        <div class="control">
          <input type="text" class="input" v-model="task.title" />
        </div>
      </div>
      <div class="field">
        <label for="type">種類</label>
        <div class="control">
          <div class="select is-fullwidth">
            <select v-model="task.taskType">
              <option value="TODO">toDo</option>
              <option value="SEARCH">toSearch</option>
            </select>
          </div>
        </div>
      </div>
      <div class="field">
        <label for="deadline">期日</label>
        <div class="control">
          <input type="date" class="input" v-model="task.deadline" />
        </div>
      </div>
      <div class="field">
        <label for="description">説明</label>
        <textarea class="textarea" v-model="task.description" />
      </div>
    </section>
    <footer class="modal-card-foot">
      <button class="button is-warning is-light" @click="close">
        キャンセル
      </button>
      <button class="button is-primary is-light" @click="save">
        {{ isNew ? "保存" : "更新" }}
      </button>
    </footer>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Emit, Watch, Vue } from "vue-property-decorator";
import { Task } from "@/helper/task";

type InputError = {
  code?: string;
  message: string;
};

@Component
export default class TaskInput extends Vue {
  @Prop()
  public taskArg!: Task;
  @Prop()
  public isNew!: boolean;

  public task: Task = Object.assign({}, this.taskArg);
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
    const END_POINT = this.isNew ? "task/" : "task/update";
    try {
      await this.$axios.post(END_POINT, this.task);
      const message = `${this.isNew ? "保存" : "更新"}に成功しました。`;
      this.closeWithSuccessMessage(message);
    } catch (error) {
      this.errors.push({ code: error.code, message: error.message });
    }
  }

  @Watch("taskArg")
  public argChanged() {
    this.$set(this, "task", Object.assign({}, this.taskArg));
  }
}
</script>

<style></style>
