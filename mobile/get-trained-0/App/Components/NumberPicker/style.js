import { StyleSheet } from 'react-native'
import Colors from 'App/Theme/Colors'

export default StyleSheet.create({
  item: {
    marginLeft: 0,
  },
  touchView: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    height: 35,
  },
  pickerView: {
    flex: 0.8,
    justifyContent: 'flex-end',
    flexDirection: 'row',
    alignItems: 'center',
  },
})
