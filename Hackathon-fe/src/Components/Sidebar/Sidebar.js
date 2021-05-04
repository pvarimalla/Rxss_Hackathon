// //import useState hook to create menu collapse state
// import React, { useState } from "react";

// //import react pro sidebar components
// import {
//   ProSidebar,
//   Menu,
//   MenuItem,
//   SidebarHeader,
//   SidebarFooter,
//   SidebarContent,
// } from "react-pro-sidebar";

// //import icons from react icons
// import { FaList, FaRegHeart } from "react-icons/fa";
// import { FiHome, FiLogOut, FiArrowLeftCircle, FiArrowRightCircle } from "react-icons/fi";
// import { RiPencilLine } from "react-icons/ri";
// import { BiCog } from "react-icons/bi";


// //import sidebar css from react-pro-sidebar module and our custom css 
// import "react-pro-sidebar/dist/css/styles.css";
// import "./Sidebar.css";
// import Suggestions from '/Users/sjammula/data-mocking/src/routes/Forms/Suggestions.js';


// const Header = () => {
  
//     //create initial menuCollapse state using useState hook
//     const [menuCollapse, setMenuCollapse] = useState(false)
//     const [showSuggestions, setShowSuggestions] =useState(false)
//     //create a custom function that will change menucollapse state from false to true and true to false
//   const menuIconClick = () => {
//     //condition checking to change state from true to false and vice versa
//     menuCollapse ? setMenuCollapse(false) : setMenuCollapse(true);
//   };
 
//   function setShowSuggestions()
//   {
//     console.log(showMemberUid)
//     showSuggestions ? setShowSuggestions(false) : setShowSuggestions(true);

//   }


//   const ListClick = () => {
//    return <Suggestions/>
//   };
//   return (
//     <>
//      <div id="header">
//           {/* collapsed props to change menu size using menucollapse state */}
//         {/* <ProSidebar collapsed={menuCollapse}> */}
//           <ProSidebar>
//           <SidebarHeader>
//           <div className="logotext">
//               {/* small and big change using menucollapse state */}
//               <p> Data Mocking</p>
//             </div>
//           </SidebarHeader>
//           <SidebarContent>
//             <Menu iconShape="square" >
//               <MenuItem active={true} icon={<FaList />} >Alerts</MenuItem>
//               <MenuItem icon={<FaList />}onClick={() => this.setState({ListClick: true})}>Suggestions</MenuItem>
//               <MenuItem icon={<FaList />}onClick={</Suggestions>}>Suggestions</MenuItem>
//               {this.state.ListClick ? this.ListClick() : null}
//             </Menu>
//           </SidebarContent>
//         </ProSidebar>
//       </div>
//     </>
//   );
// };

// export default Header;


import React from 'react';
import styled from 'styled-components';
import { BrowserRouter as Router, Route, Link, withRouter,Switch } from "react-router-dom";
// import {Route, Switch} from "react-router-dom";

const StyledSideNav = styled.div`   
    position: fixed;     /* Fixed Sidebar (stay in place on scroll and position relative to viewport) */
    height: 100%;
    width: 75px;     /* Set the width of the sidebar */
    background-color: #6496ec; /* Blue */
    overflow-x: hidden;     /* Disable horizontal scroll */
    padding-top: 10px;
`;

class SideNav extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            activePath: props.location.pathname,
            items: [
                {
                  path: '/', /* path is used as id to check which NavItem is active basically */
                  name: 'Alerts',
                  css: 'fa fa-fw fa-list',
                  key: 1 /* Key is required, else console throws error */
                },
                {
                  path: '/Suggestions',
                  name: 'Suggestions',
                  css: 'fa fa-fw fa-list',
                  key: 2
                },
              ]
        }
    }

    onItemClick = (path) => {
        this.setState({ activePath: path });
    }

    render() {
        const { items, activePath } = this.state;
        return(
            <StyledSideNav>
                {
                    items.map((item) => {
                        return (
                            <NavItem 
                                path={item.path}
                                name={item.name}
                                css={item.css}
                                onItemClick={this.onItemClick}
                                active={item.path === activePath}
                                key={item.key}
                            />
                        );
                    })
                }
            </StyledSideNav>
        );
    }
}

const RouterSideNav = withRouter(SideNav);

const StyledNavItem = styled.div`
    height: 80px;
    width: 25px; /* width must be same size as NavBar to center */
    text-align: center; /* Aligns <a> inside of NavIcon div */
    margin-bottom: 0;   /* Puts space between NavItems */
    a {
        font-size: 2.7em;
        color: ${(props) => props.active ?  "#1703f2" : "white" };
        :hover {
            opacity: 0.7;
            text-decoration: none; /* Gets rid of underlining of icons */
        }  
    }
`;

class NavItem extends React.Component {
    handleClick = () => {
        const { path, onItemClick } = this.props;
        onItemClick(path);
    }

    render() {
        const { active } = this.props;
        return(
            <StyledNavItem active={active}>
                <Link to={this.props.path} className={this.props.css} onClick={this.handleClick}>
                    <NavIcon></NavIcon>
                </Link>
            </StyledNavItem>
        );
    }
}

const NavIcon = styled.div`

`;

export default class Sidebar extends React.Component {
    render() {
        return (
            <RouterSideNav></RouterSideNav>
        );
    }
}