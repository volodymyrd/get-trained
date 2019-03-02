import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { View, Text, ListItem, Left, Right, Thumbnail, Body } from 'native-base'
import { getUrl } from 'App/Utils/HttpUtils'
import { Confirm } from 'App/Components/Alert'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import {
  connectionRequestRejectBtn,
  connectionRequestAcceptBtn,
  connectionDeleteBtn,
  titleConnectionRequestReject,
  titleConnectionDelete,
  confirmConnectionRequestReject,
  confirmConnectionDelete,
} from '../../Metadata'

import styles from './styles'

const DeleteBtn = ({
  connectionId,
  title,
  confirmTitle,
  confirmMessage,
  deleteHandler,
  localizations,
  fetches,
}) => {
  return (
    <ButtonWithLoader
      title={title}
      loading={fetches.fetchingDeleteConnection}
      onPressHandler={() =>
        Confirm({
          title: confirmTitle,
          message: confirmMessage,
          ok: () => deleteHandler(connectionId),
          localizations: localizations,
        })
      }
      small
      transparent
    />
  )
}

DeleteBtn.propTypes = {
  connectionId: PropTypes.number.isRequired,
  title: PropTypes.string.isRequired,
  confirmTitle: PropTypes.string.isRequired,
  confirmMessage: PropTypes.string.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

const TrainerAccept = ({ connectionId, acceptHandler, rejectHandler, localizations, fetches }) => {
  return (
    <View style={styles.inline}>
      <ButtonWithLoader
        title={connectionRequestAcceptBtn(localizations)}
        loading={fetches.fetchingAcceptConnection}
        onPressHandler={() => acceptHandler(connectionId)}
        small
        transparent
      />
      <ButtonWithLoader
        title={connectionRequestRejectBtn(localizations)}
        loading={fetches.fetchingDeleteConnection}
        onPressHandler={() =>
          Confirm({
            title: titleConnectionRequestReject(localizations),
            message: confirmConnectionRequestReject(localizations),
            ok: () => rejectHandler(connectionId),
            localizations: localizations,
          })
        }
        small
        transparent
      />
    </View>
  )
}

TrainerAccept.propTypes = {
  connectionId: PropTypes.number.isRequired,
  acceptHandler: PropTypes.func.isRequired,
  rejectHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

class TrainerItem extends Component {
  render() {
    const { item, deleteHandler, acceptHandler, localizations, fetches } = this.props

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
        <Body style={styles.body}>
          <Text>{item.trainerFullName}</Text>
          {item.status === 'PENDING_ON_TRAINEE' && (
            <TrainerAccept
              connectionId={item.connectionId}
              acceptHandler={acceptHandler}
              rejectHandler={deleteHandler}
              localizations={localizations}
              fetches={fetches}
            />
          )}
        </Body>
        {item.status === 'CONNECTED' && (
          <Right style={styles.verticalAlign}>
            <DeleteBtn
              title={connectionDeleteBtn(localizations)}
              connectionId={item.connectionId}
              confirmTitle={titleConnectionDelete(localizations)}
              confirmMessage={confirmConnectionDelete(localizations)}
              deleteHandler={deleteHandler}
              localizations={localizations}
              fetches={fetches}
            />
          </Right>
        )}
      </ListItem>
    )
  }
}

TrainerItem.propTypes = {
  item: PropTypes.object.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  acceptHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default TrainerItem
