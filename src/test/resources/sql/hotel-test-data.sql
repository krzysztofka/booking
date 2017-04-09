insert into hotel (id, name, city) values (-10, 'Hilton',  'Warsaw');
insert into apartment (id, name, hotel_id, daily_price) values (-11, 'Room 1', -10, 10000);
insert into apartment (id, name, hotel_id, daily_price) values (-12, 'Room 2', -10, 20000);

insert into hotel (id, name, city) values (-20, 'Hilton',  'Gdansk');
insert into apartment (id, name, hotel_id, daily_price) values (-22, 'Room 1', -20, 15000);
insert into apartment (id, name, hotel_id, daily_price) values (-23, 'Room 2', -20, 30000);

insert into hotel (id, name, city) values (-30, 'Hilton',  'London') ;

insert into hotel (id, name, city) values (-40, 'Marriott',  'Warsaw');
insert into apartment (id, name, hotel_id, daily_price) values (-41, 'Room Gold', -40, 10000);
insert into apartment (id, name, hotel_id, daily_price) values (-42, 'Room Red', -40, 24001);

insert into hotel (id, name, city) values (-50, 'Marriott',  'London');

--- bookings
--- user -1 bookings
insert into user (id, email, name) values (-1, 'email-test@test.com', 'some name');

insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-1, -1, -11, to_date('2160-10-11', 'yyyy-MM-dd'), to_date('2160-10-12', 'yyyy-MM-dd'), 'CANCELED');

insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-2, -1, -41, to_date('2160-10-11', 'yyyy-MM-dd'), to_date('2160-10-18', 'yyyy-MM-dd'), 'BOOKED');

--- user -2 bookings
insert into user (id, email, name) values (-2, 'email-test2@test.com', 'some name 2');

insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-3, -2, -11, to_date('2160-05-11', 'yyyy-MM-dd'), to_date('2160-06-03', 'yyyy-MM-dd'), 'BOOKED');
insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-4, -2, -11, to_date('2160-06-12', 'yyyy-MM-dd'), to_date('2160-06-21', 'yyyy-MM-dd'), 'BOOKED');
insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-5, -2, -22, to_date('2160-06-02', 'yyyy-MM-dd'), to_date('2160-06-05', 'yyyy-MM-dd'), 'BOOKED');
insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-6, -2, -23, to_date('2160-06-05', 'yyyy-MM-dd'), to_date('2160-06-18', 'yyyy-MM-dd'), 'BOOKED');
insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-7, -2, -41, to_date('2160-06-01', 'yyyy-MM-dd'), to_date('2160-06-29', 'yyyy-MM-dd'), 'BOOKED');
insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-8, -2, -42, to_date('2160-06-04', 'yyyy-MM-dd'), to_date('2160-06-10', 'yyyy-MM-dd'), 'BOOKED');
insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-9, -2, -12, to_date('2160-06-03', 'yyyy-MM-dd'), to_date('2160-06-12', 'yyyy-MM-dd'), 'BOOKED');

