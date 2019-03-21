import { StyleSheet } from 'react-native'
import Dimensions from 'Dimensions'

export default StyleSheet.create({
  form: {
    flex: 1,
    justifyContent: 'center',
    flexDirection: 'column',
    height: Dimensions.get('window').height / 3,
  },
})
