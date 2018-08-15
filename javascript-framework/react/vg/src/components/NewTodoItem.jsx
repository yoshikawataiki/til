import React from "react";
import PropTypes from "prop-types";

import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

export class NewTodoItem extends React.Component {
  constructor(props) {
    super(props);
    this.state = { title: "" };
  }
  setTitle(title) {
    this.setState({
      title: title
    });
  }
  register() {
    this.props.addTask(this.state.title);
  }
  render() {
    return (
      <div>
        <TextField
          id="title"
          label="Title"
          value={this.state.title}
          onChange={e => this.setTitle(e.target.value)}
          margin="normal"
        />
        <Button
          variant="contained"
          color="primary"
          onClick={() => this.register()}
        >
          ADD
        </Button>
      </div>
    );
  }
}
NewTodoItem.propTypes = {
  addTask: PropTypes.func
};
