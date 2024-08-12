import { Routes, Route } from "react-router-dom";
import Header from "./NavbarComponent/Header";
import AdminRegisterForm from "./UserComponent/AdminRegisterForm";
import UserLoginForm from "./UserComponent/UserLoginForm";
import UserRegister from "./UserComponent/UserRegister";
import HomePage from "./PageComponent/HomePage";
import ViewAllCustomers from "./UserComponent/ViewAllCustomers";
import AddLocationForm from "./LocationComponent/AddLocationForm";
import ViewAllLocations from "./LocationComponent/ViewAllLocations";
import UpdateLocationForm from "./LocationComponent/UpdateLocationForm";
import ViewAllTourGuides from "./UserComponent/ViewAllTourGuides";
import AddTransportForm from "./TransportComponent/AddTransportForm";
import ViewAllTransports from "./TransportComponent/ViewAllTransports";
import UpdateTransportForm from "./TransportComponent/UpdateTransportForm";
import AddLodgeForm from "./LodgingComponent/AddLodgeForm";
import ViewAllLodges from "./LodgingComponent/ViewAllLodges";
import UpdateLodgeForm from "./LodgingComponent/UpdateLodgeForm";
import AddTourForm from "./TourComponent/AddTourForm";
import TourDetailPage from "./TourComponent/TourDetailPage";
import AddTourActivity from "./TourComponent/AddTourActivity";
import AddTourMeal from "./TourComponent/AddTourMeal";
import UpdateTourImage from "./TourComponent/UpdateTourImage";
import UpdateTourForm from "./TourComponent/UpdateTourForm";
import ViewGuideTours from "./TourComponent/ViewGuideTours";
import TourBookingPage from "./TourComponent/TourBookingPage";
import ViewCustomerTourBookings from "./TourBookingComponent/ViewCustomerTourBookings";
import ViewTourGuideTourBookings from "./TourBookingComponent/ViewTourGuideTourBookings";
import ViewAllTourBookings from "./TourBookingComponent/ViewAllTourBookings";
import AddTypeForm from "./TypeProgComponent/AddTypeForm";
import ViewAllTypes from "./TypeProgComponent/ViewAllTypes";
import WebSocketComponent from "./WebSocketComponent/WebSocketComponent";
import AdminLoginForm from "./UserComponent/AdminLoginForm";
function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/WebS" element={<WebSocketComponent />} />

        <Route path="/user/admin/register" element={<AdminRegisterForm />} />
        <Route path="/user/login" element={<UserLoginForm />} />
        <Route path="/admin/login" element={<AdminLoginForm />} />

        <Route path="/user/customer/register" element={<UserRegister />} />
        <Route path="/user/tour-guide/register" element={<UserRegister />} />
        <Route path="/admin/location/add" element={<AddLocationForm />} />
        <Route path="/admin/location/all" element={<ViewAllLocations />} />
        <Route path="/admin/location/update" element={<UpdateLocationForm />} />
        <Route path="/admin/transport/add" element={<AddTransportForm />} />
        <Route path="/admin/transport/all" element={<ViewAllTransports />} />
        <Route 
          path="/admin/transport/update"
          element={<UpdateTransportForm />}
        />
        <Route path="/admin/type/add" element={<AddTypeForm />} />
        <Route path="/admin/type/all" element={<ViewAllTypes />} />
        <Route path="/admin/type/update" element={<UpdateTourForm />}/>

        <Route path="/admin/customer/all" element={<ViewAllCustomers />} />
        <Route path="/admin/tour-guide/all" element={<ViewAllTourGuides />} />
        <Route path="/admin/lodge/add" element={<AddLodgeForm />} />
        <Route path="/admin/lodge/all" element={<ViewAllLodges />} />
        <Route path="/admin/lodge/update" element={<UpdateLodgeForm />} />
        <Route path="/tour-guide/tour/add" element={<AddTourForm />} />
        <Route path="/tour/:tourId/detail" element={<TourDetailPage />} />
        <Route
          path="/tour-guide/tour/activity/update"
          element={<AddTourActivity />}
        />
        <Route path="/tour-guide/tour/meal/update" element={<AddTourMeal />} />

        <Route
          path="/tour-guide/tour/images/update"
          element={<UpdateTourImage />}
        />
        <Route
          path="/tour-guide/tour/update/detail"
          element={<UpdateTourForm />}
        />
        <Route path="/tour-guide/tours/view" element={<ViewGuideTours />} />
        <Route path="/tour/booking/page" element={<TourBookingPage />} />
        <Route
          path="/customer/tour/booking/all"
          element={<ViewCustomerTourBookings />}
        />
        <Route
          path="/tour-guide/tour/booking/view"
          element={<ViewTourGuideTourBookings />}
        />
        <Route
          path="/admin/tour/booking/all"
          element={<ViewAllTourBookings />}
        />
      </Routes>
    </div>
  );
}

export default App;
