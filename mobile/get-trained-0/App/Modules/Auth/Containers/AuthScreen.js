import React from 'react'
import connect from 'react-redux/es/connect/connect'
import {
  Container,
  Header,
  Content,
  Body,
  Footer,
  FooterTab,
  Title,
  Text,
  Button,
} from 'native-base';
import Loading from "App/Components/Loading";
import {AuthType} from '../Stores/InitialState'
import AuthActions from '../Stores/Actions'
import SignIn from "../Components/SignIn";
import SignUp from "../Components/SignUp";
import RestorePassword from "../Components/RestorePassword";
import {
  txtSignIn,
  txtSignUp,
  txtRestorePass,
  txtForgotPass,
  txtEmail,
  txtFirstName,
  txtPassword,
  txtRepeatPassword,
  btnSignIn,
  btnSignUp,
  btnGenNewPassword,
  hintMinPasswordLength,
  messSuccessLogin,
  errorBadCredentials
} from '../Metadata'
import ApplicationStyles from 'App/Theme/ApplicationStyles'
import styles from './AuthScreenStyle'
import {LOGO_128} from "App/Images";
import {Image, View} from "react-native";

class AuthScreen extends React.Component {

  static navigationOptions = {
    headerStyle: ApplicationStyles.navigationHeaderZeroStyle,
  }

  componentDidMount() {
    if (this.props.langCode) {
      this.props.fetchMetadata(this.props.langCode)
    }
  }

  componentDidUpdate(prevProps) {
    if (prevProps.fetchingAuthenticating
        && !this.props.fetchingAuthenticating) {
      if (this.props.authenticated) {
        this.props.navigation.navigate('App')
      }
    }
  }

  authenticationHandler = (email, password) => {
    const langCode = this.props.langCode
    const localizations = this.props.metadata.get('localizations')
    const messages = [
      messSuccessLogin(localizations),
      errorBadCredentials(localizations)]
    this.props.authenticate(email, password, langCode, messages);
  }

  render() {
    const {
      langCode,
      metadata,
      authType,
      toggleAuthType,
      fetchingMetadata,
      fetchingAuthenticating,
    } = this.props

    if (!langCode || fetchingMetadata || !metadata || !metadata.size) {
      return <Loading/>
    }

    const localizations = metadata.get('localizations')
    const settings = metadata.get('settings')
    const minPasswordLength = parseInt(settings.get('f_min_password_length'))

    const tSignIn = txtSignIn(localizations)
    const tSignUp = txtSignUp(localizations)
    const tRestorePass = txtRestorePass(localizations)

    return (
        <Container>
          <Header style={styles.header}>
            <Body style={styles.body}>
            {authType === AuthType.SIGN_IN && <Title>{tSignIn}</Title>}
            {authType === AuthType.SIGN_UP && <Title>{tSignUp}</Title>}
            {authType === AuthType.RESTORE_PASSWORD
            && <Title>{tRestorePass}</Title>}
            </Body>
          </Header>
          <Content style={styles.content}>
            <View style={styles.logo}>
              <Image source={LOGO_128} />
            </View>
            {authType === AuthType.SIGN_IN
            && <SignIn txtEmail={txtEmail(localizations)}
                       txtPassword={txtPassword(localizations)}
                       txtBtn={btnSignIn(localizations)}
                       minPasswordLength={minPasswordLength}
                       loading={fetchingAuthenticating}
                       authenticationHandler={this.authenticationHandler}/>}
            {authType === AuthType.SIGN_UP
            && <SignUp txtEmail={txtEmail(localizations)}
                       txtFirstName={txtFirstName(localizations)}
                       minFirstNameLength={2}
                       txtPassword={`${txtPassword(
                           localizations)}(${hintMinPasswordLength(
                           localizations, minPasswordLength)})`}
                       txtRepeatPassword={txtRepeatPassword(localizations)}
                       minPasswordLength={
                         parseInt(settings.get('f_min_password_length'))}
                       txtBtn={btnSignUp(localizations)}
                       loading={fetchingAuthenticating}
                       authenticationHandler={this.authenticationHandler}/>}
            {authType === 'restorePassword'
            && <RestorePassword txtEmail={txtEmail(localizations)}
                                txtBtn={btnGenNewPassword(localizations)}
                                loading={fetchingAuthenticating}
                                authenticationHandler={this.authenticationHandler}/>}
          </Content>
          <Footer>
            <FooterTab>
              <Button active={authType === AuthType.SIGN_IN}
                      onPress={() => toggleAuthType(AuthType.SIGN_IN)}>
                <Text>{tSignIn}</Text>
              </Button>
              <Button active={authType === AuthType.SIGN_UP}
                      onPress={() => toggleAuthType(AuthType.SIGN_UP)}>
                <Text>{tSignUp}</Text>
              </Button>
              <Button active={authType === AuthType.RESTORE_PASSWORD}
                      onPress={() => toggleAuthType(AuthType.RESTORE_PASSWORD)}>
                <Text>{txtForgotPass(localizations)}</Text>
              </Button>
            </FooterTab>
          </Footer>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode'),
  fetchingMetadata: state.auth.root.get('fetchingMetadata'),
  metadata: state.auth.root.get('metadata'),
  authType: state.auth.root.get('authType'),
  fetchingAuthenticating: state.auth.root.get('fetchingAuthenticating'),
  authenticated: state.auth.root.get('authenticated'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(AuthActions.fetchMetadata(langCode)),
  toggleAuthType: (authType) => dispatch(AuthActions.toggleAuthType(authType)),
  authenticate: (email, password, langCode, messages) => dispatch(
      AuthActions.fetchAuthentication(email, password, langCode, messages)),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps,
)(AuthScreen)
