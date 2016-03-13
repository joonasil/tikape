
package tikape.tikapeforum.dao;

import java.sql.SQLException;
import java.util.List;
import tikape.tikapeforum.database.taulut.Viesti;

public interface Dao<T, K> {
    
    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;
    
    int insert(String...keys) throws SQLException; 

    String insert1(String...keys) throws SQLException; 
    
    Viesti insert2(String... keys) throws SQLException;

    List<T> findTen(Integer key, int sivunro) throws SQLException;
    
}
