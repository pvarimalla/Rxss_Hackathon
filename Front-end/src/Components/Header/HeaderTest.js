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
import logo from "/Users/sjammula/Documents/GitHub/Rxss_Hackathon/Front-end/src/RxSavingsLogo.png";
import { Button} from 'react-bootstrap'


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