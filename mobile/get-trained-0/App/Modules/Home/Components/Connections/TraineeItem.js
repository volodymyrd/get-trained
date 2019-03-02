import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { View, Text, ListItem, Left, Thumbnail, Body } from 'native-base'
import { getUrl } from 'App/Utils/HttpUtils'
import { Confirm } from 'App/Components/Alert'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import { titleConnectionDeleteRequest, confirmConnectionDeleteRequest } from '../../Metadata'

import styles from './styles'

const Pending = ({ connectionId, deleteHandler, localizations, fetches }) => {
  return (
    <View style={{ flexDirection: 'row' }}>
      <View style={{ justifyContent: 'center' }}>
        <Text note>{'pending...'}</Text>
      </View>
      <ButtonWithLoader
        title="Delete"
        loading={fetches.fetchingDeleteConnection}
        onPressHandler={() =>
          Confirm({
            title: titleConnectionDeleteRequest(localizations),
            message: confirmConnectionDeleteRequest(localizations),
            ok: () => deleteHandler(connectionId),
            localizations: localizations,
          })
        }
        small
        transparent
      />
    </View>
  )
}

Pending.propTypes = {
  connectionId: PropTypes.number.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

class TraineeItem extends Component {
  render() {
    const { item, deleteHandler, localizations, fetches } = this.props

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
          {item.status === 'PENDING_ON_TRAINEE' && (
            <Pending
              connectionId={item.connectionId}
              deleteHandler={deleteHandler}
              localizations={localizations}
              fetches={fetches}
            />
          )}
        </Body>
      </ListItem>
    )
  }
}

TraineeItem.propTypes = {
  item: PropTypes.object.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default TraineeItem
