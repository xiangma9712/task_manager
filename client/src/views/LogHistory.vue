<template>
  <div class="container">
    <toast :message="toastMessage" />
    <div class="box">
      <p>Log History</p>
      <table class="table is-fullwidth">
        <thead>
          <th>タスク名</th>
          <th>作業日時</th>
          <th>作業時間</th>
          <th>進捗</th>
          <th>メモ</th>
          <th>操作</th>
        </thead>
        <tbody>
          <tr v-for="log in logs" :key="log.id">
            <td>{{ log.task.title }}</td>
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
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Toast from "@/components/Toast.vue";
import { Log } from "@/helper/log";

@Component({
  components: {
    Toast
  }
})
export default class LogHistory extends Vue {
  /** data */
  public logs: Array<Log> = [];
  public toastMessage = "";

  /** life cycle */
  async created() {
    await this.refresh();
  }

  /** computed */
  get userId() {
    return 1;
  }

  /** methods */
  public async refresh() {
    const response = await this.$axios.get(`/log/list/${this.userId}`);
    this.$set(this, "logs", response.data);
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
