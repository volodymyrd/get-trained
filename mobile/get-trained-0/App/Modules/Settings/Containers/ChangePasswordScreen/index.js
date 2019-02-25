import React, {Component} from 'react'
import {Container, Content} from "native-base";
import connect from 'react-redux/es/connect/connect'
import {setNavigationOptions} from "App/Modules/Dashboard/NavigationOptions";
import {toHome} from "App/Utils/NavigationUtils";
import {
  txtOldPassword,
  txtNewPassword,
  txtRepeatPassword,
  txtChangePasswordBtn
} from "../../Metadata";
import {somethingWentWrong} from 'App/Utils/MetadataUtils'
import Error from "App/Components/Error";
import Loading from "App/Components/Loading";
import ChangePassword from "../../Components/ChangePassword";
import SettingsActions from "../../Stores/Actions";
import {MODULE} from "../../Metadata";

import styles from './styles'

class ChangePasswordScreen extends Component {
  static navigationOptions = ({navigation}) => setNavigationOptions(navigation)

  componentDidMount() {
    if (this.props.langCode
        && !(this.props.metadata.size
            && this.props.metadata.get('module') === MODULE)) {
      this.props.fetchMetadata(this.props.langCode.toUpperCase())
    }
  }

  componentDidUpdate(prevProps) {
    if (!prevProps.passwordChanged && this.props.passwordChanged) {
      toHome(this.props.navigation)
    }
  }

  changePasswordHandler = (oldPassword, newPassword, repeatPassword) => {
    this.props.changePassword(oldPassword, newPassword, repeatPassword,
        [somethingWentWrong(this.props.metadata.get('localizations'))]);
  }

  render() {
    const {
      metadata,
      failedRetrievingMetadata,
      fetchingMetadata,
      fetchingChangePassword,
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

    if (fetchingMetadata || !metadata || !metadata.size) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')
    const settings = metadata.get('settings')
    const minPasswordLength = parseInt(settings.get('f_min_password_length'))

    return (
        <Container>
          <Content style={styles.content}>
            <ChangePassword txtOldPassword={txtOldPassword(localizations)}
                            txtNewPassword={txtNewPassword(localizations)}
                            txtRepeatPassword={txtRepeatPassword(localizations)}
                            minPasswordLength={minPasswordLength}
                            txtBtn={txtChangePasswordBtn(localizations)}
                            loading={fetchingChangePassword}
                            changePasswordHandler={this.changePasswordHandler}/>
          </Content>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),

  fetchingMetadata: state.settings.root.get('fetchingMetadata'),
  failedRetrievingMetadata: state.settings.root.get('failedRetrievingMetadata'),
  metadata: state.settings.root.get('metadata'),

  fetchingChangePassword: state.settings.root.get('fetchingChangePassword'),
  passwordChanged: state.settings.root.get('passwordChanged'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(
      SettingsActions.fetchMetadata(langCode)),
  changePassword: (oldPassword, newPassword, repeatPassword, messages) =>
      dispatch(SettingsActions
      .fetchChangePassword(oldPassword, newPassword, repeatPassword, messages)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ChangePasswordScreen)
