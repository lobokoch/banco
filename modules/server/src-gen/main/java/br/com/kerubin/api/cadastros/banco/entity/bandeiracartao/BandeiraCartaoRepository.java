/**********************************************************************************************
Code generated by MKL Plug-in
Copyright: Kerubin - kerubin.platform@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.entity.bandeiracartao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import org.springframework.data.repository.query.Param;

@Transactional(readOnly = true)
public interface BandeiraCartaoRepository extends JpaRepository<BandeiraCartaoEntity, java.util.UUID>, QuerydslPredicateExecutor<BandeiraCartaoEntity> {
	
	@Transactional
	@Modifying
	@Query("delete from BandeiraCartaoEntity bce where bce.id in ?1")
	void deleteInBulk(java.util.List<java.util.UUID> idList);
	
	
	// WARNING: supports only where clause with like for STRING fields. For relationships entities will get the first string autocomplete key field name.
	@Query("select distinct ac.id as id, ac.nomeBandeira as nomeBandeira from BandeiraCartaoEntity ac where ( upper(unaccent(ac.nomeBandeira)) like upper(concat('%', unaccent(:query), '%')) ) order by 1 asc")
	Collection<BandeiraCartaoAutoComplete> autoComplete(@Param("query") String query);
	
	// WARNING: supports only where clause with like for STRING fields. For relationships entities will get the first string autocomplete key field name.
	@Query("select distinct ac.nomeBandeira as nomeBandeira from BandeiraCartaoEntity ac where ( upper(unaccent(ac.nomeBandeira)) like upper(concat('%', unaccent(:query), '%')) ) order by 1 asc")
	Collection<BandeiraCartaoNomeBandeiraAutoComplete> bandeiraCartaoNomeBandeiraAutoComplete(@Param("query") String query);
}
