package com.kevintcoughlin.helpers;

/**
 * Coordinate Data OBject
 * @author Kevin
 *
 */
public class Coordinate {
		private long 	id;
		private String 	date;
		private int 	latitude;
		private int 	longitude;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getDate() {
			return date;
		}
		
		public void setDate(String date){
			this.date = date;
		}
		
		/**
		 * Return Longitude
		 * @return
		 */
		public int getLongitude() {
			return longitude;
		}

		/** 
		 * Set Longitude
		 * @param longitude
		 */
		public void setLongitude(int longitude) {
			this.longitude = longitude;
		}

		/**
		 * Return Latitude
		 * @return
		 */
		public int getLatitude() {
			return latitude;
		}

		/**
		 * Set Latitude
		 * @param latitude
		 */
		public void setLatitude(int latitude) {
			this.latitude = latitude;
		}
		
		/**
		 *  Will be used by the ArrayAdapter in the ListView
		 */
		@Override
		public String toString() {
			String s_latitude = String.valueOf(this.latitude);
			String s_longitude = String.valueOf(this.longitude);
			return (s_latitude + " " + s_longitude + " - " + this.date);
		}
}
