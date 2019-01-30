import { takeLatest, takeEvery } from 'redux-saga/effects'
import { fetchAccess } from './Workers'
import { MainTypes } from 'App/Stores/Main/Actions'

export default function*() {
  yield [takeLatest(MainTypes.FETCH_ACCESS, fetchAccess)]
}
