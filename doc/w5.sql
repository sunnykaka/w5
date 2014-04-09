/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-4-8 21:26:42                            */
/*==============================================================*/


drop table if exists code;

drop table if exists operation;

drop table if exists operator;

drop table if exists order_base;

drop table if exists payslip;

drop table if exists permission;

drop table if exists resource;

drop table if exists role;

drop table if exists role_permission;

drop table if exists user;

/*==============================================================*/
/* Table: code                                                  */
/*==============================================================*/
create table code
(
   id                   bigint not null,
   name                 varchar(100),
   displayName          varchar(100),
   kind                 varchar(100),
   value                varchar(200),
   description          varchar(1000),
   updatable            boolean not null,
   deletable            boolean not null,
   path                 varchar(255),
   selectable           boolean not null,
   primary key (id)
);

/*==============================================================*/
/* Table: operation                                             */
/*==============================================================*/
create table operation
(
   id                   bigint not null,
   url                  varchar(100),
   resourceId           bigint,
   name                 varchar(30),
   configable           bit,
   required             varchar(1000),
   type                 int,
   primary key (id)
);

/*==============================================================*/
/* Table: operator                                              */
/*==============================================================*/
create table operator
(
   id                   bigint not null,
   creatorId            bigint,
   addTime              datetime,
   updatorId            bigint,
   updateTime           datetime,
   type                 varchar(100),
   primary key (id)
);

/*==============================================================*/
/* Table: order_base                                            */
/*==============================================================*/
create table order_base
(
   id                   bigint not null,
   coachId              bigint,
   customerId           bigint,
   customerUsername     varchar(64),
   coachUsername        varchar(64),
   price                double,
   startTime            datetime,
   endTime              datetime,
   coachHour            double,
   coachSalary          double,
   status               varchar(20),
   remark               varchar(512),
   operatorId           bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: payslip                                               */
/*==============================================================*/
create table payslip
(
   id                   bigint not null,
   year                 int,
   month                int,
   coachHour            double,
   coachSalary          double,
   adjustSalary         double,
   actualSalary         double,
   remark               varchar(512),
   status               varchar(20),
   userId               bigint,
   balanceBefore        double,
   balanceAfter         double,
   operatorId           bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   id                   bigint not null,
   resourceId           bigint,
   operationId          bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: resource                                              */
/*==============================================================*/
create table resource
(
   id                   bigint not null,
   name                 varchar(50),
   url                  varchar(50),
   userDefinedable      bit,
   uniqueKey            varchar(100),
   shareable            bit,
   systemInit           bit,
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   bigint not null,
   name                 varchar(50),
   parentId             bigint,
   url                  varchar(660),
   operatorId           bigint,
   description          varchar(300),
   admin                bit,
   updatable            bit,
   primary key (id)
);

/*==============================================================*/
/* Table: role_permission                                       */
/*==============================================================*/
create table role_permission
(
   id                   bigint not null,
   roleId               bigint,
   permissionId         bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null,
   username             varchar(64),
   password             varchar(256),
   gender               varchar(10),
   price                double,
   balance              double,
   nickname             varchar(64),
   name                 varchar(100),
   yy                   varchar(32),
   qq                   varchar(32),
   mobile               varchar(32),
   idcard               varchar(32),
   email                varchar(64),
   alipay               varchar(64),
   bankcard             varchar(64),
   type                 varchar(20),
   remark               varchar(512),
   discount             int,
   proportion           int,
   roleId               bigint,
   operatorId           bigint,
   primary key (id)
);

alter table operation add constraint FK_Reference_7 foreign key (resourceId)
      references resource (id) on delete restrict on update restrict;

alter table permission add constraint FK_Reference_5 foreign key (resourceId)
      references resource (id) on delete restrict on update restrict;

alter table permission add constraint FK_Reference_6 foreign key (operationId)
      references operation (id) on delete restrict on update restrict;

alter table role_permission add constraint FK_Reference_3 foreign key (roleId)
      references role (id) on delete restrict on update restrict;

alter table role_permission add constraint FK_Reference_4 foreign key (permissionId)
      references permission (id) on delete restrict on update restrict;

