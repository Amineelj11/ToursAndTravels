package com.toursandtravel.utility;

import java.security.SecureRandom;

public class Helper {

	public static String generateTourBookingId() {
		// Define the characters to use in the ID
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// Use SecureRandom for cryptographically strong random number generation
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(16);

		// Prefix the ID with 'T-'
		sb.append("T-");

		// Generate 14 characters randomly from the AlphaNumericString
		for (int i = 0; i < 14; i++) {
			int index = random.nextInt(AlphaNumericString.length());  // SecureRandom ensures unpredictability
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString().toUpperCase();  // Convert to upper case for consistency
	}
	public static String generateBookingPaymentId() {
		// Define the characters allowed in the payment ID
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// Use SecureRandom for cryptographically secure random number generation
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(16);

		// Add the prefix 'P-'
		sb.append("P-");

		// Generate 14 random characters from AlphaNumericString
		for (int i = 0; i < 14; i++) {
			int index = random.nextInt(AlphaNumericString.length());  // Use SecureRandom's nextInt() for strong random numbers
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString().toUpperCase();  // Return the generated ID in uppercase
	}

}
