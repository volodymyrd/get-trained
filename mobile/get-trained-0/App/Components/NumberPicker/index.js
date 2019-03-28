import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { TouchableOpacity } from 'react-native'
import { View, Item, Label, Text } from 'native-base'
import RNNumberPickerLibrary from 'react-native-number-picker-library'

import style from './style'

export default class NumberPicker extends Component {
  constructor(props) {
    super(props)

    this.state = {
      selectedValue: this.props.value && this.props.value !== 'null' ? this.props.value : 0,
    }
  }

  getSelectedValue() {
    return this.state.selectedValue
  }

  openSpinner(selectedValue, min, max) {
    RNNumberPickerLibrary.createDialog(
      {
        minValue: min,
        maxValue: max,
        selectedValue: selectedValue,
        doneText: 'OK', // only for Android
        doneTextColor: '#000000', // only for Android
        cancelText: 'Cancel', // only for Android
        cancelTextColor: '#000000', // only for Android
      },
      (error, data) => {
        if (error) {
          console.error(error)
        } else {
          this.setState({ selectedValue: data[0] })
        }
      },
      (error, data) => {
        if (error) {
          console.error(error)
        } else {
        }
      }
    )
  }

  render() {
    const { labelName, min, max } = this.props
    const { selectedValue } = this.state
    return (
      <Item style={style.item}>
        <TouchableOpacity
          style={style.touchView}
          onPress={() => this.openSpinner(selectedValue, min, max)}
        >
          {labelName && <Label>{labelName}</Label>}
          <View style={style.pickerView}>
            <Text>{selectedValue}</Text>
          </View>
        </TouchableOpacity>
      </Item>
    )
  }
}

NumberPicker.propTypes = {
  labelName: PropTypes.string,
  value: PropTypes.number,
  min: PropTypes.number.isRequired,
  max: PropTypes.number.isRequired,
}
