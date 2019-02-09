import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {FlatList, Image} from 'react-native'
import {
  Text,
  Container,
  ListItem,
  Content,
  Accordion,
} from 'native-base'
import DashboardActions from '../Stores/Actions'
import Loading from "App/Components/Loading";

class SideBar extends Component {

  static _renderContent(menu) {
    return (
        <FlatList
            data={menu.items}
            keyExtractor={(item) => item.id}
            renderItem={({item}) =>
                <ListItem button
                          onPress={item.fun}>
                  <Text>{item.title}</Text>
                </ListItem>
            }
        />
    );
  }

  componentDidUpdate(prevProps) {
    if (prevProps.fetchingSignOut
        && !this.props.fetchingSignOut) {
      this.props.navigation.navigate('Auth')
    }
  }

  _signOutHandler = () => {
    this.props.signOut();
  }

  render() {
    const {navigation, fetchingSignOut} = this.props

    if (fetchingSignOut) {
      return <Loading/>
    }

    const menu = [
      {
        id: 'profile',
        title: 'Profile',
        items: [
          {
            id: 'menu1',
            title: 'Menu1',
            fun: () => navigation.navigate('Profile')
          },
          {
            id: 'menu2',
            title: 'Menu2',
            fun: () => navigation.navigate('Profile')
          },
          {
            id: 'menu3',
            title: 'Menu3',
            fun: () => navigation.navigate('Profile')
          },
        ]
      },
      {
        id: 'signOut',
        title: 'Sign Out',
        items: [],
        fun: this._signOutHandler
      }
    ]

    const singleMenu = menu.filter(e => e.items.length === 0)
    const nestedMenu = menu.filter(e => e.items.length > 0)
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
            <Accordion
                dataArray={nestedMenu}
                renderContent={SideBar._renderContent}
            />
            <FlatList
                data={singleMenu}
                keyExtractor={(item) => item.id}
                renderItem={({item}) =>
                    <ListItem button
                              onPress={item.fun}>
                      <Text>{item.title}</Text>
                    </ListItem>
                }
            />
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
