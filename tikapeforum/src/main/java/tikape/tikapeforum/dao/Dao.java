
package tikape.tikapeforum.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    
    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;

    void delete(K key) throws SQLException;
    
    //Insertit tarvii aina Stringejä, tässä yksi tai enempi.
    void insert(String...keys) throws SQLException;
}
