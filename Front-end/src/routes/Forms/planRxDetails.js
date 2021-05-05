import React, { useState, useEffect } from 'react';
import './App.css';
import MaterialTable from 'material-table';
import { assertExpressionStatement } from '@babel/types';
import axios from "axios";
// import UploadFile from './routes/Forms/UploadFile';


const PlanRxDetails = ({rxDetails}) => {


  console.log(rxDetails);

    
    const [rxData, rxSetData] = useState([])
    const rxColumns = [
      { title: "planId", field: "planId" },
      { title: "planName", field: "planName" },
      { title: "coinsurance", field: "coinsurance" },
      { title: "coinsuranceMaxCost", field: "coinsuranceMaxCost" },
      { title: "coinsuranceMinCost", field: "coinsuranceMinCost" },
      { title: "deductiblePaidBeforeCopay", field: "deductiblePaidBeforeCopay" },
      { title: "pharmacyTier", field: "pharmacyTier" }
      

    ]
    
  

    
    return (
      <div className="App">
        <br></br>
        <div>
        <MaterialTable options={{ paging: false }}
          title="Plan Rx Details"
          data={rxDetails}
          columns={rxColumns}
        />
        </div>
        
      </div>
    );
  }
  
  export default PlanRxDetails;














