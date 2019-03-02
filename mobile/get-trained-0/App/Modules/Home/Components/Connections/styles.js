import { StyleSheet } from 'react-native'
import Dimensions from 'Dimensions'
import ApplicationStyles from 'App/Theme/ApplicationStyles'

export default StyleSheet.create({
  content: {
    marginTop: Dimensions.get('window').height / 10,
  },
  body: {
    height: 70,
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
  inline: {
    ...ApplicationStyles.inline,
  },
  verticalAlign: {
    ...ApplicationStyles.verticalAlign,
  },
})
