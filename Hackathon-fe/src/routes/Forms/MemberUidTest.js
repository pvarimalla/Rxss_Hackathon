import React, { Fragment, PureComponent,useState } from 'react';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { withStyles } from '@material-ui/core/styles';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import TextField from '@material-ui/core/TextField';
import FormControl from '@material-ui/core/FormControl';
import CardHeader from '@material-ui/core/CardHeader';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import FormHelperText from '@material-ui/core/FormHelperText';
import {
    Paper,
    MenuItem,
    Radio,
    RadioGroup,
    FormLabel,
    FormControlLabel,
  } from '@material-ui/core';

const styles = theme => ({
  demo: {
    // height: 'auto',
    margin: theme.spacing(1),
    width: theme.spacing(32),
    height: theme.spacing(16)
  },
  divider: {
    margin: `${theme.spacing.unit * 4}px 0`,
  },
  input: {
    margin: theme.spacing.unit * 4,
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  formControl: {
    margin: theme.spacing.unit *4,
  },
  root: theme.mixins.gutters({
    paddingTop: 16,
    paddingBottom: 16,
    marginTop: theme.spacing(3),
}),
});


class MemberUidTest extends PureComponent {
  state = {
    name: ' ',
  };
  state= {showMemberUid: false}
  

  handleChange = event => {
    this.setState({ name: event.target.value });
  };

  showMemberUid = () => {
    return (
      <div> 
          <form noValidate autoComplete="off">
            <TextField required id="standard-required" label="Required" />
           </form>
       </div>
      );
  }
  render() {
    const { classes } = this.props;
    return (
      <Fragment>
        <Grid
          container
          alignItems="center"
          justify="center"
          direction="row"
          spacing={26}
        >
          <Grid
            item
            md={4}
            className={classes.demo}
          >
                <Paper elevation={16}>
                   <h1 style={{padding: "10px 20px", textAlign: "Center", color: "#6496ec"}}>Member_uid</h1>
            {/* <div className={classes.container}> */}
              <FormControl className={classes.formControl}>
              <RadioGroup name="member_uid" >
                 <FormControlLabel value="1" control={<Radio />} label="Enter a member_uid" onClick={() => this.setState({showMemberUid: true}) }/>
                 {this.state.showMemberUid ? this.showMemberUid() : null}
                 <FormControlLabel value="2" control={<Radio />} label="Choose a demo member" onClick={() => this.setState({showMemberUid: false}) }/>
             </RadioGroup>
              </FormControl>
              {/* </div> */}
              </Paper>
         {/* </div> */}
          </Grid>
          <Grid
            item
            md={8}
            className={classes.demo}
          >
            
          </Grid>
        </Grid>
      </Fragment>
    );
  }
}
  
MemberUidTest.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(MemberUidTest);



// function MemberUidTest(){
//   const [showMemberUid, setshowMemberUid] = useState(false)
//   console.log(showMemberUid) 
//   function updateSetShowMemberUid()
//   {
//     console.log(showMemberUid)
//     showMemberUid ? setshowMemberUid(false) : setshowMemberUid(true);
//   }
//  const showMemberUidText = () => {
//     return (
//       <div> 
//           <form noValidate autoComplete="off">
//             <TextField required id="standard-required" label="Required" />
//            </form>
//        </div>
//       );
//     }
//     // const { classes } = this.props;
//   return (
//     <Fragment>
//       <Grid
//         container
//         alignItems="center"
//         justify="center"
//         direction="row"
//         spacing={26}
//       >
//         <Grid
//           item
//           md={8}
//           //  className={classes.demo}
//         >
//               <Paper elevation={16}>
//                  <h1 style={{padding: "10px 20px", textAlign: "left", color: "#6496ec"}}>Member_uid</h1>
//           <div >
//             {/* className={classes.container}> */}
//             <FormControl component="fieldset">
//               {/* className={classes.formControl}> */}
//             <RadioGroup name="member_uid" >
//                <FormControlLabel value="1" control={<Radio />} label="Enter a member_uid" onClick={updateSetShowMemberUid}/>
//                <FormControlLabel value="2" control={<Radio />} label="Choose a demo member" onClick={updateSetShowMemberUid}/>
//                {showMemberUid ? showMemberUidText() : null}
//            </RadioGroup>
//             </FormControl>
//             </div>
//             </Paper>
//        {/* </div> */}
//         </Grid>
//         <Grid
//           item
//           md={8}
//           // className={classes.demo}
//         >
          
//         </Grid>
//       </Grid>
//     </Fragment>
//   );
// }
// export default withStyles(styles)(MemberUidTest);