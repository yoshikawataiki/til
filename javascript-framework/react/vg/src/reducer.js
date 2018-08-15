let lastId = 2;

export function rootReducer(state = { tasks: [] }, action) {
  switch (action.type) {
    case "SET_TASKS":
      return { tasks: action.tasks };
    case "ADD_TASK":
      return { tasks: [...state.tasks, { id: lastId++, title: action.title }] };
  }
  return state;
}
