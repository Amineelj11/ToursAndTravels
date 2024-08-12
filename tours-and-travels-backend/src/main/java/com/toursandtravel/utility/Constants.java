package com.toursandtravel.utility;

public class Constants {

	public enum UserRole {
		ROLE_CUSTOMER("Customer"), ROLE_ADMIN("Admin"), ROLE_TOUR_GUIDE("Tour Guide");

		private String role;

		private UserRole(String role) {
			this.role = role;
		}

		public String value() {
			return this.role;
		}
	}

	public enum ActiveStatus {
		ACTIVE("Active"), DEACTIVATED("Deactivated");

		private String status;

		private ActiveStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

	public enum BookingStatus {
		CONFIRMED("Confirmed"), CANCELLED("Cancelled");

		private String status;

		private BookingStatus(String status) {
			this.status = status;
		}

		public String value() {
			return this.status;
		}
	}

}
