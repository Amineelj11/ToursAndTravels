import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import { useLocation } from "react-router-dom";
import creditcard from "../images/credit-card.png";

const EventBookingPage = () => {
  const customer = JSON.parse(sessionStorage.getItem("active-customer"));
  const customer_jwtToken = sessionStorage.getItem("customer-jwtToken");

  const navigate = useNavigate();
  const location = useLocation();
  const [event, setEvent] = useState(location.state);

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const [paymentRequest, setPaymentRequest] = useState({
    eventId: event.id,
    customerId: customer.id,
    cardNo: "",
    nameOnCard: "",
    noOfTickets: "",
    cvv: "",
    expiryDate: "",
  });

  const handleUserInput = (e) => {
    setPaymentRequest({
      ...paymentRequest,
      [e.target.name]: e.target.value,
    });
  };

  const payAndConfirmBooking = (e) => {
    fetch("http://localhost:8085/api/booking/add", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(paymentRequest),
    })
      .then((result) => {
        result.json().then((res) => {
          if (res.success) {
            toast.success(res.responseMessage, {
              position: "top-center",
              autoClose: 3000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });

            setTimeout(() => {
              navigate("/home");
            }, 2000); // Redirect after 3 seconds
          } else {
            toast.error(res.responseMessage, {
              position: "top-center",
              autoClose: 1000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
          }
        });
      })
      .catch((error) => {
        console.error(error);
        toast.error("It seems server is down", {
          position: "top-center",
          autoClose: 1000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      });
    e.preventDefault();
  };

  return (
    <div className="mb-3">
      <div className="col ml-5 mt-3 ms-5 me-5">
        {/* Company and Employer Details Card */}
        <div className="card rounded-card h-100 shadow-lg ">
          <div className="row g-0">
            {/* Left side - Company Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <h4 className="card-title text-color-second">Event Details</h4>
                <div className="row g-0">
                  {/* Left side - Company Logo */}
                  <div className="col-md-4 d-flex align-items-center justify-content-center">
                    <img
                      src={"http://localhost:8085/api/event/" + event.image}
                      className="card-img-top rounded img-fluid"
                      alt="event image"
                      style={{
                        maxHeight: "100px",
                        width: "auto",
                      }}
                    />
                  </div>
                  {/* Right side - Job Details */}
                  <div className="col-md-8">
                    <div className="card-body text-color">
                      <h3 className="card-title d-flex justify-content-between text-color-second">
                        <div>
                          <b>{event.name}</b>
                        </div>
                      </h3>
                      <b className="card-text">{event.description}</b>
                      <br />
                      <b className="card-text">{event.location}</b>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            {/* Right side - Employer Details Card */}
            <div className="col-md-6">
              <div className="card-body">
                <h4 className="card-title text-color-second">Venue Details</h4>
                {/* Include the necessary details for the employer */}
                {/* Display First Name and Last Name in a row */}
                <div className="row mt-4">
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Venue Name:</b> {event.venueName}
                    </p>
                  </div>
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Venue Type:</b> {event.venueType}
                    </p>
                  </div>
                </div>
                <div className="row mt-2">
                  <div className="col-md-6">
                    <p className="mb-2">
                      <b>Location:</b> {event.location}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Booking Payment Page */}
        <div className="card rounded-card h-100 shadow-lg mt-4">
          <div className="text-color-second text-center">
            <h4> Book an Event here!!!</h4>

            <div className="row mt-4">
              <div class="col-sm-1 mt-2"></div>
              <div class="col-sm-4 mt-2">
                <img
                  src={creditcard}
                  className="card-img-top rounded img-fluid"
                  alt="img"
                  style={{
                    maxWidth: "300px",
                  }}
                />
              </div>
              <div class="col-sm-4 mt-2">
                <form className="row g-3" onSubmit={payAndConfirmBooking}>
                  <div className="text-color">
                    <input
                      type="number"
                      className="form-control"
                      id="noOfTickets"
                      name="noOfTickets"
                      onChange={handleUserInput}
                      value={paymentRequest.noOfTickets}
                      placeholder="No. of Tickets..."
                      required
                    />
                  </div>
                  <div className="text-color">
                    <input
                      type="text"
                      className="form-control"
                      id="nameOnCard"
                      name="nameOnCard"
                      onChange={handleUserInput}
                      value={paymentRequest.nameOnCard}
                      placeholder="Name on Card..."
                      required
                    />
                  </div>
                  <div className="mb-3 text-color">
                    <input
                      type="number"
                      className="form-control"
                      id="cardNo"
                      name="cardNo"
                      onChange={handleUserInput}
                      value={paymentRequest.cardNo}
                      placeholder="Enter Card Number here..."
                      required
                    />
                  </div>

                  <div className="col text-color">
                    <input
                      type="text"
                      className="form-control"
                      id="expiryDate"
                      name="expiryDate"
                      onChange={handleUserInput}
                      value={paymentRequest.expiryDate}
                      placeholder="Valid Through"
                      required
                    />
                  </div>

                  <div className="col text-color">
                    <input
                      type="number"
                      className="form-control"
                      id="cvv"
                      name="cvv"
                      onChange={handleUserInput}
                      value={paymentRequest.cvv}
                      placeholder="CVV"
                      required
                    />
                  </div>

                  <input
                    type="submit"
                    className="btn bg-color custom-bg-text ms-2 "
                    value="Book Event"
                  />
                  <ToastContainer />
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EventBookingPage;
