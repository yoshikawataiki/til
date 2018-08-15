import * as React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import { createStore } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";

import { setTasks } from "./actions";
import { rootReducer } from "./reducer";
import { App } from "./components/App";

const store = createStore(rootReducer, composeWithDevTools());

const view = (
  <Provider store={store}>
    <App />
  </Provider>
);
render(view, document.getElementById("root"));

const initialTasks = [
  { id: 1, title: "家に帰る" },
  { id: 2, title: "橙に行く" }
];

store.dispatch(setTasks(initialTasks));
