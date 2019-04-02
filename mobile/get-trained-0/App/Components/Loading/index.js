import React from 'react'
import { Image, View } from 'react-native'
import { Spinner } from 'native-base'
import { LOGO_HEAD } from 'App/Images/'
import styles from './style'

const Loading = () => {
  return (
    <View style={styles.container}>
      <Image square style={styles.logo} source={LOGO_HEAD} />
      <Spinner color="black" />
    </View>
  )
}

export default Loading
