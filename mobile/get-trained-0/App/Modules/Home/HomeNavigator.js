import { createStackNavigator } from 'react-navigation'
import HomeScreen from './Containers/HomeScreen'
import AddTraineeScreen from './Containers/AddTraineeScreen'

const HomeNavigator = createStackNavigator(
  {
    Home: { screen: HomeScreen },
    AddTrainee: { screen: AddTraineeScreen },
  },
  {
    initialRouteName: 'Home',
  }
)
export default HomeNavigator
