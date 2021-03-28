package shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import shoppinglist.entity.DaftarBelanja;

public interface DaftarBelanjaRepo extends JpaRepository<DaftarBelanja, Long>
{
}
