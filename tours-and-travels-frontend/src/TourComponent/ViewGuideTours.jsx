import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const ViewGuideTours = () => {
  const [tours, setTours] = useState([]);

  const guide = JSON.parse(sessionStorage.getItem("active-guide"));
  const guide_jwtToken = sessionStorage.getItem("guide-jwtToken");

  let navigate = useNavigate();

  useEffect(() => {
    const getAllTour = async () => {
      const allTour = await retrieveAllTour();
      if (allTour) {
        setTours(allTour.tours);
      }
    };

    getAllTour();
  }, []);

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const retrieveAllTour = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/tour/fetch/guide-wise?tourGuideId=" + guide.id
    );
    console.log(response.data);
    return response.data;
  };

  const deleteTour = (tourId, e) => {
    fetch("http://localhost:8085/api/tour/delete?tourId=" + tourId, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        //   Authorization: "Bearer " + admin_jwtToken,
      },
    })
      .then((result) => {
        result.json().then((res) => {
          if (res.success) {
            toast.success(res.responseMessage, {
              position: "top-center",
              autoClose: 2000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });

            setTimeout(() => {
              window.location.reload(true);
            }, 3000); // Redirect after 3 seconds
          } else if (!res.success) {
            toast.error(res.responseMessage, {
              position: "top-center",
              autoClose: 1000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            setTimeout(() => {
              window.location.reload(true);
            }, 1000); // Redirect after 3 seconds
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
        setTimeout(() => {
          window.location.reload(true);
        }, 1000); // Redirect after 3 seconds
      });
  };

  const viewTour = (tourId) => {
    navigate(`/tour/${tourId}/detail`);
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
          <h2>My Tours</h2>
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
                  <th scope="col">Description</th>
                  <th scope="col">Tour Date</th>
                  <th scope="col">From Location</th>
                  <th scope="col">To Location</th>
                  <th scope="col">Total Ticket</th>
                  <th scope="col">Ticket Price</th>
                  <th scope="col">Status</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {tours.map((tour) => {
                  return (
                    <tr>
                      <td>
                        <img
                          src={"http://localhost:8085/api/tour/" + tour.image1}
                          class="img-fluid"
                          alt="event_pic"
                          style={{
                            maxHeight: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{tour.name}</b>
                      </td>
                      <td>
                        <b>{tour.description}</b>
                      </td>
                      <td>
                        <b>
                          {formatDateFromEpoch(tour.startDate) +
                            " - " +
                            formatDateFromEpoch(tour.endDate)}
                        </b>
                      </td>
                      <td>
                        <b>{tour.fromLocation.name}</b>
                      </td>
                      <td>
                        <b>{tour.toLocation.name}</b>
                      </td>
                      <td>
                        <b>{tour.totalTickets}</b>
                      </td>
                      <td>
                        <b>&#8377;{tour.ticketPrice}</b>
                      </td>
                      <td>
                        <b>{tour.status}</b>
                      </td>
                      <td>
                        <button
                          onClick={() => viewTour(tour.id)}
                          className="btn btn-sm bg-color custom-bg-text ms-2"
                        >
                          View
                        </button>

                        {(() => {
                          if (tour.status !== "Deactivated") {
                            return (
                              <button
                                onClick={() => deleteTour(tour.id)}
                                className="btn btn-sm bg-color custom-bg-text ms-2"
                              >
                                Delete
                              </button>
                            );
                          }
                        })()}
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

export default ViewGuideTours;
