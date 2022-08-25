CREATE TABLE Cliente(
   idCliente integer primary key auto increment,
   telefone VARCHAR(20),
   endereco VARCHAR(50),
   Codigo  VARCHAR(50),
   nome VARCHAR(60)
    
);

select * from Cliente;

INSERT INTO Cliente VALUES (1,'99785-6546', 'Rua Olinda - n°22','123644','Carol')
INSERT INTO Cliente VALUES (2,'99589-7456', 'Rua vida - n°55','789654','Lucia')

CREATE TABLE Produto(
   idProduto integer primary key auto increment,
   nome VARCHAR(50),
   valor int,
   categoria VARCHAR(50)
);

select * from produto;

INSERT INTO produto VALUES (1, 'Cau Jens', 100, 'Roupa')

DROP TABLE Cliente