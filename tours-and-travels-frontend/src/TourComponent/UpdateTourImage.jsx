import { useState, useEffect } from "react";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import { useParams } from "react-router-dom";
import TourCarousel from "./TourCarousel";

const UpdateTourImage = () => {
  const location = useLocation();
  const [tour, setTour] = useState(location.state);

  let navigate = useNavigate();

  const guide = JSON.parse(sessionStorage.getItem("active-guide"));
  const guide_jwtToken = sessionStorage.getItem("guide-jwtToken");

  const [selectedImage1, setSelectImage1] = useState(null);
  const [selectedImage2, setSelectImage2] = useState(null);
  const [selectedImage3, setSelectImage3] = useState(null);

  const updateTourImage = (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("id", tour.id);
    formData.append("image1", selectedImage1);
    formData.append("image2", selectedImage2);
    formData.append("image3", selectedImage3);

    axios
      .put("http://localhost:8085/api/tour/update/images", formData, {
        headers: {
          //       Authorization: "Bearer " + guide_jwtToken, // Replace with your actual JWT token
        },
      })
      .then((resp) => {
        let response = resp.data;

        if (response.success) {
          toast.success(response.responseMessage, {
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
        } else if (!response.success) {
          toast.error(response.responseMessage, {
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
        // }, 2000); // Redirect after 3 seconds
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
            <h3 className="card-title text-center">Update Tour Images</h3>

            <div className="d-flex align-items-center justify-content-center mt-4">
              <TourCarousel
                item={{
                  image1: tour.image1,
                  image2: tour.image2,
                  image3: tour.image3,
                }}
              />
            </div>

            <form className="mt-3">
              <div className="mb-3">
                <label for="formFile" class="form-label">
                  <b> Select Tour Image 1</b>
                </label>
                <input
                  class="form-control"
                  type="file"
                  id="formFile"
                  name="image1"
                  onChange={(e) => setSelectImage1(e.target.files[0])}
                  required
                />
              </div>
              <div className=" mb-3">
                <label for="formFile" class="form-label">
                  <b> Select Tour Image 2</b>
                </label>
                <input
                  class="form-control"
                  type="file"
                  id="formFile"
                  name="image2"
                  onChange={(e) => setSelectImage2(e.target.files[0])}
                  required
                />
              </div>
              <div className=" mb-3">
                <label for="formFile" class="form-label">
                  <b> Select Tour Image 3</b>
                </label>
                <input
                  class="form-control"
                  type="file"
                  id="formFile"
                  name="image3"
                  onChange={(e) => setSelectImage3(e.target.files[0])}
                  required
                />
              </div>

              <div className="d-flex aligns-items-center justify-content-center mb-2">
                <button
                  type="submit"
                  class="btn bg-color custom-bg-text"
                  onClick={updateTourImage}
                >
                  Update Image
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateTourImage;
