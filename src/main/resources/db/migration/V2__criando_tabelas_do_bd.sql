create table tb_categoria (
                              id bigint AUTO_INCREMENT,
                              nome varchar(255),
                              criado_em DATETIME,
                              atualizado_em DATETIME,
                              primary key (id)
);

create table tb_produto (
                            id bigint AUTO_INCREMENT,
                            nome varchar(255),
                            descricao TEXT,
                            preco DOUBLE,
                            img_url varchar(255),
                            criado_em DATETIME,
                            atualizado_em DATETIME,
                            primary key (id)
);

create table tb_produto_categoria (
                                      id_produto bigint not null,
                                      id_categoria bigint not null,
                                      primary key (id_produto, id_categoria)
);

create table tb_role (
                         id bigint AUTO_INCREMENT,
                         autoridade varchar(255),
                         criado_em DATETIME,
                         atualizado_em DATETIME,
                         primary key (id)
);

create table tb_usuario (
                            id bigint AUTO_INCREMENT,
                            nome varchar(255),
                            telefone varchar(255),
                            email varchar(255),
                            senha varchar(255),
                            criado_em DATETIME,
                            atualizado_em DATETIME,
                            primary key (id)
);

create table tb_usuario_role (
                                 id_usuario bigint not null,
                                 id_perfil bigint not null,
                                 primary key (id_usuario, id_perfil)
);

alter table tb_produto_categoria add constraint FK_produto_categoria_categoria foreign key (id_categoria) references tb_categoria(id);
alter table tb_produto_categoria add constraint FK_produto_categoria_produto foreign key (id_produto) references tb_produto(id);

alter table tb_usuario_role add constraint FK_usuario_role_role foreign key (id_perfil) references tb_role(id);
alter table tb_usuario_role add constraint FK_usuario_role_usuario foreign key (id_usuario) references tb_usuario(id);
