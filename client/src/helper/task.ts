export type Task = {
  id?: number;
  title: string;
  taskType: string;
  description: string;
  deadline: string;
  assigneeId: number;
  done: boolean;
};

export const EMPTY_TASK = {
  title: "",
  taskType: "",
  description: "",
  deadline: "",
  assigneeId: 1,
  doing: false,
  done: false
};
