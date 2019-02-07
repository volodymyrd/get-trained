import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {Image} from 'react-native'
import {Text, Container, List, ListItem, Content} from 'native-base'
import DashboardActions from '../Stores/Actions'
import Loading from "App/Components/Loading";
import {workspace} from "../Stores/Reducers";

class SideBar extends Component {

  componentDidUpdate(prevProps) {
    if (prevProps.fetchingSignOut
        && !this.props.fetchingSignOut) {
      this.props.navigation.navigate('Auth')
    }
  }

  signOutHandler = () => {
    this.props.signOut();
  }

  render() {
    const {navigation, fetchingSignOut} = this.props

    if (fetchingSignOut) {
      return <Loading/>
    }

    return (
        <Container>
          <Content>
            <Image
                source={{
                  uri:
                      'https://raw.githubusercontent.com/GeekyAnts/NativeBase-KitchenSink/master/assets/drawer-cover.png',
                }}
                style={{
                  height: 120,
                  width: '100%',
                  alignSelf: 'stretch',
                  position: 'absolute',
                }}
            />
            <Image
                square
                style={{
                  height: 80,
                  width: 70,
                  position: 'absolute',
                  alignSelf: 'center',
                  top: 20,
                }}
                source={{
                  uri:
                      'https://raw.githubusercontent.com/GeekyAnts/NativeBase-KitchenSink/master/assets/logo.png',
                }}
            />
            <List style={{marginTop: 120}}>
              <ListItem button onPress={() => navigation.navigate('Home')}>
                <Text>Home</Text>
              </ListItem>
              <ListItem button onPress={() => navigation.navigate('Profile')}>
                <Text>Profile</Text>
              </ListItem>
              <ListItem button onPress={() => navigation.navigate('Settings')}>
                <Text>Settings</Text>
              </ListItem>
              <ListItem button onPress={this.signOutHandler}>
                <Text>Sign Out</Text>
              </ListItem>
            </List>
          </Content>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  fetchingSignOut: state.dashboard.workspace.get('fetchingSignOut'),
})

const mapDispatchToProps = (dispatch) => ({
  signOut: () => dispatch(DashboardActions.fetchSignOut()),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SideBar)
