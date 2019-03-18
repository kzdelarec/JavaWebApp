delete from expense;
delete from wallet;
delete from authorities;
delete from users;

insert into users (username, password, enabled) values ('admin', '$2a$10$eG28hqAjihXGfSyrNUji9OZEGnMNh66uQUjjIBU0OaaE4Os4u1tom', 1); 
insert into users (username, password, enabled) values ('user', '$2a$10$9P8MUV6cFuWyeys48sQMSe99vu5C1GzwMvsvKvjuvyTCl69p0KsrO', 1); 

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN'); 
insert into authorities (username, authority) values ('admin', 'ROLE_USER'); 
insert into authorities (username, authority) values ('user', 'ROLE_USER');

insert into wallet(name,createdate,username) values('adminWallet', curdate(), 'admin');
insert into wallet(name,createdate,username) values('userWallet', curdate(), 'user');

insert into expense(name,price,type,createdate,walletid,expensetype) values('Pivo', 10, 'DRINK', TO_DATE('2019-05-07', 'YYYY-MM-DD'), 1, 'expense');
insert into expense(name,price,type,createdate,walletid,expensetype) values('Æevapi', 45, 'FOOD', TO_DATE('2019-05-05', 'YYYY-MM-DD'), 1, 'expense');
insert into expense(name,price,type,createdate,walletid,expensetype) values('Struja', 800, 'BILLS', TO_DATE('2019-04-27', 'YYYY-MM-DD'), 1, 'expense');
insert into expense(name,price,type,createdate,walletid,expensetype) values('Plæa', 5500, 'OTHER', TO_DATE('2019-04-13', 'YYYY-MM-DD'), 1, 'transaction');
insert into expense(name,price,type,createdate,walletid,expensetype) values('Osiguranje', 5000, 'BILLS', TO_DATE('2019-05-01', 'YYYY-MM-DD'), 1, 'expense');
insert into expense(name,price,type,createdate,walletid,expensetype) values('Plæa', 3000, 'OTHER', TO_DATE('2019-05-07', 'YYYY-MM-DD'), 2, 'transaction');
insert into expense(name,price,type,createdate,walletid,expensetype) values('Mobitel', 1853, 'SHOP', TO_DATE('2019-04-08', 'YYYY-MM-DD'), 2, 'expense');


