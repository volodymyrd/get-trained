import { StyleSheet } from 'react-native'
import Dimensions from 'Dimensions'
import Colors from 'App/Theme/Colors'

export default StyleSheet.create({
  wrapper: {
    flex: 1,
    justifyContent: 'flex-end',
    flexDirection: 'column',
    alignItems: 'center',
  },
  container: {
    // width: Dimensions.get('window').width,
    alignSelf: 'stretch',
    zIndex: 1,
    borderRadius: 20,
    borderWidth: 1,
    borderColor: Colors.black,
    marginLeft: 5,
    marginRight: 5,
    backgroundColor: Colors.white,
    // paddingLeft: 16,
    // paddingRight: 8,
    shadowColor: Colors.black,
    shadowOpacity: 0.8,
    shadowRadius: 10,
    shadowOffset: {
      height: 1,
      width: 1,
    },
  },
  title: {
    flex: 0.4,
    borderBottomWidth: 1,
    borderColor: Colors.underline,
  },
  cancel: {
    flex: 0.5,
    flexDirection: 'column',
    justifyContent: 'flex-end',
    borderTopWidth: 2,
    borderColor: Colors.underline,
  },
})
