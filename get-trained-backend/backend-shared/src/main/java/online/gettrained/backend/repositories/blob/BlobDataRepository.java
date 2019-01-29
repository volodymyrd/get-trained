package online.gettrained.backend.repositories.blob;

import java.util.List;
import online.gettrained.backend.domain.blob.BlobData;
import online.gettrained.backend.domain.blob.BlobData.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 */
public interface BlobDataRepository extends JpaRepository<BlobData, Long> {

  @Query(
      "SELECT b.id, b.dateCreate, b.dateUpdate, b.name, b.size, b.type, b.contentType, b.version, b.keyInStorage "
          + "FROM BlobData b WHERE b.id=:id")
  Object[][] findOneWithoutBlob(@Param("id") long id);

  @Query("SELECT b.id, b.dateCreate, b.dateUpdate, b.name, b.size, b.type, b.contentType, b.version FROM BlobData b")
  List<Object[]> findAllWithoutBlob();

  @Query("SELECT b.type FROM BlobData b WHERE b.id=:id")
  Type getType(@Param("id") long id);

  @Modifying(flushAutomatically = true)
  @Query("DELETE FROM BlobData b WHERE b.id=:id")
  void deleteById(@Param("id") long id);

  int countByNameInLocalStorageCache(String name);

  @Query("SELECT b.id, b.keyInStorage FROM BlobData b WHERE b.object IS NOT NULL")
  List<Object[]> findAllWithBlobInDatabase();

  int countByKeyInStorage(String key);
}
