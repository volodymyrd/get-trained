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
import ButtonWithLoader from 'App/Components/ButtonWithLoader'
import {
  connectionRequestRejectBtn,
  connectionRequestAcceptBtn,
  connectionDeleteBtn,
  titleConnectionRequestReject,
  titleConnectionDelete,
  confirmConnectionRequestReject,
  confirmConnectionDelete,
  titleActions,
  titleChat,
  cancel,
} from '../../Metadata'

import styles from './styles'
import color from 'App/Theme/Colors'

const DESTRUCTIVE_INDEX = 1
const CANCEL_INDEX = 2

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
    const { navigation, item, deleteHandler, acceptHandler, localizations, fetches } = this.props

    const ACTIONS = [
      titleChat(localizations),
      connectionDeleteBtn(localizations),
      cancel(localizations),
    ]

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
              <Ionicons name="ios-more" size={30} color={color.primary} />
            </Button>
          </Right>
        )}
      </ListItem>
    )
  }
}

TrainerItem.propTypes = {
  item: PropTypes.object.isRequired,
  navigation: PropTypes.object.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  acceptHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default TrainerItem
