alter table conciliacao_transacao
	add column titulo_conciliado_multiple BOOLEAN DEFAULT false;
	
alter table conciliacao_transacao_titulo
	add column titulo_conciliado_multiple BOOLEAN DEFAULT false;	
	
