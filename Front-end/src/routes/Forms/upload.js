import { FaBars } from 'react-icons/fa';
import { NavLink as Link } from 'react-router-dom';
import styled from 'styled-components';

/* #container {
	width: 800px;
	margin: auto;
} */

export const label = styled.div`
padding-left: 100px;
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
padding: 20px 40px;   
`;

export const NavLink = styled.div`
  color: #33658a;
  display: flex;
  align-items: center;
  text-decoration: none;
  padding: 0 1rem;
  height: 100%;
  cursor: pointer;

  &.active {
    color: #33658a;
  }
`;


export const CreateIm = styled.div`

padding: 10px;
margin-bottom: .5rem;
box-sizing: border-box;
`;
