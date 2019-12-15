-- Registros de inicialização do plano de contas pré-cadastrado.

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('cf1865a2-a7c4-48af-bca5-bad9b108c0d2','1','ATIVO','RECEITA',NULL,NULL,true,false)
,('282d5860-aca6-4f23-87b3-d508ab8e8d81','2','PASSIVO','DESPESA',NULL,NULL,true,false)
,('5cd7d81e-7e69-4c26-bf2f-12ad2e286fc5','3','DESPESAS','DESPESA',NULL,NULL,true,false)
,('1ea1d30c-83e2-4f8f-8c39-ee53ef0d79fe','4','RECEITAS','RECEITA',NULL,NULL,true,false)
,('a57d351d-cfba-49dd-9c0a-f75317bdf902','1.1','ATIVO CIRCULANTE','RECEITA',NULL,'cf1865a2-a7c4-48af-bca5-bad9b108c0d2',true,false)
,('71aaea58-638d-4d87-8020-7da200674c43','1.1.1','Caixa','RECEITA',NULL,'a57d351d-cfba-49dd-9c0a-f75317bdf902',true,false)
,('d634b428-20ef-4e57-b179-21d416facba1','1.1.2','Bancos Conta Movimento','RECEITA',NULL,'a57d351d-cfba-49dd-9c0a-f75317bdf902',true,false)
,('f6f37b73-50e3-4b04-a709-3c1bc5a6c1be','1.1.3','Aplicações Financeiras','RECEITA',NULL,'a57d351d-cfba-49dd-9c0a-f75317bdf902',true,false)
,('4933c82e-4e9d-4fb9-b7bf-b17ce28c9213','1.1.4','Contas a Receber','RECEITA',NULL,'a57d351d-cfba-49dd-9c0a-f75317bdf902',true,false)
,('34b9fab6-5d78-47f0-a265-f6a55d429773','1.1.5','Estoques','RECEITA',NULL,'a57d351d-cfba-49dd-9c0a-f75317bdf902',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('be18541c-86e6-4a56-95ea-6adcaa5002d7','1.2','ATIVO NÃO CIRCULANTES','RECEITA',NULL,'cf1865a2-a7c4-48af-bca5-bad9b108c0d2',true,false)
,('7d0db045-8675-427d-9682-1a2f1b12a894','1.2.1','Contas a Receber','RECEITA',NULL,'be18541c-86e6-4a56-95ea-6adcaa5002d7',true,false)
,('38d626c1-baaf-4829-bd4c-2d41d7fa3624','1.2.2','Investimentos','RECEITA',NULL,'be18541c-86e6-4a56-95ea-6adcaa5002d7',true,false)
,('d5dde6d0-dc35-47ae-b29a-90b05b412fb6','1.2.2.01','Participações Societárias','RECEITA',NULL,'38d626c1-baaf-4829-bd4c-2d41d7fa3624',true,false)
,('3c63f4b6-406f-44ce-958f-61427928df75','1.2.2.02','Imobilizado','RECEITA',NULL,'38d626c1-baaf-4829-bd4c-2d41d7fa3624',true,false)
,('9326e522-c83b-4d00-8de2-14f473d59ff8','1.2.2.03','Terrenos','RECEITA',NULL,'38d626c1-baaf-4829-bd4c-2d41d7fa3624',true,false)
,('1607b4a6-62e0-4ba5-953c-5fd93fe2476a','1.2.2.04','Veículos','RECEITA',NULL,'38d626c1-baaf-4829-bd4c-2d41d7fa3624',true,false)
,('4c99bae7-0068-4a48-ad20-fe5b2fb876de','1.2.2.05','Móveis','RECEITA',NULL,'38d626c1-baaf-4829-bd4c-2d41d7fa3624',true,false)
,('950f33b5-b4e3-4cd3-ac2c-8a6a12f9b154','1.2.2.06','Máquinas','RECEITA',NULL,'38d626c1-baaf-4829-bd4c-2d41d7fa3624',true,false)
,('071a3780-d3e4-4480-948b-c5da21568ae8','1.2.2.07','Ferramentas','RECEITA',NULL,'38d626c1-baaf-4829-bd4c-2d41d7fa3624',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('2e8a6f7d-32b7-416d-8635-9e31a3eca85c','1.2.3','Intangíveis','RECEITA',NULL,'be18541c-86e6-4a56-95ea-6adcaa5002d7',true,false)
,('1660900f-bbc0-4c6e-8c86-06771f810ed1','1.2.3.01','Softwares','RECEITA',NULL,'2e8a6f7d-32b7-416d-8635-9e31a3eca85c',true,false)
,('6262b472-fe26-40b9-8af1-9e13b2d7129d','1.2.3.02','Marcas','RECEITA',NULL,'2e8a6f7d-32b7-416d-8635-9e31a3eca85c',true,false)
,('4b657c0d-35c0-4745-b643-61752711822f','2.1','PASSIVO CIRCULANTE','DESPESA',NULL,'282d5860-aca6-4f23-87b3-d508ab8e8d81',true,false)
,('ed6494a0-032c-4bf8-a0b2-479e286447d0','2.1.1','Impostos e Contribuições a Recolher','DESPESA',NULL,'4b657c0d-35c0-4745-b643-61752711822f',true,false)
,('9875368b-1d62-4760-bc4b-2aefb4bdb04c','2.1.1.02','INSS','DESPESA',NULL,'ed6494a0-032c-4bf8-a0b2-479e286447d0',true,false)
,('9493372c-9628-4cff-9488-47b5111abb39','2.1.1.03','FGTS','DESPESA',NULL,'ed6494a0-032c-4bf8-a0b2-479e286447d0',true,false)
,('e6ba5596-0f5f-4228-a553-a4f456ff84f3','2.1.2','Contas a Pagar','DESPESA',NULL,'4b657c0d-35c0-4745-b643-61752711822f',true,false)
,('45362ea3-0098-4527-81b3-bf41a40b7419','2.1.2.07','Empréstimos','DESPESA',NULL,'e6ba5596-0f5f-4228-a553-a4f456ff84f3',true,false)
,('bc20bccc-e5c9-4eaa-ae34-f8ae10da17cc','2.1.2.08','Financiamentos','DESPESA',NULL,'e6ba5596-0f5f-4228-a553-a4f456ff84f3',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('4d810c74-a708-4d2c-a8ec-7a9986b76c9b','2.2','PASSIVO NÃO CIRCULANTE','DESPESA',NULL,'282d5860-aca6-4f23-87b3-d508ab8e8d81',true,false)
,('ffaa28fa-fd11-4493-941a-da99015237d5','2.2.1','Empréstimos Bancários','DESPESA',NULL,'4d810c74-a708-4d2c-a8ec-7a9986b76c9b',true,false)
,('0cc44ace-43fb-423f-9db0-ba3560813717','2.1.1.01','Simples a Recolher','DESPESA',NULL,'ed6494a0-032c-4bf8-a0b2-479e286447d0',true,false)
,('3961b834-605b-4816-8c7e-56178d4d830f','2.3','PATRIMÔNIO LÍQUIDO','DESPESA',NULL,'282d5860-aca6-4f23-87b3-d508ab8e8d81',true,false)
,('cb0056cf-24e4-42fb-811b-e7be3ff8fc93','2.3.1','Capital Social','DESPESA',NULL,'3961b834-605b-4816-8c7e-56178d4d830f',true,false)
,('a1471e3f-8603-4c32-a7de-f5a4c0e987ce','2.3.1.01','Capital Social Subscrito','DESPESA',NULL,'cb0056cf-24e4-42fb-811b-e7be3ff8fc93',true,false)
,('e966a2f3-702a-4395-8e8e-7412111a54c9','2.3.1.02','Capital Social a Integralizar','DESPESA',NULL,'cb0056cf-24e4-42fb-811b-e7be3ff8fc93',true,false)
,('9886ca57-e030-4f75-af34-6392b975cc42','2.3.2','Reservas','DESPESA',NULL,'3961b834-605b-4816-8c7e-56178d4d830f',true,false)
,('633ff70f-af39-47b7-818d-69d37ac99fe0','2.3.2.01','Reserva de Capital','DESPESA',NULL,'9886ca57-e030-4f75-af34-6392b975cc42',true,false)
,('b9ee55e8-f3be-4d30-81b7-f1904625e305','2.3.2.02','Reserva de Lucros','DESPESA',NULL,'9886ca57-e030-4f75-af34-6392b975cc42',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('cf2646c9-38e6-4546-8e07-6a56e73becce','2.3.3','Prejuízos Acumulados','DESPESA',NULL,'3961b834-605b-4816-8c7e-56178d4d830f',true,false)
,('05b907f0-dd35-4bbb-a6a1-2d49cfb53f45','2.3.3.01','Prejuízos Acumulados de Exercícios Anteriores','DESPESA',NULL,'cf2646c9-38e6-4546-8e07-6a56e73becce',true,false)
,('7049c3a4-781b-4128-b0da-51a1fb0dc559','2.3.3.02','Prejuízos do Exercício Atual','DESPESA',NULL,'cf2646c9-38e6-4546-8e07-6a56e73becce',true,false)
,('a349a8ca-2baa-412d-ab11-b0a52d5ab602','3.1','DESPESAS OPERACIONAIS','DESPESA',NULL,'5cd7d81e-7e69-4c26-bf2f-12ad2e286fc5',true,false)
,('24a4acf9-815d-4098-a34e-1ccfd4545265','3.1.1','Despesas Com Vendas','DESPESA',NULL,'a349a8ca-2baa-412d-ab11-b0a52d5ab602',true,false)
,('27f047af-9506-45af-a2f0-745b2d099030','3.1.1.01','Comissão Sobre Vendas','DESPESA',NULL,'24a4acf9-815d-4098-a34e-1ccfd4545265',true,false)
,('c78076a5-d21d-4c75-b9a7-e7974c51256f','3.1.1.02','Fretes','DESPESA',NULL,'24a4acf9-815d-4098-a34e-1ccfd4545265',true,false)
,('fde1bf29-bc19-470c-8760-958699263fae','3.1.1.03','Material de Embalagem','DESPESA',NULL,'24a4acf9-815d-4098-a34e-1ccfd4545265',true,false)
,('bf1025bb-6875-49d5-b9f4-66987241521c','3.1.1.04','Publicidade e Propaganda','DESPESA',NULL,'24a4acf9-815d-4098-a34e-1ccfd4545265',true,false)
,('a0ee2334-a4c9-47d6-95e1-f449416e7b5e','3.1.2','Despesas Administrativas','DESPESA',NULL,'a349a8ca-2baa-412d-ab11-b0a52d5ab602',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('bf53e9ff-f7f4-46e8-9deb-fee0de4c84aa','3.1.2.01','Aluguel','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('0a51cddb-3f96-462b-b754-90b407c204e9','3.1.2.02','Energia Elétrica','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('7b59c68b-835f-487e-85fa-9dc56ea89750','3.1.2.03','Água e Esgoto','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('985194a7-3575-4df4-b566-d56ea6137813','3.1.2.04','Internet','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('ca93c04d-ebcb-414c-9ba6-5214c2a53a94','3.1.2.05','Salários','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('e418fdb0-d2a4-4de8-9cc4-8f5a9667eba4','3.1.2.06','Telefonia Fixa','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('741f9e93-6388-42e4-9ee9-25098108ae96','3.1.2.07','Telefonia Móvel','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('63264b8a-a99c-45b8-a82a-656b4d49f2db','3.1.2.08','Impostos e Taxas','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('6b81c32a-af18-45fc-b969-3457ca15d01c','3.1.2.09','Material de Expediente','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('c0606a93-bd5a-4273-ae41-011591c7092d','3.1.2.10','Férias','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('774428f1-b0ee-4680-8590-9b9e6b2a443f','3.1.2.11','Décimo Terceiro Salário','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('3a83d411-0d27-4e9c-8d63-994d3bf7f42a','3.1.2.12','Encargos Sociais','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('cb9ba534-2f46-412a-baf9-4e94a9ad3986','3.1.3','Despesas Financeiras','DESPESA',NULL,'a349a8ca-2baa-412d-ab11-b0a52d5ab602',true,false)
,('3b48de74-4f0a-4fff-8d6c-70203e0660ee','3.1.3.01','Despesas Bancárias','DESPESA',NULL,'cb9ba534-2f46-412a-baf9-4e94a9ad3986',true,false)
,('cf329499-3256-4b6a-abf6-4e6ae2adaa33','3.1.2.13','Alimentação','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('c2acc4a3-c54a-4f30-a0ee-b9b4b2a129c5','3.1.2.14','Material de Limpeza','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('8f025ca3-c51a-432c-b0cc-5e834b1311e4','3.1.2.15','Plano de Saúde','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('b77122e7-af12-4eba-8762-aa6e59e3cd1f','3.1.2.16','Plano Odontológico','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('773c3e76-3a10-4933-86fd-5376679c0599','3.1.2.17','Vales','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('1992f25b-1bc9-4e71-970a-d16ff64dcfb4','3.1.2.18','Despesas Diversas','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('735cbc91-5d52-48cc-9296-23c431993833','3.1.2.19','Manutenções','DESPESA',NULL,'a0ee2334-a4c9-47d6-95e1-f449416e7b5e',true,false)
,('cd1e6fda-0d80-4198-a961-1c41977879a3','4.1','RECEITAS OPERACIONAIS','RECEITA',NULL,'1ea1d30c-83e2-4f8f-8c39-ee53ef0d79fe',true,false)
,('26a1c78b-dfff-4464-8725-661a933d844d','4.1.3','Receitas Financeiras','RECEITA',NULL,'cd1e6fda-0d80-4198-a961-1c41977879a3',true,false)
,('7024bd56-fa22-4f9a-8c45-af38ee5b3546','4.1.1','Vendas de Produtos','RECEITA',NULL,'cd1e6fda-0d80-4198-a961-1c41977879a3',true,false)
,('850c6667-ef5e-446f-aabb-0355a0da3cd8','4.1.2','Vendas de Serviços','RECEITA',NULL,'cd1e6fda-0d80-4198-a961-1c41977879a3',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;

-- Para conciliação bancária
INSERT INTO plano_conta (id,codigo,descricao,tipo_financeiro,tipo_receita_despesa,plano_conta_pai,ativo,deleted) VALUES 
('edd70429-20ba-4a07-a15b-942051e5eb29','3.999','BANCO','DESPESA',null,'5cd7d81e-7e69-4c26-bf2f-12ad2e286fc5',true,false)
,('5c29f176-eef5-4658-b00a-7e6d282429db','3.999.1','Conciliação bancária (dédito)','DESPESA',NULL,'edd70429-20ba-4a07-a15b-942051e5eb29',true,false)
--
,('02dcf115-a574-47dc-a598-93394d1e0b94','4.999','BANCO','RECEITA',null,'1ea1d30c-83e2-4f8f-8c39-ee53ef0d79fe',true,false)
,('34ff8dca-0bea-41ef-84a6-25440c87b211','4.999.1','Conciliação bancária (crédito)','RECEITA',NULL,'02dcf115-a574-47dc-a598-93394d1e0b94',true,false)
ON CONFLICT ON CONSTRAINT pk_plano_conta_id DO NOTHING;
