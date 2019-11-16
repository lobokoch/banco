package br.com.kerubin.api.cadastros.banco.conciliacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.webcohesion.ofx4j.domain.data.MessageSetType;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.ResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.domain.data.signon.SignonResponse;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;

import br.com.kerubin.api.cadastros.banco.conciliacao.ConciliacaoOFXReader;
import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

public class ConciliacaoParser {
	
	@Test
	public void test1() throws FileNotFoundException, IOException, OFXParseException {
		
		ConciliacaoOFXReader reader = new ConciliacaoOFXReader();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("ofx/Bradesco.ofx").getFile());
		reader.readOFXFile(file);
		
		System.out.println("**********************************************");
		System.out.println("Banco: " + reader.getBankId());
		System.out.println("Agência: " + reader.getBranchId());
		System.out.println("Conta: " + reader.getAccountNumber());
		
		System.out.println("Data inicial: " + reader.getTransactionsStartDate());
		System.out.println("Data final: " + reader.getTransactionsEndDate());
		List<Transaction> list = reader.getTransactions();
		System.out.println("");
		System.out.println(list.size() + " TRANSAÇÕES ENCONTRADAS\n");
		for (Transaction transaction : list) {
			System.out.println("Data: " + toLocalDate(transaction.getDatePosted()));
			System.out.println("Histórico: " + transaction.getMemo());
			System.out.println("Documento: " + reader.getTransactionDocument(transaction));
			System.out.println("Id: " + reader.getTransactionId(transaction));
			System.out.println("Tipo (C/D): " + transaction.getTransactionType().name());
			System.out.println("Valor: " + transaction.getAmount());
			System.out.println("");
		} // for
		
	}

	//@Test
	public void test() throws FileNotFoundException, IOException, OFXParseException {

		AggregateUnmarshaller<ResponseEnvelope> a = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("ofx/Bradesco.ofx").getFile());
		ResponseEnvelope re = a.unmarshal(new FileInputStream(file));
		
		//ResponseEnvelope re = a.unmarshal(new FileInputStream(new File(System.getProperty("user.home") + "/Desktop/teste.ofx")));

		// objeto contendo informações como instituição financeira, idioma, data da
		// conta.
		SignonResponse sr = re.getSignonResponse();
		System.out.println(sr);

		// como não existe esse get "BankStatementResponse bsr =
		// re.getBankStatementResponse();"
		// fiz esse codigo para capturar a lista de transações
		MessageSetType type = MessageSetType.banking;
		ResponseMessageSet message = re.getMessageSet(type);

		if (message != null) {
			List<BankStatementResponseTransaction> bank = ((BankingResponseMessageSet) message).getStatementResponses();
			int count = 0;
			for (BankStatementResponseTransaction b : bank) {
				count++;
				if (count > 1) {
					throw new RuntimeException("Count > 1");
				}
				
				System.out.println("banco: " + b.getMessage().getAccount().getBankId());
				System.out.println("cc: " + b.getMessage().getAccount().getAccountNumber());
				System.out.println("ag: " + b.getMessage().getAccount().getBranchId());
				System.out.println("balanço final: " + b.getMessage().getLedgerBalance().getAmount());
				System.out.println("dataDoArquivo: " + toDate(b.getMessage().getLedgerBalance().getAsOfDate()));
				
				System.out.println("Data inicial: " + toDate(b.getMessage().getTransactionList().getStart()));
				System.out.println("Data final: " + toDate(b.getMessage().getTransactionList().getEnd()));
				List<Transaction> list = b.getMessage().getTransactionList().getTransactions();
				System.out.println("TRANSAÇÕES\n");
				for (Transaction transaction : list) {
					System.out.println("tipo: " + transaction.getTransactionType().name());
					
					String tranDoc = getTranDoc(transaction.getId());
					if (tranDoc == null) {
						throw new RuntimeException("tranDoc == null");
					}
					System.out.println("id: " + tranDoc);
					System.out.println("data: " + toDate(transaction.getDatePosted()));
					System.out.println("valor: " + transaction.getAmount());
					System.out.println("descricao: " + transaction.getMemo());
					System.out.println("");
				}
			}
		}

	}
	
	private String getTranDoc(String id) {
		// N10214:15/08/19:-102.85:5370001: Tit.capitalizac:
		// N10214 : 15/08/19 : -102.85 : 5370001 : Tit.capitalizac :
		// ------------------------------^
		String[] parts = id.split(":");
		return parts != null && parts.length >= 4 ? parts[3] : null;
		
	}
	
	private LocalDate toDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
