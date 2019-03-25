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
      waist: this.waist.getSelectedValue(),
      hips: this.hips.getSelectedValue(),
      innerThigh: this.innerThigh.getSelectedValue(),
      calf: this.calf.getSelectedValue(),
      biceps: this.biceps.getSelectedValue(),
      forearm: this.forearm.getSelectedValue(),
      wrist: this.wrist.getSelectedValue(),
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
    const metricWidth2 = fontSize / 2
    const metricWidth3 = fontSize / 3

    const buttonPosition = {
      top: height / 8,
      left: (3 * width) / 4 - middle / 8,
      width: middle / 2,
    }

    const neckMetricPosition = {
      left: middle - metricWidth2,
      top: height / 6.5,
    }

    const chestMetricPosition = {
      left: middle - 2 * metricWidth3,
      top: height / 4.7,
    }

    const waistMetricPosition = {
      left: middle - metricWidth2,
      top: height / 2.4,
    }

    const hipsMetricPosition = {
      left: 5 * middle / 4.1 - metricWidth2,
      top: height / 2.1,
    }

    const innerThighMetricPosition = {
      left: 5 * middle / 5.9 - metricWidth2,
      top: height / 1.95,
    }

    const calfMetricPosition = {
      left: 5 * middle / 6.5 - metricWidth2,
      top: height / 1.4,
    }

    const bicepsMetricPosition = {
      left: 3 * middle / 7.8 - metricWidth2,
      top: height / 3.3,
    }

    const forearmMetricPosition = {
      left: 5 * middle / 3.1 - metricWidth2,
      top: height / 2.8,
    }

    const wristMetricPosition = {
      left: 3 * middle / 7.8 - metricWidth2,
      top: height / 2.4,
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
                value={traineeFitnessProfile.neck || 30}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.chest = c)}
                position={chestMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.chest || 100}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.waist = c)}
                position={waistMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.waist || 70}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.hips = c)}
                position={hipsMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.hips || 90}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.innerThigh = c)}
                position={innerThighMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.innerThigh || 60}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.calf = c)}
                position={calfMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.calf || 35}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.biceps = c)}
                position={bicepsMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.biceps || 30}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.forearm = c)}
                position={forearmMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.forearm || 25}
                min={0}
                max={200}
            />

            <Metric
                ref={(c) => (this.wrist = c)}
                position={wristMetricPosition}
                fontSize={fontSize}
                value={traineeFitnessProfile.wrist || 15}
                min={0}
                max={200}
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
