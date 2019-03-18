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
import Ionicons from 'react-native-vector-icons/Ionicons'
import { getUrl } from 'App/Utils/HttpUtils'
import { Confirm } from 'App/Components/Alert'
import DeleteBtn from './DeleteBtn'
import {
  cancel,
  connectionRequestDeleteBtn,
  connectionDeleteBtn,
  titleConnectionRequestDelete,
  titleConnectionDelete,
  confirmConnectionRequestDelete,
  confirmConnectionDelete,
  titleActions,
  traineeProfile,
  titleChat,
} from '../../Metadata'

import styles from './styles'
import color from 'App/Theme/Colors'

const PROFILE_INDEX = 0
const CHAT_INDEX = 1
const DELETE_CONNECTION_INDEX = 2
const DESTRUCTIVE_INDEX = 2
const CANCEL_INDEX = 3

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
    const { navigation, item, deleteHandler, localizations, fetches, onSelectItem } = this.props

    const ACTIONS = [
      traineeProfile(localizations),
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
                      case PROFILE_INDEX:
                        onSelectItem(item, '_TraineeProfile')
                        break
                      case CHAT_INDEX:
                        navigation.navigate('Chat', { title: titleChat(localizations) })
                        break
                      case DELETE_CONNECTION_INDEX:
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
              <Ionicons name="ios-more" size={30} color={color.primary} />
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
  onSelectItem: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default TraineeItem
