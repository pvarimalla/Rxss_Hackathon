import React from 'react';
import './upload.css';
import Plan_Details from './planDetails';
import PlanRxDetails from './planRxDetails';
import { FormControlLabel} from '@material-ui/core';

class UploadFile extends React.Component {

	constructor(props) {
		super(props);
		this.state = {file: '', msg: '', isFileUploaded: false, planDetails: [], rxDetails: [],value:'',name: null,showDropDown: false,fileCollection:''};
		// let data = new FormData();
	}
	//state= {showDropDown: false}
	onFileChange = (event) => {
// 		const files = event.target.files;
// 		for (let i = 0; i < files.length; i++) {
//     	formData.append(`file[${i}]`, files[i])
// }
		this.setState({
			// fileCollection: event.target.files
			file: event.target.files[0]
		});
	}

	uploadFileData = (event) => {
		event.preventDefault();
		this.setState({msg: ''});
		let data = new FormData();
		data.append('file', this.state.file);
		console.log(data);
		console.log(data);

		// var formData = new FormData();

		// files.map((file, index) => {
		//   formData.append('file', this.state.file)
		// });
	/*	for (const key of Object.keys(this.state.fileCollection)) {
			console.log(key);
			console.log(this.state.fileCollection[key])
            formData.append('fileCollection', this.state.fileCollection[key])
		}*/
		// console.log("before fetch "+formData.values(0));
		/* for(var pair of formData.entries()) {
		// 	console.log(pair[0]+ ', '+ pair[1]);
		 }*/
		fetch('http://localhost:8080/api/pdf-parse', {
			mode: 'no-cors',
			enctype: "multipart/form-data",
			method: 'POST',
			body: data
		})  .then(resp => resp.json())
        .then(resp => {
			// console.log(resp.json());
			this.setState({msg: "File successfully uploaded", isFileUploaded: true, planDetails: resp.planDetails, rxDetails: resp.rxDetails});
			console.log(resp.json())
		  console.log(resp.planDetails)
		  console.log(resp.rxDetails)
		}).catch(err => {
			this.setState({error: err});
		});

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


		render() {
		return (
			<div id="container">
				<h3>Upload a File</h3>
				<h4>{this.state.msg}</h4>
				{/* <input onChange={this.onFileChange} type="file"></input>
				{/* <input type="file" name="fileCollection" onChange={this.onFileChange} multiple></input> */}
				{/* <button  onClick={this.uploadFileData}>Upload</button> */}

				<input onChange={this.onFileChange} type="file"></input>
				<button disabled={!this.state.file} onClick={this.uploadFileData}>Upload</button>

				{/* <button disabled={!this.state.file} onClick={this.uploadFileData, this.fetchFileData}>Upload</button> */}
				{this.state.isFileUploaded ? this.showDropDown() : null}
				 {this.showButtons()}
				 {/* {this.state.name==='Plan_Rx_Details' && <PlanRxDetails rxDetails= { this.state.rxDetails}/>} */}
			</div>






		)
	}

}

export default UploadFile;
