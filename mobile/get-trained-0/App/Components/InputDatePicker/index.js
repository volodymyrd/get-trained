import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {
  TouchableOpacity,
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
import {isIOS, isAndroid} from 'App/Utils/Utils'
import {formatToDDMMYYY, formatDDMMYYYToDate} from 'App/Utils/DateUtils'

import style from './style';

class DatePickerIOSWrapper extends Component {
  constructor(props) {
    super(props)

    this.state = {
      modalVisible: false,
      selectedDate: this.props.date ? this.props.date : new Date(),
      callback: undefined
    }
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
    const {locale} = this.props
    const {
      modalVisible,
      selectedDate,
      callback
    } = this.state
    return (
        <View style={style.iosModalView}>
          <Modal animationType="slide"
                 transparent={false}
                 visible={modalVisible}
                 onRequestClose={() => {
                   //console.log('close modal');
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
                               locale={locale}
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
  constructor(props) {
    super(props)

    this.state = {
      selectedDate: this.props.date ?
          formatDDMMYYYToDate(this.props.date, '.') : null,
    }
  }

  getSelectedFormattedDate = () => {
    date = this.getSelectedDate()
    if (date) {
      return formatToDDMMYYY(date, '.')
    }
    return date
  }

  getSelectedDate = () => {
    const {selectedDate} = this.state
    return selectedDate instanceof Date ? selectedDate : null
  }

  async open() {
    if (isAndroid()) {
      const {selectedDate} = this.state
      try {
        const {action, year, month, day} = await DatePickerAndroid.open({
          date: selectedDate ?
              new Date(
                  selectedDate.getFullYear(),
                  selectedDate.getMonth(),
                  selectedDate.getDate())
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
    } else if (isIOS()) {
      this.ios.setModalVisible(true)
      this.setSelectedDate(await this.ios.getPromise())
    }
  }

  setSelectedDate(date) {
    this.setState({selectedDate: date});
  }

  render() {
    const {labelName, placeholder, locale} = this.props

    const selectedFormattedDate = this.getSelectedFormattedDate()

    return (
        <View>
          <DatePickerIOSWrapper ref={(c) => this.ios = c}
                                locale={locale}
                                date={this.getSelectedDate()}/>
          <Item picker>
            <TouchableOpacity
                style={isIOS() ? style.inputDatePickerViewIOS
                    : style.inputDatePickerViewAndroid}
                onPress={() => this.open()}>
              <Label>{labelName}</Label>
              <View style={isIOS() ? style.inputDatePickerViewIOS
                  : style.inputDatePickerViewAndroid}>
                <Label style={selectedFormattedDate ?
                    style.value : style.placeholder}>
                  {selectedFormattedDate ? selectedFormattedDate : placeholder}
                </Label>
              </View>
              <View style={style.selectButtonView}>
                <Button transparent onPress={() => this.open()}>
                  <Icon name="calendar" style={style.selectButton}/>
                </Button>
              </View>
            </TouchableOpacity>
          </Item>
        </View>
    )
  }
}

InputDatePicker.propTypes = {
  labelName: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  date: PropTypes.string,
  locale: PropTypes.string,
}
