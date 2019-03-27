import { StyleSheet } from 'react-native'
import Colors from 'App/Theme/Colors'

export default StyleSheet.create({
  iosModalView: {
    //marginTop: 22,
  },
  datePickerFooter: {
    height: 20,
    backgroundColor: Colors.white,
    marginTop: 10,
  },
  inputDatePickerViewIOS: {
    flex: 1,
    justifyContent: 'flex-end',
    flexDirection: 'row',
    alignItems: 'center',
  },
  inputDatePickerViewAndroid: {
    flex: 1,
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
    backgroundColor: 'transparent',
    marginLeft: 0,
    marginRight: 0,
    paddingLeft: 16,
    paddingRight: 8,
    color: Colors.value,
    fontFamily: 'System',
    fontSize: 16.5,
  },
  placeholder: {
    backgroundColor: 'transparent',
    color: Colors.placeholder,
    fontFamily: 'System',
    fontSize: 16,
    lineHeight: null,
    marginLeft: 0,
    marginRight: 0,
    paddingLeft: 16,
    paddingRight: 8,
  },
  selectButtonView: {
    flex: 0.22,
    justifyContent: 'flex-end',
    flexDirection: 'row',
    alignItems: 'center',
  },
  selectButton: {
    color: Colors.black,
    fontSize: 18,
  },
})
