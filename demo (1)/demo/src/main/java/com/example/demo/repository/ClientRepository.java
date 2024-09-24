package com.example.demo.repository;


import com.example.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository

public class ClientRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Client> getClients() {
        var clients = new ArrayList<Client>();

        String sql = "select * from clients ORDER BY id desc";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);


        while (rows.next()) {

            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));
            clients.add(client);
        }
        return clients;

    }

    public Client getClient(int id) {
        String sql = "Select * from clients where id =?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);

        if (rows.next()) {
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));
            return client;

        }
        return null;
    }

    public Client getClient(String email) {
        String sql = "Select * from clients where email =?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, email);

        if (rows.next()) {
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));
            return client;

        }
        return null;
    }

    public Client createClient(Client client) {
        String sql = "insert into clients(firstname,lastname,email,phone," +
                "address,created_at) values (?, ?, ?, ?, ?, ?)";
        int count = jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress(), client.getCreatedAt());

        if (count > 0) {
            int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
            return getClient(id);
        }
        return null;
    }

    public Client updateClient(Client client) {
        String sql = "update clients set firstname=?,lastname=?,email=?,"+""
        		+ "phone=?,address=?,created_at=? where id=?";
        jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress()
                , client.getCreatedAt(), client.getId());
        return getClient(client.getId());


    }
    public void deleteClient(int id){
        String sql ="Delete from clients where id=?";
        jdbcTemplate.update(sql,id);
    }
}

