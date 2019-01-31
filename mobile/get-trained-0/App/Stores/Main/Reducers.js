import { INITIAL_STATE } from './InitialState'
import { createReducer } from 'reduxsauce'
import { MainTypes } from './Actions'

export const setLocale = (state, { locale }) =>
  state.merge({
    locale,
    langCode: locale ? locale.substring(0, 2) : 'NO',
  })

export const fetchAccessLoading = (state) =>
  state.merge({
    accessIsLoading: true,
  })

export const fetchAccessSuccess = (state, { isAuthenticated }) =>
  state.merge({
    isAuthenticated,
    accessIsLoading: false,
  })

export const fetchAccessFailure = (state) =>
  state.merge({
    isAuthenticated: false,
    accessIsLoading: false,
  })

export const main = createReducer(INITIAL_STATE, {
  [MainTypes.SET_LOCALE]: setLocale,
  [MainTypes.FETCH_ACCESS_LOADING]: fetchAccessLoading,
  [MainTypes.FETCH_ACCESS_SUCCESS]: fetchAccessSuccess,
  [MainTypes.FETCH_ACCESS_FAILURE]: fetchAccessFailure,
})
