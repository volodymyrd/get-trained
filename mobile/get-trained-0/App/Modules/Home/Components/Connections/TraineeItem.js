import React, {Component} from 'react'
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
} from 'native-base'
import Ionicons from 'react-native-vector-icons/Ionicons'
import {getUrl} from 'App/Utils/HttpUtils'
import {Confirm} from 'App/Components/Alert'
import ActionMenu from 'App/Components/ActionMenu'
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

const Pending = ({connectionId, deleteHandler, localizations, fetches}) => {
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

  _openProfile = (item) => {
    this.props.onSelectItem(item, '_TraineeProfile')
  }

  render() {
    const {navigation, item, deleteHandler, localizations, fetches, onSelectItem} = this.props

    return (
        <ListItem avatar>
          <Left>
            {item.traineeLogoUrl ? (
                <Thumbnail source={{uri: getUrl(item.traineeLogoUrl)}}/>
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
                <ActionMenu
                    title={`${titleActions(localizations)} ${item.traineeFullName}`}
                    cancelBtnName={cancel(localizations)}
                    height={300}
                    ref={(c) => (this.menu = c)}
                >
                  <View>
                    <Button full
                            large
                            transparent
                            onPress={() => {
                              this._openProfile(item);
                              this.menu.close()
                            }}>
                      <Text>{traineeProfile(localizations)}</Text>
                    </Button>
                    <Button full
                            large
                            transparent
                            onPress={() => {
                              navigation.navigate(
                                  'Chat',
                                  {title: titleChat(localizations)})
                              this.menu.close()
                            }}>
                      <Text>{titleChat(localizations)}</Text>
                    </Button>
                    <Button danger
                            full
                            large
                            transparent
                            onPress={() => {
                              this.menu.close()
                              setTimeout(
                                  function () {
                                    Confirm({
                                      title: titleConnectionDelete(
                                          localizations),
                                      message: confirmConnectionDelete(
                                          localizations),
                                      ok: () => deleteHandler(
                                          item.connectionId),
                                      localizations: localizations,
                                    })
                                  },
                                  500)
                            }}>
                      <Text>{connectionDeleteBtn(localizations)}</Text>
                    </Button>
                  </View>
                </ActionMenu>
                <Button transparent onPress={() => this.menu.open()}>
                  <Ionicons name="ios-more" size={30} color={color.primary}/>
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
