import * as React from "react";

import Typography from "@material-ui/core/Typography";

import { ConnectedTodoItemList, ConnectedNewTodoItem } from "../containers";

export const App = () => (
  <div>
    <Typography variant="display1" gutterBottom={true}>
      Todos
    </Typography>
    <ConnectedTodoItemList />
    <ConnectedNewTodoItem />
  </div>
);
