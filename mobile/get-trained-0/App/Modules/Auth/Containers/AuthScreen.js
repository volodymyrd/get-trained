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
  Icon,
} from 'native-base';
import {AuthType} from '../Stores/InitialState'
import AuthActions from '../Stores/Actions'
import SignIn from "../Components/SignIn";
import SignUp from "../Components/SignUp";
import RestorePassword from "../Components/RestorePassword";
import styles from './AuthScreenStyle'

class AuthScreen extends React.Component {

  componentDidUpdate(prevProps) {
    if (prevProps.fetching.get('authenticating')
        && !this.props.fetching.get('authenticating')) {
      if (this.props.authenticated) {
        this.props.navigation.navigate('App')
      }
    }
  }

  authenticationHandler = (email, password) => {
    this.props.authenticate(email, password);
  }

  render() {
    const {authType, toggleAuthType, fetching} = this.props

    return (
        <Container>
          <Header style={styles.header}>
            <Body>
            {authType === AuthType.SIGN_IN && <Title>Sign In</Title>}
            {authType === AuthType.SIGN_UP && <Title>Sign Up</Title>}
            {authType === AuthType.RESTORE_PASSWORD
            && <Title>Restore password</Title>}
            </Body>
          </Header>
          <Content>
            {authType === AuthType.SIGN_IN
            && <SignIn loading={fetching.get('authenticating')}
                       authenticationHandler={this.authenticationHandler}/>}
            {authType === AuthType.SIGN_UP
            && <SignUp loading={fetching.authenticating}
                       authenticationHandler={this.authenticationHandler}/>}
            {authType === 'restorePassword'
            && <RestorePassword loading={fetching.authenticating}
                                authenticationHandler={this.authenticationHandler}/>}
          </Content>
          <Footer>
            <FooterTab>
              <Button active={authType === AuthType.SIGN_IN}
                      onPress={() => toggleAuthType(AuthType.SIGN_IN)}>
                <Text>Sign in</Text>
              </Button>
              <Button active={authType === AuthType.SIGN_UP}
                      onPress={() => toggleAuthType(AuthType.SIGN_UP)}>
                <Text>Sign up</Text>
              </Button>
              <Button active={authType === AuthType.RESTORE_PASSWORD}
                      onPress={() => toggleAuthType(AuthType.RESTORE_PASSWORD)}>
                <Text>Forgot password?</Text>
              </Button>
            </FooterTab>
          </Footer>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  authType: state.auth.root.get('authType'),
  fetching: state.auth.root.get('fetching'),
  authenticated: state.auth.root.get('authenticated'),
})

const mapDispatchToProps = (dispatch) => ({
  toggleAuthType: (authType) => dispatch(AuthActions.toggleAuthType(authType)),
  authenticate: (email, password) => dispatch(
      AuthActions.fetchAuthentication(email, password, "EN")),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps,
)(AuthScreen)
