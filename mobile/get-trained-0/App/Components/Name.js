import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Item, Input, Label, Icon} from 'native-base'

class Name extends Component {

  constructor(props) {
    super(props);
    this.state = {
      valid: false,
    };
  }

  _changeTextHandler = (name) => {
    if (name.length >= this.props.minLength) {
      this.setState({
        valid: true,
      })
      this.props.onValid(name)
    } else {
      this.setState({
        valid: false,
      })
      this.props.onInValid()
    }
  }

  render() {
    const {txtName} = this.props
    const {valid} = this.state

    return (
        <Item floatingLabel success={valid} error={!valid}>
          <Label>{txtName}</Label>
          <Input email-address onChangeText={this._changeTextHandler}/>
          {valid ?
              <Icon name='checkmark-circle'/> :
              <Icon name='close-circle'/>}
        </Item>
    )
  }
}

Name.propTypes = {
  txtName: PropTypes.string.isRequired,
  minLength: PropTypes.number.isRequired,
  onValid: PropTypes.func.isRequired,
  onInValid: PropTypes.func.isRequired,
}

export default Name
