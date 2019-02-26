import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { FlatList } from 'react-native'
import TrainerItem from './TrainerItem'
import TraineeItem from './TraineeItem'

class Connections extends Component {
  render() {
    const { isTrainer, connections } = this.props

    return (
      <FlatList
        data={connections.get('data').toJSON()}
        keyExtractor={(item) => item.connectionId.toString()}
        renderItem={({ item }) =>
          isTrainer ? <TraineeItem item={item} /> : <TrainerItem item={item} />
        }
      />
    )
  }
}

Connections.propTypes = {
  isTrainer: PropTypes.bool.isRequired,
  connections: PropTypes.object.isRequired,
}

export default Connections
