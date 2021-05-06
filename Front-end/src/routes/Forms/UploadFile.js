import React,{ Component } from 'react';
import './upload.css';
import './planInfo.css';
import Plan_Details from './planDetails';
import PlanRxDetails from './planRxDetails';
import FormControl from '@material-ui/core/FormControl';
import axios from "axios";
import { Checkbox, TextField} from '@material-ui/core';
import { NavLink as Link } from 'react-router-dom';
import styled from 'styled-components';
import Switch from "react-switch";
import { DragSwitch } from 'react-dragswitch'
import 'react-dragswitch/dist/index.css'


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
		};
		// let data = new FormData();
	}
	
	//state= {showDropDown: false}
	onFileChange = (event) => {
// 		const files = event.target.files;
// 		for (let i = 0; i < files.length; i++) {
//     	formData.append(`file[${i}]`, files[i])
// }
		this.setState({
			 fileCollection: event.target.files
			// file: event.target.files[0]
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
		// const formData = new FormData();
		// formData.append('benefitsCoverageDetails', this.state.pdfParseResponse);
		// formData.append('GroupCode', this.state.groupCode);
		// formData.append('PBM', this.state.pbm);
		var formData = new FormData();
		formData.append('GroupCode', this.state.groupCode);
		// const formData = {
		// 	// benefitsCoverageDetails: this.state.pdfParseResponse,
		// 	GroupCode: this.state.groupCode,
		// 	// PBM: this.state.pbm
		// };

		axios.post("http://localhost:8080/api/create-table", formData, {}).then(() => {
			console.log("Success");
		});
	}

/*
		fetch('http://localhost:8080/api/pdf-parse', {
			mode: 'no-cors',
			// enctype: "form-data",
			method: 'POST',
			body: formData
		})  .then(resp => resp.json())
        .then(resp => {
			 console.log(resp.json());
			this.setState({msg: "File successfully uploaded", isFileUploaded: true, planDetails: resp.planDetails, rxDetails: resp.rxDetails});
			console.log(resp.json())
		  console.log(resp.planDetails)
		  console.log(resp.rxDetails)
		}).catch(err => {
			this.setState({error: err});
		});*/
		

	// handlechange= event => {
	// 	this.setState({ name: event.target.value });
	// 	console.log("entered handleChange"+event.target.value);
	// // 	this.setState({value: event.target.value});
	// // if(event.target.value==1 )
	// // 	{<Plan_Details details={this.state.planDetails}/>}
	// // else
	// // 	{ <PlanRxDetails rxDetails= { this.state.rxDetails}/>}
	// }
	
	// handleChange = event => {
	// 	// this.setState({ name: event.target.value });
	// 	return <Plan_Details details={this.state.planDetails}/>
	//   };
	
		showDropDown= () => {	
			   
			return(
				<>
				<div id="planInfo">
				{/* <div>
	  <span>Plan Details</span>
	  <DragSwitch
		checked={(this.state.checked)}
		
        onChange={c => {
          this.setState({ name:'Plan_Details',checked: c })
        }}
      />
				{/* </div>

				<div> }
	  <span>Plan Rx Details</span>
	  <DragSwitch
		checked={(this.state.checked)}
		
        onChange={c => {
          this.setState({ name:'Plan_Rx_Details',checked: c })
        }}
      />
				</div> */}
					
					
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
	
	render() {
		return (
			<div id="container">
				
				<h4>{this.state.msg}</h4>
				<div>
				<FormControl >
            
				  <h4>Enter a group code</h4>
				  <TextField 
					  required 
					  id="group-id" 
					  label="Required" 
					  onChange={(event)=> this.setState({groupCode: event.target.value})}
					/>
                <h4>Enter PBM</h4>
				<TextField 
					required 
					id="pbm-id" 
					label="Required" 
					onChange={(event)=> this.setState({pbm: event.target.value})}
				/>
				</FormControl>
			  </div>
			  <br></br>
			  <div>
				{/* <input onChange={this.onFileChange} type="file"></input>*/}
				 <input type="file" name="fileCollection" onChange={this.onFileChange} multiple/> 
				 <button  class="Upload" onClick={this.uploadFileData}>Upload</button> 

				{/* <input onChange={this.onFileChange} type="file"></input>
				<button disabled={!this.state.file} onClick={this.uploadFileData}>Upload</button> */}

				
				{this.state.isFileUploaded && this.showDropDown()} 
				 {this.showButtons()}
				 </div>
				 {/* {this.state.name==='Plan_Rx_Details' && <PlanRxDetails rxDetails= { this.state.rxDetails}/>} */}
			</div>
		)
	}

}

export default UploadFile;

const NavLink = styled.div`
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

