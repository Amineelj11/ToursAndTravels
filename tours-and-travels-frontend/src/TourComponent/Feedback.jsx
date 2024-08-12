import { useState, useEffect } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Feedback = ({ tourId }) => {
  const [feedbacks, setFeedbacks] = useState([]);
  const [newFeedback, setNewFeedback] = useState("");

  useEffect(() => {
    fetchFeedbacks();
  }, []);

  const fetchFeedbacks = async () => {
    try {
      const response = await axios.get(`http://localhost:8085/api/feedbacks/all`);
      setFeedbacks(response.data);
    } catch (error) {
      toast.error("Failed to fetch feedbacks.");
    }
  };

  const addFeedback = async () => {
    try {
      const response = await axios.post(`http://localhost:8085/api/feedbacks/add`, {
        feedback: newFeedback,
        tourId: tourId,
      });
      setFeedbacks([...feedbacks, response.data]);
      setNewFeedback("");
      toast.success("Feedback added successfully!");
    } catch (error) {
      toast.error("Failed to add feedback.");
    }
  };

  const deleteFeedback = async (id) => {
    try {
      await axios.delete(`http://localhost:8085/api/feedbacks/${id}`);
      setFeedbacks(feedbacks.filter((feedback) => feedback.id !== id));
      toast.success("Feedback deleted successfully!");
    } catch (error) {
      toast.error("Failed to delete feedback.");
    }
  };

  return (
    <div className="feedback-section">
      <h4 className="text-primary-gradient">Feedbacks</h4>
      <ul className="list-group">
        {feedbacks.map((feedback) => (
          <li className="list-group-item d-flex justify-content-between align-items-center" key={feedback.id}>
            {feedback.feedback}
            <button className="btn btn-danger btn-sm" onClick={() => deleteFeedback(feedback.id)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
      <div className="mt-3">
        <input
          type="text"
          className="form-control"
          value={newFeedback}
          onChange={(e) => setNewFeedback(e.target.value)}
          placeholder="Add new feedback"
        />
        <button className="btn btn-primary mt-2" onClick={addFeedback}>
          Add Feedback
        </button>
      </div>
      <ToastContainer />
    </div>
  );
};

export default Feedback;
