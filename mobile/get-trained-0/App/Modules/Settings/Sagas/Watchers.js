import { takeLatest } from 'redux-saga/effects'
import { SettingsTypes } from '../Stores/Actions'
import {
  fetchMetadata,
  fetchChangePassword,
  fetchIsTrainer,
  fetchAddTrainer,
  fetchRemoveTrainer,
} from './Workers'

export default function*() {
  yield [
    takeLatest(SettingsTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(SettingsTypes.FETCH_CHANGE_PASSWORD, fetchChangePassword),
    takeLatest(SettingsTypes.FETCH_IS_TRAINER, fetchIsTrainer),
    takeLatest(SettingsTypes.FETCH_ADD_TRAINER, fetchAddTrainer),
    takeLatest(SettingsTypes.FETCH_REMOVE_TRAINER, fetchRemoveTrainer),
  ]
}
