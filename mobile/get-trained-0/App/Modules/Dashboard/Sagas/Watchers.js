import { takeLatest } from 'redux-saga/effects'
import { WorkspaceTypes } from '../Stores/Actions'
import { fetchMetadata, fetchSignOut } from './Workers'

export default function*() {
  yield [
    takeLatest(WorkspaceTypes.FETCH_METADATA, fetchMetadata),
    takeLatest(WorkspaceTypes.FETCH_SIGN_OUT, fetchSignOut),
  ]
}
