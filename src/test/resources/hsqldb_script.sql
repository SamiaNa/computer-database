drop table if exists computer;
drop table if exists company;
create table computer (
              id                        bigint not null identity,
              name                      varchar(255),
              introduced                date NULL,
              discontinued              date NULL,
              company_id                bigint default NULL,
              constraint pk_computer primary key (id));

create table company (
               id bigint not null identity,
               name varchar(255),
               constraint pk_company primary key (id));

alter table computer add constraint fk_computer_company_1 
            foreign key (company_id) references company (id) on delete restrict on update restrict;

INSERT INTO company (name) VALUES ('HP');
INSERT INTO company (name) VALUES ('Dell');
INSERT INTO company (name) VALUES ('Apple');

INSERT INTO computer (name, company_id, introduced, discontinued) VALUES ('HP1', 0, NULL, NULL);
INSERT INTO computer (name, company_id, introduced, discontinued) VALUES ('Ordi1', NULL, '1998-01-01' , NULL);
INSERT INTO computer (name, company_id, introduced, discontinued) values ('Apple IIe', 2, NULL, NULL);

