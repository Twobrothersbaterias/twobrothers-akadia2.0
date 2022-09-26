INSERT INTO tb_usuario(nome, nome_usuario, senha) VALUES('Gabriel', 'admin', '$2a$10$C7QV0ojH0CaUZVYzVNTVUuf2Jpeuxn9OyrJW85/NF0EGESWvsdvNe');

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
INSERT INTO tb_patrimonio(nome, data_cadastro, tipo_patrimonio, status_patrimonio, data_agendamento, data_pagamento, valor, usuario_responsavel) VALUES
    ('Carro', '2022-09-10', 'PASSIVO', 'PAGO', 'Não possui', '2022-09-18', 60000.0, 'admin');

# CARGA DE ITENS PAGOS
INSERT INTO tb_despesa(data_cadastro, data_pagamento, data_agendamento, descricao, valor, status_despesa, tipo_despesa, id_usuario_responsavel) VALUES
('2028-01-01', '2028-01-01', null, 'Pago 1', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 2', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 3', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 4', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 5', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 6', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 7', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 8', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 9', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 10', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 12', 100.0, 'PAGO', 'VARIAVEL', 1),
('2028-01-01', '2028-01-01', null, 'Pago 12', 100.0, 'PAGO', 'VARIAVEL', 1);

# CARGA DE ITENS PENDENTES
INSERT INTO tb_despesa(data_cadastro, data_pagamento, data_agendamento, descricao, valor, status_despesa, tipo_despesa, id_usuario_responsavel) VALUES
('2028-01-01', null, '2028-01-01',  'Pago 1', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 2', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01',  'Pago 3', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 4', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 5', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 6', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 7', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 8', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 9', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 10', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 12', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 12', 100.0, 'PENDENTE', 'VARIAVEL', 1);

# CARGA DE PRODUTOS
INSERT INTO tb_produto(data_cadastro, sigla, marca_bateria, especificacao, quantidade_minima, usuario_responsavel, quantidade, custo_total, custo_unitario, tipo_produto) values
('2022-01-01', 'M220PD', '220PD', '220PD', 0, 'gui123', 2, 200.0, 100.0, 'BATERIA'),
('2022-01-01', 'M220PD', '220PD', '220PD', 0, 'gui123', 2, 200.0, 100.0, 'BATERIA'),
('2022-01-01', 'M220PD', '220PD', '220PD', 0, 'gui123', 2, 200.0, 100.0, 'BATERIA'),
('2022-01-01', 'M220PD', '220PD', '220PD', 0, 'gui123', 2, 200.0, 100.0, 'BATERIA'),
('2022-01-01', 'M220PD', '220PD', '220PD', 0, 'gui123', 2, 200.0, 100.0, 'BATERIA'),
('2022-01-01', 'M220PD', '220PD', '220PD', 0, 'gui123', 2, 200.0, 100.0, 'BATERIA');

 SELECT * FROM tb_despesa;
 SELECT * FROM tb_cliente;
 SELECT * FROM tb_endereco;
 SELECT * FROM tb_usuario;

DELETE FROM tb_produto;