create table foodtrucks (
    id SERIAL primary key
  , date DATE
  , startDate TIMESTAMP
  , endDate TIMESTAMP
  , location text
  , latitude float
  , longitude float
  , truckName text
  , truckId text
  , constraint u_constraint unique (date, startDate, endDate, location, truckId)
);