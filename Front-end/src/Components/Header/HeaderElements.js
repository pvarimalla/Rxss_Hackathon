import { FaBars } from 'react-icons/fa';
import { NavLink as Link } from 'react-router-dom';
import styled from 'styled-components';

export const Nav = styled.nav`
  background: #fff;
  height: 80px;
  display: flex;
  justify-content: space-between;
  padding: 0.5rem calc((100vw - 1000px) / 2);
  z-index: 10;

  /* Third Nav */
  /* justify-content: flex-start; */
`;

export const NavLink = styled(Link)`
  color: #000000;
  display: flex;
  align-items: center;
  text-decoration: none;
  padding: 0 1rem;
  height: 100%;
  cursor: pointer;

  &.active {
    color: #6496ec;
  }
`;

export const Bars = styled(FaBars)`
  display: none;
  color: #fff;

  @media screen and (max-width: 768px) {
    display: block;
    position: absolute;
    top: 0;
    right: 0;
    transform: translate(-100%, 75%);
    font-size: 1.8rem;
    cursor: pointer;
  }
`;

export const label = styled.div`
display: inline-block;
    margin-bottom: .5rem;
    box-sizing: border-box;
    cursor: default;
    font-weight: 300;
    font-family: Roboto,sans-serif;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #4c3e54;
    text-align: left;
`;

export const NavMenu = styled.div`
  display: flex;
  align-items: center;
  margin-right: -24px;

  /* Second Nav */
  /* margin-right: 24px; */

  /* Third Nav */
  /* width: 100vw;
  white-space: nowrap; */

  @media screen and (max-width: 768px) {
    display: none;
  }
`;

// export const NavBtn = styled.nav`
//   display: flex;
//   align-items: center;
//   margin-right: 24px;

//   /* Third Nav */
//   /* justify-content: flex-end;
//   width: 100vw; */

//   @media screen and (max-width: 768px) {
//     display: none;
//   }
// `;

export const NavBtnLink = styled(Link)`
  border-radius: 4px;
  background: #256ce1;
  padding: 10px 22px;
  color: #010606;
  outline: none;
  border: none;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  text-decoration: none;

  /* Second Nav */
  margin-left: 24px;

  &:hover {
    transition: all 0.2s ease-in-out;
    background: #6496ec;
    color: #6496ec;
  }
`;

export const NavLeft = styled.div`
  // width: 33.333%;
  // text-align: center;
  position:absolute;
  left:10px;
  top:10px;
  margin: 0px 15px 15px 0px;
`;

export const NavRight = styled.div`
 width: 63.333%;
text-align: right;
  float:right;
  color: #a841aa;
  
`;

export const Heading = styled.div`
text-transform: uppercase;
    font-size: 2rem;
    margin-bottom: .5rem;
    font-weight: 300;
    line-height: 1.2;
    color: #5f5067;
    box-sizing: border-box;
    display: block;
    font-size: 2em;
    margin-block-start: 0.67em;
    margin-block-end: 0.67em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
`;

export const rightHeader= styled.div`
color: #a841aa;
    text-decoration: none;
    background-color: transparent;
    box-sizing: border-box;
    cursor: pointer;
    text-align: right;
    font-size: 1rem;
    font-weight: 300;
    line-height: 1.2;
    font-family: Roboto,sans-serif;
    
`;





// export const NavBtn= styled.div`
// &:hover {
//   border-radius: 2px;
//   padding-right: 3px;
//   padding-bottom: 5px;
//   padding-left: 3px;
//   transition: all 0.2s ease-in-out;
//   height: 50px;
//   background: #6496ec;
//   color: white;
// }`;
export const NavBtn= styled.div`
&:hover {
-moz-border-radius-topleft: 5px;
-moz-border-radius-topright: 5px;
-moz-border-radius-bottomright: 5px;
-moz-border-radius-bottomleft: 5px;
-webkit-border-radius: 10px 10px 10px 10px;
border-radius: 5px 5px 5px 5x;
background-color: #6496ec;
color: white;
}`;

