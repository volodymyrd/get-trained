import { createStackNavigator } from 'react-navigation'
import SettingsScreen from './Containers/HomeScreen'

const HomeNavigator = createStackNavigator(
  {
    Settings: { screen: SettingsScreen },
  },
  {
    initialRouteName: 'Settings',
  }
)
export default HomeNavigator
