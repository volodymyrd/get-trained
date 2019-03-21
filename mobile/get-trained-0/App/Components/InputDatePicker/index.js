import React, {Component} from 'react'
import {
  Platform,
  Modal,
  DatePickerIOS,
  DatePickerAndroid,
  Text,
} from 'react-native'
import {
  View,
  Header,
  Body,
  Footer,
  Left,
  Right,
  Title,
  Item,
  Label,
  Icon,
  Button,
} from 'native-base'
import {formatToDDMMYYY} from 'App/Utils/DateUtils'
import PropTypes from "prop-types";

import style from "./style";

class DatePickerIOSWrapper extends Component {
  state = {
    modalVisible: false,
    selectedDate: new Date(),
    callback: undefined
  }

  setModalVisible(visible) {
    this.setState({modalVisible: visible});
  }

  setSelectedDate(date) {
    this.setState({selectedDate: date});
  }

  setCallback(f) {
    this.setState({callback: f});
  }

  getPromise() {
    return new Promise((resolve, reject) => {
      this.setCallback(() => resolve(this.state.selectedDate))
    })
  }

  render() {
    const {date} = this.props
    const {modalVisible, selectedDate, callback} = this.state

    if (date) {
      this.setSelectedDate(date)
    }

    return (
        <View style={style.iosModalView}>
          <Modal animationType="slide"
                 transparent={false}
                 visible={modalVisible}
                 onRequestClose={() => {
                   console.log('close modal');
                 }}>
            <View>
              <Header>
                <Left>
                  <Button transparent
                          onPress={() => this.setModalVisible(!modalVisible)}>
                    <Icon name="arrow-back"/>
                  </Button>
                </Left>
                <Body>
                <Title>Select Date</Title>
                </Body>
                <Right/>
              </Header>
              <View>
                <DatePickerIOS date={selectedDate}
                               mode={'date'}
                               locale={'uk'}
                               onDateChange={(d) => this.setSelectedDate(d)}
                />
              </View>
              <Footer style={style.datePickerFooter}>
                <Button rounded style={style.okButton} onPress={() => {
                  this.setModalVisible(!modalVisible)
                  callback()
                }}>
                  <Text>OK</Text>
                </Button>
              </Footer>
            </View>
          </Modal>
        </View>
    )
  }
}

export default class InputDatePicker extends Component {
  state = {
    selectedDate: 'Select date',
  }

  getSelectedDate() {
    return this.state.selectedDate
  }

  async open() {
    if (Platform.OS === 'android') {
      const {date} = this.props.date
      try {
        const {action, year, month, day} = await DatePickerAndroid.open({
          date: date ?
              new Date(date.getFullYear(), date.getMonth(), date.getDate())
              : new Date(),
          mode: 'default'//(enum('calendar', 'spinner', 'default'))
        });
        if (action !== DatePickerAndroid.dismissedAction) {
          // Selected year, month (0-11), day
          this.setSelectedDate(new Date(year, month, day))
        }
      } catch ({code, message}) {
        console.warn('Cannot open date picker', message);
      }
    } else if (Platform.OS === 'ios') {
      this.ios.setModalVisible(true)
      this.setSelectedDate(await this.ios.getPromise())
    }
  }

  setSelectedDate(date) {
    const isDate = date instanceof Date

    this.setState({
      selectedDate: isDate ? formatToDDMMYYY(date, '.') : date
    });
  }

  render() {
    const {date, labelName} = this.props
    const {selectedDate} = this.state

    if (date) {
      this.setSelectedDate(date)
    }

    const isDate = selectedDate instanceof Date

    return (
        <View>
          <DatePickerIOSWrapper ref={(c) => this.ios = c} date={date}/>
          <Item picker onPress={() => this.open()}>
            <Label>{labelName}</Label>
            <View style={style.inputDatePickerView}>
              <Label style={isDate ? style.value : style.placeholder}>
                {selectedDate}
              </Label>
              <Button transparent onPress={() => this.open()}>
                <Icon name="arrow-down" style={style.downArrow}/>
              </Button>
            </View>
          </Item>
        </View>
    )
  }
}

InputDatePicker.propTypes = {
  labelName: PropTypes.string.isRequired,
  date: PropTypes.object,
  //onPressHandler: PropTypes.func.isRequired,
  //notFull: PropTypes.bool,
}
