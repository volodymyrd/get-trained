import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Button, Input, Item, View} from 'native-base'
import Ionicons from 'react-native-vector-icons/Ionicons';

import color from 'App/Theme/Colors'

class ChatInput extends Component {
  constructor(props) {
    super(props)
    this.state = {
      message: '',
    }
  }

  _changeTextHandler = (text) => {
    this.setState({
      message: text,
    })
  }

  _sendMessage = () => {
    this.props.sendHandler(this.state.message)

    this.setState({
      message: '',
    })
  }

  render() {
    const {sendDisabled, placeholder} = this.props
    const {message} = this.state

    return (
        <View style={{marginBottom: 30, alignItems: 'center'}}>
          <Item rounded style={{width: '90%'}}>
            <Input placeholder={placeholder}
                   value={message}
                   onChangeText={this._changeTextHandler}/>
            <Button transparent
                    disabled={sendDisabled}
                    style={{marginRight: 10}}
                    onPress={this._sendMessage}>
              <Ionicons name="ios-send" size={35} color={color.primary}/>
            </Button>
          </Item>
        </View>
    )
  }
}

ChatInput.propTypes = {
  sendDisabled: PropTypes.bool.isRequired,
  placeholder: PropTypes.string.isRequired,
  sendHandler: PropTypes.func.isRequired,
}

export default ChatInput
