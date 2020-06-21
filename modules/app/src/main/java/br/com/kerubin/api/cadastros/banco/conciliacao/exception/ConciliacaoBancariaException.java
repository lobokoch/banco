package br.com.kerubin.api.cadastros.banco.conciliacao.exception;

public class ConciliacaoBancariaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ConciliacaoBancariaException(String message) {
        super(message);
    }
	
	public ConciliacaoBancariaException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public ConciliacaoBancariaException(Throwable cause) {
        super(cause);
    }

}
