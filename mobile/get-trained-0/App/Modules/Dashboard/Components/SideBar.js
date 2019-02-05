import React from 'react'
import { Image } from 'react-native'
import { Text, Container, List, ListItem, Content } from 'native-base'
export default class SideBar extends React.Component {
  render() {
    const { signOutHandler } = this.props

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
          <List style={{ marginTop: 120 }}>
            <ListItem button onPress={() => this.props.navigation.navigate('Home')}>
              <Text>Home</Text>
            </ListItem>
            <ListItem button onPress={() => this.props.navigation.navigate('Profile')}>
              <Text>Profile</Text>
            </ListItem>
            <ListItem button onPress={signOutHandler}>
              <Text>Sign Out</Text>
            </ListItem>
          </List>
        </Content>
      </Container>
    )
  }
}
