INSERT INTO conciliacao_bancaria
(id, banco_id, agencia_id, conta_id, data_ini, data_fim, situacao_conciliacao, created_by, created_date, last_modified_by, last_modified_date)
VALUES('53a51b1a-f53c-4544-b480-a3931af14359', '237', '7225', '12563', '2020-06-05', '2020-06-10', 'TRANSACOES_ANALISADAS', 'lobokoch@gmail.com', '2020-06-15 07:23:40.428', 'lobokoch@gmail.com', '2020-06-15 07:23:55.270');

INSERT INTO plano_conta
(id, codigo, descricao, tipo_financeiro, tipo_receita_despesa, plano_conta_pai, ativo, deleted)
VALUES('45be8f2c-4eed-4c3d-938c-7fee88230504', '2.1.1', 'Salário', 'RECEITA', 'FIXO', null, true, false);
INSERT INTO plano_conta
(id, codigo, descricao, tipo_financeiro, tipo_receita_despesa, plano_conta_pai, ativo, deleted)
VALUES('fc3143c2-1c42-4e47-87dc-1052e686e58e', '1.10.2', 'Topique', 'DESPESA', NULL, null, true, false);


INSERT INTO conciliacao_transacao
(id, trn_id, trn_data, trn_historico, trn_documento, trn_tipo, trn_valor, conciliacao_bancaria, situacao_conciliacao_trn, titulo_conciliado_id, titulo_conciliado_desc, titulo_conciliado_valor, titulo_conciliado_data_ven, titulo_conciliado_data_pag, titulo_plano_contas, data_conciliacao, conciliado_com_erro, conciliado_msg, created_by, created_date, last_modified_by, last_modified_date, titulo_conciliado_multiple)
VALUES('2714e069-b412-4b82-9dfd-503a423b58a0', '237_7225_12563_N10170:08/06/20:-430.0:0000248:_Pagto_Eletron_Cobranca:_Joseane_de_Oliveira_me', '2020-06-08', 'Pagto Eletron Cobranca Joseane de Oliveira me', '0000248', 'DEBITO', 430.0, '53a51b1a-f53c-4544-b480-a3931af14359', 'CONCILIAR_CONTAS_PAGAR', '20b32cfd-dd5c-43b9-bcf7-858886271c9a', 'Topique', 430, '2020-06-10', NULL, 'fc3143c2-1c42-4e47-87dc-1052e686e58e', NULL, NULL, NULL, 'lobokoch@gmail.com', '2020-06-15 07:23:41.008', 'lobokoch@gmail.com', '2020-06-21 08:41:35.545', false);
INSERT INTO conciliacao_transacao
(id, trn_id, trn_data, trn_historico, trn_documento, trn_tipo, trn_valor, conciliacao_bancaria, situacao_conciliacao_trn, titulo_conciliado_id, titulo_conciliado_desc, titulo_conciliado_valor, titulo_conciliado_data_ven, titulo_conciliado_data_pag, titulo_plano_contas, data_conciliacao, conciliado_com_erro, conciliado_msg, created_by, created_date, last_modified_by, last_modified_date, titulo_conciliado_multiple)
VALUES('854bf700-a3d6-4506-b840-4922ec6cb982', '237_7225_12563_N100DB:05/06/20:6534.73:8999875:_Ted_c_Sal_p/_c_Corrente:_Remet.marcio_Koch', '2020-06-05', 'Ted c Sal p/ c Corrente Remet.marcio Koch', '8999875', 'CREDITO', 6534.73, '53a51b1a-f53c-4544-b480-a3931af14359', 'CONCILIAR_CONTAS_RECEBER', '56868794-179a-4805-ac49-bd7a690e654f', 'Salário Márcio da Senior', 6500, '2020-05-06', NULL, '45be8f2c-4eed-4c3d-938c-7fee88230504', NULL, NULL, 'Valor da transação e valor da conta são diferentes em: R$ 34,73', 'lobokoch@gmail.com', '2020-06-15 07:23:40.974', 'lobokoch@gmail.com', '2020-06-21 11:32:12.730', NULL);
