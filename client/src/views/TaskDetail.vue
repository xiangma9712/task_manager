<template>
  <div class="container">
      <h2>Task Detail</h2>
      <div class="box">
          <h4>{{ task.title }}</h4>
          <p>進捗</p>
          <div class="row">
              <h4>作業記録</h4>
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
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { Task, EMPTY_TASK } from "@/helper/task";
import { Log } from "@/helper/log";

@Component
export default class TaskDetail extends Vue {
    /** data */
    public task: Task =EMPTY_TASK;
    public relatedLogs: Array<Log> = [];
    public progress: number;

    /** lify cycle */
    async created(){
        const id = this.$route.params.id;
        const taskResponse = await this.$axios.get(`/task/each/${id}`);
        this.$set(this,"task",taskResponse.data);

        const logResponse = await this.$axios.get(`/log/${id}`);
        this.progress = logResponse.data.sumProgress;
        this.$set(this,"relatedLogs",logResponse.data.logs);
    }

}
</script>

<style></style>