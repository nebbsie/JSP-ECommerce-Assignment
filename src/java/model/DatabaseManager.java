package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class DatabaseManager
{

    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/shopping;user=root;password=root";

    private Connection connection = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private int updateCount = -1;

    public void establishConnection()
    {
        if (connection == null)
        {
            try
            {
                DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
                connection = DriverManager.getConnection(DATABASE_URL);
            }
            catch (SQLException sqle)
            {
            }
        }
    }

    public void releaseConnection()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException sqle)
            {
            }
        }
        connection = null;
    }

    public boolean prepareStatement(String sql)
    {
        boolean statementPrepared = false;

        try
        {
            stmt = connection.prepareStatement(sql);
            statementPrepared = true;
        }
        catch (SQLException sqle)
        {
        }

        return statementPrepared;
    }

    public boolean setStatementParameter(int paramIndex, int paramValue)
    {
        boolean paramSet = false;

        if (stmt != null)
        {
            try
            {
                stmt.setInt(paramIndex, paramValue);
                paramSet = true;
            }
            catch (SQLException sqle)
            {
            }
        }
        return paramSet;
    }

    public boolean setStatementParameter(int paramIndex, String paramValue)
    {
        boolean paramSet = false;

        if (stmt != null)
        {
            try
            {
                stmt.setString(paramIndex, paramValue);
                paramSet = true;
            }
            catch (SQLException sqle)
            {
            }
        }
        return paramSet;
    }

    public boolean setStatementParameter(int paramIndex, boolean paramValue)
    {
        boolean paramSet = false;

        if (stmt != null)
        {
            try
            {
                stmt.setBoolean(paramIndex, paramValue);
                paramSet = true;
            }
            catch (SQLException sqle)
            {
            }
        }
        return paramSet;
    }

    public boolean setStatementParameter(int paramIndex, Date paramValue)
    {
        boolean paramSet = false;

        if (stmt != null)
        {
            try
            {
                stmt.setDate(paramIndex, paramValue);
                paramSet = true;
            }
            catch (SQLException sqle)
            {
            }
        }
        return paramSet;
    }

    public boolean setStatementParameter(int paramIndex, Time paramValue)
    {
        boolean paramSet = false;

        if (stmt != null)
        {
            try
            {
                stmt.setTime(paramIndex, paramValue);
                paramSet = true;
            }
            catch (SQLException sqle)
            {
            }
        }
        return paramSet;
    }

    public boolean executePreparedStatement()
    {
        boolean resultSetAvailable = false;

        try
        {
            resultSetAvailable = stmt.execute();
            updateCount = stmt.getUpdateCount();
            rs = stmt.getResultSet();
        }
        catch (SQLException sqle)
        {
        }

        return resultSetAvailable;
    }

    public int getUpdateCount()
    {
        return updateCount;
    }

    public ResultSet getResultSet()
    {
        return rs;
    }
}
