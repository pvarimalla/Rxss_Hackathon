import React, { useState, useEffect } from 'react';
import './App.css';
import MaterialTable from 'material-table';
import { assertExpressionStatement } from '@babel/types';
import axios from "axios";
// import UploadFile from './routes/Forms/UploadFile';


const PlanRxDetails = ({rxDetails}) => {
    const rxColumns = [
      { title: "Group Prefix Code", field: "groupName" },
      { title: "Plan ID", field: "planId" },
      { title: "Plan Name", field: "planName" },
      { title: "Plan Begin Date", field: "coverageBeginDate" },
      { title: "Plan End Date", field: "coverageEndDate" },
      { title: "Drug Tier", field: "drugTier" },
      { title: "Pharmacy Tier", field: "pharmacyTier" },
      { title: "Copay Value", field: "copay" },
      { title: "Coinsurance Value", field: "coinsurance" },
      { title: "Coinsurance Max Cost", field: "coinsuranceMaxCost" },
      { title: "Coinsurance Min Cost", field: "coinsuranceMinCost" },
      { title: "Hierarchy", field: "hierarcy" },
      { title: "Is deductible paid before copay?", field: "deductiblePaidBeforeCopay" }
    ]

    return (
      <div className="App">
        <br></br>
        <div>
        <MaterialTable options={{ paging: false }}
          title="Plan Rx Details Information"
          data={rxDetails}
          columns={rxColumns}
        />
        </div>
        
      </div>
    );
  }
  
  export default PlanRxDetails;














