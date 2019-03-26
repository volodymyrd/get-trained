import { StyleSheet } from 'react-native'
import Dimensions from 'Dimensions'
import Colors from 'App/Theme/Colors'

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
  saveButton: {
    position: 'absolute',
    zIndex: 2,
  },
  metric: {
    position: 'absolute',
    zIndex: 1,
  },
  metricUnSelected: {
    backgroundColor: Colors.primary,
  },
  metricSelected: {
    backgroundColor: Colors.error,
  },
  image: {
    flex: 1,
    width: undefined,
    height: undefined,
    resizeMode: 'contain',
  },
})
