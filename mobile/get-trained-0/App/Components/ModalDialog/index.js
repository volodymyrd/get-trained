import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Modal} from 'react-native'
import {
  Root,
  View,
  Header,
  Body,
  Left,
  Right,
  Title,
  Icon,
  Button,
} from 'native-base'

import style from './style'

export default class ModalDialog extends Component {
  state = {
    modalVisible: false,
    params: {}
  };

  setModalVisible(visible, params) {
    this.setState({modalVisible: visible, params});
  }

  render() {
    const {title} = this.props
    const {modalVisible} = this.state
    return (
        <View style={style.modalView}>
          <Modal
              animationType="slide"
              transparent={false}
              visible={modalVisible}
              onRequestClose={() => {
                // console.log('close modal');
              }}
          >
            <View>
              <Header>
                <Left>
                  <Button transparent
                          onPress={() => this.setModalVisible(!modalVisible)}>
                    <Icon name="arrow-back"/>
                  </Button>
                </Left>
                <Body>
                <Title>{title}</Title>
                </Body>
                <Right/>
              </Header>
              <View>
                {React.cloneElement(
                    this.props.children,
                    {...this.state.params})}
              </View>
            </View>
          </Modal>
        </View>
    )
  }
}

ModalDialog.propTypes = {
  title: PropTypes.string.isRequired,
}
