import { createStackNavigator } from 'react-navigation'
import HomeScreen from './Containers/HomeScreen'
import AddTraineeScreen from './Containers/AddTraineeScreen'
import ChatScreen from './Containers/ChatScreen'

const HomeNavigator = createStackNavigator(
  {
    Home: { screen: HomeScreen },
    AddTrainee: { screen: AddTraineeScreen },
    Chat: { screen: ChatScreen },
  },
  {
    initialRouteName: 'Home',
  }
)
export default HomeNavigator
