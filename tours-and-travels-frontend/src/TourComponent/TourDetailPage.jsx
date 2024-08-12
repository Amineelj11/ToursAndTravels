import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import TourCarousel from "./TourCarousel";
import { Modal, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './TourDetailPage.css';  // Import the custom CSS file

const TourDetailPage = () => {
  const { tourId } = useParams();

  const customer = JSON.parse(sessionStorage.getItem("active-customer"));
  const customer_jwtToken = sessionStorage.getItem("customer-jwtToken");

  const guide = JSON.parse(sessionStorage.getItem("active-guide"));
  const guide_jwtToken = sessionStorage.getItem("guide-jwtToken");

  const navigate = useNavigate();

  const [tour, setTour] = useState({
    id: "",
    name: "",
    description: "",
    totalDaysOfTour: "",
    guide: {
      id: "",
      firstName: "",
      lastName: "",
      emailId: "",
      phoneNo: "",
      role: "",
      address: {
        id: "",
        street: "",
        city: "",
        pincode: "",
      },
      status: "",
    },
    fromLocation: {
      id: "",
      name: "",
      description: "",
      status: "",
    },
    toLocation: {
      id: "",
      name: "",
      description: "",
      status: "",
    },
    activities: [
      {
        id: "",
        name: "",
        description: "",
      },
    ],
    meals: [
      {
        id: "",
        name: "",
        description: "",
      },
    ],
    transport: {
      id: "",
      name: "",
      description: "",
      status: "",
    },
    typeProg: {
      id: "",
      name: "",
      description: "",
      status: "",
    },
    vehicleRegistrationNo: "",
    transportDescription: "",
    lodging: {
      id: "",
      type: "",
      description: "",
      status: "",
    },
    lodgingName: "",
    lodgingAddress: "",
    totalTickets: "",
    availableTickets: "",
    ticketPrice: "",
    addedDate: "",
    startDate: "",
    endDate: "",
    specialNote: "",
    status: "",
  });

  useEffect(() => {
    const getTour = async () => {
      const fetchTourResponse = await retrieveTour();
      if (fetchTourResponse) {
        setTour(fetchTourResponse.tours[0]);
      }
    };
    getTour();
  }, []);

  const retrieveTour = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/tour/fetch?tourId=" + tourId
    );
    console.log(response.data);
    return response.data;
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const [showModal, setShowModal] = useState(false);

  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);
  const handleLoginRedirect = () => navigate("/user/login");

  const bookTourPage = (e) => {
    e.preventDefault();
    if (customer === null) {
      handleShow();
    } else {
      navigate("/tour/booking/page", { state: tour });
    }
  };

  const updateTourActivity = () => {
    navigate("/tour-guide/tour/activity/update", { state: tour });
  };

  const updateTourMeal = () => {
    navigate("/tour-guide/tour/meal/update", { state: tour });
  };

  const updateTourImages = () => {
    navigate("/tour-guide/tour/images/update", { state: tour });
  };

  const updateTourDetails = () => {
    navigate("/tour-guide/tour/update/detail", { state: tour });
  };

  return (
    <div className="tour-detail-page mb-3">
      <div className="col ml-5 mt-3 ms-5 me-5">
        {/* Company and Employer Details Card */}
        <div className="card rounded-card h-100 shadow-lg fancy-card">
          <h2 className="card-title text-center text-primary-gradient">
            Tour Detail
          </h2>

          <div className="row g-0">
            {/* Left side - Company Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <div className="row g-0">
                  {/* Left side - Company Logo */}
                  <div className="col-md-4 d-flex align-items-center justify-content-center">
                    <TourCarousel
                      item={{
                        image1: tour.image1,
                        image2: tour.image2,
                        image3: tour.image3,
                      }}
                    />
                  </div>
                  {/* Right side - Job Details */}
                  <div className="col-md-8">
                    <div className="card-body text-color">
                      <h3 className="card-title d-flex justify-content-between text-secondary-gradient">
                        <div>
                          <b>{tour.name}</b>
                        </div>
                      </h3>
                      <p className="card-text">{tour.description}</p>
                      <div className="col-md-6">
                        <p className="mb-2">
                          <b>Type Program:</b>
                          <span className="text-primary"> {tour.typeProg.name}</span>
                        </p>
                      </div>
                      <br />
                      <b className="card-text">
                        <div className="col-md-4 d-flex justify-content-between">
                          <div>
                            <span>From:</span>
                            <span className="text-primary ms-2">
                              {tour.fromLocation.name + " "}
                            </span>
                          </div>
                          <div className="ms-5">
                            <span> To:</span>
                            <span className="text-primary ms-2">
                              {tour.toLocation.name + " "}
                            </span>
                          </div>
                        </div>
                      </b>
                      <br />
                      <b>
                        <span>Tour Guide:</span>
                        <span className="text-primary ms-2">
                          {tour.guide.firstName + " " + tour.guide.lastName}
                        </span>
                      </b>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            {/* Right side - Employer Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <div className="row mt-5">
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Tour Start:</b>
                      <span className="text-primary">
                        {" "}
                        {formatDateFromEpoch(tour.startDate)}
                      </span>
                    </p>
                  </div>
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Tour End:</b>
                      <span className="text-primary">
                        {" "}
                        {formatDateFromEpoch(tour.endDate)}
                      </span>
                    </p>
                  </div>
                </div>

                <div className="row mt-3">
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Total Tour Day:</b>
                      <span className="text-primary">
                        {" "}
                        {tour.totalDaysOfTour}
                      </span>
                    </p>
                  </div>
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Added Date:</b>
                      <span className="text-primary">
                        {" "}
                        {formatDateFromEpoch(tour.addedDate)}
                      </span>
                    </p>
                  </div>
                </div>

                <div className="row mt-3">
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Transport:</b>
                      <span className="text-primary"> {tour.transport.name}</span>
                    </p>
                  </div>
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Vehicle Registration No:</b>
                      <span className="text-primary">
                        {" "}
                        {tour.vehicleRegistrationNo}
                      </span>
                    </p>
                  </div>
                </div>

                <div className="row mt-3">
                  <div className="col-md-12">
                    <p className="mb-2">
                      <b>Transport Description:</b>
                      <span className="text-primary">
                        {" "}
                        {tour.transportDescription}
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div className="row g-0">
            {/* Left side - Company Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <div className="text-left mb-2">
                  <h4 className="text-primary-gradient">Activities</h4>
                </div>
                {tour.activities && tour.activities.length > 0 ? (
                  tour.activities.map((activity, index) => (
                    <div className="row" key={index}>
                      <div className="col-md-8 mb-2">
                        <b className="me-2">Activity:</b>
                        <span className="text-primary"> {activity.name}</span>
                      </div>
                      <div className="col-md-4">
                        <span>{activity.description}</span>
                      </div>
                    </div>
                  ))
                ) : (
                  <div>No activities available</div>
                )}
              </div>
            </div>

            {/* Right side - Employer Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <div className="text-left mb-2">
                  <h4 className="text-primary-gradient">Meals</h4>
                </div>
                {tour.meals && tour.meals.length > 0 ? (
                  tour.meals.map((meal, index) => (
                    <div className="row" key={index}>
                      <div className="col-md-8 mb-2">
                        <b className="me-2">Meal:</b>
                        <span className="text-primary"> {meal.name}</span>
                      </div>
                      <div className="col-md-4">
                        <span>{meal.description}</span>
                      </div>
                    </div>
                  ))
                ) : (
                  <div>No meals available</div>
                )}
              </div>
            </div>
          </div>

          <div className="row g-0">
            {/* Left side - Company Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <div className="text-left mb-2">
                  <h4 className="text-primary-gradient">Lodging</h4>
                </div>
                <div className="row">
                  <div className="col-md-6 mb-2">
                    <b className="me-2">Lodging Name:</b>
                    <span className="text-primary"> {tour.lodgingName}</span>
                  </div>
                  <div className="col-md-6">
                    <b className="me-2">Type:</b>
                    <span className="text-primary"> {tour.lodging.type}</span>
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-12">
                    <b className="me-2">Lodging Address:</b>
                    <span className="text-primary"> {tour.lodgingAddress}</span>
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-12">
                    <b className="me-2">Lodging Description:</b>
                    <span className="text-primary">
                      {" "}
                      {tour.lodging.description}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            {/* Right side - Employer Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <div className="row mt-2">
                  <div className="col-md-6">
                    <b className="me-2">Ticket Price:</b>
                    <span className="text-primary">
                      {" "}
                      {tour.ticketPrice} DT
                    </span>
                  </div>
                  <div className="col-md-6">
                    <b className="me-2">Total Tickets:</b>
                    <span className="text-primary">
                      {" "}
                      {tour.totalTickets}
                    </span>
                  </div>
                </div>
                <div className="row mt-3">
                  <div className="col-md-6">
                    <b className="me-2">Available Tickets:</b>
                    <span className="text-primary">
                      {" "}
                      {tour.availableTickets}
                    </span>
                  </div>
                  <div className="col-md-6">
                    <b className="me-2">Special Note:</b>
                    <span className="text-primary"> {tour.specialNote}</span>
                  </div>
                </div>

                <div className="row mt-5">
                  <div className="col-md-12 d-flex justify-content-center">
                    {guide !== null ? (
                      <div className="mt-2">
                        <button
                          type="button"
                          className="btn btn-warning rounded-pill me-3"
                          onClick={updateTourActivity}
                        >
                          Update Activity
                        </button>
                        <button
                          type="button"
                          className="btn btn-warning rounded-pill me-3"
                          onClick={updateTourMeal}
                        >
                          Update Meal
                        </button>
                        <button
                          type="button"
                          className="btn btn-warning rounded-pill me-3"
                          onClick={updateTourImages}
                        >
                          Update Images
                        </button>
                        <button
                          type="button"
                          className="btn btn-warning rounded-pill me-3"
                          onClick={updateTourDetails}
                        >
                          Update Details
                        </button>
                      </div>
                    ) : (
                      <button
                        type="button"
                        className="btn btn-primary rounded-pill mt-3"
                        onClick={bookTourPage}
                      >
                        Book Now
                      </button>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
          <ToastContainer />
        </div>
      </div>

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Login Required</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Please login to book a tour. Do you want to go to the login page?
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleLoginRedirect}>
            Login
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default TourDetailPage;
