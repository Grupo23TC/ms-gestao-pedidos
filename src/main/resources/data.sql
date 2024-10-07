INSERT INTO tb_pedido (usuario_id, codigo_rastreio, valor_total, status) VALUES (1, NULL, 150.45, 'CRIADO'), (2, NULL, 289.90, 'ENVIADO'), (3, NULL, 80.50, 'FATURADO'), (4, NULL, 50.75, 'CANCELADO'), (5, NULL, 120.20, 'ESTORNADO');

INSERT INTO tb_item_pedido (produto_id, quantidade, preco, pedido_id) VALUES  (1, 50, 1.99, 1), (2, 30, 23.99, 1), (3, 10, 9.99, 2), (4, 5, 15.49, 2), (5, 12, 7.99, 3), (6, 8, 10.99, 3), (7, 15, 3.49, 4), (8, 25, 5.75, 4), (9, 18, 4.99, 5), (10, 20, 6.00, 5);
