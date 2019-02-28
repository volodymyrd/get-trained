import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { FlatList } from 'react-native'
import TrainerItem from './TrainerItem'
import TraineeItem from './TraineeItem'

class Connections extends Component {
  render() {
    const { isTrainer, connections, refreshing, refreshHandler, deleteHandler } = this.props

    return (
      <FlatList
        data={connections.get('data').toJSON()}
        keyExtractor={(item) => item.connectionId.toString()}
        refreshing={refreshing}
        onRefresh={refreshHandler}
        renderItem={({ item }) =>
          isTrainer ? (
            <TraineeItem item={item} deleteHandler={deleteHandler} />
          ) : (
            <TrainerItem item={item} />
          )
        }
      />
    )
  }
}

Connections.propTypes = {
  isTrainer: PropTypes.bool.isRequired,
  connections: PropTypes.object.isRequired,
  refreshing: PropTypes.bool.isRequired,
  refreshHandler: PropTypes.func.isRequired,
}

export default Connections
