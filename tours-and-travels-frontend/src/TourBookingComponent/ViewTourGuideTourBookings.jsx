import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const ViewTourGuideTourBookings = () => {
  const [allTourBookings, setAllTourBookings] = useState([
    {
      customer: {
        firstName: "",
        lastName: "",
      },
      tour: {
        fromLocation: {
          name: "",
        },
        toLocation: {
          name: "",
        },

        guide: {
          firstName: "",
          lastName: "",
        },
      },
    },
  ]);
  const guide = JSON.parse(sessionStorage.getItem("active-guide"));

  const guide_jwtToken = sessionStorage.getItem("guide-jwtToken");

  let navigate = useNavigate();

  useEffect(() => {
    const getAllTourBookings = async () => {
      const allToursBookings = await retrieveAllTourBookings();
      if (allToursBookings) {
        setAllTourBookings(allToursBookings.bookings);
      }
    };

    getAllTourBookings();
  }, []);

  const retrieveAllTourBookings = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/tour/booking/fetch/guide-wise?tourGuideId=" +
        guide.id
    );
    console.log(response.data);
    return response.data;
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 shadow-lg"
        style={{
          height: "45rem",
        }}
      >
        <div
          className="card-header custom-bg-text text-center bg-color"
          style={{
            borderRadius: "1em",
            height: "50px",
          }}
        >
          <h2>My Tour Bookings</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive">
            <table className="table table-hover text-color text-center">
              <thead className="table-bordered border-color bg-color custom-bg-text">
                <tr>
                  <th scope="col">Tour</th>
                  <th scope="col">Name</th>
                  <th scope="col">Customer Name</th>
                  <th scope="col">Tour Date</th>
                  <th scope="col">From Location</th>
                  <th scope="col">To Location</th>
                  <th scope="col">Price (per ticket)</th>
                  <th scope="col">Total Tickets</th>
                  <th scope="col">Total Ticket Price</th>
                  <th scope="col">Booking Time</th>
                  <th scope="col">Booking Id</th>
                  <th scope="col">Status</th>
                </tr>
              </thead>
              <tbody>
                {allTourBookings.map((booking) => {
                  return (
                    <tr>
                      <td>
                        <img
                          src={
                            "http://localhost:8085/api/tour/" +
                            booking.tour.image1
                          }
                          class="img-fluid"
                          alt="event_pic"
                          style={{
                            maxHeight: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{booking.tour.name}</b>
                      </td>
                      <td>
                        <b>
                          {booking.customer.firstName +
                            " " +
                            booking.customer.lastName}
                        </b>
                      </td>
                      <td>
                        <b>
                          {formatDateFromEpoch(booking.tour.startDate) +
                            " - " +
                            formatDateFromEpoch(booking.tour.endDate)}
                        </b>
                      </td>
                      <td>
                        <b>{booking.tour.fromLocation.name}</b>
                      </td>
                      <td>
                        <b>{booking.tour.toLocation.name}</b>
                      </td>
                      <td>
                        <b>{booking.tour.ticketPrice}</b>
                      </td>
                      <td>
                        <b>{booking.noOfTickets}</b>
                      </td>
                      <td>
                        <b>{booking.noOfTickets * booking.tour.ticketPrice}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(booking.bookingTime)}</b>
                      </td>
                      <td>
                        <b>{booking.bookingId}</b>
                      </td>
                      <td>
                        <b>{booking.status}</b>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewTourGuideTourBookings;
