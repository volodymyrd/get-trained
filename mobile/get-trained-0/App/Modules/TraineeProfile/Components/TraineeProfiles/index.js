import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { FlatList, TouchableOpacity } from 'react-native'
import { SwipeRow, View, Text, Icon, Button, Spinner } from 'native-base'

const Profile = ({ item, onSelectItem, fetchingItem, deleteHandler, deletingItem }) => {
  return (
    <SwipeRow
      disableRightSwipe={true}
      rightOpenValue={-75}
      body={
        <TouchableOpacity onPress={() => onSelectItem(item)}>
          <View style={{ paddingLeft: 20 }}>
            {fetchingItem === item.traineeProfileId ? (
              <Spinner color="blue" />
            ) : (
              <Text>Profile on: {item.measure}</Text>
            )}
          </View>
        </TouchableOpacity>
      }
      right={
        <Button danger onPress={() => deleteHandler(item)}>
          {deletingItem === item.traineeProfileId ? (
            <Spinner color="blue" />
          ) : (
            <Icon active name="trash" />
          )}
        </Button>
      }
    />
  )
}

export default class TraineeProfiles extends Component {
  render() {
    const {
      profiles,
      refreshing,
      refreshHandler,
      deleteHandler,
      // localizations,
      // fetches,
      onSelectItem,
      fetchingItem,
      deletingItem,
    } = this.props

    return (
      <FlatList
        data={profiles.get('data').toJSON()}
        keyExtractor={(item) => item.traineeProfileId.toString()}
        refreshing={refreshing}
        onRefresh={refreshHandler}
        renderItem={({ item }) => (
          <Profile
            item={item}
            deleteHandler={deleteHandler}
            // localizations={localizations}
            onSelectItem={onSelectItem}
            fetchingItem={fetchingItem}
            deletingItem={deletingItem}
          />
        )}
      />
    )
  }
}

TraineeProfiles.propTypes = {
  profiles: PropTypes.object.isRequired,
  refreshing: PropTypes.bool.isRequired,
  refreshHandler: PropTypes.func.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  onSelectItem: PropTypes.func.isRequired,
  fetchingItem: PropTypes.number.isRequired,
  deletingItem: PropTypes.number.isRequired,
  // localizations: PropTypes.object.isRequired,
}
