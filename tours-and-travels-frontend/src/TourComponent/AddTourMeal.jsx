import { useState, useEffect } from "react";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import { useParams } from "react-router-dom";

const AddTourMeal = () => {
  const guide = JSON.parse(sessionStorage.getItem("active-guide"));
  const guide_jwtToken = sessionStorage.getItem("guide-jwtToken");

  const location = useLocation();
  const [tour, setTour] = useState(location.state);
  const [meals, setMeals] = useState(tour && tour.meals ? tour.meals : []);

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  let navigate = useNavigate();

  const saveTourMeal = (e) => {
    let data = { tourId: tour.id, name, description };

    fetch("http://localhost:8085/api/tour/meal/add", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
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
            setMeals(res.meals);
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
    e.preventDefault();
  };

  const deleteMeal = (mealId) => {
    fetch("http://localhost:8085/api/tour/meal/delete?mealId=" + mealId, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      //   body: JSON.stringify(data),
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
            setMeals(res.meals);
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

  return (
    <div className="mb-5">
      <div className="mt-2 d-flex aligns-items-center justify-content-center">
        <div
          className="card rounded-card h-100 shadow-lg"
          style={{ width: "45rem" }}
        >
          <div className="card-body text-color">
            <h3 className="card-title text-center">Add Meal</h3>
            <form className="mt-3">
              <div class="mb-3">
                <label for="title" class="form-label">
                  <b>Meal Name</b>
                </label>
                <input
                  type="text"
                  class="form-control"
                  id="title"
                  placeholder="enter name.."
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                  value={name}
                />
              </div>
              <div class="mb-3">
                <label for="description" class="form-label">
                  <b>Meal Description</b>
                </label>
                <textarea
                  class="form-control"
                  id="description"
                  rows="3"
                  placeholder="enter description.."
                  onChange={(e) => {
                    setDescription(e.target.value);
                  }}
                  value={description}
                />
              </div>

              <div className="d-flex aligns-items-center justify-content-center mb-2">
                <button
                  type="submit"
                  onClick={saveTourMeal}
                  class="btn bg-color custom-bg-text"
                >
                  Add Meal
                </button>
                <ToastContainer />
              </div>
            </form>

            <div className="mt-5 text-center">
              <h3>Tour Meals</h3>
            </div>

            <div className="table-responsive mt-2">
              <table className="table table-hover custom-bg-text text-center">
                <thead className="bg-color table-bordered border-color">
                  <tr>
                    <th scope="col">Meal Name</th>
                    <th scope="col">Meal Description</th>
                    <th scope="col">Action</th>
                  </tr>
                </thead>
                <tbody className="text-color">
                  {meals.map((meal) => {
                    return (
                      <tr>
                        <td>
                          <b>{meal.name}</b>
                        </td>
                        <td>
                          <b>{meal.description}</b>
                        </td>

                        <td>
                          <button
                            className="btn bg-danger text-white custom-bg btn-sm"
                            onClick={() => deleteMeal(meal.id)}
                          >
                            Delete
                          </button>
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
    </div>
  );
};

export default AddTourMeal;
