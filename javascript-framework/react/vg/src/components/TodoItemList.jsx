import * as React from "react";
import PropTypes from "prop-types";

export const TodoItemList = props => {
  const tasks = props.tasks.map((v, i) => <div key={i}>{v.title}</div>);
  return <div>{tasks}</div>;
};

TodoItemList.propTypes = { tasks: PropTypes.array };
