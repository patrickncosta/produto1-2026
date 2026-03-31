insert into tb_categoria(nome, criado_em) values('Livros',NOW());
insert into tb_categoria(nome, criado_em) values('Notebooks',NOW());
insert into tb_categoria(nome, criado_em) values('Computadores',NOW());
insert into tb_categoria(nome, criado_em) values('Brinquedos',NOW());

insert into tb_produto(nome, descricao, preco, img_url, criado_em) values('tela 15', 'tela para notebook', 400, 'aaa',NOW());
insert into tb_produto(nome, descricao, preco, img_url, criado_em) values('bola', 'bola de brinquedo', 20, 'bbb', NOW());
insert into tb_produto(nome, descricao, preco, img_url, criado_em) values('projetor', 'projetor portatil', 150, 'ccc', NOW());

insert into tb_produto_categoria(id_produto, id_categoria) values ('1','1');
insert into tb_produto_categoria(id_produto, id_categoria) values ('1','2');
insert into tb_produto_categoria(id_produto, id_categoria) values ('2','3');
insert into tb_produto_categoria(id_produto, id_categoria) values ('3','1');

insert into tb_role(autoridade) values('ROLE_ADMINISTRADOR');
insert into tb_role(autoridade) values('ROLE_VENDEDOR');
insert into tb_role(autoridade) values('ROLE_CLIENTE');

insert into tb_usuario(nome, telefone, email, senha) values('patrick', '37999404021', 'patrickcosta0501@gmail.com', '123@');
insert into tb_usuario(nome, telefone, email, senha) values('kauan', '37999404056', 'kauan@email.com', '132@');
insert into tb_usuario(nome, telefone, email, senha) values('matheus', '37998404061', 'matheus@email.com', '@321');

insert into tb_usuario_role(id_usuario, id_perfil) values(1,1);
insert into tb_usuario_role(id_usuario, id_perfil) values(2,2);
insert into tb_usuario_role(id_usuario, id_perfil) values(3,3);