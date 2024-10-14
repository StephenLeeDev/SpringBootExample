package com.stephenleedev.SpringBoot.Example.repository;

import com.stephenleedev.SpringBoot.Example.domain.Member;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;

    // Constructor to initialize the DataSource
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        // SQL insert query to add a new member
        String sql = "insert into member(name) values(?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Get a connection from the data source
            conn = getConnection();
            // Prepare statement and set return key generation option
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, member.getName()); // Set the member's name in the query
            pstmt.executeUpdate(); // Execute the insert query

            rs = pstmt.getGeneratedKeys(); // Get the generated keys (ID)
            if (rs.next()) {
                member.setId(rs.getLong(1)); // Set the auto-generated ID to the member
            } else {
                throw new SQLException("id 조회 실패"); // Throw an error if ID retrieval fails
            }
            return member; // Return the saved member object
        } catch (Exception e) {
            throw new IllegalStateException(e); // Handle exceptions
        } finally {
            // Close all connections and resources
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        // SQL query to find a member by ID
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(); // Get a connection from the data source
            pstmt = conn.prepareStatement(sql); // Prepare the query
            pstmt.setLong(1, id); // Set the ID parameter in the query
            rs = pstmt.executeQuery(); // Execute the query

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id")); // Set the member's ID
                member.setName(rs.getString("name")); // Set the member's name
                return Optional.of(member); // Return the found member
            } else {
                return Optional.empty(); // Return an empty result if no member is found
            }
        } catch (Exception e) {
            throw new IllegalStateException(e); // Handle exceptions
        } finally {
            // Close all connections and resources
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        // SQL query to find a member by name
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(); // Get a connection from the data source
            pstmt = conn.prepareStatement(sql); // Prepare the query
            pstmt.setString(1, name); // Set the name parameter in the query
            rs = pstmt.executeQuery(); // Execute the query

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id")); // Set the member's ID
                member.setName(rs.getString("name")); // Set the member's name
                return Optional.of(member); // Return the found member
            }
            return Optional.empty(); // Return an empty result if no member is found
        } catch (Exception e) {
            throw new IllegalStateException(e); // Handle exceptions
        } finally {
            // Close all connections and resources
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        // SQL query to retrieve all members from the database
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(); // Get a connection from the data source
            pstmt = conn.prepareStatement(sql); // Prepare the query
            rs = pstmt.executeQuery(); // Execute the query

            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id")); // Set the member's ID
                member.setName(rs.getString("name")); // Set the member's name
                members.add(member); // Add member to the list
            }
            return members; // Return the list of all members
        } catch (Exception e) {
            throw new IllegalStateException(e); // Handle exceptions
        } finally {
            // Close all connections and resources
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void clearStore() {
        // This method is not implemented
    }

    // Helper method to get a database connection
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    // Helper method to close the connection, statement, and result set
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close(); // Close the result set
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close(); // Close the prepared statement
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn); // Close the connection
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to release a connection back to the DataSource
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}