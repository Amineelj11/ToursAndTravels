import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const ViewAllLodges = () => {
  const [allLodges, setAllCategories] = useState([]);
  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  let navigate = useNavigate();

  useEffect(() => {
    const getAllLodge = async () => {
      const allLodges = await retrieveAllLodge();
      if (allLodges) {
        setAllCategories(allLodges.lodges);
      }
    };

    getAllLodge();
  }, []);

  const retrieveAllLodge = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/lodge/fetch/all"
    );
    console.log(response.data);
    return response.data;
  };

  const deleteLodge = (lodgeId, e) => {
    fetch("http://localhost:8085/api/lodge/delete?lodgeId=" + lodgeId, {
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

  const updateLodge = (lodge) => {
    navigate("/admin/lodge/update", { state: lodge });
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
          <h2>All Tour Lodges</h2>
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
                  <th scope="col">Lodge Id</th>
                  <th scope="col">Lodge Type</th>
                  <th scope="col">Description</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {allLodges.map((lodge) => {
                  return (
                    <tr>
                      <td>
                        <b>{lodge.id}</b>
                      </td>
                      <td>
                        <b>{lodge.type}</b>
                      </td>
                      <td>
                        <b>{lodge.description}</b>
                      </td>
                      <td>
                        <button
                          onClick={() => updateLodge(lodge)}
                          className="btn btn-sm bg-color custom-bg-text ms-2"
                        >
                          Update
                        </button>

                        <button
                          onClick={() => deleteLodge(lodge.id)}
                          className="btn btn-sm bg-color custom-bg-text ms-2"
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
  );
};

export default ViewAllLodges;
