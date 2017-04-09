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

insert into user (id, email, name) values (-1, 'email-test@test.com', 'some name');

insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-1, -1, -11, to_date('2160-10-11', 'yyyy-MM-dd'), to_date('2160-10-12', 'yyyy-MM-dd'), 'CANCELED');

insert into booking (id, user_id, apartment_id, from_date, to_date, status)
    values (-2, -1, -41, to_date('2160-10-11', 'yyyy-MM-dd'), to_date('2160-10-18', 'yyyy-MM-dd'), 'BOOKED');

