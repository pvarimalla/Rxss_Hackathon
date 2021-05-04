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
import Alerts from './routes/Forms/Alerts'
import Suggestions from './routes/Forms/Suggestions'
import Sidebar from './Components/Sidebar/Sidebar';
import Home from './routes/Forms/Home'
import UploadFile from './routes/Forms/UploadFile';
import HeaderTest from './Components/Header/HeaderTest';
import plan_details from './routes/Forms/plan_details';
function App() {
  return (
    <React.Fragment>
<Router>
 {/* <Navbar/>  */}
 <HeaderTest/> 
  {/* <Sidebar /> */}

  <Switch>
    <Route exact path="/UploadFile" component={UploadFile} />
    <Route path="/Suggestions" component={Suggestions} />
    <Route path="/plan_details" component={plan_details} />
  </Switch>
</Router>
</React.Fragment>
  );
}

export default App;