import React, { Component } from 'react'
import PropTypes from 'prop-types'
import {
  View,
  Text,
  ListItem,
  Left,
  Right,
  Thumbnail,
  Body,
  Button,
  ActionSheet,
} from 'native-base'
import { getUrl } from 'App/Utils/HttpUtils'
import { Confirm } from 'App/Components/Alert'
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import {
  cancel,
  connectionRequestDeleteBtn,
  connectionDeleteBtn,
  titleConnectionRequestDelete,
  titleConnectionDelete,
  confirmConnectionRequestDelete,
  confirmConnectionDelete,
  titleActions,
  titleChat,
} from '../../Metadata'

import styles from './styles'

const DESTRUCTIVE_INDEX = 1
const CANCEL_INDEX = 2

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
    const { navigation, item, deleteHandler, localizations, fetches } = this.props

    const ACTIONS = [
      titleChat(localizations),
      connectionDeleteBtn(localizations),
      cancel(localizations),
    ]

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
            <Button
              transparent
              onPress={() =>
                ActionSheet.show(
                  {
                    options: ACTIONS,
                    cancelButtonIndex: CANCEL_INDEX,
                    destructiveButtonIndex: DESTRUCTIVE_INDEX,
                    title: titleActions(localizations),
                  },
                  (buttonIndex) => {
                    switch (buttonIndex) {
                      case 0:
                        navigation.navigate('Chat', { title: titleChat(localizations) })
                        break
                      case 1:
                        Confirm({
                          title: titleConnectionDelete(localizations),
                          message: confirmConnectionDelete(localizations),
                          ok: () => deleteHandler(item.connectionId),
                          localizations: localizations,
                        })
                        break
                    }
                  }
                )
              }
            >
              <Text>...</Text>
            </Button>
          </Right>
        )}
      </ListItem>
    )
  }
}

TraineeItem.propTypes = {
  item: PropTypes.object.isRequired,
  navigation: PropTypes.object.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default TraineeItem
