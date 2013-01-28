module Api
  module V1
    class ContactController < ApplicationController
      respond_to :json

      def index
        respond_with "Hey"
      end

      def show
      end

      # Send a Message
      def create
        # Get Message Details from Query String
        message = params[:msg]      # Get User's Message
        contact = params[:contact]  # Get User's Contact info to reply

        # Send SMS
        kevins_number       = "16097818544"
        twilio_sid          = "AC63192fc7e4e399a231783f3f3a6d3a4f"
        twilio_token        = "37d04f3d7c34552f842f56b594c6ad30"
        twilio_phone_number = "18563164961"

        @twilio_client = Twilio::REST::Client.new twilio_sid, twilio_token

        @twilio_client.account.sms.messages.create(
          :from => "+1#{twilio_phone_number}",
          :to   => kevins_number,
          :body => "Truman: #{message} | Reply to: #{contact}"
        )
        respond_with :accepted
        #respond_with :not_accepted

        # Return location to user that made request
        #def location   
        # message_body  = params["Body"]
        # from_number   = params["From"]
        # SMSLogger.log_text_message from_number, message_body
      end

      def update
      end

      def destroy
      end
    end
  end
end
