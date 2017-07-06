insert into Role values('ROLE_ADMIN');
insert into Role values('ROLE_COMPRADOR');

insert into User(email,username,password,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled) values('comprador@gmail.com','Comprador','$2a$10$3Qrx0rv8qSmZ8s3RlD5qE.upleP7.Qzbg5EoIAm62evEkY4c023TK',1,1,1,1);

insert into User(email,username,password,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled) values('admin@casadocodigo.com.br','Administrador','$2a$10$3Qrx0rv8qSmZ8s3RlD5qE.upleP7.Qzbg5EoIAm62evEkY4c023TK',1,1,1,1);

insert into User_Role(User_email,authorities_role) values('comprador@gmail.com','ROLE_COMPRADOR');
insert into User_Role(User_email,authorities_role) values('admin@casadocodigo.com.br','ROLE_ADMIN');