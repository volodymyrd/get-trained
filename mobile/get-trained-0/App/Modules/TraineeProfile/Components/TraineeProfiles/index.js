import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { FlatList } from 'react-native'
import { ListItem, Body, Text } from 'native-base'

const Profile = ({ item }) => {
  return (
    <ListItem>
      <Body>
        <Text>{item.measure}</Text>
      </Body>
    </ListItem>
  )
}

export default class TraineeProfiles extends Component {
  render() {
    const {
      profiles,
      refreshing,
      refreshHandler,
      // deleteHandler,
      // acceptHandler,
      // localizations,
      // fetches,
      // onSelectItem,
    } = this.props

    console.log(profiles.get('data').toJSON())

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
            // acceptHandler={acceptHandler}
            // localizations={localizations}
            // onSelectItem={onSelectItem}
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
  // acceptHandler: PropTypes.func.isRequired,
  // onSelectItem: PropTypes.func.isRequired,
  // localizations: PropTypes.object.isRequired,
}
