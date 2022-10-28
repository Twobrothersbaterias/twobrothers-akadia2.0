# CARGA DE USUÁRIO
INSERT INTO tb_usuario(nome, nome_usuario, senha) VALUES('Gabriel', 'admin', '$2a$10$h3NuWXkQLOjJyCW171i10O45tBf9J9weNh54eNemC6AjxmkVKpVZe');

# CARGA DE ITENS ALEATÓRIOS
INSERT INTO tb_despesa(data_cadastro, data_pagamento, data_agendamento, descricao, valor, status_despesa, tipo_despesa, persistencia, usuario_responsavel) VALUES
       ('2022-09-16', '2022-09-14', 'Não possui', 'Conta de água', 90.0, 'PAGO', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Conta de luz', 200.0, 'PAGO', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', '2022-09-10', 'Não possui', 'Internet', 100.0, 'PAGO', 'FIXO', 'NAO', 'admin'),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Gasolina moto', 50.0, 'PAGO', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Funcionários', 6000.0, 'PAGO', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', '2022-09-14', 'Não possui', 'Marketing', 500.0, 'PAGO', 'FIXO', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-16', 'Fatura cartão loja', 900.0, 'PENDENTE', 'FIXO', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-28', 'Financiamento loja nova', 4000.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-14', 'Reforma', 2400.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-14', 'Impostos', 400.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-14', 'Gasolina moto', 50.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-22', 'Comissão vendedores', 1200.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-30', 'Panfletos', 80.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-19', 'Mensalidade site', 110.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin'),
       ('2022-09-16', 'Em aberto', '2022-09-12', 'Ferramentas', 220.0, 'PENDENTE', 'VARIAVEL', 'NAO', 'admin');

DELETE FROM tb_despesa;

# CARGA DE PATRIMONIOS
INSERT INTO tb_patrimonio(nome, data_cadastro, tipo_patrimonio, status_patrimonio, data_entrada, valor, usuario_responsavel) VALUES
    ('Carro', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 60000.0, 'admin'),
    ('Carro', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 50000.0, 'admin'),
    ('Carro', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 40000.0, 'admin'),
    ('Carro', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 30000.0, 'admin'),
    ('Carro', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 20000.0, 'admin'),
    ('Carro', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 10000.0, 'admin'),
    ('Casa', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 650000.0, 'admin'),
    ('Casa', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 550000.0, 'admin'),
    ('Casa', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 450000.0, 'admin'),
    ('Casa', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 350000.0, 'admin'),
    ('Casa', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 250000.0, 'admin'),
    ('Casa', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 150000.0, 'admin'),
    ('Banco', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 1500000.0, 'admin'),
    ('Banco', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 950000.0, 'admin'),
    ('Banco', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 550000.0, 'admin'),
    ('Banco', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 150000.0, 'admin'),
    ('Banco', '2022-09-10', 'PASSIVO', 'PAGO', '2022-09-18', 21000.0, 'admin');
# CARGA DE PRODUTOS
INSERT INTO tb_produto(data_cadastro, sigla, marca_bateria, especificacao, quantidade_minima, usuario_responsavel, quantidade, custo_total, custo_unitario, tipo_produto) values
('2022-01-01', 'AGF65', 'AGF65', 'AGF65', 0, 'gui123', 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'MBN78', 'MBN78', 'MBN78', 0, 'gui123', 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'JBD67', 'JBD67', 'JBD67', 0, 'gui123', 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'HFI90', 'HFI90', 'HFI90', 0, 'gui123', 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'FSB35', 'FSB35', 'FSB35', 0, 'gui123', 10, 1000.0, 100.0, 'BATERIA'),
('2022-01-01', 'KJI12', 'KJI12', 'KJI12', 0, 'gui123', 10, 1000.0, 100.0, 'BATERIA');

 SELECT * FROM tb_despesa;
 SELECT * FROM tb_cliente;
 SELECT * FROM tb_endereco;
 SELECT * FROM tb_usuario;
 SELECT * FROM tb_ordem;

DELETE FROM tb_produto;
DELETE FROM tb_cliente;
