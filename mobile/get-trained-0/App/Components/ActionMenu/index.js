import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Modal} from 'react-native'
import {
  View,
  Title,
  Button,
  Text,
} from 'native-base'

import style from './style'

export default class ActionMenu extends Component {
  state = {
    modalVisible: false,
    params: {}
  };

  open = () => {
    this._setModalVisible(true)
  }

  close = () => {
    this._setModalVisible(false)
  }

  _setModalVisible(visible, params) {
    this.setState({modalVisible: visible, params});
  }

  render() {
    const {title, cancelBtnName, height} = this.props
    const {modalVisible} = this.state
    return (
        <View style={style.modalView}>
          <Modal
              animationType="slide"
              transparent={true}
              visible={modalVisible}
              onRequestClose={() => {
                // console.log('close modal');
              }}
          >
            <View style={style.wrapper}>
              <View style={[{height: height ? height : 100}, style.container]}>
                <View style={style.title}>
                  <Title>{title}</Title>
                </View>
                {React.cloneElement(
                    this.props.children,
                    {...this.state.params})}
                <View style={style.cancel}>
                  <Button full
                          large
                          transparent
                          onPress={this.close}>
                    <Text>{cancelBtnName}</Text>
                  </Button>
                </View>
              </View>
            </View>
          </Modal>
        </View>
    )
  }
}

ActionMenu.propTypes = {
  title: PropTypes.string.isRequired,
  cancelBtnName: PropTypes.string.isRequired,
  height: PropTypes.number
}
