import React from 'react'
import PropTypes from 'prop-types'
import { Button, Text } from 'native-base'
import { Col, Grid } from 'react-native-easy-grid'

const TrainerAccept = ({ acceptHandler, rejectHandler }) => {
  return (
    <Grid>
      <Col>
        <Button small transparent onPress={acceptHandler}>
          <Text note>Accept</Text>
        </Button>
      </Col>
      <Col>
        <Button small transparent style={{ marginLeft: -70 }} onPress={rejectHandler}>
          <Text note>Reject</Text>
        </Button>
      </Col>
    </Grid>
  )
}

TrainerAccept.propTypes = {
  acceptHandler: PropTypes.func.isRequired,
  rejectHandler: PropTypes.func.isRequired,
}

export default TrainerAccept
