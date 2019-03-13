import { createStackNavigator } from 'react-navigation'
import HomeScreen from './Containers/HomeScreen'
import AddTraineeScreen from './Containers/AddTraineeScreen'
import ChatScreen from './Containers/ChatScreen'
import TraineeProfileNavigator from 'App/Modules/TraineeProfile/TraineeProfileNavigator'

const HomeNavigator = createStackNavigator(
  {
    Home: { screen: HomeScreen },
    AddTrainee: { screen: AddTraineeScreen },
    Chat: { screen: ChatScreen },
    _TraineeProfile: TraineeProfileNavigator,
  },
  {
    initialRouteName: 'Home',
  }
)
export default HomeNavigator
