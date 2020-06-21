/**************** WARNING WILL DELETE ALL TABLES *********
DROP TABLE IF EXISTS banco CASCADE;
DROP TABLE IF EXISTS agencia_bancaria CASCADE;
DROP TABLE IF EXISTS bandeira_cartao CASCADE;
DROP TABLE IF EXISTS conta_bancaria CASCADE;
DROP TABLE IF EXISTS cartao_credito CASCADE;
DROP TABLE IF EXISTS conciliacao_bancaria CASCADE;
DROP TABLE IF EXISTS conciliacao_transacao CASCADE;
DROP TABLE IF EXISTS conciliacao_transacao_titulo CASCADE;
DROP TABLE IF EXISTS plano_conta CASCADE;
**********************************************************/

CREATE TABLE banco /* Banco */  (
	id UUID NOT NULL,
	numero VARCHAR(20) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE agencia_bancaria /* AgenciaBancaria */  (
	id UUID NOT NULL,
	banco UUID NOT NULL,
	numero_agencia VARCHAR(50) NOT NULL /* numeroAgencia */,
	digito_agencia VARCHAR(10) NOT NULL /* digitoAgencia */,
	endereco VARCHAR(255),
	nome_gerente VARCHAR(255) /* nomeGerente */,
	fone VARCHAR(50),
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE bandeira_cartao /* BandeiraCartao */  (
	id UUID NOT NULL,
	nome_bandeira VARCHAR(255) NOT NULL /* nomeBandeira */,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE conta_bancaria /* ContaBancaria */  (
	id UUID NOT NULL,
	nome_titular VARCHAR(255) NOT NULL /* nomeTitular */,
	cpf_cnpj_titular VARCHAR(20) /* cpfCnpjTitular */,
	tipo_conta_bancaria VARCHAR(255) NOT NULL /* tipoContaBancaria */,
	agencia UUID NOT NULL,
	numero_conta VARCHAR(30) NOT NULL /* numeroConta */,
	digito VARCHAR(10),
	saldo DECIMAL,
	ativo BOOLEAN NOT NULL DEFAULT true,
	data_validade DATE /* dataValidade */,
	numero_cartao VARCHAR(50) /* numeroCartao */,
	codigo_seguranca VARCHAR(10) /* codigoSeguranca */,
	bandeira_cartao UUID /* bandeiraCartao */,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE cartao_credito /* CartaoCredito */  (
	id UUID NOT NULL,
	nome_titular VARCHAR(255) NOT NULL /* nomeTitular */,
	banco UUID NOT NULL,
	numero_cartao VARCHAR(50) NOT NULL /* numeroCartao */,
	validade DATE,
	codigo_seguranca VARCHAR(10) /* codigoSeguranca */,
	valor_limite DECIMAL /* valorLimite */,
	bandeira_cartao UUID /* bandeiraCartao */,
	ativo BOOLEAN NOT NULL DEFAULT true,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE conciliacao_bancaria /* ConciliacaoBancaria */  (
	id UUID NOT NULL,
	banco_id VARCHAR(255) NOT NULL /* bancoId */,
	agencia_id VARCHAR(255) NOT NULL /* agenciaId */,
	conta_id VARCHAR(255) NOT NULL /* contaId */,
	data_ini DATE NOT NULL /* dataIni */,
	data_fim DATE NOT NULL /* dataFim */,
	situacao_conciliacao VARCHAR(255) NOT NULL /* situacaoConciliacao */,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE conciliacao_transacao /* ConciliacaoTransacao */  (
	id UUID,
	trn_id VARCHAR(255) NOT NULL /* trnId */,
	trn_data DATE NOT NULL /* trnData */,
	trn_historico VARCHAR(255) NOT NULL /* trnHistorico */,
	trn_documento VARCHAR(255) NOT NULL /* trnDocumento */,
	trn_tipo VARCHAR(255) NOT NULL /* trnTipo */,
	trn_valor DECIMAL NOT NULL /* trnValor */,
	conciliacao_bancaria UUID NOT NULL /* conciliacaoBancaria */,
	situacao_conciliacao_trn VARCHAR(255) NOT NULL /* situacaoConciliacaoTrn */,
	titulo_conciliado_id UUID /* tituloConciliadoId */,
	titulo_conciliado_desc VARCHAR(255) /* tituloConciliadoDesc */,
	titulo_conciliado_valor DECIMAL /* tituloConciliadoValor */,
	titulo_conciliado_data_ven DATE /* tituloConciliadoDataVen */,
	titulo_conciliado_data_pag DATE /* tituloConciliadoDataPag */,
	titulo_plano_contas UUID /* tituloPlanoContas */,
	titulo_conciliado_multiple BOOLEAN DEFAULT false /* tituloConciliadoMultiple */,
	data_conciliacao DATE /* dataConciliacao */,
	conciliado_com_erro BOOLEAN DEFAULT false /* conciliadoComErro */,
	conciliado_msg VARCHAR(255) /* conciliadoMsg */,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE conciliacao_transacao_titulo /* ConciliacaoTransacaoTitulo */  (
	id UUID NOT NULL,
	conciliacao_transacao UUID NOT NULL /* conciliacaoTransacao */,
	titulo_conciliado_id UUID NOT NULL /* tituloConciliadoId */,
	titulo_conciliado_desc VARCHAR(255) NOT NULL /* tituloConciliadoDesc */,
	titulo_conciliado_valor DECIMAL /* tituloConciliadoValor */,
	titulo_conciliado_data_ven DATE NOT NULL /* tituloConciliadoDataVen */,
	titulo_conciliado_data_pag DATE /* tituloConciliadoDataPag */,
	titulo_plano_contas UUID /* tituloPlanoContas */,
	titulo_conciliado_multiple BOOLEAN DEFAULT false /* tituloConciliadoMultiple */,
	data_conciliacao DATE /* dataConciliacao */,
	situacao_conciliacao_trn VARCHAR(255) NOT NULL /* situacaoConciliacaoTrn */
);

CREATE TABLE plano_conta /* PlanoConta */  (
	id UUID NOT NULL,
	codigo VARCHAR(255) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	tipo_financeiro VARCHAR(255) NOT NULL /* tipoFinanceiro */,
	tipo_receita_despesa VARCHAR(255) /* tipoReceitaDespesa */,
	plano_conta_pai UUID /* planoContaPai */,
	ativo BOOLEAN NOT NULL DEFAULT true,
	deleted BOOLEAN DEFAULT false
);

/* PRIMARY KEYS */
ALTER TABLE banco ADD CONSTRAINT pk_banco_id PRIMARY KEY (id);
ALTER TABLE agencia_bancaria ADD CONSTRAINT pk_agencia_bancaria_id PRIMARY KEY (id);
ALTER TABLE bandeira_cartao ADD CONSTRAINT pk_bandeira_cartao_id PRIMARY KEY (id);
ALTER TABLE conta_bancaria ADD CONSTRAINT pk_conta_bancaria_id PRIMARY KEY (id);
ALTER TABLE cartao_credito ADD CONSTRAINT pk_cartao_credito_id PRIMARY KEY (id);
ALTER TABLE conciliacao_bancaria ADD CONSTRAINT pk_conciliacao_bancaria_id PRIMARY KEY (id);
ALTER TABLE conciliacao_transacao ADD CONSTRAINT pk_conciliacao_transacao_id PRIMARY KEY (id);
ALTER TABLE conciliacao_transacao_titulo ADD CONSTRAINT pk_conciliacao_transacao_titulo_id PRIMARY KEY (id);
ALTER TABLE plano_conta ADD CONSTRAINT pk_plano_conta_id PRIMARY KEY (id);

/* FOREIGN KEYS */
ALTER TABLE agencia_bancaria ADD CONSTRAINT fk_agencia_bancaria_banco FOREIGN KEY (banco) REFERENCES banco (id);
ALTER TABLE conta_bancaria ADD CONSTRAINT fk_conta_bancaria_agencia FOREIGN KEY (agencia) REFERENCES agencia_bancaria (id);
ALTER TABLE conta_bancaria ADD CONSTRAINT fk_conta_bancaria_bandeira_cartao FOREIGN KEY (bandeira_cartao) REFERENCES bandeira_cartao (id);
ALTER TABLE cartao_credito ADD CONSTRAINT fk_cartao_credito_banco FOREIGN KEY (banco) REFERENCES banco (id);
ALTER TABLE cartao_credito ADD CONSTRAINT fk_cartao_credito_bandeira_cartao FOREIGN KEY (bandeira_cartao) REFERENCES bandeira_cartao (id);
ALTER TABLE conciliacao_transacao ADD CONSTRAINT fk_conciliacao_transacao_conciliacao_bancaria FOREIGN KEY (conciliacao_bancaria) REFERENCES conciliacao_bancaria (id);
ALTER TABLE conciliacao_transacao ADD CONSTRAINT fk_conciliacao_transacao_titulo_plano_contas FOREIGN KEY (titulo_plano_contas) REFERENCES plano_conta (id);
ALTER TABLE conciliacao_transacao_titulo ADD CONSTRAINT fk_conciliacao_transacao_titulo_conciliacao_transacao FOREIGN KEY (conciliacao_transacao) REFERENCES conciliacao_transacao (id);
ALTER TABLE conciliacao_transacao_titulo ADD CONSTRAINT fk_conciliacao_transacao_titulo_titulo_plano_contas FOREIGN KEY (titulo_plano_contas) REFERENCES plano_conta (id);
ALTER TABLE plano_conta ADD CONSTRAINT fk_plano_conta_plano_conta_pai FOREIGN KEY (plano_conta_pai) REFERENCES plano_conta (id);


/* INDEXES */
CREATE INDEX conciliacao_transacao_titulo_conciliado_id_idx ON conciliacao_transacao (titulo_conciliado_id);
