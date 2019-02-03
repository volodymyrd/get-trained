import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Item, Input, Label, Icon} from 'native-base'

class Password extends Component {

  constructor(props) {
    super(props);
    this.state = {
      valid: false,
      hidePassword: true,
    };
  }

  _changeTextHandler = (password) => {
    if (password.length >= this.props.minLength
        && (this.props.repeatPassword ? this.props.repeatPassword === password
            : true)) {
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

  _showHandler = () => {
    this.setState({
      hidePassword: false,
    })
  }

  _hideHandler = () => {
    this.setState({
      hidePassword: true,
    })
  }

  render() {
    const {txtPassword} = this.props
    const {valid, hidePassword} = this.state

    return (
        <Item floatingLabel success={valid} error={!valid}>
          <Label>{txtPassword}</Label>
          <Input secureTextEntry={hidePassword}
                 onChangeText={this._changeTextHandler}/>
          {hidePassword ?
              <Icon active name='eye-off' onPress={this._showHandler}/>
              :
              <Icon active name='eye' onPress={this._hideHandler}/>
          }
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
  repeatPassword: PropTypes.string,
}

export default Password
