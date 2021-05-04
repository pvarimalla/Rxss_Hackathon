import React from 'react';
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavBtn,
  NavBtnLink,
  NavLeft,
  NavRight

} from './HeaderElements';
// import logo from "/Users/sjammula/data-mocking/src/RxSavingsLogo.png";
import logo from "/Users/sjammula/rxss-business-validation/data-mocking/src/RxSavingsLogo.png";
import { Button} from 'react-bootstrap'


const HeaderTest = () => {
  return (
    <>
      <Nav>
        <NavLink to='/Alerts'>
        <NavLeft><img src={logo} alt='logo'/></NavLeft>
        </NavLink>
        <Bars />
        <NavMenu>
        {/* <NavRight> */}
          <NavLink to='/UploadFile' activeStyle>
          <NavBtn><h1>Upload</h1></NavBtn> 
          </NavLink>
          <NavLink to='/plan_details' activeStyle>
          <NavBtn><h1>Plan Details</h1></NavBtn>
          </NavLink>
          <NavLink to='/Suggestions' activeStyle>
          <NavBtn><h1>Plan_rx_details</h1></NavBtn>
          </NavLink>
          {/* </NavRight> */}
        </NavMenu>
      </Nav>
    </>
  );
};

export default HeaderTest;