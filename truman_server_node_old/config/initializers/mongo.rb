MongoMapper.connection = Mongo::Connection.new('flame.mongohq.com', 27056, { :logger => Rails.logger })
MongoMapper.database = 'coordinates'
MongoMapper.database.authenticate('excel542', 'notme542')

if defined?(PhusionPassenger)
   PhusionPassenger.on_event(:starting_worker_process) do |forked|
     MongoMapper.connection.connect if forked
   end
end