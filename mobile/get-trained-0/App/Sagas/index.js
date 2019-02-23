import { all } from 'redux-saga/effects'
import main from './Main/Watchers'
import auth from 'App/Modules/Auth/Sagas/Watchers'
import dashboard from 'App/Modules/Dashboard/Sagas/Watchers'
import home from 'App/Modules/Home/Sagas/Watchers'
import profile from 'App/Modules/Profile/Sagas/Watchers'
import settings from 'App/Modules/Settings/Sagas/Watchers'

export default function* root() {
  yield all([main(), auth(), dashboard(), home(), profile(), settings()])
}
