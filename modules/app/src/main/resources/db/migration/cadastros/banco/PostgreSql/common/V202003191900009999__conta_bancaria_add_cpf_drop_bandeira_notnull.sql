alter table conta_bancaria
	add column cpf_cnpj_titular VARCHAR(20);
	
alter table conta_bancaria
	alter column bandeira_cartao drop not null;
	
alter table cartao_credito
	alter column bandeira_cartao drop not null;	