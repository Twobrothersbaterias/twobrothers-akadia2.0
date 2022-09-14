INSERT INTO tb_usuario(nome, nome_usuario, senha) VALUES('Gabriel', 'admin', '$2a$10$C7QV0ojH0CaUZVYzVNTVUuf2Jpeuxn9OyrJW85/NF0EGESWvsdvNe');

# CARGA DE ITENS ALEATÓRIOS
INSERT INTO tb_despesa(data_cadastro, data_pagamento, data_agendamento, descricao, valor, status_despesa, tipo_despesa, usuario_responsavel) VALUES
        ('2022-01-01', '2022-09-13', 'Não possui', 'Conta A', 200.0, 'PAGO', 'VARIAVEL', 'admin'),
       ('2022-01-01', '2022-09-13', 'Não possui', 'Conta B', 300.0, 'PAGO', 'FIXO', 'admin'),
       ('2022-13-13', '2022-09-10', 'Não possui', 'Conta C', 400.0, 'PAGO', 'FIXO', 'admin'),
       ('2022-01-01', '2022-13-14', 'Não possui', 'Conta D', 500.0, 'PAGO', 'VARIAVEL', 'admin'),
       ('2022-09-13', '2022-13-14', 'Não possui', 'Conta E', 500.0, 'PAGO', 'VARIAVEL', 'admin'),
       ('2022-01-01', '2022-09-13', 'Não possui', 'Conta F', 500.0, 'PAGO', 'VARIAVEL', 'admin'),
       ('2022-01-01', 'Em aberto', '2022-09-10', 'Teste A', 300.0, 'PENDENTE', 'FIXO', 'admin'),
       ('2022-01-01', 'Em aberto', '2022-09-14', 'Teste C', 500.0, 'PENDENTE', 'VARIAVEL', 'admin'),
       ('2022-01-01', 'Em aberto', '2022-09-14', 'Teste D', 600.0, 'PENDENTE', 'VARIAVEL', 'admin'),
       ('2022-09-13', 'Em aberto', '2022-09-14', 'Teste E', 1600.0, 'PENDENTE', 'VARIAVEL', 'admin'),
       ('2022-01-01', 'Em aberto', '2022-09-13', 'Teste F', 900.0, 'PENDENTE', 'VARIAVEL', 'admin'),
       ('2022-09-13', 'Em aberto', '2022-09-13', 'Teste G', 100.0, 'PENDENTE', 'VARIAVEL', 'admin'),
       ('2022-09-13', 'Em aberto', 'Não possui', 'Teste H', 100.0, 'PENDENTE', 'VARIAVEL', 'admin'),
       ('2022-01-01', '2022-09-14', '2022-09-14', 'Teste ERRADO', 1600.0, 'PENDENTE', 'VARIAVEL', 'admin');

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

 SELECT * FROM tb_despesa;
 SELECT * FROM tb_usuario;


