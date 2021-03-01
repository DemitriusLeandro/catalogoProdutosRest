package catalogo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import catalogo.api.model.Produtos;

@Repository
@Transactional
public interface ProdutosRepository extends CrudRepository<Produtos, Long> {

    //
    @Query(value="select p.id, "
    		+ " p.description, "
    		+ " p.name, "
    		+ " p.price "
    		+ " from produtos p "
    		+ " where p.description like %:vDescription% "
    		+ " or p.name like %:vDescription% "
    		+ " and p.price >= :vMinPrice  "
    		+ " and p.price <= :vMaxPrice",nativeQuery = true)
  	public List<Produtos> findSearchProducts(@Param("vDescription") String vDescription, 
			                                 @Param("vMaxPrice") Double vMaxPrice,
			                                 @Param("vMinPrice") Double vMinPrice);	
	

    @Query("SELECT count(p) as total FROM Produtos p where p.id = :vId")
	public Long getContaProdId(@Param("vId") Long vDvIdescription);
}


