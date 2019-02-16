import { createStackNavigator } from 'react-navigation'
import ProfileScreen from './Containers/ProfileScreen'
import AvatarUploaderScreen from './Containers/AvatarUploaderScreen'

const ProfileNavigator = createStackNavigator({
  Profile: { screen: ProfileScreen },
  AvatarUploader: { screen: AvatarUploaderScreen },
})

export default ProfileNavigator
