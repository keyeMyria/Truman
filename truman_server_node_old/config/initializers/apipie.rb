Apipie.configure do |config|
  config.app_name = "Truman"
  config.app_info = "A living API"
  config.api_controllers_matcher = "#{Rails.root}/app/controllers/api/v1/*.rb"
  config.copyright = "&copy; 2012 Kevin Coughlin"
  config.doc_base_url = "/docs"
  config.app_info = <<-DOC
    Truman RESTful API Documentation
  DOC
end