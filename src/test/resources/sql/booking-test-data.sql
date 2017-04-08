insert into user (id, email, name) values (-1, 'email-test@test.com',  'some name');

insert into hotel (id, name, city) values (-1, 'Hilton',  'Warsaw');
insert into apartment (id, name, hotel_id, daily_price) values (-1, 'Room 1', -1, 10000);
insert into apartment (id, name, hotel_id, daily_price) values (-2, 'Room 2', -1, 20000);

insert into booking (id, user_id, apartment_id, from_date, to_date) values (-1, -1, -1, to_date('2017-10-10', 'yyyy-MM-dd'), to_date('2017-10-12', 'yyyy-MM-dd'));