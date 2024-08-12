import { useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { useNavigate, useLocation } from "react-router-dom";

const UpdateTourForm = () => {
  const location = useLocation();
  const [tourDetail, setTourDetail] = useState(location.state);

  const [tour, setTour] = useState({
    id: tourDetail.id,
    name: tourDetail.name,
    description: tourDetail.description,
    totalDaysOfTour: tourDetail.totalDaysOfTour,
    guideId: tourDetail.guide.id,
    fromLocationId: tourDetail.fromLocation.id,
    toLocationId: tourDetail.toLocation.id,
    transportId: tourDetail.transport.id,
    vehicleRegistrationNo: tourDetail.vehicleRegistrationNo,
    transportDescription: tourDetail.transportDescription,
    lodgingId: tourDetail.lodging.id,
    lodgingName: tourDetail.lodgingName,
    lodgingAddress: tourDetail.lodgingAddress,
    totalTickets: tourDetail.totalTickets,
    ticketPrice: tourDetail.ticketPrice,
    startDate: tourDetail.startDate,
    endDate: tourDetail.endDate,
    specialNote: tourDetail.specialNote,
  });

  const guide = JSON.parse(sessionStorage.getItem("active-guide"));
  const guide_jwtToken = sessionStorage.getItem("guide-jwtToken");

  const [locations, setLocations] = useState([]);
  const [lodges, setLodges] = useState([]);
  const [transports, setTransports] = useState([]);

  let navigate = useNavigate();

  const retrieveAllTransports = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/transport/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllTransports = async () => {
      const resTransport = await retrieveAllTransports();
      if (resTransport) {
        setTransports(resTransport.transports);
      }
    };

    getAllTransports();
  }, []);

  const retrieveAllLocations = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/location/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllLocations = async () => {
      const resLocation = await retrieveAllLocations();
      if (resLocation) {
        setLocations(resLocation.locations);
      }
    };

    getAllLocations();
  }, []);

  const retrieveAllLodge = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/lodge/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllLodges = async () => {
      const resLodge = await retrieveAllLodge();
      if (resLodge) {
        setLodges(resLodge.lodges);
      }
    };

    getAllLodges();
  }, []);

  const handleInput = (e) => {
    setTour({ ...tour, [e.target.name]: e.target.value });
  };

  const isNumeric = (value) => {
    return /^\d+$/.test(value);
  };

  const saveTour = (e) => {
    e.preventDefault();
    if (tour === null) {
      toast.error("invalid input!!!", {
        position: "top-center",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });

      return;
    }

    tour.startDate = isNumeric(tour.startDate)
      ? tour.startDate
      : convertToEpochTime(tour.startDate);
    tour.endDate = isNumeric(tour.endDate)
      ? tour.endDate
      : convertToEpochTime(tour.endDate);

    fetch("http://localhost:8085/api/tour/update/detail", {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        //     Authorization: "Bearer " + admin_jwtToken,
      },
      body: JSON.stringify(tour),
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
              navigate(`/tour/${tour.id}/detail`);
            }, 2000); // Redirect after 3 seconds

            // setTimeout(() => {
            //   navigate("/home");
            // }, 2000); // Redirect after 3 seconds
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
            // setTimeout(() => {
            //   window.location.reload(true);
            // }, 2000); // Redirect after 3 seconds
          } else {
            toast.error("It Seems Server is down!!!", {
              position: "top-center",
              autoClose: 1000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            // setTimeout(() => {
            //   window.location.reload(true);
            // }, 2000); // Redirect after 3 seconds
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
        // setTimeout(() => {
        //   window.location.reload(true);
        // }, 1000); // Redirect after 3 seconds
      });
  };

  const convertToEpochTime = (dateString) => {
    const selectedDate = new Date(dateString);
    const epochTime = selectedDate.getTime();
    return epochTime;
  };

  return (
    <div>
      <div class="mt-2 d-flex aligns-items-center justify-content-center mb-4 ms-3 me-3">
        <div class="card form-card shadow-lg">
          <div className="container-fluid">
            <div
              className="card-header bg-color custom-bg-text mt-2 text-center"
              style={{
                borderRadius: "1em",
                height: "45px",
              }}
            >
              <h5 class="card-title">Update Tour Detail</h5>
            </div>
            <div class="card-body text-color">
              <form className="row g-3">
                <div className="col-md-3 mb-3">
                  <label htmlFor="title" className="form-label">
                    <b>Tour Name</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="name"
                    name="name"
                    onChange={handleInput}
                    value={tour.name}
                  />
                </div>
                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Tour Description</b>
                  </label>
                  <textarea
                    class="form-control"
                    id="description"
                    name="description"
                    rows="2"
                    placeholder="enter description.."
                    onChange={handleInput}
                    value={tour.description}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Tour From Location</b>
                  </label>

                  <select
                    name="fromLocationId"
                    onChange={handleInput}
                    className="form-control"
                  >
                    <option value="">Select From Location</option>

                    {locations.map((location) => {
                      return (
                        <option value={location.id}> {location.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Tour To Location</b>
                  </label>

                  <select
                    name="toLocationId"
                    onChange={handleInput}
                    className="form-control"
                  >
                    <option value="">Select To Location</option>

                    {locations.map((location) => {
                      return (
                        <option value={location.id}> {location.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Lodge</b>
                  </label>

                  <select
                    name="lodgingId"
                    onChange={handleInput}
                    className="form-control"
                  >
                    <option value="">Select Lodge</option>

                    {lodges.map((lodge) => {
                      return <option value={lodge.id}> {lodge.type} </option>;
                    })}
                  </select>
                </div>

                <div className="col-md-3 mb-3">
                  <label htmlFor="title" className="form-label">
                    <b>Lodge Name</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="lodgingName"
                    name="lodgingName"
                    onChange={handleInput}
                    value={tour.lodgingName}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Lodge Address</b>
                  </label>
                  <textarea
                    class="form-control"
                    id="lodgingAddress"
                    name="lodgingAddress"
                    rows="2"
                    placeholder="enter lodgingAddress.."
                    onChange={handleInput}
                    value={tour.lodgingAddress}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Transport</b>
                  </label>

                  <select
                    name="transportId"
                    onChange={handleInput}
                    className="form-control"
                  >
                    <option value="">Select Transport</option>

                    {transports.map((transport) => {
                      return (
                        <option value={transport.id}> {transport.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Transport Description</b>
                  </label>
                  <textarea
                    class="form-control"
                    id="transportDescription"
                    name="transportDescription"
                    rows="2"
                    placeholder="enter transport Description.."
                    onChange={handleInput}
                    value={tour.transportDescription}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label htmlFor="title" className="form-label">
                    <b>Tranport Registration No</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="vehicleRegistrationNo"
                    name="vehicleRegistrationNo"
                    onChange={handleInput}
                    value={tour.vehicleRegistrationNo}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label htmlFor="title" className="form-label">
                    <b>Total Tour Days</b>
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    id="totalDaysOfTour"
                    name="totalDaysOfTour"
                    onChange={handleInput}
                    value={tour.totalDaysOfTour}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label htmlFor="pincode" className="form-label">
                    <b>No. Of Tickets</b>
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    id="totalTickets"
                    name="totalTickets"
                    onChange={handleInput}
                    value={tour.totalTickets}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label htmlFor="pincode" className="form-label">
                    <b>Ticket Price</b>
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    id="ticketPrice"
                    name="ticketPrice"
                    onChange={handleInput}
                    value={tour.ticketPrice}
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label htmlFor="ticketPrice" className="form-label">
                    <b>Tour Start Time</b>
                  </label>
                  <input
                    type="datetime-local"
                    className="form-control"
                    id="startDate"
                    name="startDate"
                    onChange={handleInput}
                    value={tour.startDate}
                  />
                </div>
                <div className="col-md-3 mb-3">
                  <label htmlFor="ticketPrice" className="form-label">
                    <b>Tour End Time</b>
                  </label>
                  <input
                    type="datetime-local"
                    className="form-control"
                    id="endDate"
                    name="endDate"
                    onChange={handleInput}
                    value={tour.endDate}
                    required
                  />
                </div>

                <div className="col-md-3 mb-3">
                  <label className="form-label">
                    <b>Special Note</b>
                  </label>
                  <textarea
                    class="form-control"
                    id="specialNote"
                    name="specialNote"
                    rows="2"
                    placeholder="enter special note if any.."
                    onChange={handleInput}
                    value={tour.specialNote}
                  />
                </div>
                <div className="d-flex aligns-items-center justify-content-center mb-2">
                  <button
                    type="submit"
                    class="btn bg-color custom-bg-text"
                    onClick={saveTour}
                  >
                    Update Detail
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateTourForm;
