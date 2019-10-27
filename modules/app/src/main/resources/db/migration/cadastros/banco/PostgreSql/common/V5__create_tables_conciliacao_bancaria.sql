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
	id UUID NOT NULL,
	trn_data DATE NOT NULL /* trnData */,
	trn_historico VARCHAR(255) NOT NULL /* trnHistorico */,
	trn_documento VARCHAR(255) NOT NULL /* trnDocumento */,
	trn_tipo VARCHAR(255) NOT NULL /* trnTipo */,
	trn_valor DECIMAL NOT NULL /* trnValor */,
	conciliacao_bancaria UUID NOT NULL /* conciliacaoBancaria */,
	situacao_conciliacao_trn VARCHAR(255) NOT NULL /* situacaoConciliacaoTrn */,
	titulo_conciliado_id UUID /* tituloConciliadoId */,
	titulo_conciliado_desc VARCHAR(255) /* tituloConciliadoDesc */,
	data_conciliacao DATE /* dataConciliacao */,
	conciliado_com_erro BOOLEAN DEFAULT false /* conciliadoComErro */,
	conciliado_msg VARCHAR(255) /* conciliadoMsg */,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

ALTER TABLE conciliacao_bancaria ADD CONSTRAINT pk_conciliacao_bancaria_id PRIMARY KEY (id);
ALTER TABLE conciliacao_transacao ADD CONSTRAINT pk_conciliacao_transacao_id PRIMARY KEY (id);

ALTER TABLE conciliacao_transacao ADD CONSTRAINT fk_conciliacao_transacao_conciliacao_bancaria FOREIGN KEY (conciliacao_bancaria) REFERENCES conciliacao_bancaria (id);