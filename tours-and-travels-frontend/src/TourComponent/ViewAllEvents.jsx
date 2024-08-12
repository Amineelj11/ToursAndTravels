import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const ViewAllEvents = () => {
  const [allEvents, setAllEvents] = useState([]);
  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  let navigate = useNavigate();

  useEffect(() => {
    const getAllEvent = async () => {
      const allEvents = await retrieveAllEvent();
      if (allEvents) {
        setAllEvents(allEvents.events);
      }
    };

    getAllEvent();
  }, []);

  const retrieveAllEvent = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/event/fetch/all?status=Active"
    );
    console.log(response.data);
    return response.data;
  };

  const deleteEvent = (eventId) => {
    fetch("http://localhost:8085/api/event/delete?eventId=" + eventId, {
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

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const updateEvent = (event) => {
    navigate("/admin/event/update", { state: event });
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
          <h2>All Events</h2>
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
                  <th scope="col">Event</th>
                  <th scope="col">Event Name</th>
                  {/* <th scope="col">Description</th> */}
                  <th scope="col">Category</th>
                  <th scope="col">Venue Type</th>
                  <th scope="col">Venue Name</th>
                  <th scope="col">Location</th>
                  <th scope="col">Total Tickets</th>
                  <th scope="col">Ticket Price</th>
                  <th scope="col">Event Time</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {allEvents.map((event) => {
                  return (
                    <tr>
                      <td>
                        <img
                          src={"http://localhost:8085/api/event/" + event.image}
                          class="img-fluid"
                          alt="event_pic"
                          style={{
                            maxWidth: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{event.name}</b>
                      </td>
                      {/* <td>
                        <b>{event.description}</b>
                      </td> */}
                      <td>
                        <b>{event.category.name}</b>
                      </td>
                      <td>
                        <b>{event.venueType}</b>
                      </td>
                      <td>
                        <b>{event.venueName}</b>
                      </td>
                      <td>
                        <b>{event.location}</b>
                      </td>
                      <td>
                        <b>{event.noOfTickets}</b>
                      </td>
                      <td>
                        <b>{event.ticketPrice}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(event.startDate)}</b>
                      </td>
                      <td>
                        <button
                          onClick={() => updateEvent(event)}
                          className="btn btn-sm bg-color custom-bg-text ms-2"
                        >
                          Update
                        </button>
                        <ToastContainer />

                        <button
                          onClick={() => deleteEvent(event.id)}
                          className="btn btn-sm bg-color custom-bg-text ms-2"
                        >
                          Delete
                        </button>
                        <ToastContainer />
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

export default ViewAllEvents;
