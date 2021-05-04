
 import MemberUidTest from './MemberUidTest'
import ProductDetails from './ProductDetails'
import Home from './Home'
import { withStyles } from '@material-ui/core/styles';

function Alerts() {
 return (
   <div className="Alerts">
     
       <MemberUidTest/>
       {/* <MemberUid/> */}
       <ProductDetails/>
       
   </div>
 );
}

export default Alerts;