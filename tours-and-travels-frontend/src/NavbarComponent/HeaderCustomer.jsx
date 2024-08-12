import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const HeaderCustomer = () => {
  let navigate = useNavigate();

  const employee = JSON.parse(sessionStorage.getItem("active-employee"));

  const userLogout = () => {
    toast.success("logged out!!!", {
      position: "top-center",
      autoClose: 1000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
    sessionStorage.removeItem("active-customer");
    sessionStorage.removeItem("customer-jwtToken");
    window.location.reload(true);
    setTimeout(() => {
      navigate("/home");
    }, 2000); // Redirect after 3 seconds
  };

  const navigateToChangePasswordPage = () => {
    navigate("/customer/changePassword");
  };

  return (
    <ul class="navbar-nav ms-auto mb-2 mb-lg-0 me-5">
      <li class="nav-item">
        <Link
          to="/customer/tour/booking/all"
          class="nav-link active"
          aria-current="page"
        >
          <b className="text-color">Event Bookings</b>
        </Link>
      </li>

      <li class="nav-item">
        <Link
          to=""
          class="nav-link active"
          aria-current="page"
          onClick={userLogout}
        >
          <b className="text-color">Logout</b>
        </Link>
        <ToastContainer />
      </li>
    </ul>
  );
};

export default HeaderCustomer;
