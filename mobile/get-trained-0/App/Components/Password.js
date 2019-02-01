import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Item, Input, Label, Icon} from 'native-base'

class Password extends Component {

  constructor(props) {
    super(props);
    this.state = {
      valid: false,
    };
  }

  _changeTextHandler = (password) => {
    if (password.length >= this.props.minLength) {
      this.setState({
        valid: true,
      })
      this.props.onValid(password)
    } else {
      this.setState({
        valid: false,
      })
      this.props.onInValid()
    }
  }

  render() {
    const {txtPassword} = this.props
    const {valid} = this.state

    return (
        <Item floatingLabel success={valid} error={!valid}>
          <Label>{txtPassword}</Label>
          <Input secureTextEntry onChangeText={this._changeTextHandler}/>
          {valid ?
              <Icon name='checkmark-circle'/> :
              <Icon name='close-circle'/>}
        </Item>
    )
  }
}

Password.propTypes = {
  txtPassword: PropTypes.string.isRequired,
  minLength: PropTypes.number.isRequired,
  onValid: PropTypes.func.isRequired,
  onInValid: PropTypes.func.isRequired,
}

export default Password
