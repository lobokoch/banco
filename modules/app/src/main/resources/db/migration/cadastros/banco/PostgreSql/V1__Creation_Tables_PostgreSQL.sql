/**************** WARNING WILL DELETE ALL TABLES *********
DROP TABLE IF EXISTS banco CASCADE;
DROP TABLE IF EXISTS agencia_bancaria CASCADE;
DROP TABLE IF EXISTS conta_bancaria CASCADE;
DROP TABLE IF EXISTS cartao_credito CASCADE;
DROP TABLE IF EXISTS bandeira_cartao CASCADE;
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

CREATE TABLE conta_bancaria /* ContaBancaria */  (
	id UUID NOT NULL,
	nome_titular VARCHAR(255) NOT NULL /* nomeTitular */,
	agencia UUID NOT NULL,
	tipo_conta_bancaria VARCHAR(255) NOT NULL /* tipoContaBancaria */,
	numero_conta VARCHAR(30) NOT NULL /* numeroConta */,
	digito VARCHAR(10),
	saldo DECIMAL,
	numero_cartao VARCHAR(50) /* numeroCartao */,
	codigo_seguranca VARCHAR(10) /* codigoSeguranca */,
	data_validade DATE /* dataValidade */,
	bandeira_cartao UUID NOT NULL /* bandeiraCartao */,
	ativo BOOLEAN NOT NULL DEFAULT true,
	created_by VARCHAR(255) /* createdBy */,
	created_date TIMESTAMP /* createdDate */,
	last_modified_by VARCHAR(255) /* lastModifiedBy */,
	last_modified_date TIMESTAMP /* lastModifiedDate */
);

CREATE TABLE cartao_credito /* CartaoCredito */  (
	id UUID NOT NULL,
	banco UUID NOT NULL,
	nome_titular VARCHAR(255) NOT NULL /* nomeTitular */,
	numero_cartao VARCHAR(50) NOT NULL /* numeroCartao */,
	validade DATE,
	codigo_seguranca VARCHAR(10) /* codigoSeguranca */,
	valor_limite DECIMAL /* valorLimite */,
	bandeira_cartao UUID NOT NULL /* bandeiraCartao */,
	ativo BOOLEAN NOT NULL DEFAULT true,
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

/* PRIMARY KEYS */
ALTER TABLE banco ADD CONSTRAINT pk_banco_id PRIMARY KEY (id);
ALTER TABLE agencia_bancaria ADD CONSTRAINT pk_agencia_bancaria_id PRIMARY KEY (id);
ALTER TABLE conta_bancaria ADD CONSTRAINT pk_conta_bancaria_id PRIMARY KEY (id);
ALTER TABLE cartao_credito ADD CONSTRAINT pk_cartao_credito_id PRIMARY KEY (id);
ALTER TABLE bandeira_cartao ADD CONSTRAINT pk_bandeira_cartao_id PRIMARY KEY (id);

/* FOREIGN KEYS */
ALTER TABLE agencia_bancaria ADD CONSTRAINT fk_agencia_bancaria_banco FOREIGN KEY (banco) REFERENCES banco (id);
ALTER TABLE conta_bancaria ADD CONSTRAINT fk_conta_bancaria_agencia FOREIGN KEY (agencia) REFERENCES agencia_bancaria (id);
ALTER TABLE conta_bancaria ADD CONSTRAINT fk_conta_bancaria_bandeira_cartao FOREIGN KEY (bandeira_cartao) REFERENCES bandeira_cartao (id);
ALTER TABLE cartao_credito ADD CONSTRAINT fk_cartao_credito_banco FOREIGN KEY (banco) REFERENCES banco (id);
ALTER TABLE cartao_credito ADD CONSTRAINT fk_cartao_credito_bandeira_cartao FOREIGN KEY (bandeira_cartao) REFERENCES bandeira_cartao (id);

