insert into category (name) values ('categ_1');
insert into category (name, parent_id) values ('categ_2', 1);
insert into category (name, parent_id) values ('categ_3', 1);
insert into category (name, parent_id) values ('categ_4', 2);
insert into category (name, parent_id) values ('categ_5', 2);
insert into category (name, parent_id) values ('categ_6', 3);
insert into category (name, parent_id) values ('categ_7', 3);

insert into product (name) values ('prod_1');
insert into price (amount, currency, product_id) values (10.01, 'BYN', 1);
insert into price (amount, currency, product_id) values (20.01, 'EUR', 1);
insert into price (amount, currency, product_id) values (30.01, 'USD', 1);
insert into category_product (product_id, category_id) values (1, 3);

insert into product (name) values ('prod_2');
insert into price (amount, currency, product_id) values (10.01, 'BYN', 2);
insert into price (amount, currency, product_id) values (20.01, 'EUR', 2);
insert into price (amount, currency, product_id) values (30.01, 'USD', 2);
insert into category_product (product_id, category_id) values (2, 3);

insert into product (name) values ('prod_3');
insert into price (amount, currency, product_id) values (10.01, 'BYN', 3);
insert into price (amount, currency, product_id) values (20.01, 'EUR', 3);
insert into price (amount, currency, product_id) values (30.01, 'USD', 3);
insert into category_product (product_id, category_id) values (3, 2);
