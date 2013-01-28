module Api
  module V1
    class CoordinatesController < ApplicationController
      respond_to :json

      # Show All Coordinates
      def index
        respond_with Coordinate.all
      end

      # Show Coordinate
      def show
          @coordinate = Coordinate.find(params[:id])
          respond_with @coordinate
      end

      # Create Coordinate
      def create
        respond_with Coordinate.create(params[:coordinate])
      end

      # Update Coordinate
      def update
        respond_with Coordinate.update(params[:id], params[:coordinate])
      end

      # Destroy Coordinate
      def destroy
        respond_with Coordinate.destroy(params[:id])
      end
    end
  end
end
