import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { FlatList, TouchableOpacity } from 'react-native'
import { SwipeRow, View, Text, Icon, Button, Spinner } from 'native-base'

const Profile = ({ item, onSelectItem, fetchingItem }) => {
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
              <Text>{item.measure}</Text>
            )}
          </View>
        </TouchableOpacity>
      }
      right={
        <Button danger onPress={() => alert('Trash')}>
          <Icon active name="trash" />
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
      // deleteHandler,
      // localizations,
      // fetches,
      onSelectItem,
      fetchingItem,
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
            // deleteHandler={deleteHandler}
            // localizations={localizations}
            onSelectItem={onSelectItem}
            fetchingItem={fetchingItem}
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
  // deleteHandler: PropTypes.func.isRequired,
  onSelectItem: PropTypes.func.isRequired,
  fetchingItem: PropTypes.number.isRequired,
  // localizations: PropTypes.object.isRequired,
}
