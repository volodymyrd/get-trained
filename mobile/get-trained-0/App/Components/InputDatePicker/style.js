import { StyleSheet } from 'react-native'
import Colors from 'App/Theme/Colors'

export default StyleSheet.create({
  iosModalView: {
    marginTop: 22,
  },
  datePickerFooter: {
    height: 20,
    backgroundColor: Colors.white,
    marginTop: 10,
  },
  inputDatePickerView: {
    flex: 1,
    justifyContent: 'flex-end',
    flexDirection: 'row',
    alignItems: 'center',
  },
  okButton: {
    marginTop: 20,
    height: 55,
    width: 55,
    justifyContent: 'center',
    alignItems: 'center',
  },
  value: {
    marginLeft: 0,
    marginRight: 0,
    paddingLeft: 16,
    paddingRight: 16,
  },
  placeholder: {
    backgroundColor: 'transparent',
    color: '#a7a7a7',
    fontFamily: 'System',
    fontSize: 16,
    lineHeight: null,
    marginLeft: 0,
    marginRight: 0,
    paddingLeft: 16,
    paddingRight: 16,
  },
  downArrow: {
    color: Colors.black,
    fontSize: 22,
  },
})
