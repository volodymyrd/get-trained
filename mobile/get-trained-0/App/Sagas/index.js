import { all } from 'redux-saga/effects'
import main from './Main/Watchers'
import auth from 'App/Modules/Auth/Sagas/Watchers'
import dashboard from 'App/Modules/Dashboard/Sagas/Watchers'
import settings from 'App/Modules/Settings/Sagas/Watchers'

export default function* root() {
  yield all([main(), auth(), dashboard(), settings()])
}
