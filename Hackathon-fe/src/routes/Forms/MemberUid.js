import React, { Fragment, PureComponent } from 'react';
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
    height: 'auto',
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


class MemberUid extends PureComponent {
  // state = {
  //   showTextBox: false
  // };
  //  handleOnChange = e => {
  //   this.setState({
  //     showTextBox: e.target.value === 1
  //   });
  // };
  constructor() {
    super();

    this.state = {
      showTextBox: false
    };

    this.onRadioChange = this.onRadioChange.bind(this);
  }
    onRadioChange = (e) => {
      this.setState({
        showTextBox: e.target.value
      });
    }
    // this.onSubmit = this.onSubmit.bind(this);

  

  // handleChange = event => {
  //   this.setState({ name: event.target.value });
  // };

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
            md={8}
            className={classes.demo}
          >
              {/* <div style={{ backgroundColor: "#00aaff"}}> */}
                <Paper elevation={16}>
                   <h1 style={{padding: "10px 20px", textAlign: "left", color: "#6496ec"}}>Member_uid</h1>
            <div className={classes.container}>
              <FormControl className={classes.formControl}>
              {/* <RadioGroup name="member_uid" value={value} onChange={handleChange}> */}
              {/* <RadioGroup name="member_uid" >
                 <FormControlLabel value="1" control={<Radio />} label="Enter a member_uid" onClick={() => this.setState({showMemberUid: true}) }/>
                 <FormControlLabel value="2" control={<Radio />} label="Choose a demo member" onClick={() => this.setState({showMemberUid: false}) }/>
                 {this.state.showMemberUid ? this.showMemberUid() : null}
             </RadioGroup> */}
             
              <label>
                <input
                  type="radio"
                  value="1"
                  checked={this.state.showTextBox === '1'}
                  onChange={this.onRadioChange}
                />
                <span>Enter a member_uid</span>
              </label>
            
            
              <label>
                <input
                  type="radio"
                  value="2"
                  checked={this.state.showTextBox === '2'}
                  onChange={this.onRadioChange}
                />
                <span>Choose a demo member</span>
              </label>
            
            {this.state.showTextBox ? this.showMemberUid() : null}
              </FormControl>
              </div>
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
  
MemberUid.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(MemberUid);
