import React from 'react';
import './upload.css';
import './planInfo.css';

import Plan_Details from './planDetails';
import PlanRxDetails from './planRxDetails';
import axios from "axios";
// import LoadingButton from '@material-ui/lab/LoadingButton';
import TextField from '@material-ui/core/TextField';
import {
	Grid,
	CircularProgress,
	Button, 
	Checkbox,
  } from '@material-ui/core';
import { Gif } from '@material-ui/icons';

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
			createImpButton: true,
			showParsePdfLoadingState: false,
			showSaveToDbLoadingState: false,
			isSavedToDb: false,
		};
	}
	
	
	onFileChange = (event) => {
		this.setState({
			 fileCollection: event.target.files
		});
	}
	
	uploadFileData = (event) => {
		event.preventDefault();
	
		var formData = new FormData();
  
		 if (!this.state.groupCode || !this.state.pbm) {
			 alert("Please enter all fields");
			 return;
		 }

		this.setState({msg: '', showParsePdfLoadingState: true});
		
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
			   showParsePdfLoadingState: false,
			});
		}).catch(err => {
		   this.setState({error: err});
	   });
	}

	handleSaveToDatabase = () => {
		this.setState({showSaveToDbLoadingState: true});
		var formData = new FormData();
		formData.append('GroupCode', this.state.groupCode);
		axios.post("http://localhost:8080/api/create-table", formData, {}).then(() => {
			this.setState({showSaveToDbLoadingState: false, isSavedToDb: true});
			alert("Success, saved to database");
		}).catch(() => {
			alert("Something went wrong with saving to database.");
			this.setState({isSavedToDb: false});
		});
	}

	handleExport= () => {
		var formData = new FormData();
		for (const key of Object.keys(this.state.fileCollection)) {
			formData.append('file', this.state.fileCollection[key]);
		}
		console.log(this.state.fileCollection);
		formData.append('GroupCode', this.state.groupCode);
		formData.append('PBM', this.state.pbm);
		
		axios.post("http://localhost:8080/api/export", formData, {}).then(() => {
			console.log("Success");
		});
	}
		showDropDown= () => {
			return(
				<>
				<div id="planInfo">			
					<div>
						<button class="details" onClick={()=>this.setState({name:'Plan_Details'})}><b>Plan details</b></button>
						<button class="details" onClick={()=>this.setState({name:'Plan_Rx_Details'})}><b>Plan Rx details</b></button>
					</div>
					<div class="clientApproval">
						<Checkbox 
							checked={this.state.isClientApproved} 
							required 
							onClick={() => this.setState({isClientApproved: !this.state.isClientApproved})}
						/> Client Approval &nbsp; &nbsp;
						{
							this.state.showSaveToDbLoadingState 
							? <CircularProgress /> 
							: !this.state.isSavedToDb && <Button
								variant="contained" 
								disabled={!this.state.isClientApproved} 
								onClick={this.handleSaveToDatabase}
								style={{
									backgroundColor: "#e5f9fe",
									color: "#33658a", 
									opacity: this.state.isClientApproved ? 1 : 0.3
								}}
								>
									Submit to database 
								</Button>
						}
						
						<br></br>
						<label>
						<Button style={{backgroundColor: "#e5f9fe" , color: "#33658a" }} onClick={this.handleExport} variant="contained">Export as CSV</Button>
												
						</label>

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

	  createNewImplementation = () => { 
		return(
			<div>
			<form  noValidate autoComplete="off">	
				<div>
				  
					<TextField 
						id="outlined-search" 
						label="Group code" 
						type="search" 
						variant="outlined" 
						size="small" 
						placeholder="Enter a Group Code"
						onChange={(event)=> this.setState({groupCode: event.target.value})} 
					/>			   
				</div>
				<div>
					<TextField 
						id="outlined-search" 
						label="PBM" 
						type="search" 
						variant="outlined" 
						size="small"
						placeholder="Enter a PBM"
						onChange={(event)=> this.setState({pbm: event.target.value})}
					/>
				</div>
			</form>
				<br></br>
				<Grid container alignItems="center">
					<Grid item>
					<input 
						id="choose-file" 
						type="file" 
						class="chooseFile" 
						name="fileCollection" 
						onChange={this.onFileChange} 
						multiple
					/>
					</Grid>
					<Grid item>
						{this.state.showParsePdfLoadingState ? <CircularProgress /> : <button class="Upload" onClick={this.uploadFileData}>Upload</button>} 
					</Grid>
					<Grid item xs={12}>
						{this.state.isFileUploaded && this.showDropDown()} 
						{this.showButtons()}
					</Grid>
				</Grid>
			  	{/* <div>
					<input 
						id="choose-file" 
						type="file" 
						class="chooseFile" 
						name="fileCollection" 
						onChange={this.onFileChange} 
						multiple
					/>
					<CircularProgress />
				 <button  class="Upload" onClick={this.uploadFileData}>Upload</button> 
				{this.state.isFileUploaded && this.showDropDown()} 
				 {this.showButtons()}
				</div> */}
				</div>
		)
	  }


	 

	  render() {
		return (
			<div id="container">
				<h1></h1> 
				<br></br>
				<label>
				{this.state.createImpButton && (
					<Button class="imp" onClick={() => this.setState({createImp: true, createImpButton: false})}>
						Create New Implementation
					</Button>	
				)} 
				{this.state.createImp? this.createNewImplementation(): null}
				<br></br>
				</label>
			</div>
		)}

}

export default UploadFile;
