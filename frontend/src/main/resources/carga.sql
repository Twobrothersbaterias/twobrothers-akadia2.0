INSERT INTO tb_usuario(nome, nome_usuario, senha) VALUES('Gabriel', 'admin', '$2a$10$C7QV0ojH0CaUZVYzVNTVUuf2Jpeuxn9OyrJW85/NF0EGESWvsdvNe');

# CARGA DE ITENS ALEATÓRIOS
INSERT INTO tb_despesa(data_cadastro, data_pagamento, data_agendamento, descricao, valor, status_despesa, tipo_despesa, id_usuario_responsavel) VALUES
        ('2022-01-01', '2022-09-10', null, 'Conta A', 200.0, 'PAGO', 'VARIAVEL', 1),
       ('2022-01-01', '2022-09-13', null, 'Conta B', 300.0, 'PAGO', 'FIXO', 1),
       ('2022-10-13', '2022-09-11', null, 'Conta C', 400.0, 'PAGO', 'FIXO', 1),
       ('2022-01-01', '2022-10-14', null, 'Conta D', 500.0, 'PAGO', 'VARIAVEL', 1),
       ('2022-09-10', '2022-10-14', null, 'Conta E', 500.0, 'PAGO', 'VARIAVEL', 1),
       ('2022-01-01', '2022-09-10', null, 'Conta F', 500.0, 'PAGO', 'VARIAVEL', 1),
       ('2022-01-01', null, '2022-09-12', 'Teste A', 300.0, 'PENDENTE', 'FIXO', 1),
       ('2022-01-01', null, '2022-09-10', 'Teste B', 400.0, 'PENDENTE', 'FIXO', 1),
       ('2022-01-01', null, '2022-09-14', 'Teste C', 500.0, 'PENDENTE', 'VARIAVEL', 1),
       ('2022-01-01', null, '2022-09-14', 'Teste D', 600.0, 'PENDENTE', 'VARIAVEL', 1),
       ('2022-09-10', null, '2022-09-14', 'Teste E', 1600.0, 'PENDENTE', 'VARIAVEL', 1),
       ('2022-01-01', null, '2022-09-11', 'Teste F', 900.0, 'PENDENTE', 'VARIAVEL', 1),
       ('2022-09-10', null, '2022-09-10', 'Teste G', 100.0, 'PENDENTE', 'VARIAVEL', 1),
       ('2022-09-10', null, null, 'Teste H', 100.0, 'PENDENTE', 'VARIAVEL', 1),
       ('2022-01-01', '2022-09-14', '2022-09-14', 'Teste ERRADO', 1600.0, 'PENDENTE', 'VARIAVEL', 1);

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
('2028-01-01', '2028-01-01', null, 'Pago 11', 100.0, 'PAGO', 'VARIAVEL', 1),
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
('2028-01-01', null, '2028-01-01', 'Pago 11', 100.0, 'PENDENTE', 'VARIAVEL', 1),
('2028-01-01', null, '2028-01-01', 'Pago 12', 100.0, 'PENDENTE', 'VARIAVEL', 1);

 SELECT * FROM tb_despesa;
 SELECT * FROM tb_usuario;


