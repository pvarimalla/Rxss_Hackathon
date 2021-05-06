//  import Header from './Components/Sidebar/Sidebar'
//  import MemberUidTest from './routes/Forms/MemberUidTest'
//  import MemberUid from './routes/Forms/MemberUid'
//  import ProductDetails from './routes/Forms/ProductDetails'
//  import Alerts from './routes/Forms/Alerts'
import { withStyles } from '@material-ui/core/styles';

import React from 'react';
import logo from './logo.svg';
import './App.css';
// import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import UploadFile from './routes/Forms/UploadFile';
import Sidebar from './Components/Sidebar/Sidebar';
import HeaderTest from './Components/Header/HeaderTest';
import planDetails from './routes/Forms/planDetails';

// function App() {
//   return (
//     <React.Fragment>
// <Router>
//  {/* <Navbar/>  */}
//  <HeaderTest/>
//  <UploadFile/> 

//   {/* <Sidebar /> */}
// {/* 
//   <Switch>
//     <Route exact path="/ProductDetails" component={ProductDetails} />
//     <Route path="/UploadFile" component={UploadFile} />
//     {/* <Route path="/plan_details" component={plan_details} /> */}
//   {/* </Switch> */} 
// </Router>
// </React.Fragment>
//   );
// }

// export default App;

 
  function App() {
    return (
      <div className="App">
        <Router>
           {<Sidebar/> }
           <HeaderTest/>
          <UploadFile/> 
          </Router>
      </div>
    );
  }

  export default App;
