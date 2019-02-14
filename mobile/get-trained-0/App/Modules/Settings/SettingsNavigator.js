import { createStackNavigator } from 'react-navigation'
import SettingsScreen from './Containers/SettingsScreen'
import ChangePasswordScreen from './Containers/ChangePasswordScreen'

const SettingsNavigator = createStackNavigator({
  Settings: { screen: SettingsScreen },
  ChangePassword: { screen: ChangePasswordScreen },
})

// SettingsNavigator.navigationOptions = ({ navigation }) => {
// }

export default SettingsNavigator
