import { getTxt } from '../Metadata'

export const getMenu = (navigation, localizations) => {
  return [
    {
      id: 'profile',
      title: getTxt(localizations, MENU_PROFILE, 'Profile'),
      items: [
        {
          id: 'profile_avatar',
          title: getTxt(localizations, MENU_PROFILE_AVATAR, 'Avatar'),
          fun: () => navigation.navigate('_Profile'),
        },
        {
          id: 'profile_personal',
          title: getTxt(localizations, MENU_PROFILE_PERSONAL, 'Personal Info'),
          fun: () => navigation.navigate('_Profile'),
        },
        {
          id: 'profile_contacts',
          title: getTxt(localizations, MENU_PROFILE_CONTACTS, 'Contacts'),
          fun: () => navigation.navigate('_Profile'),
        },
      ],
    },
    {
      id: 'settings',
      title: getTxt(localizations, MENU_SETTINGS, 'Settings'),
      items: [
        {
          id: 'settings_avatar',
          title: getTxt(localizations, MENU_SETTINGS_CHANGE_PASSWORD, 'Change Password'),
          fun: () => navigation.navigate('_Settings'),
        },
      ],
    },
  ]
}
const MENU_PROFILE = 'menu.profile'
const MENU_PROFILE_AVATAR = 'menu.profile.avatar'
const MENU_PROFILE_PERSONAL = 'menu.profile.personal'
const MENU_PROFILE_CONTACTS = 'menu.profile.contacts'
const MENU_SETTINGS = 'menu.settings'
const MENU_SETTINGS_CHANGE_PASSWORD = 'menu.settings.change_password'
