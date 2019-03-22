import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Modal} from 'react-native'
import {
  View,
  Header,
  Body,
  Footer,
  Left,
  Right,
  Title,
  Icon,
  Button,
} from 'native-base'

import style from './style'
import TraineeItem from "../../Modules/Home/Components/Connections/TraineeItem";

export default class ModalDialog extends Component {
  state = {
    modalVisible: false,
  };

  setModalVisible(visible) {
    this.setState({modalVisible: visible});
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
                {this.props.children}
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
