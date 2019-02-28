import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { View, Text, ListItem, Left, Thumbnail, Body } from 'native-base'
import { Col, Grid } from 'react-native-easy-grid'
import { getUrl } from 'App/Utils/HttpUtils'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'

import styles from './styles'

const Pending = ({ deleteHandler }) => {
  return (
    <View style={{ flexDirection: 'row' }}>
      <View style={{ justifyContent: 'center' }}>
        <Text note>{'pending...'}</Text>
      </View>
      <ButtonWithLoader title="Delete" onPressHandler={deleteHandler} small transparent />
    </View>
  )
}

class TraineeItem extends Component {
  render() {
    const { item, deleteHandler } = this.props

    return (
      <ListItem avatar>
        <Left>
          {item.traineeLogoUrl ? (
            <Thumbnail source={{ uri: getUrl(item.traineeLogoUrl) }} />
          ) : (
            <View style={styles.avatar}>
              <Text>{item.traineeFullName.charAt(0)}</Text>
            </View>
          )}
        </Left>
        <Body>
          <Text>{item.traineeFullName}</Text>
          {item.status === 'PENDING_ON_TRAINEE' && <Pending deleteHandler={deleteHandler} />}
        </Body>
      </ListItem>
    )
  }
}

TraineeItem.propTypes = {
  item: PropTypes.object.isRequired,
}

export default TraineeItem
