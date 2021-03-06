/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.contabancaria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import org.springframework.data.repository.query.Param;

@Transactional(readOnly = true)
public interface ContaBancariaRepository extends JpaRepository<ContaBancariaEntity, java.util.UUID>, QuerydslPredicateExecutor<ContaBancariaEntity> {
	
	@Transactional
	@Modifying
	@Query("delete from ContaBancariaEntity cbe where cbe.id in ?1")
	void deleteInBulk(java.util.List<java.util.UUID> idList);
	
	
	// WARNING: supports only where clause with like for STRING fields. For relationships entities will get the first string autocomplete key field name.
	@Query("select distinct ac.id as id, ac.nomeTitular as nomeTitular, ac.cpfCnpjTitular as cpfCnpjTitular, ac.numeroConta as numeroConta, ac.digito as digito from ContaBancariaEntity ac where ( upper(unaccent(ac.nomeTitular)) like upper(concat('%', unaccent(:query), '%')) ) or ( upper(ac.cpfCnpjTitular) like upper(concat('%', :query, '%')) ) order by 1 asc")
	Collection<ContaBancariaAutoComplete> autoComplete(@Param("query") String query);
	
	// WARNING: supports only where clause with like for STRING fields. For relationships entities will get the first string autocomplete key field name.
	@Query("select distinct ac.numeroConta as numeroConta from ContaBancariaEntity ac where ( upper(ac.numeroConta) like upper(concat('%', :query, '%')) ) order by 1 asc")
	Collection<ContaBancariaNumeroContaAutoComplete> contaBancariaNumeroContaAutoComplete(@Param("query") String query);
}
