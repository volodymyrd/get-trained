import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Dimensions from 'Dimensions'
import { View } from 'react-native'
import Ionicons from 'react-native-vector-icons/Ionicons'
import InputDatePicker from 'App/Components/InputDatePicker'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import color from 'App/Theme/Colors'
import Metric from './Metric'

import style from './style'

export default class BodyProfile extends Component {

  saveProfile = () => {
    console.log('saveProfile:',
        this.datePicker.getSelectedFormattedDate(),
        this.neck.getSelectedValue(),
        this.chest.getSelectedValue())
  }

  render() {
    const { locale, measureDate } = this.props

    const height = Dimensions.get('window').height
    const width = Dimensions.get('window').width
    const size = height * 0.8
    const middle = width / 2
    const fontSize = 25
    const metricWidth = fontSize / 2

    const buttonPosition = {
      top: height / 8,
      left: (3 * width) / 4 - middle / 8,
      width: middle / 2,
    }

    const neckMetricPosition = {
      left: middle - metricWidth,
      top: height / 6.5,
    }

    const chestMetricPosition = {
      left: middle - metricWidth,
      top: height / 4.7,
    }

    return (
      <View>
        <InputDatePicker
          ref={(c) => (this.datePicker = c)}
          locale={locale}
          labelName={'Measure date:'}
          placeholder={'Select date'}
          date={measureDate}
        />
        <View style={style.body}>
          <Metric
            ref={(c) => (this.neck = c)}
            position={neckMetricPosition}
            fontSize={fontSize}
            value={50}
            min={0}
            max={100}
          />
          <Metric
            ref={(c) => (this.chest = c)}
            position={chestMetricPosition}
            fontSize={fontSize}
            value={50}
            min={0}
            max={100}
          />
          <Ionicons name="ios-man" size={size} color={color.primary} />
        </View>
        <ButtonWithLoader
          title={'save'}
          styles={[style.saveButton, buttonPosition]}
          // disabled={fetchingUpdateTraineeProfile}
          // loading={fetchingUpdateTraineeProfile}
          onPressHandler={this.saveProfile}
        />
      </View>
    )
  }
}

BodyProfile.propTypes = {
  locale: PropTypes.string.isRequired,
  measureDate: PropTypes.string.isRequired,
  // title: PropTypes.string.isRequired,
}
