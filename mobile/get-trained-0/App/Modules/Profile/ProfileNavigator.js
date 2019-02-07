import { createStackNavigator } from 'react-navigation'
import ProfileScreen from './Containers/ProfileScreen'

const ProfileNavigator = createStackNavigator(
  {
    Profile: { screen: ProfileScreen },
  },
  {
    initialRouteName: 'Profile',
  }
)
export default ProfileNavigator
