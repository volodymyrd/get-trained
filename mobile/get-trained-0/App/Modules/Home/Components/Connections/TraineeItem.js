import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { View, Text, ListItem, Left, Right, Thumbnail, Body } from 'native-base'
import { getUrl } from 'App/Utils/HttpUtils'
import { Confirm } from 'App/Components/Alert'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import {
  connectionRequestDeleteBtn,
  connectionDeleteBtn,
  titleConnectionRequestDelete,
  titleConnectionDelete,
  confirmConnectionRequestDelete,
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

const Pending = ({ connectionId, deleteHandler, localizations, fetches }) => {
  return (
    <View style={styles.inline}>
      <View style={styles.verticalAlign}>
        <Text note>{'pending...'}</Text>
      </View>
      <DeleteBtn
        connectionId={connectionId}
        title={connectionRequestDeleteBtn(localizations)}
        confirmTitle={titleConnectionRequestDelete(localizations)}
        confirmMessage={confirmConnectionRequestDelete(localizations)}
        deleteHandler={deleteHandler}
        localizations={localizations}
        fetches={fetches}
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
        <Body style={styles.body}>
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

TraineeItem.propTypes = {
  item: PropTypes.object.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default TraineeItem
