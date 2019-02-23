import { combineReducers } from 'redux'
import configureStore from './CreateStore'
import rootSaga from 'App/Sagas'
import { main as MainReducer } from './Main/Reducers'
import AuthReducer from 'App/Modules/Auth/RootReducer'
import DashboardReducer from 'App/Modules/Dashboard/RootReducer'
import HomeReducer from 'App/Modules/Home/RootReducer'
import ProfileReducer from 'App/Modules/Profile/RootReducer'
import SettingsReducer from 'App/Modules/Settings/RootReducer'

export default () => {
  const appReducer = combineReducers({
    main: MainReducer,
    auth: AuthReducer,
    dashboard: DashboardReducer,
    home: HomeReducer,
    profile: ProfileReducer,
    settings: SettingsReducer,
  })

  const rootReducer = (state, action) => {
    if (action.type === 'DASHBOARD_FETCH_SIGN_OUT_SUCCESS') {
      state.home = undefined
      state.profile = undefined
    }
    return appReducer(state, action)
  }

  return configureStore(rootReducer, rootSaga)
}
