import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const TourGuideHeader = () => {
  let navigate = useNavigate();

  const user = JSON.parse(sessionStorage.getItem("active-guide"));
  console.log(user);

  const guideLogout = () => {
    toast.success("logged out!!!", {
      position: "top-center",
      autoClose: 1000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
    sessionStorage.removeItem("active-guide");
    sessionStorage.removeItem("guide-jwtToken");
    window.location.reload(true);
    setTimeout(() => {
      navigate("/home");
    }, 2000); // Redirect after 3 seconds
  };
  return (
    <ul class="navbar-nav ms-auto mb-2 mb-lg-0 me-5">
      <li class="nav-item dropdown">
        <a
          class="nav-link dropdown-toggle text-color"
          href="#"
          id="navbarDropdown"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <b> My Tours</b>
        </a>
        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
          <li>
            <Link
              to="/tour-guide/tour/add"
              class="nav-link active"
              aria-current="page"
            >
              <b className="text-color"> Add Tour</b>
            </Link>
          </li>
          <li>
            <Link
              to="/tour-guide/tours/view"
              class="nav-link active"
              aria-current="page"
            >
              <b className="text-color">View Tours</b>
            </Link>
          </li>
        </ul>
      </li>

      <li class="nav-item">
        <Link
          to="/tour-guide/tour/booking/view"
          class="nav-link active"
          aria-current="page"
        >
          <b className="text-color">My Tour Bookings</b>
        </Link>
      </li>
      <li class="nav-item">
        <Link
          to=""
          class="nav-link active"
          aria-current="page"
          onClick={guideLogout}
        >
          <b className="text-color">Logout</b>
        </Link>
        <ToastContainer />
      </li>
    </ul>
  );
};

export default TourGuideHeader;
