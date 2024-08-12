import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import Carousel from "./Carousel";
import Footer from "../NavbarComponent/Footer";
import { useNavigate } from "react-router-dom";
import TourCard from "../TourComponent/TourCard";
import Pagination from "./Pagination";

const HomePage = () => {
  const navigate = useNavigate();
  const [locations, setLocations] = useState([]);
  const [eventName, setEventName] = useState("");
  const [eventFromLocationId, setEventFromLocationId] = useState("");
  const [eventToLocationId, setEventToLocationId] = useState("");
  const [tempEventName, setTempEventName] = useState("");
  const [tempEventFromLocationId, setTempEventFromLocationId] = useState("");
  const [tempEventToLocationId, setTempEventToLocationId] = useState("");
  const [tours, setTours] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [toursPerPage] = useState(5);

  const retrieveAllLocations = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/location/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllEvents = async () => {
      const allEvents = await retrieveAllEvents();
      if (allEvents) {
        setTours(allEvents.tours);
      }
    };

    const getSearchedEvents = async () => {
      const allEvents = await searchEvents();
      if (allEvents) {
        setTours(allEvents.tours);
      }
    };

    const getAllLocations = async () => {
      const resLocation = await retrieveAllLocations();
      if (resLocation) {
        setLocations(resLocation.locations);
      }
    };

    if (
      eventFromLocationId !== "" ||
      eventToLocationId !== "" ||
      eventName !== ""
    ) {
      getSearchedEvents();
    } else {
      getAllEvents();
    }

    getAllLocations();
  }, [eventFromLocationId, eventToLocationId, eventName]);

  const retrieveAllEvents = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/tour/fetch/all/active"
    );
    return response.data;
  };

  const searchEvents = async () => {
    if (eventName !== "") {
      const response = await axios.get(
        "http://localhost:8085/api/tour/fetch/name-wise?tourName=" + eventName
      );
      return response.data;
    } else if (
      eventFromLocationId !== "" ||
      eventFromLocationId !== "0" ||
      eventToLocationId !== "" ||
      eventToLocationId !== "0"
    ) {
      const response = await axios.get(
        "http://localhost:8085/api/tour/fetch/location-wise?fromLocationId=" +
          eventFromLocationId +
          "&toLocationId=" +
          eventToLocationId
      );
      return response.data;
    }
  };

  const searchEventByName = (e) => {
    e.preventDefault();
    setEventName(tempEventName);
    setTempEventName("");
    setEventFromLocationId("");
    setEventToLocationId("");
  };

  const searchEventByCategory = (e) => {
    e.preventDefault();
    setEventFromLocationId(tempEventFromLocationId);
    setEventToLocationId(tempEventToLocationId);
    setTempEventFromLocationId("");
    setTempEventToLocationId("");
    setEventName("");
  };

  // Pagination logic
  const indexOfLastTour = currentPage * toursPerPage;
  const indexOfFirstTour = indexOfLastTour - toursPerPage;
  const currentTours = tours.slice(indexOfFirstTour, indexOfLastTour);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="container-fluid mb-2">
      <Carousel />
      <h5 className="text-color-second text-center mt-3">
        Search Tours here..!!
      </h5>
      <div className="d-flex aligns-items-center justify-content-center">
        <div className="row">
          <div className="col-auto">
            <div className="mt-3">
              <form className="row g-3">
                <div className="col-auto">
                  <input
                    type="text"
                    className="form-control"
                    id="city"
                    name="eventName"
                    onChange={(e) => setTempEventName(e.target.value)}
                    value={tempEventName}
                    placeholder="Search Tour here..."
                  />
                </div>
                <div className="col-auto">
                  <button
                    type="submit"
                    className="btn bg-color custom-bg-text mb-3"
                    onClick={searchEventByName}
                  >
                    Search
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div className="col">
            <div className="mt-3">
              <form className="row g-3">
                <div className="col-auto">
                  <select
                    name="tempEventFromLocationId"
                    onChange={(e) => setTempEventFromLocationId(e.target.value)}
                    className="form-control"
                    required
                  >
                    <option value="">From Tour Location</option>
                    {locations.map((location) => {
                      return (
                        <option value={location.id} key={location.id}>
                          {location.name}
                        </option>
                      );
                    })}
                  </select>
                </div>
                <div className="col-auto">
                  <select
                    name="tempEventToLocationId"
                    onChange={(e) => setTempEventToLocationId(e.target.value)}
                    className="form-control"
                    required
                  >
                    <option value="">To Tour Location</option>
                    {locations.map((location) => {
                      return (
                        <option value={location.id} key={location.id}>
                          {location.name}
                        </option>
                      );
                    })}
                  </select>
                </div>
                <div className="col-auto">
                  <button
                    type="submit"
                    className="btn bg-color custom-bg-text mb-3"
                    onClick={searchEventByCategory}
                  >
                    Search
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div className="col-md-12 mt-3 mb-5">
        <div className="row row-cols-1 row-cols-md-2 g-4">
          {currentTours.map((tour) => {
            return <TourCard item={tour} key={tour.id} />;
          })}
        </div>
      </div>
      <Pagination
        toursPerPage={toursPerPage}
        totalTours={tours.length}
        paginate={paginate}
        currentPage={currentPage}
      />
      <hr />
      <Footer />
    </div>
  );
};

export default HomePage;
