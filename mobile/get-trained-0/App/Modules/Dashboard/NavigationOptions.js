import React from 'react'
import { Body, Button, Header, Icon, Left, Right, Title } from 'native-base'

export const setNavigationOptions = (navigation, backNav) => {
  return {
    header: (
      <Header>
        <Left style={{ maxWidth: 30 }}>
          <Button
            transparent
            onPress={() => (backNav ? navigation.goBack() : navigation.openDrawer())}
          >
            <Icon name={backNav ? 'arrow-back' : 'menu'} style={{ fontSize: 35 }} />
          </Button>
        </Left>
        <Body>
          <Title>{navigation.getParam('title')}</Title>
        </Body>
        <Right style={{ maxWidth: 30 }} />
      </Header>
    ),
  }
}
