import { createStackNavigator } from 'react-navigation'
import SettingsScreen from './Containers/SettingsScreen'
import ChangePasswordScreen from './Containers/ChangePasswordScreen'

const SettingsNavigator = createStackNavigator({
  Settings: { screen: SettingsScreen },
  ChangePassword: { screen: ChangePasswordScreen },
})

SettingsNavigator.navigationOptions = ({ navigation }) => {
  if (navigation.state.params && navigation.state.params.route) {
    navigation.navigate(navigation.state.params.route)
  }
}

export default SettingsNavigator
