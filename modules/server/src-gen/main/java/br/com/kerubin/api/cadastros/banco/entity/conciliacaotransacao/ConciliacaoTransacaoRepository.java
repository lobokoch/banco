/**********************************************************************************************
Code generated with MKL Plug-in version: 47.8.0
Code generated at time stamp: 2020-01-13T08:12:13.831
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

@Transactional(readOnly = true)
public interface ConciliacaoTransacaoRepository extends JpaRepository<ConciliacaoTransacaoEntity, java.util.UUID>, QuerydslPredicateExecutor<ConciliacaoTransacaoEntity> {
	
	// WARNING: supports only where clause with like for STRING fields. For relationships entities will get the first string autocomplete key field name.
	@Query("select distinct ac.id as id, ac.trnId as trnId from ConciliacaoTransacaoEntity ac where ( upper(ac.trnId) like upper(concat('%', :query, '%')) ) order by 1 asc")
	Collection<ConciliacaoTransacaoAutoComplete> autoComplete(@Param("query") String query);
	// WARNING: supports only where clause with like for STRING fields. For relationships entities will get the first string autocomplete key field name.
	@Query("select distinct ac.trnHistorico as trnHistorico from ConciliacaoTransacaoEntity ac where ( upper(ac.trnHistorico) like upper(concat('%', :query, '%')) ) order by 1 asc")
	Collection<ConciliacaoTransacaoTrnHistoricoAutoComplete> conciliacaoTransacaoTrnHistoricoAutoComplete(@Param("query") String query);
	// WARNING: supports only where clause with like for STRING fields. For relationships entities will get the first string autocomplete key field name.
	@Query("select distinct ac.trnDocumento as trnDocumento from ConciliacaoTransacaoEntity ac where ( upper(ac.trnDocumento) like upper(concat('%', :query, '%')) ) order by 1 asc")
	Collection<ConciliacaoTransacaoTrnDocumentoAutoComplete> conciliacaoTransacaoTrnDocumentoAutoComplete(@Param("query") String query);
	
	// Begin generated findBy
	
	Collection<ConciliacaoTransacaoEntity> findByConciliacaoBancariaId(java.util.UUID id);
	
	List<ConciliacaoTransacaoEntity> findByIdIsNotAndTituloConciliadoIdIs(java.util.UUID id, java.util.UUID tituloConciliadoId);
	// End generated findBy
}
