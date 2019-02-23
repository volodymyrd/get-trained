import React from 'react'
import { Body, Button, Header, Icon, Left, Right, Title } from 'native-base'

export const setNavigationOptions = (navigation, title) => {
  return {
    header: (
      <Header>
        <Left style={{ maxWidth: 30 }}>
          <Button transparent onPress={() => navigation.openDrawer()}>
            <Icon name="menu" />
          </Button>
        </Left>
        <Body>
          <Title>{title}</Title>
        </Body>
        <Right style={{ maxWidth: 30 }} />
      </Header>
    ),
  }
}
