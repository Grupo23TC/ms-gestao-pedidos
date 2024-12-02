INSERT INTO tb_pedido (usuario_id, codigo_rastreio, valor_total, status) VALUES (1, NULL, 150.45, 'CRIADO'), (2, NULL, 289.90, 'ENVIADO'), (3, NULL, 80.50, 'FATURADO'), (4, NULL, 50.75, 'CANCELADO'), (5, NULL, 120.20, 'ESTORNADO');

INSERT INTO tb_item_pedido (produto_id, quantidade, preco, pedido_id) VALUES  (1, 50, 1.99, 1), (2, 30, 23.99, 1), (3, 10, 9.99, 2), (4, 5, 15.49, 2), (5, 12, 7.99, 3), (6, 8, 10.99, 3), (7, 15, 3.49, 4), (8, 25, 5.75, 4), (9, 18, 4.99, 5), (10, 20, 6.00, 5);

INSERT INTO TB_PAGAMENTO (ID, STATUS, VALOR, PEDIDO_ID, DESCRICAO, TITULO) VALUES
                                                                           ('3a61cbd5-15ed-4d35-94f1-ee18beb4617d', 'PENDENTE', 150.00, 1, 'Pagamento aguardando confirmação', 'Pagamento Pedido 1'),
                                                                           ('6bcca141-3154-4b25-b799-a3bb647c4176', 'APROVADO', 200.50, 2, 'Pagamento aprovado', 'Pagamento Pedido 2'),
                                                                           ('55a9504f-48ab-4e50-b5c5-6cda516579b5', 'RECUSADO', 300.00, 3, 'Pagamento recusado pelo banco', 'Pagamento Pedido 3'),
                                                                           ('1666f4cb-ecf2-4de2-aa80-197d4454384a', 'CANCELADO', 120.00, 4, 'Pagamento cancelado pelo cliente', 'Pagamento Pedido 4'),
                                                                           ('51bab514-feed-4ac2-ada5-b4c7db14f34c', 'ESTORNADO', 250.00, 5, 'Pagamento estornado ao cliente', 'Pagamento Pedido 5');

