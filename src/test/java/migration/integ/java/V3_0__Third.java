package migration.integ.java;

import com.builtamont.cassandra.migration.api.migration.java.JavaMigration;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class V3_0__Third implements JavaMigration {

    @Override
    public void migrate(Session session) throws Exception {
        Insert insert = QueryBuilder.insertInto("test1");
        insert.value("space", "web");
        insert.value("key", "google");
        insert.value("value", "google.com");

        session.execute(insert);
    }
}
