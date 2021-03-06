<template>
  <div class="container">
    <h2 class="is-size-3 has-margin-bottom-10">Task List</h2>
    <toast :message="toastMessage" />
    <div class="box">
      <button class="button is-primary" @click="addTask">追加</button>
      <table v-if="isWideEnough" class="table is-fullwidth">
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
                class="button is-small is-info has-margin-left-5 has-margin-right-5"
                @click="recordLog(task)"
              >
                記録
              </button>
              <router-link
                :to="`/task/${task.id}`"
                class="button is-small has-margin-left-5 has-margin-right-5"
                tag="button"
                >詳細</router-link
              >
            </td>
          </tr>
        </tbody>
      </table>
      <table v-else class="table is-fullwidth">
        <thead>
          <th>タイトル</th>
          <th>種類</th>
          <th>操作</th>
        </thead>
        <tbody>
          <tr v-for="(task, index) in tasks" :key="index">
            <td>{{ task.title }}</td>
            <td>
              <span class="tag">{{ task.taskType }}</span>
            </td>
            <td>
              <router-link
                :to="`/task/${task.id}`"
                class="button is-small has-margin-left-5 has-margin-right-5"
                tag="button"
                >詳細</router-link
              >
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="modal" :class="{ 'is-active': isEditingTask }">
      <div class="modal-background"></div>
      <task-input
        :taskArg="editingTask"
        :isNew="true"
        @close="close"
        @closeWithSuccessMessage="closeWithSuccessMessage"
      />
    </div>
    <div class="modal" :class="{ 'is-active': isRecodingLog }">
      <div class="modal-background"></div>
      <log-input
        :task="editingTask"
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
import LogInput from "@/components/LogInput.vue";

const enum SystemStatus {
  SHOWING_LIST,
  ADDING_TASK,
  RECORDING_LOG
}

@Component({
  components: {
    TaskInput,
    LogInput,
    Toast
  }
})
export default class Home extends Vue {
  /** data */
  public tasks: Array<Task> = [];
  public editingTask: Task = EMPTY_TASK;
  public status: SystemStatus = SystemStatus.SHOWING_LIST;
  public toastMessage = "";
  public width = window.innerWidth;

  /** computed */
  get userId() {
    return 1;
  }
  get isEditingTask() {
    return this.status === SystemStatus.ADDING_TASK;
  }
  get isRecodingLog() {
    return this.status === SystemStatus.RECORDING_LOG;
  }

  get isWideEnough() {
    return this.width >= 620;
  }

  /** life cycle */
  created() {
    this.refresh();
  }

  mounted() {
    window.addEventListener("resize", this.handleResize);
  }

  /** methods */
  public async refresh() {
    try {
      const response = await this.$axios.get(`/task/${this.userId}`);
      this.$set(this, "tasks", response.data.tasks);
    } catch (error) {
      console.log(error);
    }
  }

  //filterは今後なくなる仕様のためmethodとして定義
  public isOver(deadlineStr: string) {
    const deadline = moment(deadlineStr);
    const now = moment();
    return now > deadline;
  }

  public addTask() {
    this.status = SystemStatus.ADDING_TASK;
  }

  public recordLog(task: Task) {
    this.$set(this, "editingTask", Object.assign({}, task));
    this.status = SystemStatus.RECORDING_LOG;
  }

  public close() {
    this.status = SystemStatus.SHOWING_LIST;
    this.$set(this, "editingTask", EMPTY_TASK);
  }

  public async closeWithSuccessMessage(payload: string) {
    this.toastMessage = "";
    await this.refresh();
    this.status = SystemStatus.SHOWING_LIST;
    this.toastMessage = payload;
    this.$set(this, "editingTask", EMPTY_TASK);
  }

  public handleResize() {
    this.width = window.innerWidth;
  }
}
</script>
