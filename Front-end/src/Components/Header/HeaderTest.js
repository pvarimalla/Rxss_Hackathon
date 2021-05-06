import React from 'react';
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavLeft,
} from './HeaderElements';
import logo from "./../../images/RxSavingsLogo.png";

const HeaderTest = () => {
  return (
    <>
      <Nav>
        <NavLink to='/UploadFile'>
        <NavLeft><img src={logo} alt='logo'/></NavLeft>
        </NavLink>
        <Bars />
        <NavMenu>
        {/* <NavRight> */}
          
           {/* <NavLink to='/UploadFile' activeStyle>
          <NavBtn><h1>UploadFile</h1></NavBtn>
          </NavLink> */}
          {/*</Nav><NavLink to='/Suggestions' activeStyle>
          <NavBtn><h1>Plan_rx_details</h1></NavBtn>
          </NavLink> */}
          {/* </NavRight> */}
        </NavMenu>
      </Nav>
    </>
  );
};

export default HeaderTest;