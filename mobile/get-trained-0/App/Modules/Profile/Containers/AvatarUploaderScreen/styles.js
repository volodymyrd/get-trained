import { StyleSheet } from 'react-native'
import Dimensions from 'Dimensions'

export default StyleSheet.create({
  content: {
    marginTop: Dimensions.get('window').height / 10,
  },
  avatar: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
})
