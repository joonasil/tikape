
package tikape.tikapeforum.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    
    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;
    
    //Insertit tarvii aina Stringejä, tässä nolla tai enempi.
    int insert(String...keys) throws SQLException; 
    // Tässä tehty kauheuksia jotta saataisiin insertistä ulos viestin sisältö...
    String insert1(String...keys) throws SQLException; 

   //List<T> liittyvatObjektit(Integer key) throws SQLException;
    List<T> findTen(Integer key, int sivunro) throws SQLException;
}
