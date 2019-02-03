import { StyleSheet } from 'react-native'
import ApplicationStyles from 'App/Theme/ApplicationStyles'
import Dimensions from 'Dimensions'

export default StyleSheet.create({
  header: {
    ...ApplicationStyles.screen.header,
  },
  content: {
    marginTop: Dimensions.get('window').height / 7,
  },
  logo: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
})
