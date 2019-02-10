import React, {Component} from 'react'
import connect from 'react-redux/es/connect/connect'
import {FlatList, Image} from 'react-native'
import {
  Container,
  Content,
  View,
  Accordion,
  ListItem,
  Button,
  Icon,
  Text,
} from 'native-base'
import DashboardActions from '../Stores/Actions'
import Loading from "App/Components/Loading";
import {MENU_MODULE} from "../Metadata";
import {getMenu} from "../Components/Menu";

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

  componentDidMount() {
    if (this.props.langCode
        && !(this.props.metadata.size
            && this.props.metadata.get('module') === MENU_MODULE)) {
      this.props.fetchMetadata(this.props.langCode)
    }
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
    const {
      navigation,
      fetchingMetadata,
      metadata,
      fetchingSignOut
    } = this.props

    if (fetchingMetadata || fetchingSignOut || !metadata || !metadata.size) {
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
            <View style={{
              marginTop: 120,
              flexDirection: 'row',
              justifyContent: 'center',
            }}>
              <Button transparent onPress={() => navigation.navigate('_Home')}>
                <Icon name='home'/>
              </Button>
              <Button transparent onPress={this._signOutHandler}>
                <Icon name='md-exit'/>
              </Button>
            </View>
            <Accordion
                dataArray={getMenu(navigation, metadata.get('localizations'))}
                renderContent={SideBar._renderContent}
            />
          </Content>
        </Container>
    )
  }
}

const mapStateToProps = (state) => ({
  langCode: state.main.get('langCode').toUpperCase(),
  fetchingMetadata: state.dashboard.workspace.get('fetchingMetadata'),
  metadata: state.dashboard.workspace.get('metadata'),
  fetchingSignOut: state.dashboard.workspace.get('fetchingSignOut'),
})

const mapDispatchToProps = (dispatch) => ({
  fetchMetadata: (langCode) => dispatch(
      DashboardActions.fetchMetadata(langCode)),
  signOut: () => dispatch(DashboardActions.fetchSignOut()),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SideBar)
