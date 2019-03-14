import {StyleSheet} from 'react-native'
import ApplicationStyles from 'App/Theme/ApplicationStyles'
import Dimensions from 'Dimensions'

export default StyleSheet.create({
  container: {
    height: 0.9 * Dimensions.get('window').height,
    width: Dimensions.get('window').width,
    justifyContent: 'center',
    alignItems: 'center',
  },
  body: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  pos: {
    position: 'absolute',
    zIndex: 1,
  },
  image: {
    flex: 1,
    width: undefined,
    height: undefined,
    resizeMode: 'contain',
  }
})
