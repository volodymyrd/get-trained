import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { FlatList } from 'react-native'
import TrainerItem from './TrainerItem'
import TraineeItem from './TraineeItem'

class Connections extends Component {
  render() {
    const {
      navigation,
      isTrainer,
      connections,
      refreshing,
      refreshHandler,
      deleteHandler,
      acceptHandler,
      localizations,
      fetches,
    } = this.props

    return (
      <FlatList
        data={connections.get('data').toJSON()}
        keyExtractor={(item) => item.connectionId.toString()}
        refreshing={refreshing}
        onRefresh={refreshHandler}
        renderItem={({ item }) =>
          isTrainer ? (
            <TraineeItem
              item={item}
              navigation={navigation}
              deleteHandler={deleteHandler}
              localizations={localizations}
              fetches={fetches}
            />
          ) : (
            <TrainerItem
              item={item}
              navigation={navigation}
              deleteHandler={deleteHandler}
              acceptHandler={acceptHandler}
              localizations={localizations}
              fetches={fetches}
            />
          )
        }
      />
    )
  }
}

Connections.propTypes = {
  navigation: PropTypes.object.isRequired,
  isTrainer: PropTypes.bool.isRequired,
  connections: PropTypes.object.isRequired,
  refreshing: PropTypes.bool.isRequired,
  refreshHandler: PropTypes.func.isRequired,
  deleteHandler: PropTypes.func.isRequired,
  acceptHandler: PropTypes.func.isRequired,
  localizations: PropTypes.object.isRequired,
  fetches: PropTypes.object.isRequired,
}

export default Connections
