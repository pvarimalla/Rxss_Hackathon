import React from 'react';
import './upload.css';
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


class UploadFile extends React.Component {
	
	constructor(props) {
		super(props);
		this.state = {file: '', msg: '', isFileUploaded: false, planDetails: [], rxDetails: [],value:'',name: null,showDropDown: false,fileCollection:'',showGroupId:'',pd:[]};
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
		// let data = new FormData();
		// data.append('file', this.state.file);
		// console.log(data);
	
		 var formData = new FormData();
  
		// files.map((file, index) => {
		//   formData.append('file', this.state.file)
		// });
		for (const key of Object.keys(this.state.fileCollection)) {
            formData.append('files', this.state.fileCollection[key])
		}
		
		 axios.post("http://localhost:8080/api/pdf-parse", formData, {
        }).then((res) => res.data
        ).then(res => {
			console.log(res)
			
			const pd=[res[0].planDetails];
			 pd.push(res[1].planDetails);
			 console.log(pd);
			 const prd=[];
			 for(var i=0;i<res[1].rxDetails.length;i++){
				prd.push(res[0].rxDetails[i]);
			 }
			 for(var i=0;i<res[1].rxDetails.length;i++){
				prd.push(res[1].rxDetails[i]);
			 }
			 console.log(prd);
		   this.setState({msg: "File successfully uploaded", isFileUploaded: true, planDetails: pd ,rxDetails: prd  });
		//    this.setState({msg: "File successfully uploaded", isFileUploaded: true, planDetails: res[1].planDetails,rxDetails: res[0].rxDetails,  });
		//    this.setState({ planDetails.push(res[1].planDetails), rxDetails.push(res[1].rxDetails)});
	   }).catch(err => {
		   this.setState({error: err});
	   });

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
		
	}
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
		// console.log(this.state.showDropDown)
		
   return(
	   <div>
	<button onClick={()=>this.setState({name:'Plan_Details'})}>Plan details</button>
	<button onClick={()=>this.setState({name:'Plan_Rx_Details'})}>Plan Rx details</button>
	</div>
   );
	}
	
	showButtons=()=>{
		if(this.state.name===null){
			return null;
		}
		if(this.state.name==='Plan_Details'){
			// return console.log("1");
			return  <Plan_Details details={this.state.planDetails}/>
		}
		//return console.log("2");
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
		render() {
		return (
			<div id="container">
				
				<h4>{this.state.msg}</h4>
				<div>
				<FormControl >
              <RadioGroup name="Enter details" >
				  <h4>Enter a group ID</h4>
				 {this.showGroupId()}
                <h4>Enter a PBM ID</h4>
				{this.showGroupId()}
             </RadioGroup>
			 <input type="submit" value="Submit" />
              </FormControl>
			  </div>
			  <br></br>
			  <div>
				{/* <input onChange={this.onFileChange} type="file"></input>*/}
				 <input type="file" name="fileCollection" onChange={this.onFileChange} multiple/> 
				 <button  onClick={this.uploadFileData}>Upload</button> 

				{/* <input onChange={this.onFileChange} type="file"></input>
				<button disabled={!this.state.file} onClick={this.uploadFileData}>Upload</button> */}

				
				{this.state.isFileUploaded ? this.showDropDown() : null} 
				 {this.showButtons()}
				 </div>
				 {/* {this.state.name==='Plan_Rx_Details' && <PlanRxDetails rxDetails= { this.state.rxDetails}/>} */}
			</div>
			





		)
	}

}

export default UploadFile;