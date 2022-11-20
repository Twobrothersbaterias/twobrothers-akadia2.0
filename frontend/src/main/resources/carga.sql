# CARGA DE USUÁRIO
INSERT INTO tb_usuario(nome, nome_usuario, senha) VALUES('Gabriel', 'admin', '$2a$10$h3NuWXkQLOjJyCW171i10O45tBf9J9weNh54eNemC6AjxmkVKpVZe');

# CARGA DE DESPESAS
INSERT INTO tb_despesa(data_cadastro, data_pagamento, data_agendamento, descricao, valor, status_despesa, tipo_despesa, persistencia, usuario_id) VALUES
       ('2022-09-16', '2022-09-14', 'Não possui', 'Conta de água', 90.0, 'PAGO', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Conta de luz', 200.0, 'PAGO', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', '2022-09-10', 'Não possui', 'Internet', 100.0, 'PAGO', 'FIXO', 'NAO', 1),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Gasolina moto', 50.0, 'PAGO', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Funcionários', 6000.0, 'PAGO', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Marketing', 500.0, 'PAGO', 'FIXO', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-16', 'Fatura cartão loja', 900.0, 'PENDENTE', 'FIXO', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-28', 'Financiamento loja nova', 4000.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-14', 'Reforma', 2400.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-14', 'Impostos', 400.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-14', 'Gasolina moto', 50.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-22', 'Comissão vendedores', 1200.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-30', 'Panfletos', 80.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-19', 'Mensalidade site', 110.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1),
       ('2022-09-16', 'Em aberto', '2022-09-12', 'Ferramentas', 220.0, 'PENDENTE', 'VARIAVEL', 'NAO', 1);

DELETE FROM tb_despesa;

# CARGA DE PATRIMONIOS
INSERT INTO tb_patrimonio(nome, data_cadastro, tipo_patrimonio, status_patrimonio, data_entrada, valor, usuario_id) VALUES
    ('Carro', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 60000.0, 1),
    ('Carro', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 50000.0, 1),
    ('Carro', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 40000.0, 1),
    ('Carro', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 30000.0, 1),
    ('Carro', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 20000.0, 1),
    ('Carro', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 10000.0, 1),
    ('Casa', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 650000.0, 1),
    ('Casa', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 550000.0, 1),
    ('Casa', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 450000.0, 1),
    ('Casa', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 350000.0, 1),
    ('Casa', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 250000.0, 1),
    ('Casa', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 150000.0, 1),
    ('Banco', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 1500000.0, 1),
    ('Banco', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 950000.0, 1),
    ('Banco', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 550000.0, 1),
    ('Banco', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 150000.0, 1),
    ('Banco', '2022-11-10', 'PASSIVO', 'PAGO', '2022-11-18', 21000.0, 1);

# CARGA DE PRODUTOS
INSERT INTO tb_produto(data_cadastro, sigla, marca_bateria, especificacao, quantidade_minima, usuario_id, quantidade, custo_total, custo_unitario, tipo_produto) values
('2022-01-01', 'DELCO38JD', 'ACDELCO', '38JD', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'DELCO40FD', 'ACDELCO', '40FD', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'DELCO45', 'ACDELCO', 'DELCO45', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'DELCO50GD', 'ACDELCO', 'DELCO50GD', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'DELCO50JD', 'ACDELCO', 'DELCO50JD', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'A38JD', 'AMERICA', 'A38JD', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'BAXTERCIVIC', 'BAXTER', 'BAXTERCIVIC', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'BAXTERFIT', 'BAXTER', 'BAXTERFIT', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'E100HE', 'ELETRAN', 'E100HE', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'E80D', 'ELETRAN', 'E80D', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'DF700', 'FREEDOM', 'ESTFREEDOM', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'FREEDOM93AH', 'FREEDOM', 'FREEDOM70AH', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', '12MN45', 'MOURA', 'MNMOURA', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'M100HE', 'MOURA', 'M100HE', 0, 1, 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'M100QD', 'MOURA', 'M100HE', 0, 1, 10, 1000.0, 100.0, 'BATERIA');


 SELECT * FROM tb_despesa;
 SELECT * FROM tb_cliente;
 SELECT * FROM tb_endereco;
 SELECT * FROM tb_usuario;
 SELECT * FROM tb_ordem;

DELETE FROM tb_produto;
DELETE FROM tb_cliente;