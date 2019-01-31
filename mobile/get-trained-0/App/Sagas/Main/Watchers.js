import { takeLatest, takeEvery } from 'redux-saga/effects'
import { getLocale, fetchAccess } from './Workers'
import { MainTypes } from 'App/Stores/Main/Actions'

export default function*() {
  yield [
    takeEvery(MainTypes.GET_LOCALE, getLocale),
    takeLatest(MainTypes.FETCH_ACCESS, fetchAccess),
  ]
}
