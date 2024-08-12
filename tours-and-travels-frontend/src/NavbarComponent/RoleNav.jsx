import AdminHeader from "./AdminHeader";
import HeaderCustomer from "./HeaderCustomer";
import NormalHeader from "./NormalHeader";
import TourGuideHeader from "./TourGuideHeader";

const RoleNav = () => {
  const customer = JSON.parse(sessionStorage.getItem("active-customer"));
  const admin = JSON.parse(sessionStorage.getItem("active-admin"));
  const guide = JSON.parse(sessionStorage.getItem("active-guide"));

  if (customer != null) {
    return <HeaderCustomer />;
  } else if (admin != null) {
    return <AdminHeader />;
  } else if (guide != null) {
    return <TourGuideHeader />;
  } else {
    return <NormalHeader />;
  }
};

export default RoleNav;
