<template>
  <div class="container">
    <toast :message="toastMessage" />
    <div class="box">
      <button class="button is-primary" @click="addTask">追加</button>
      <p>ToDoList</p>
      <table class="table is-fullwidth">
        <thead>
          <th>タイトル</th>
          <th>種類</th>
          <th>期日</th>
          <th>説明</th>
          <th>操作</th>
        </thead>
        <tbody>
          <tr v-for="(task, index) in tasks" :key="index">
            <td>{{ task.title }}</td>
            <td>
              <span class="tag">{{ task.taskType }}</span>
            </td>
            <td>
              <span :class="{ 'has-text-danger': isOver(task.deadline) }">{{
                task.deadline
              }}</span>
            </td>
            <td>{{ task.description }}</td>
            <td>
              <button
                class="button is-small is-danger"
                @click="deleteTask(task.id)"
              >
                削除
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="modal" :class="{ 'is-active': isEditing }">
      <div class="modal-background"></div>
      <task-input
        :taskArg="editingTask"
        :isNew="isEditingNew"
        @close="close"
        @closeWithSuccessMessage="closeWithSuccessMessage"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import moment from "moment";
import { Task, EMPTY_TASK } from "@/helper/task";
import TaskInput from "@/components/TaskInput.vue";
import Toast from "@/components/Toast.vue";

@Component({
  components: {
    TaskInput,
    Toast
  }
})
export default class Home extends Vue {
  //data
  public tasks: Array<Task> = [];
  public isEditing = false;
  public editingTask: Task = EMPTY_TASK;
  public isEditingNew = true;
  public toastMessage = "";
  //computed
  public userId() {
    return 1;
  }

  created() {
    this.refresh();
  }

  //methods
  public async refresh() {
    try {
      const response = await this.$axios.get(`/task/${this.userId()}`);
      this.$set(this, "tasks", response.data.tasks);
    } catch (error) {
      console.log(error);
    }
  }

  public isOver(deadlineStr: string) {
    const deadline = moment(deadlineStr);
    const now = moment();
    return now > deadline;
  }

  public addTask() {
    this.isEditingNew = true;
    this.isEditing = true;
  }

  public close() {
    this.isEditing = false;
    this.$set(this,"editingTask",EMPTY_TASK);
  }

  public async closeWithSuccessMessage(payload: string) {
    this.toastMessage = "";
    await this.refresh();
    this.isEditing = false;
    this.toastMessage = payload;
    this.$set(this,"editingTask",EMPTY_TASK);
  }

  public async deleteTask(id: number) {
    this.toastMessage = "";
    await this.$axios.delete(`task/${id}`);
    await this.refresh();
    this.toastMessage = "削除されました";
  }
}
</script>
