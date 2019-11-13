package br.com.kerubin.api.cadastros.banco.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import br.com.kerubin.api.cadastros.banco.entity.banco.BancoEntity;
import br.com.kerubin.api.cadastros.banco.entity.banco.BancoServiceImpl;

@Primary
@Service
public class CustomBancoServiceImpl extends BancoServiceImpl {
	
	@Override
	public BancoEntity create(BancoEntity bancoEntity) {
		ajustarNumeroBanco(bancoEntity);
		return super.create(bancoEntity);
	}
	
	@Override
	public BancoEntity update(UUID id, BancoEntity bancoEntity) {
		ajustarNumeroBanco(bancoEntity);
		return super.update(id, bancoEntity);
	}
	
	private void ajustarNumeroBanco(BancoEntity bancoEntity) {
		//85-x => 085-x
		String numero = bancoEntity.getNumero();
		List<String> partes = Arrays.asList(numero.split("-"));
		if (!partes.isEmpty()) {
			numero = partes.get(0);
			while (numero.length() < 3) {
				numero = "0".concat(numero);
			}
			partes.set(0, numero);
			numero = partes.stream().collect(Collectors.joining("-"));
			bancoEntity.setNumero(numero);
		}
	}

}
