package br.com.kerubin.api.cadastros.banco.conciliacao.model;

public class ConciliacaoBancariaConstants {
	
	public static final String HTTP = "http://";
	public static final String FINANCEIRO_CONTASPAGAR_SERVICE = "financeiro-contaspagar/financeiro/contas_pagar/conciliacaoBancaria";
	public static final String FINANCEIRO_CONTASRECEBER_SERVICE = "financeiro-contasreceber/financeiro/contas_receber/conciliacaoBancaria";
	public static final String FINANCEIRO_FLUXOCAIXA_SERVICE = "financeiro-fluxocaixa/financeiro/fluxo_caixa/conciliacaoBancaria";
	
	public static final int MODULO_FINANCEIRO_CONTASPAGAR = 0;
	public static final int MODULO_FINANCEIRO_CONTASRECEBER = 1;
	public static final int MODULO_FINANCEIRO_FLUXOCAIXA = 2;
	
	public static final String[] MODULOS_FINANCEIRO = {FINANCEIRO_CONTASPAGAR_SERVICE, FINANCEIRO_CONTASRECEBER_SERVICE, FINANCEIRO_FLUXOCAIXA_SERVICE};
	public static final String[] MODULOS_FINANCEIRO_DESC = {"Contas a Pagar", "Contas a Receber", "Fluxo de Caixa"};

}
