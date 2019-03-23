import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Dimensions from 'Dimensions'
import { View, Text } from 'react-native'
// import {View} from 'native-base'
import Ionicons from 'react-native-vector-icons/Ionicons'
import InputDatePicker from 'App/Components/InputDatePicker'
import color from 'App/Theme/Colors'

import style from './style'

export default class BodyProfile extends Component {
  render() {
    const { locale } = this.props

    const height = Dimensions.get('window').height
    const weight = Dimensions.get('window').weight
    const size = height * 0.8
    const middle = weight / 2

    const neckPosition = {
      left: middle,
      top: height / 6,
    }

    return (
      <View>
        <InputDatePicker
          ref={(c) => (this.datePicker = c)}
          locale={locale}
          labelName={'Measure date:'}
          placeholder={'Select date'}
          // date={traineeProfile.get('birthdayStr')}
        />
        <View style={style.body}>
          <Text style={[style.pos, neckPosition]}>10</Text>
          <Ionicons name="ios-man" size={size} color={color.primary} />
        </View>
      </View>
    )
  }
}

BodyProfile.propTypes = {
  locale: PropTypes.string.isRequired,
  // title: PropTypes.string.isRequired,
}
