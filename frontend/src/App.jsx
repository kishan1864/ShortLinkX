import {BrowserRouter,Navigate,Route,Routes,useLocation} from "react-router-dom";
import AnimatedBackground from "./components/common/AnimatedBackground";
import AppLayout from "./components/layout/AppLayout";
import ProtectedRoute from "./components/common/ProtectedRoute";
import Login from "./pages/Login"; import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard"; import CreateUrl from "./pages/CreateUrl";
import UrlHistory from "./pages/UrlHistory"; import Analytics from "./pages/Analytics";
import QrCodes from "./pages/QrCodes"; import Profile from "./pages/Profile";
import "./App.css";
function AnimatedRoutes(){
 const location=useLocation();
 return <Routes location={location} key={location.pathname}>
  <Route path="/" element={<Navigate to="/dashboard" replace/>}/>
  <Route path="/login" element={<Login/>}/><Route path="/register" element={<Register/>}/>
  <Route element={<ProtectedRoute/>}><Route element={<AppLayout/>}>
   <Route path="/dashboard" element={<Dashboard/>}/><Route path="/create" element={<CreateUrl/>}/>
   <Route path="/history" element={<UrlHistory/>}/><Route path="/analytics" element={<Analytics/>}/>
   <Route path="/qr-codes" element={<QrCodes/>}/><Route path="/profile" element={<Profile/>}/>
  </Route></Route>
  <Route path="*" element={<Navigate to="/dashboard" replace/>}/>
 </Routes>;
}
export default function App(){return <BrowserRouter><AnimatedBackground/><AnimatedRoutes/></BrowserRouter>}