import { createStackNavigator } from 'react-navigation'
import SettingsScreen from './Containers/SettingsScreen'

const SettingsNavigator = createStackNavigator(
  {
    Settings: { screen: SettingsScreen },
  },
  {
    initialRouteName: 'Settings',
  }
)
export default SettingsNavigator
