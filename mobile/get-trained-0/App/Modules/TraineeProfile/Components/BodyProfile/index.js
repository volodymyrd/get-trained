import React, {Component} from 'react'
import PropTypes from 'prop-types'
import Dimensions from 'Dimensions'
import {View} from 'react-native'
import Ionicons from 'react-native-vector-icons/Ionicons'
import InputDatePicker from 'App/Components/InputDatePicker'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import color from 'App/Theme/Colors'
import Metric from './Metric'

import style from './style'

export default class BodyProfile extends Component {

  saveProfile = () => {
    const {traineeFitnessProfile} = this.props
    let traineeProfileId = null
    if (traineeFitnessProfile.traineeProfileId) {
      traineeProfileId = traineeFitnessProfile.traineeProfileId
    } else if (this.props.traineeProfileId) {
      traineeProfileId = this.props.traineeProfileId
    }

    this.props.fetchUpdateTraineeFitnessProfile({
      traineeProfileId,
      traineeUserId: traineeFitnessProfile.traineeUserId,
      measure: this.datePicker.getSelectedFormattedDate(),
      neck: this.neck.getSelectedValue(),
      chest: this.chest.getSelectedValue(),
    })
  }

  render() {
    const {
      locale,
      traineeFitnessProfile,
      fetchingUpdateTraineeFitnessProfile
    } = this.props

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
              date={traineeFitnessProfile.measure}
          />
          <View style={style.body}>
            <Metric
                ref={(c) => (this.neck = c)}
                position={neckMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.neck || 0}
                min={0}
                max={100}
            />
            <Metric
                ref={(c) => (this.chest = c)}
                position={chestMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.chest || 0}
                min={0}
                max={100}
            />
            <Ionicons name="ios-man" size={size} color={color.primary}/>
          </View>
          <ButtonWithLoader
              title={'save'}
              styles={[style.saveButton, buttonPosition]}
              disabled={fetchingUpdateTraineeFitnessProfile}
              loading={fetchingUpdateTraineeFitnessProfile}
              onPressHandler={this.saveProfile}
          />
        </View>
    )
  }
}

BodyProfile.propTypes = {
  locale: PropTypes.string.isRequired,
  fetchingUpdateTraineeFitnessProfile: PropTypes.bool.isRequired,
  fetchUpdateTraineeFitnessProfile: PropTypes.func.isRequired,
  traineeProfileId: PropTypes.number,
}
