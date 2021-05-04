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


class ProductDetails extends PureComponent {
  state = {
    name: ' ',
  };
  state= {showProductId: false}
  state= {showProductDropDown: false}
  

  handleChange = event => {
    this.setState({ name: event.target.value });
  };

  showProductId = () => {
    console.log(this.state.showProductId);
    return (
      <div> 
          <form noValidate autoComplete="off">
            <TextField required id="standard-required" label="Required" />
           </form>
       </div>
      );
    }
      showProductDropDown= () => {
        console.log(this.state.showProductDropDown)
        return (
          <div> 
             <form onSubmit={this.handleSubmit}>
            <label>
                <select value={this.state.value} onChange={this.handleChange}>
                    <option value="1">Atorvastatin</option>
                     <option value="2">Simvastatin</option>
                     <option value="3">Proair</option>
                </select>
            </label>
            <input type="submit" value="Submit" />
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
              {/* <div style={{ backgroundColor: "#00aaff"}}> */}
                <Paper elevation={16}>
               
                   <h1 style={{padding: "10px 20px", textAlign: "center", color: "#6496ec"}}>Product Details</h1>
            <div className={classes.container}>
              <FormControl className={classes.formControl}>
              {/* <RadioGroup name="member_uid" value={value} onChange={handleChange}> */}
              <RadioGroup name="Product_id" >
                 <FormControlLabel value="1" control={<Radio />} label="Enter a product id" onChange={() => this.setState({showProductId: true, showProductDropDown: false})} />
                 {this.state.showProductId ? this.showProductId() : null}
                 <FormControlLabel value="2" control={<Radio />} label="Choose from a list of products" onChange={() => this.setState({showProductDropDown: true, showProductId: false}) }/>
                 {this.state.showProductDropDown ? this.showProductDropDown() : null}  
             </RadioGroup>
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
  
ProductDetails.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ProductDetails);
