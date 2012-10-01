# Coordinate class for Truman API
class Coordinate
  include MongoMapper::Document

  key :latitude,        String
  key :longitude,      	String
  key :date, 			String
end