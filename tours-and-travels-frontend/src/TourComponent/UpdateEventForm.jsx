import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const UpdateEventForm = () => {
  const [categories, setCategories] = useState([]);

  const location = useLocation();

  const admin = JSON.parse(sessionStorage.getItem("active-admin"));
  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  let navigate = useNavigate();

  const retrieveAllCategories = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/event/category/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllCategories = async () => {
      const resCategory = await retrieveAllCategories();
      if (resCategory) {
        setCategories(resCategory.categories);
      }
    };

    getAllCategories();
  }, []);

  const [selectedImage1, setSelectImage1] = useState(null);

  const [event, setEvent] = useState(location.state);

  const handleInput = (e) => {
    setEvent({ ...event, [e.target.name]: e.target.value });
  };

  const saveEvent = (e) => {
    e.preventDefault();
    if (event === null) {
      alert("invalid input!!!");

      return;
    }

    if (event.categoryId === "" || event.categoryId === "0") {
      alert("Select Category!!!");

      return;
    }

    if (event.venueType === "" || event.venueType === null) {
      alert("Select Venue Type!!!");

      return;
    }

    const formData = new FormData();
    formData.append("id", event.id);
    formData.append("name", event.name);
    formData.append("description", event.description);
    formData.append("venueName", event.venueName);
    formData.append("venueType", event.venueType);
    formData.append("location", event.location);
    formData.append("image", selectedImage1);
    formData.append("noOfTickets", event.noOfTickets);
    formData.append("startDate", convertToEpochTime(event.startDate));
    formData.append("endDate", convertToEpochTime(event.endDate));
    formData.append("ticketPrice", event.ticketPrice);
    formData.append("categoryId", event.categoryId);
    formData.append("availableTickets", event.availableTickets);

    axios
      .put("http://localhost:8085/api/event/update", formData, {
        headers: {
          // Authorization: "Bearer " + admin_jwtToken, // Replace with your actual JWT token
        },
      })
      .then((resp) => {
        let response = resp.data;

        if (response.success) {
          alert(response.responseMessage);

          setTimeout(() => {
            navigate("/admin/event/all");
          }, 2000); // Redirect after 3 seconds
        } else if (!response.success) {
          alert(response.responseMessage);
          // setTimeout(() => {
          //   window.location.reload(true);
          // }, 2000); // Redirect after 3 seconds
        } else {
          alert(response.responseMessage);
          // setTimeout(() => {
          //   window.location.reload(true);
          // }, 2000); // Redirect after 3 seconds
        }
      })
      .catch((error) => {
        console.error(error);
        alert("It seems server is down!!!");
        navigate("/admin/event/all");
        // setTimeout(() => {
        //   window.location.reload(true);
        // }, 2000); // Redirect after 3 seconds
      });
  };

  const convertToEpochTime = (dateString) => {
    const selectedDate = new Date(dateString);
    const epochTime = selectedDate.getTime();
    return epochTime;
  };

  return (
    <div>
      <div class="mt-2 d-flex aligns-items-center justify-content-center mb-4">
        <div class="card form-card shadow-lg" style={{ width: "60rem" }}>
          <div className="container-fluid">
            <div
              className="card-header bg-color custom-bg-text mt-2 text-center"
              style={{
                borderRadius: "1em",
                height: "45px",
              }}
            >
              <h5 class="card-title">Update Event</h5>
            </div>
            <div class="card-body text-color">
              <form className="row g-3">
                <div className="col-md-6 mb-3">
                  <label htmlFor="title" className="form-label">
                    <b>Event Title</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="name"
                    name="name"
                    onChange={handleInput}
                    value={event.name}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label">
                    <b>Event Description</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    name="description"
                    onChange={handleInput}
                    value={event.description}
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label">
                    <b>Event Category</b>
                  </label>

                  <select
                    name="categoryId"
                    onChange={handleInput}
                    className="form-control"
                  >
                    <option value="">Select Event Category</option>

                    {categories.map((category) => {
                      return (
                        <option value={category.id}> {category.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div className="col-md-6 mb-3">
                  <label htmlFor="title" className="form-label">
                    <b>Event Venue Name</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="venueName"
                    name="venueName"
                    onChange={handleInput}
                    value={event.venueName}
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label className="form-label">
                    <b>Event Venue Type</b>
                  </label>

                  <select
                    name="venueType"
                    onChange={handleInput}
                    className="form-control"
                  >
                    <option value="">Select Venue Type</option>
                    <option value="Conference Centers">
                      Conference Centers
                    </option>
                    <option value="Hotels">Hotels</option>
                    <option value="Banquet Halls">Banquet Halls</option>
                    <option value="Restaurants">Restaurants</option>
                    <option value="Ballrooms">Ballrooms</option>
                    <option value="Outdoor Venues">Outdoor Venues</option>
                    <option value="Stadiums/Arenas">Stadiums/Arenas</option>
                    <option value="Convention Centers">
                      Convention Centers
                    </option>
                    <option value="Theaters">Theaters</option>
                    <option value="Auditoriums">Auditoriums</option>
                    <option value="Museums/Galleries">Museums/Galleries</option>
                    <option value="Country Clubs">Country Clubs</option>
                    <option value="Historic Buildings/Venues">
                      Historic Buildings/Venues
                    </option>
                    <option value="Wineries/Vineyards">
                      Wineries/Vineyards
                    </option>
                    <option value="Resorts">Resorts</option>
                    <option value="Yachts/Boats">Yachts/Boats</option>
                    <option value="Farms/Ranches">Farms/Ranches</option>
                    <option value="Warehouses/Lofts">Warehouses/Lofts</option>
                    <option value="Churches/Temples/Mosques">
                      Churches/Temples/Mosques
                    </option>
                    <option value="Universities/Colleges">
                      Universities/Colleges
                    </option>
                  </select>
                </div>
                <div className="col-md-6 mb-3">
                  <label htmlFor="street" className="form-label">
                    <b>Location</b>
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="location"
                    name="location"
                    onChange={handleInput}
                    value={event.location}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label htmlFor="pincode" className="form-label">
                    <b>No. Of Tickets</b>
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    id="noOfTickets"
                    name="noOfTickets"
                    onChange={handleInput}
                    value={event.noOfTickets}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label htmlFor="pincode" className="form-label">
                    <b>Available Tickets</b>
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    id="availableTickets"
                    name="availableTickets"
                    onChange={handleInput}
                    value={event.availableTickets}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label htmlFor="ticketPrice" className="form-label">
                    <b>Ticket Price</b>
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    id="ticketPrice"
                    name="ticketPrice"
                    onChange={handleInput}
                    value={event.ticketPrice}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label htmlFor="ticketPrice" className="form-label">
                    <b>Event Start Time</b>
                  </label>
                  <input
                    type="datetime-local"
                    className="form-control"
                    id="startDate"
                    name="startDate"
                    onChange={handleInput}
                    value={event.startDate}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label htmlFor="ticketPrice" className="form-label">
                    <b>Event End Time</b>
                  </label>
                  <input
                    type="datetime-local"
                    className="form-control"
                    id="endDate"
                    name="endDate"
                    onChange={handleInput}
                    value={event.endDate}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label for="formFile" class="form-label">
                    <b> Select Event Image</b>
                  </label>
                  <input
                    class="form-control"
                    type="file"
                    id="formFile"
                    name="image"
                    onChange={(e) => setSelectImage1(e.target.files[0])}
                    required
                  />
                </div>
                <div className="d-flex aligns-items-center justify-content-center mb-2">
                  <button
                    type="submit"
                    class="btn bg-color custom-bg-text"
                    onClick={saveEvent}
                  >
                    Update Event
                  </button>
                  <ToastContainer />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateEventForm;
