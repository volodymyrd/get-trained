import { combineReducers } from 'redux'
import configureStore from './CreateStore'
import rootSaga from 'App/Sagas'
import { main as MainReducer } from './Main/Reducers'
import AuthReducer from 'App/Modules/Auth/RootReducer'
import DashboardReducer from 'App/Modules/Dashboard/RootReducer'
import ProfileReducer from 'App/Modules/Profile/RootReducer'
import SettingsReducer from 'App/Modules/Settings/RootReducer'

export default () => {
  const rootReducer = combineReducers({
    main: MainReducer,
    auth: AuthReducer,
    dashboard: DashboardReducer,
    profile: ProfileReducer,
    settings: SettingsReducer,
  })

  return configureStore(rootReducer, rootSaga)
}
