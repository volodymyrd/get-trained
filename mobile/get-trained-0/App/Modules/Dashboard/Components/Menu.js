import { NavigationActions } from 'react-navigation'
import { getTxt } from '../Metadata'

export const getMenu = (navigation, localizations) => {
  const profileTxt = getTxt(localizations, MENU_PROFILE, 'Profile')
  const avatarTxt = getTxt(localizations, MENU_PROFILE_AVATAR, 'Avatar')

  const settingsTxt = getTxt(localizations, MENU_SETTINGS, 'Settings')
  const changePasswordTxt = getTxt(localizations, MENU_SETTINGS_CHANGE_PASSWORD, 'Change Password')

  return [
    {
      id: 'profile',
      title: profileTxt,
      items: [
        {
          id: 'profile_avatar',
          title: avatarTxt,
          fun: () =>
            navigation.navigate(
              '_Profile',
              {},
              NavigationActions.navigate({
                routeName: 'AvatarUploader',
                params: { title: avatarTxt },
              })
            ),
        },
        // {
        //   id: 'profile_personal',
        //   title: getTxt(localizations, MENU_PROFILE_PERSONAL, 'Personal Info'),
        //   fun: () => navigation.navigate('_Profile'),
        // },
        // {
        //   id: 'profile_contacts',
        //   title: getTxt(localizations, MENU_PROFILE_CONTACTS, 'Contacts'),
        //   fun: () => navigation.navigate('_Profile'),
        // },
      ],
    },
    {
      id: 'settings',
      title: settingsTxt,
      items: [
        {
          id: 'settings_change_password',
          title: changePasswordTxt,
          fun: () =>
            navigation.navigate(
              '_Settings',
              {},
              NavigationActions.navigate({
                routeName: 'ChangePassword',
                params: { title: changePasswordTxt },
              })
            ),
        },
        {
          id: 'settings_settings',
          title: settingsTxt,
          fun: () =>
            navigation.navigate(
              '_Settings',
              {},
              NavigationActions.navigate({
                routeName: 'Settings',
                params: { title: settingsTxt },
              })
            ),
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
