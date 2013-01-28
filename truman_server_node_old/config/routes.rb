require 'api_constraints'

TrumanServer::Application.routes.draw do

  # App Root Route
  root :to => 'coordinates#main'

  # App Resources
  resources :coordinates

  # API Documentation
  apipie

  # Hardcoded SMS route defined because having namespace problems
  match "api/v1/contact" => "api/v1/contact#create"

  # API Namespace - /api .. Api::
  namespace :api, defaults: {format: 'json'} do
    # API - Version 1 (Default)
    scope module: :v1, constraints: ApiConstraints.new(version: 1, default: true) do
      resources :coordinates
    end
  end
end
