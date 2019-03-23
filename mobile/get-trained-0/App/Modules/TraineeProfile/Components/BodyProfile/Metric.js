import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Text } from 'react-native'
import RNNumberPickerLibrary from 'react-native-number-picker-library'

import style from './style'

export default class Metric extends Component {
  constructor(props) {
    super(props)

    this.state = {
      selectedValue: this.props.value ? this.props.value : 0,
    }
  }

  getSelectedValue() {
    return this.state.selectedValue
  }

  render() {
    const { position, fontSize, min, max } = this.props
    const { selectedValue } = this.state

    return (
      <Text
        style={[style.metric, position, { fontSize: fontSize }]}
        onPress={() =>
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
      >
        {selectedValue}
      </Text>
    )
  }
}

Metric.propTypes = {
  position: PropTypes.object.isRequired,
  fontSize: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
  min: PropTypes.number.isRequired,
  max: PropTypes.number.isRequired,
}
