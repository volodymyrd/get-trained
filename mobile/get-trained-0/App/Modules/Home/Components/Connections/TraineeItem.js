import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { View, Text, ListItem, Left, Thumbnail, Body } from 'native-base'
import styles from './styles'
import { getUrl } from 'App/Utils/HttpUtils'

class TraineeItem extends Component {
  render() {
    const { item } = this.props

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
          <Text note>{item.status}</Text>
        </Body>
      </ListItem>
    )
  }
}

TraineeItem.propTypes = {
  item: PropTypes.object.isRequired,
}

export default TraineeItem
