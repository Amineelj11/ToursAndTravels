import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const ViewAllTransports = () => {
  const [allTransports, setAllCategories] = useState([]);
  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  let navigate = useNavigate();

  useEffect(() => {
    const getAllTransport = async () => {
      const allTransports = await retrieveAllTransport();
      if (allTransports) {
        setAllCategories(allTransports.transports);
      }
    };

    getAllTransport();
  }, []);

  const retrieveAllTransport = async () => {
    const response = await axios.get(
      "http://localhost:8085/api/transport/fetch/all"
    );
    console.log(response.data);
    return response.data;
  };

  const deleteTransport = (transportId, e) => {
    fetch(
      "http://localhost:8085/api/transport/delete?transportId=" + transportId,
      {
        method: "DELETE",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          //   Authorization: "Bearer " + admin_jwtToken,
        },
      }
    )
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

  const updateTransport = (transport) => {
    navigate("/admin/transport/update", { state: transport });
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
          <h2>All Tour Transports</h2>
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
                  <th scope="col">Transport Id</th>
                  <th scope="col">Transport Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {allTransports.map((transport) => {
                  return (
                    <tr>
                      <td>
                        <b>{transport.id}</b>
                      </td>
                      <td>
                        <b>{transport.name}</b>
                      </td>
                      <td>
                        <b>{transport.description}</b>
                      </td>
                      <td>
                        <button
                          onClick={() => updateTransport(transport)}
                          className="btn btn-sm bg-color custom-bg-text ms-2"
                        >
                          Update
                        </button>

                        <button
                          onClick={() => deleteTransport(transport.id)}
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

export default ViewAllTransports;
