package br.com.kerubin.api.cadastros.banco.conciliacao;

import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.stripStart;

import com.webcohesion.ofx4j.domain.data.MessageSetType;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.ResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.banking.BankAccountDetails;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.domain.data.common.TransactionList;
import com.webcohesion.ofx4j.domain.data.common.TransactionType;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConciliacaoOFXReader {

	private BankStatementResponseTransaction bankStatementResponseTransaction;

	public BankStatementResponseTransaction getBankStatementResponseTransaction() {
		return bankStatementResponseTransaction;
	}

	public BankAccountDetails getAccount() {
		return bankStatementResponseTransaction.getMessage().getAccount();
	}

	public TransactionList getTransactionList() {
		return bankStatementResponseTransaction.getMessage().getTransactionList();
	}
	
	/**
	 * Retorna a lista de transações dos movimentos do extrato.
	 * */
	public List<Transaction> getTransactions() {
		return bankStatementResponseTransaction.getMessage().getTransactionList().getTransactions();
	}

	/**
	 * Retorna o número do banco, exemplo: 0237, para o Bradesco.
	 * */
	public String getBankId() {
		String bankId = bankStatementResponseTransaction.getMessage().getAccount().getBankId();
		return trimLeftZeros(bankId);
	}
	
	/**
	 * Retorna o número da agência bancária.
	 * */
	public String getBranchId() {
		String ag = bankStatementResponseTransaction.getMessage().getAccount().getBranchId();
		if (ag != null) {
			return trimLeftZeros(ag);
		}
		
		ag = bankStatementResponseTransaction.getMessage().getAccount().getAccountNumber();
		// ag = "7225/12563"
		//-------^
		if (ag != null) {
			String[] valores = ag.split("/");
			if (valores != null & valores.length > 0) {
				return trimLeftZeros(valores[0]);
			}
		}
		
		return null;
	}
	
	/**
	 * Retorna o número da conta bancária.
	 * */
	public String getAccountNumber() {
		String ag = bankStatementResponseTransaction.getMessage().getAccount().getBranchId();
		if (isNotEmpty(ag)) {
			return trimLeftZeros(bankStatementResponseTransaction.getMessage().getAccount().getAccountNumber());
		}
		
		String str = bankStatementResponseTransaction.getMessage().getAccount().getAccountNumber();
		// str = "7225/12563"
		//-------------^
		if (str != null) {
			String[] valores = str.split("/");
			if (valores != null & valores.length >= 2) {
				return trimLeftZeros(valores[1]);
			}
		}
		
		return null;
	}
	
	/**
	 * Retorna a data inicial da lista de transações.
	 * */
	public LocalDate getTransactionsStartDate() {
		return toLocalDate(bankStatementResponseTransaction.getMessage().getTransactionList().getStart());
	}
	
	/**
	 * Retorna a data final da lista de transações.
	 * */
	public LocalDate getTransactionsEndDate() {
		return toLocalDate(bankStatementResponseTransaction.getMessage().getTransactionList().getEnd());
	}

	public void readOFXFile(File file) {
		try {
			readOFXStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readOFXStream(InputStream stream) {
		bankStatementResponseTransaction = null;
		AggregateUnmarshaller<ResponseEnvelope> au = new AggregateUnmarshaller<ResponseEnvelope>(
				ResponseEnvelope.class);
		try {
			ResponseEnvelope re = au.unmarshal(stream);
			MessageSetType type = MessageSetType.banking;
			ResponseMessageSet message = re.getMessageSet(type);

			if (message != null) {
				List<BankStatementResponseTransaction> bank = ((BankingResponseMessageSet) message)
						.getStatementResponses();
				if (bank != null && !bank.isEmpty()) {
					bankStatementResponseTransaction = bank.get(0);
				}
			}
		} catch (IOException | OFXParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Extrai o número do documento da transação gerado pelo banco.
	 * */
	public String getTransactionDocument(Transaction transaction) {
		return transaction.getCheckNumber();
	}
	
	/**
	 * Monta e retorna o identificador único da transação: banco, agência, conta e FITID.
	 * */
	public String getTransactionId(Transaction transaction) {
		String bancoId = getBankId();
		String agenciaId = getBranchId();
		String contaId = getAccountNumber();
		
		String id = transaction.getId();
		id = StringUtils.normalizeSpace(id);

		// If it is null, I would like to know about that.
		id = id.replace(" ", "_");
		
		StringBuilder sb = new StringBuilder();
		sb.append(bancoId).append("_")
		.append(agenciaId).append("_")
		.append(contaId).append("_")
		.append(id);
		
		String str = sb.toString();
		if (str.length() > 255) {
			log.warn("getTransactionId with more then 255 characteres: ({}) {}", str.length(), str);
			str = str.substring(255);
		}
		
		return str;
	}
	
	/**
	 * Extrai o número do documento da transação gerado pelo banco.
	 * */
	public String getTransactionDocument(String id) {
		// N10214:15/08/19:-102.85:5370001: Tit.capitalizac:
		// N10214 : 15/08/19 : -102.85 : 5370001 : Tit.capitalizac :
		// ------------------------------^
		String[] parts = id.split(":");
		return parts != null && parts.length >= 4 ? parts[3] : null;
		
	}
	
	public static String trimLeftZeros(String str) {
		return stripStart(str, "0");
	}
	
	public static boolean isCredito(Transaction transaction) {
		return transaction != null && TransactionType.CREDIT.equals(transaction.getTransactionType());
	}
	
	public static boolean isDebito(Transaction transaction) {
		return transaction != null && TransactionType.DEBIT.equals(transaction.getTransactionType());
	}
	
}
