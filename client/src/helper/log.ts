import moment from "moment";
import { Task } from "./task";

export type Log = {
  id: number;
  task: Task;
  logDate: string;
  usedHour: number;
  progress: number;
  memo?: string;
};

export type LogAddRequest = {
  taskId: number;
  logDate: string;
  usedHour: number;
  progress: number;
  memo?: string;
};

export function createLogWithTask(taskId: number | undefined) {
  const today = moment().format("YYYY-MM-DD");
  return {
    taskId: taskId ? taskId : 0,
    logDate: today,
    usedHour: 0,
    progress: 0
  };
}
