import React, {Component} from 'react'
import PropTypes from 'prop-types';
import {
  Body,
  Button,
  Header,
  Icon,
  Item,
  Label,
  Left,
  Picker, Right, Title,
  View
} from 'native-base';

import style from './style';

export default class CommonPicker extends Component {
  state = {
    selectedValue: undefined,
  }

  componentDidMount() {
    this.setSelectedValue(this.props.value)
  }

  getSelectedValue() {
    return this.state.selectedValue
  }

  setSelectedValue(value) {
    this.setState({selectedValue: value});
  }

  render() {

    const {labelName, placeholder, values, value} = this.props
    const {selectedValue} = this.state

    return (
        <Item picker>
          <Label>{labelName}</Label>
          <View style={style.pickerView}>
            <Picker
                renderHeader={backAction =>
                    <Header>
                      <Left>
                        <Button transparent onPress={backAction}>
                          <Icon name="arrow-back"/>
                        </Button>
                      </Left>
                      <Body>
                      <Title>Gender</Title>
                      </Body>
                      <Right/>
                    </Header>}
                mode="dropdown"
                iosIcon={<Icon name="arrow-down"/>}
                style={{width: undefined}}
                placeholder={placeholder}
                //placeholderStyle={{color: "#bfc6ea"}}
                //placeholderIconColor="#007aff"
                selectedValue={selectedValue}
                onValueChange={(newValue) => this.setSelectedValue(newValue)}
            >
              {values.entrySeq().toArray().map(([k, v]) =>
                  (<Picker.Item label={v} value={k} key={k}/>)
              )}
            </Picker>
          </View>
        </Item>
    )
  }
}

CommonPicker.propTypes = {
  labelName: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  values: PropTypes.object.isRequired,// expects Map
  value: PropTypes.string,
  //onPressHandler: PropTypes.func.isRequired,
  //notFull: PropTypes.bool,
}
