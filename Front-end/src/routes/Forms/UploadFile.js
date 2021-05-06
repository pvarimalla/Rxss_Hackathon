
import React,{ Component } from 'react';
import './upload.css';
import './planInfo.css';

import Plan_Details from './planDetails';
import PlanRxDetails from './planRxDetails';
import FormControl from '@material-ui/core/FormControl';
import axios from "axios";

import TextField from '@material-ui/core/TextField';
import {
    Paper,
    MenuItem,
    Radio,
    RadioGroup,
    FormLabel,
    FormControlLabel,
  } from '@material-ui/core';
import {label, NavLink, CreateIm} from './upload.js';

import { Checkbox} from '@material-ui/core';

import styled from 'styled-components';



class UploadFile extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {
			file: '', 
			msg: '', 
			isFileUploaded: false, 
			planDetails: [], 
			rxDetails: [],
			value:'',
			name: null,
			showDropDown: false,
			fileCollection:'',
			showGroupId:'',
			pd:[],
			groupCode: undefined,
			pbm: undefined,
			isClientApproved: false,
			pdfParseResponse: undefined,
			createImp: false,
			createImpButton: true
		};
	}
	
	
	onFileChange = (event) => {
		this.setState({
			 fileCollection: event.target.files
		});
	}
	
	uploadFileData = (event) => {
		event.preventDefault();
		this.setState({msg: ''});
	
		 var formData = new FormData();
  
		 if (!this.state.groupCode || !this.state.pbm) {
			 alert("Please enter all fields");
			 return;
		 }
		
		for (const key of Object.keys(this.state.fileCollection)) {
			formData.append('file', this.state.fileCollection[key]);
		}
		formData.append('GroupCode', this.state.groupCode);
		formData.append('PBM', this.state.pbm);
		
		axios.post("http://localhost:8080/api/save-pdf", formData, {
        }).then((res) => res.data
        ).then(res => {
			const pd = [];
			const prd =[];
			res.map(re => {
				pd.push(re.planDetails);
				prd.push(re.rxDetails);
			});
			
			const planDetails = [].concat(...pd);
			const rxDetails = [].concat(...prd);

		   this.setState({
			   msg: "File successfully uploaded", 
			   isFileUploaded: true, 
			   planDetails: planDetails,
			   rxDetails: rxDetails,
			   pdfParseResponse: res,
			});
		}).catch(err => {
		   this.setState({error: err});
	   });
	}

	handleSaveToDatabase = () => {
		var formData = new FormData();
		formData.append('GroupCode', this.state.groupCode);
		axios.post("http://localhost:8080/api/create-table", formData, {}).then(() => {
			console.log("Success");
		});
	}

	
		showDropDown= () => {	
			   
			return(
				<>
				<div id="planInfo">			
					<div>
						<NavLink>
						<button class="details" onClick={()=>this.setState({name:'Plan_Details'})}>Plan details</button>
						<button class="details" onClick={()=>this.setState({name:'Plan_Rx_Details'})}>Plan Rx details</button>
						</NavLink>
					</div>
					<div>
						<Checkbox 
							checked={this.state.isClientApproved} 
							required 
							onClick={() => this.setState({isClientApproved: !this.state.isClientApproved})}
						/> Client Approval &nbsp; &nbsp;
						<button 
						disabled={!this.state.isClientApproved} 
						onClick={this.handleSaveToDatabase}>
							Submit to database 
						</button>
					</div>
					</div>
				</>
			)
		}
	
	showButtons=()=>{
		if(this.state.name===null){
			return null;
		}
		if(this.state.name==='Plan_Details'){
			return  <Plan_Details details={this.state.planDetails}/>
		}
		return  <PlanRxDetails rxDetails= { this.state.rxDetails}/>
	
	}
	

	showGroupId = () => {
		return (
		  <div> 
			  <form noValidate autoComplete="off">
				<TextField required id="standard-required" label="Required" />
			   </form>
		   </div>
		  );
	  }

	  createNewImplementation = () =>{
		return(
			<div>
			<form  noValidate autoComplete="off">	
				<div>
				  <label>Enter a group code</label>
				
				  <label>
				  <TextField id="outlined-search" label="Group code" type="search" variant="outlined" size="small"
				   onChange={(event)=> this.setState({groupCode: event.target.value})} />
				   </label>
				   </div>
				<div>
                <label>Enter a PBM</label>
				<label>
				<TextField id="outlined-search" label="PBM" type="search" variant="outlined" size="small"
				onChange={(event)=> this.setState({pbm: event.target.value})}/>
				</label>
				</div>
				</form>
				<br></br>
			  <div>
				 <input type="file" name="fileCollection" onChange={this.onFileChange} multiple/> 
				 <button  class="Upload" onClick={this.uploadFileData}>Upload</button> 
				{this.state.isFileUploaded && this.showDropDown()} 
				 {this.showButtons()}
				 </div>
				 </div>
		)
	  }

	  render() {
		return (
			<div id="container">
				<h1>{this.state.msg}</h1>
				<br></br>
				<h4>{this.state.msg}</h4>
				<label>
				<button  class="imp" onClick={() => this.setState({createImp: true, createImpButton: false})}>Create New Implementation</button> 
				{this.state.createImp? this.createNewImplementation(): null}
				<br></br>
				</label>
			
			</div>
		)
	}
}

export default UploadFile;


