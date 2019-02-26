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
    height: 56,
    width: 56,
    backgroundColor: 'white',
    borderRadius: 28,
    borderStyle: 'solid',
    borderColor: 'black',
    borderWidth: 1,
  },
})
