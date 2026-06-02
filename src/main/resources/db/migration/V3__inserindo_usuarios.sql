insert into tb_role(autoridade) values('ROLE_ADMINISTRADOR');
insert into tb_role(autoridade) values('ROLE_VENDEDOR');
insert into tb_role(autoridade) values('ROLE_CLIENTE');

insert into tb_usuario(nome, telefone, email, senha) values('patrick', '37999404021', 'patrickcosta0501@gmail.com', '$2a$10$nSb63UMa0XpeRF2o/6m4Q.p2Fu6apq60/9OCFGmOkEdvA5YbuL.rK');
insert into tb_usuario(nome, telefone, email, senha) values('kauan', '37999404056', 'kauan@email.com', '$2a$10$nSb63UMa0XpeRF2o/6m4Q.p2Fu6apq60/9OCFGmOkEdvA5YbuL.rK');
insert into tb_usuario(nome, telefone, email, senha) values('matheus', '11998404061', 'matheus@email.com', '$2a$10$nSb63UMa0XpeRF2o/6m4Q.p2Fu6apq60/9OCFGmOkEdvA5YbuL.rK');

insert into tb_usuario_role(id_usuario, id_perfil) values(1,1);
insert into tb_usuario_role(id_usuario, id_perfil) values(2,2);
insert into tb_usuario_role(id_usuario, id_perfil) values(3,3);