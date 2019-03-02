import React from 'react'
import PropTypes from 'prop-types'
import { View } from 'native-base'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'

import styles from './styles'

const TrainerAccept = ({ acceptHandler, rejectHandler }) => {
  return (
    <View style={styles.inline}>
      <ButtonWithLoader title="Accept" onPressHandler={acceptHandler} small transparent />
      <ButtonWithLoader title="Reject" onPressHandler={rejectHandler} small transparent />
    </View>
  )
}

TrainerAccept.propTypes = {
  acceptHandler: PropTypes.func.isRequired,
  rejectHandler: PropTypes.func.isRequired,
}

export default TrainerAccept
