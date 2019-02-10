import { text } from 'App/Utils/MetadataUtils'

export const MENU_MODULE = 'MENU'

export const getTxt = (localizations, key, defaultTxt) => {
  return text(localizations.get(key), defaultTxt)
}
