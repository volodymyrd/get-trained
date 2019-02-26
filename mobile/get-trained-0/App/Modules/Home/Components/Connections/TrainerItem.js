import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { View, Text, ListItem, Left, Right, Thumbnail, Body } from 'native-base'
import styles from './styles'
import { getUrl } from 'App/Utils/HttpUtils'
import TrainerAccept from './TrainerAccept'

class TrainerItem extends Component {
  render() {
    const { item } = this.props

    return (
      <ListItem avatar>
        <Left>
          {item.trainerLogoUrl ? (
            <Thumbnail source={{ uri: getUrl(item.trainerLogoUrl) }} />
          ) : (
            <View style={styles.avatar}>
              <Text>{item.trainerFullName.charAt(0)}</Text>
            </View>
          )}
        </Left>
        <Body>
          <Text>{item.trainerFullName}</Text>
          {item.status === 'PENDING_ON_TRAINEE' && (
            <TrainerAccept
              acceptHandler={() => console.log('accept')}
              rejectHandler={() => console.log('reject')}
            />
          )}
        </Body>
        <Right />
      </ListItem>
    )
  }
}

TrainerItem.propTypes = {
  item: PropTypes.object.isRequired,
}

export default TrainerItem
