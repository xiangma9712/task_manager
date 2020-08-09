<template>
  <div class="container">
    <h2 class="is-size-3 has-margin-bottom-10">Task Detail</h2>
    <toast :message="toastMessage" />
    <div class="box">
      <h3 class="has-text-info-dark is-size-5 has-padding-bottom-10">
        {{ task.title }}
      </h3>
      <div class="columns">
        <div class="column is-4">
          <p>進捗 : {{ progress }}%</p>
        </div>
        <div class="column is-4">
          <p>期日 : {{ task.deadline }}</p>
        </div>
        <div class="column is-4">
          <p>
            ステータス :
            <span :class="`tag ${getStatus.color}`">{{
              getStatus.status
            }}</span>
          </p>
        </div>
      </div>
      <div class="columns">
        <div class="column">
          {{ task.description }}
        </div>
      </div>
      <div class="columns">
        <div class="column">
          <button
            class="button is-light is-info has-margin-left-5 has-margin-right-5"
            @click="startEdit"
          >
            編集
          </button>
          <button
            class="button is-light is-primary has-margin-left-5 has-margin-right-5"
            @click="recordLog"
          >
            記録
          </button>
          <button
            class="button is-light is-danger has-margin-left-5 has-margin-right-5"
            @click="deleteTask"
          >
            削除
          </button>
        </div>
      </div>
      <div class="has-margin-top-20">
        <p>作業ログ一覧</p>
      </div>
      <div>
        <table class="table is-fullwidth">
          <thead>
            <th>作業日時</th>
            <th>作業時間</th>
            <th>進捗</th>
            <th>メモ</th>
            <th>操作</th>
          </thead>
          <tbody>
            <tr v-for="log in relatedLogs" :key="log.id">
              <td>{{ log.logDate }}</td>
              <td>{{ log.usedHour }}</td>
              <td>{{ log.progress }}</td>
              <td>{{ log.memo }}</td>
              <td>
                <button
                  class="button is-small is-danger"
                  @click="deleteLog(log.id)"
                >
                  削除
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="modal" :class="{ 'is-active': isEditingTask }">
      <div class="modal-background"></div>
      <task-input
        :taskArg="task"
        :isNew="false"
        @close="close"
        @closeWithSuccessMessage="closeWithSuccessMessage"
      />
    </div>
    <div class="modal" :class="{ 'is-active': isRecodingLog }">
      <div class="modal-background"></div>
      <log-input
        :task="task"
        @close="close"
        @closeWithSuccessMessage="closeWithSuccessMessage"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { Task, EMPTY_TASK } from "@/helper/task";
import { Log } from "@/helper/log";
import LogInput from "@/components/LogInput.vue";
import TaskInput from "@/components/TaskInput.vue";
import Toast from "@/components/Toast.vue";

const enum SystemStatus {
  HOME,
  EDITING_TASK,
  RECORDING_LOG
}

@Component({
  components: {
    LogInput,
    TaskInput,
    Toast
  }
})
export default class TaskDetail extends Vue {
  /** data */
  public task: Task = EMPTY_TASK;
  public relatedLogs: Array<Log> = [];
  public progress = 0;
  public status: SystemStatus = SystemStatus.HOME;
  public toastMessage = "";

  /** lify cycle */
  async created() {
    await this.refresh();
  }

  /** computed */
  get getStatus() {
    if (this.task.done) {
      return { status: "完了", color: "is-dark" };
    }
    if (this.relatedLogs.length === 0) {
      return { status: "手つかず", color: "is-warning" };
    }
    return { status: "進行中", color: "is-primary" };
  }

  get isRecodingLog() {
    return this.status === SystemStatus.RECORDING_LOG;
  }

  get isEditingTask() {
    return this.status === SystemStatus.EDITING_TASK;
  }

  /** methods */
  public async refresh() {
    const id = this.$route.params.id;
    const taskResponse = await this.$axios.get(`/task/each/${id}`);
    this.$set(this, "task", taskResponse.data);

    const logResponse = await this.$axios.get(`/log/${id}`);
    this.progress = logResponse.data.sumProgress;
    this.$set(this, "relatedLogs", logResponse.data.logs);
  }

  public async deleteTask() {
    await this.$axios.delete(`task/${this.task.id}`);
    this.$router.push("/");
  }

  public startEdit() {
    this.status = SystemStatus.EDITING_TASK;
  }

  public recordLog() {
    this.status = SystemStatus.RECORDING_LOG;
  }

  public close() {
    this.status = SystemStatus.HOME;
  }

  public closeWithSuccessMessage(payload: string) {
    this.toastMessage = "";
    this.status = SystemStatus.HOME;
    this.refresh();
    this.toastMessage = payload;
  }

  public async deleteLog(logId: number) {
    this.toastMessage = "";
    await this.$axios.delete(`/log/${logId}`);
    await this.refresh();
    this.toastMessage = "削除しました。";
  }
}
</script>

<style></style>
