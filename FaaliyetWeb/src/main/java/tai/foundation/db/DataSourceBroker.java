package tai.foundation.db;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class DataSourceBroker {
    private static DataSourceBroker broker = null;
    private HashMap<String, DataSource> sources;

    public static Connection getConnection(String dsName) throws Exception {
        boolean activeConnection = false;
        int retryCount = 0;
        Connection conn = null;
        Statement st = null;

        while (!activeConnection) {
            try {
                conn = getBroker().getDataSource(dsName).getConnection();
                st = conn.createStatement();
                activeConnection = true;
                if (st != null) {
                    st.close();
                }
            } catch (Exception e) {
                if (isStaleConnectionException(e)) {
                    retryCount++;
                    if (retryCount > 3) {
                        throw new Exception("Failed to establish connection after 3 retries", e);
                    }
                } else {
                    throw e;
                }
            }
        }
        return conn;
    }

    private DataSource getDataSource(String dsName) {
        return sources.get(dsName);
    }

    private static synchronized DataSourceBroker getBroker() throws Exception {
        if (broker == null) {
            broker = new DataSourceBroker();
        }
        return broker;
    }

    public DataSourceBroker() throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("dataSources");
        Enumeration<String> dsNames = rb.getKeys();

        if (dsNames == null) {
            throw new Exception("NO DATA SOURCE FOUND");
        }

        sources = new HashMap<>();
        InitialContext ctx = new InitialContext();

        while (dsNames.hasMoreElements()) {
            String dsName = dsNames.nextElement();
            String dsLookupString = rb.getString(dsName);

            try {
                Object obj = ctx.lookup(dsLookupString);
                if (obj instanceof DataSource) {
                    sources.put(dsName, (DataSource) obj);
                } else {
                    throw new NamingException("Object found at " + dsLookupString + " is not a DataSource");
                }
            } catch (Exception e) {
                System.err.println("Error loading data source: " + dsName);
                throw e;
            }
        }
    }

    private static boolean isStaleConnectionException(Exception e) {
        return e.getClass().getName().contains("StaleConnectionException");
    }
}
