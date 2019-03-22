import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { FlatList } from 'react-native'
import { ListItem, Body, Text } from 'native-base'

const Profile = (item) => {
  return (
    <ListItem>
      <Body>
        <Text>v</Text>
      </Body>
    </ListItem>
  )
}

export default class TraineeProfiles extends Component {
  render() {
    const {
      // navigation,
      // isTrainer,
      // connections,
      // refreshing,
      // refreshHandler,
      // deleteHandler,
      // acceptHandler,
      // localizations,
      // fetches,
      // onSelectItem,
    } = this.props

    return (
      <FlatList
        // data={connections.get('data').toJSON()}
        // keyExtractor={(item) => item.connectionId.toString()}
        // refreshing={refreshing}
        // onRefresh={refreshHandler}
        renderItem={({ item }) => (
          <Profile item={item}
            // navigation={navigation}
            // deleteHandler={deleteHandler}
            // acceptHandler={acceptHandler}
            // localizations={localizations}
            // fetches={fetches}
            // onSelectItem={onSelectItem}
          />
        )}
      />
    )
  }
}

TraineeProfiles.propTypes = {
  // navigation: PropTypes.object.isRequired,
  // isTrainer: PropTypes.bool.isRequired,
  // connections: PropTypes.object.isRequired,
  // refreshing: PropTypes.bool.isRequired,
  // refreshHandler: PropTypes.func.isRequired,
  // deleteHandler: PropTypes.func.isRequired,
  // acceptHandler: PropTypes.func.isRequired,
  // onSelectItem: PropTypes.func.isRequired,
  // localizations: PropTypes.object.isRequired,
  // fetches: PropTypes.object.isRequired,
}
