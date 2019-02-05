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
import Error from "App/Components/Error";
import Loading from "App/Components/Loading";
import {AuthStep} from '../Stores/InitialState'
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
  errorBadCredentials,
  undefinedError,
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
    const langCode = this.props.langCode.toUpperCase()
    const localizations = this.props.metadata.get('localizations')
    const messages = [errorBadCredentials(localizations)]
    this.props.authenticate(email, password, langCode, messages);
  }

  signUpHandler = (email, password, firstName) => {
    const langCode = this.props.langCode.toUpperCase()
    const localizations = this.props.metadata.get('localizations')
    const messages = [undefinedError(localizations)]
    this.props.signUp(email, password, firstName, langCode, messages);
  }

  restorePasswordHandler = (email) => {
    const langCode = this.props.langCode.toUpperCase()
    const localizations = this.props.metadata.get('localizations')
    const messages = [undefinedError(localizations)]
    this.props.restorePassword(email, langCode, messages);
  }

  render() {
    const {
      langCode,
      metadata,
      failedRetrievingMetadata,
      authStep,
      toggleAuthStep,
      fetchingMetadata,
      fetchingAuthenticating,
      fetchingSignUp,
      fetchingRestorePassword,
    } = this.props

    if (failedRetrievingMetadata) {
      return <Error/>
    }

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
            {authStep === AuthStep.SIGN_IN && <Title>{tSignIn}</Title>}
            {authStep === AuthStep.SIGN_UP && <Title>{tSignUp}</Title>}
            {authStep === AuthStep.RESTORE_PASSWORD
            && <Title>{tRestorePass}</Title>}
            </Body>
          </Header>
          <Content style={styles.content}>
            <View style={styles.logo}>
              <Image source={LOGO_128}/>
            </View>
            {authStep === AuthStep.SIGN_IN
            && <SignIn txtEmail={txtEmail(localizations)}
                       txtPassword={txtPassword(localizations)}
                       txtBtn={btnSignIn(localizations)}
                       minPasswordLength={minPasswordLength}
                       loading={fetchingAuthenticating}
                       authenticationHandler={this.authenticationHandler}/>}
            {authStep === AuthStep.SIGN_UP
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
                       loading={fetchingSignUp}
                       signUpHandler={this.signUpHandler}/>}
            {authStep === 'restorePassword'
            && <RestorePassword txtEmail={txtEmail(localizations)}
                                txtBtn={btnGenNewPassword(localizations)}
                                loading={fetchingRestorePassword}
                                restorePasswordHandler={this.restorePasswordHandler}/>}
          </Content>
          <Footer>
            <FooterTab>
              <Button active={authStep === AuthStep.SIGN_IN}
                      disabled={fetchingAuthenticating || fetchingSignUp
                      || fetchingRestorePassword}
                      onPress={() => toggleAuthStep(AuthStep.SIGN_IN)}>
                <Text>{tSignIn}</Text>
              </Button>
              <Button active={authStep === AuthStep.SIGN_UP}
                      disabled={fetchingAuthenticating || fetchingSignUp
                      || fetchingRestorePassword}
                      onPress={() => toggleAuthStep(AuthStep.SIGN_UP)}>
                <Text>{tSignUp}</Text>
              </Button>
              <Button active={authStep === AuthStep.RESTORE_PASSWORD}
                      disabled={fetchingAuthenticating || fetchingSignUp
                      || fetchingRestorePassword}
                      onPress={() => toggleAuthStep(AuthStep.RESTORE_PASSWORD)}>
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
  failedRetrievingMetadata: state.auth.root.get('failedRetrievingMetadata'),
  metadata: state.auth.root.get('metadata'),
  authStep: state.auth.root.get('authStep'),
  fetchingAuthenticating: state.auth.root.get('fetchingAuthenticating'),
  authenticated: state.auth.root.get('authenticated'),
  fetchingSignUp: state.auth.root.get('fetchingSignUp'),
  fetchingRestorePassword: state.auth.root.get('fetchingRestorePassword'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(AuthActions.fetchMetadata(langCode)),
  toggleAuthStep: (authStep) => dispatch(AuthActions.toggleAuthStep(authStep)),
  authenticate: (email, password, langCode, messages) => dispatch(
      AuthActions.fetchAuthentication(email, password, langCode, messages)),
  signUp: (email, password, firstName, langCode, messages) => dispatch(
      AuthActions.fetchSignUp(email, password, firstName, langCode, messages)),
  restorePassword: (email, langCode, messages) => dispatch(
      AuthActions.fetchRestorePassword(email, langCode, messages))
})

export default connect(
    mapStateToProps,
    mapDispatchToProps,
)(AuthScreen)
