create table foodtrucks (
    id SERIAL primary key
  , date DATE
  , startHour text
  , endHour text
  , location text
  , latitude float
  , longitude float
  , truckName text
  , truckId text
  , constraint u_constraint unique (date, startHour, endHour, location, truckId)
);