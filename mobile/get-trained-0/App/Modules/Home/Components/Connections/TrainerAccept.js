import React from 'react'
import PropTypes from 'prop-types'
import { Col, Grid } from 'react-native-easy-grid'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'

const TrainerAccept = ({ acceptHandler, rejectHandler }) => {
  return (
    <Grid>
      <Col>
        <ButtonWithLoader title="Accept" onPressHandler={acceptHandler} small transparent />
      </Col>
      <Col>
        <ButtonWithLoader
          title="Reject"
          onPressHandler={rejectHandler}
          small
          transparent
          style={{ marginLeft: -70 }}
        />
      </Col>
    </Grid>
  )
}

TrainerAccept.propTypes = {
  acceptHandler: PropTypes.func.isRequired,
  rejectHandler: PropTypes.func.isRequired,
}

export default TrainerAccept
