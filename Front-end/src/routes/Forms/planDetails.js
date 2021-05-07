
import React, { useState, useEffect } from 'react';
import './App.css';
import { forwardRef } from 'react';
// import Avatar from 'react-avatar';
import Grid from '@material-ui/core/Grid'

import MaterialTable from "material-table";
import AddBox from '@material-ui/icons/AddBox';
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import axios from 'axios'
import Alert from '@material-ui/lab/Alert';
import {label, NavLink, CreateIm} from './upload.js';

const tableIcons = {
  Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
  Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
  Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
  DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
  Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
  Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
  FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
  LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
  NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
  ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
  SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref} />),
  ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
  ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};

const api = axios.create({
  baseURL: `https://reqres.in/api`
})

const PlanDetails = ({details}) => {
  console.log(details);
  const [data, setData] = useState([])
  const columns = [
    { title: "Group Prefix Code", field: "groupName" },
    { title: "Plan ID", field: "planId" },
    { title: "Plan Name", field: "planName" },
    { title: "Plan Begin Date", field: "coverageBeginDate" },
    { title: "Plan End Date", field: "coverageEndDate" },
    {title: "Brand Over Generic Flag", field: "brandOverGeneric"},
    { title: "PBM", field: "pbm" },
    { title: "Formulary Description", field: "formularyDescription" },
    { title: "Network Description", field: "networkDescription" },
    { title: "Individual Deductible Amount", field: "individualDeductibleAmount" },
    { title: "Family Deductible Amount", field: "familyDeductibleAmount" },
    { title: "Individual OOP Amount", field: "individualOutOfPocketAmount" },
    { title: "Family OOP Amount", field: "familyOutOfPocketAmount" },
    { title: "Embedded Deductible Flag", field: "deductibleEmbedded" },
    { title: "Embedded Deductible Amount", field: "embeddedDeductibleAmount" },
    { title: "Embedded OOP Flag", field: "outOfPocketEmbedded" },
    { title: "Embedded OOP Amount", field: "embeddedOutOfPocketAmount" },
    { title: "Coverage Type", field: "coverageType" },
    { title: "Mandatory Maintenance info", field: "mandatoryMaintenance" },
    { title: "Special Conditions", field: "specialConditions" },

  ]


  //for error handling
  const [iserror, setIserror] = useState(false)
  const [errorMessages, setErrorMessages] = useState([])


  const handleRowUpdate = (newData, oldData, resolve) => {
    //validation
    let errorList = []
  
    const dataUpdate = [...data];
    const index = oldData.tableData.id;
    dataUpdate[index] = newData;
    setData([...dataUpdate]);
    resolve()
    setIserror(false)
    setErrorMessages([])
    
    
    
  }

  const handleRowAdd = (newData, resolve) => {
    let errorList = []
 
    
  }

  const handleRowDelete = (oldData, resolve) => {
 
  }


  return (
    <div className="App">
      
      <Grid container spacing={1}>
          <Grid item xs={12}>
          <div>
            {iserror && 
              <Alert severity="error">
                  {errorMessages.map((msg, i) => {
                      return <div key={i}>{msg}</div>
                  })}
              </Alert>
            }       
          </div>
         
            <MaterialTable options={{ align:"left", paging: false ,
            //  headerStyle:{backgroundColor:'#33658a', color: '#fff',}}} 
           
            headerStyle: {
              width: 35,
              whiteSpace: 'nowrap',
              textAlign: 'left',
              flexDirection: 'row',
              overflow: 'hidden',
              textOverflow: 'ellipsis',
              paddingLeft: 5,
              paddingRight: 5,
              backgroundColor: '#33658a',
              fontWeight: 'bold',
              fontSize: "18px",
              height: 100,
              color: '#fff',
            },
            rowStyle: {
              backgroundColor: '#e5f9fe', 
              color: '#33658a', // not working! i tried without the !important as well
              fontWeight: 'bold',
            },
          }}
            style={{ width: 1500, alignItems: "left" }}
            
            title="Plan Details Information"
              columns={columns}
              data={details}
              icons={tableIcons}
              
              editable={{
                
                onRowUpdate: (newData, oldData) =>
                {
                  console.log(newData);
                console.log(oldData);
                details[0].planId="Activities"
                return new Promise((resolve) => {
                  console.log("resolved");
                  resolve(details)
                  console.log("details");
                })},
              
            
                 
                onRowAdd: (newData) =>
                  new Promise((resolve) => {
                    handleRowAdd(newData, resolve)
                  }),
                onRowDelete: (oldData) =>
                  new Promise((resolve) => {
                    handleRowDelete(oldData, resolve)
                  }),
              }}
              
            />
            
          </Grid>
          <Grid item xs={3}></Grid>
        </Grid>
        
    </div>
  );
}

export default PlanDetails;