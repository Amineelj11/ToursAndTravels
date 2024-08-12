  import { Link } from "react-router-dom";
  import dollor from "../images/dollor_logo.png";
  import timing from "../images/timing_logo.png";
  import experience from "../images/experience_logo.png";

  const TourCard = (tour) => {
    const descriptionToShow = (description, maxLength) => {
      if (description.length <= maxLength) {
        return description;
      } else {
        const truncatedText = description.substring(0, maxLength);
        return truncatedText + "...";
      }
    };

    const formatDateFromEpoch = (epochTime) => {
      const date = new Date(Number(epochTime));
      const formattedDate = date.toLocaleString(); // Adjust the format as needed

      return formattedDate;
    };

    return (
      <div className="col">
        <Link
          to={`/tour/${tour.item.id}/detail`}
          className="card job-card rounded-card h-100 shadow-lg"
          style={{ textDecoration: "none" }}
        >
          <div className="row g-0">
            {/* Left side - Company Logo */}
            <div className="col-md-4 d-flex align-items-center justify-content-center">
              <img
                src={"http://localhost:8085/api/tour/" + tour.item.image1}
                className="card-img-top rounded img-fluid"
                alt="event image"
                style={{
                  height: "250px",
                  width: "auto",
                }}
              />
            </div>
            {/* Right side - Job Details */}
            <div className="col-md-8">
              <div className="card-body text-color">
                <h3 className="card-title d-flex justify-content-between text-color-second">
                  <div>
                    <b>{tour.item.name}</b>
                  </div>
                </h3>
                <p className="card-text text-dark">
                  {descriptionToShow(tour.item.description, 50)}
                </p>

                <div className="d-flex justify-content-between text-color-second">
                <b>
                  <span className="text-dark">Program Type: </span>
                  <span className="text-color">
                  {tour.item.typeProg.name}
                  </span>
                  </b>
                  </div>
                <div className="d-flex justify-content-between text-color-second mt-3">
                  
                  <b>
                    <span className="text-dark">From: </span>
                    <span className="text-color">
                      {tour.item.fromLocation.name}
                    </span>
                  </b>
                  <b>
                    <span className="text-dark">To: </span>
                    <span className="text-color">
                      {tour.item.toLocation.name}
                    </span>
                  </b>
                </div>
                <div className="d-flex justify-content-left text-color-second mt-3">
                  <b>
                    <span className="text-dark">Tour Time: </span>
                    <span className="text-color">
                      {formatDateFromEpoch(tour.item.startDate)}
                    </span>
                  </b>
                </div>
                <div className="d-flex justify-content-between text-color-second mt-3">
                  <b>
                    <span className="text-dark">Available Ticket: </span>
                    <span className="text-color">
                      {tour.item.availableTickets}
                    </span>
                  </b>
                  <b>
                    <span className="text-dark">Ticket Price: </span>
                    <span className="text-color">
                      TND {tour.item.ticketPrice}
                    </span>
                  </b>
                </div>
              </div>
            </div>
          </div>
        </Link>
      </div>
    );
  };

  export default TourCard;
