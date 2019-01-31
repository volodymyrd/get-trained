import React from 'react'
import { View } from 'react-native'
import { Spinner } from 'native-base'
import styles from './style'

const Loading = () => {
  return (
    <View style={styles.container}>
      <Spinner color="white" />
    </View>
  )
}

export default Loading
