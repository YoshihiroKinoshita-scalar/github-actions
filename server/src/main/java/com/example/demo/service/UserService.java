package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.model.User;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scalar.db.api.Delete;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.Get;
import com.scalar.db.api.Put;
import com.scalar.db.api.Result;
import com.scalar.db.api.Scan;
import com.scalar.db.api.Scanner;
import com.scalar.db.config.DatabaseConfig;
import com.scalar.db.exception.storage.ExecutionException;
import com.scalar.db.exception.transaction.CommitException;
import com.scalar.db.exception.transaction.CrudException;
import com.scalar.db.exception.transaction.UnknownTransactionStatusException;
import com.scalar.db.io.IntValue;
import com.scalar.db.io.Key;
import com.scalar.db.io.TextValue;
import com.scalar.db.service.StorageModule;
import com.scalar.db.service.StorageService;
import com.scalar.db.service.TransactionModule;
import com.scalar.db.service.TransactionService;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final String NAMESPACE = "demo";
  private final String TABLE_NAME = "user";
  private final String ID = "id";
  private final String USERNAME = "username";
  private final String FIRSTNAME = "firstname";
  private final String LASTNAME = "lastname";
  private final String EMAIL = "email";
  private final String PASSWORD = "password";
  private final String PHONE = "phone";
  private final String USERSTATUS = "userstatus";
  private final String GROUP_ID = "group_id";
  private final String GROUP_IDV = "grp1";
  private final StorageService storageService;
  private final TransactionService transactionService;

  public UserService() throws IOException {
    File prop_file = new File("/etc/scalar/database.properties");
    DatabaseConfig config = new DatabaseConfig(prop_file);
    Injector injector = Guice.createInjector(new StorageModule(config));
    storageService = injector.getInstance(StorageService.class);
    storageService.with(NAMESPACE, TABLE_NAME);
    injector = Guice.createInjector(new TransactionModule(config));
    transactionService = injector.getInstance(TransactionService.class);
    transactionService.with(NAMESPACE, TABLE_NAME);
  }

  public boolean create(User user) throws CrudException, CommitException, UnknownTransactionStatusException {
    DistributedTransaction tx = transactionService.start();
    Key partitionKey = new Key(new TextValue(GROUP_ID, GROUP_IDV));
    Key clusteringKey = new Key(new TextValue(USERNAME, user.getUsername()));
    Get get = new Get(partitionKey, clusteringKey);
    Optional<Result> result = tx.get(get);
    if ( result.isPresent() ) {
      return false;
    }
    Put put = new Put(partitionKey,clusteringKey)
            .withValue(new TextValue(ID,user.getId()))
            .withValue(new TextValue(FIRSTNAME, user.getFirstname()))
            .withValue(new TextValue(LASTNAME, user.getLastname()))
            .withValue(new TextValue(EMAIL,user.getEmail()))
            .withValue(new TextValue(PASSWORD, user.getPassword()))
            .withValue(new TextValue(PHONE, user.getPhone()))
            .withValue(new IntValue(USERSTATUS,user.getUserstatus()));
    tx.put(put);
    tx.commit();
    return true;
  }

  public boolean update(User user) throws CrudException, CommitException, UnknownTransactionStatusException {
    DistributedTransaction tx = transactionService.start();
    Key partitionKey = new Key(new TextValue(GROUP_ID, GROUP_IDV));
    Key clusteringKey = new Key(new TextValue(USERNAME, user.getUsername()));
    Get get = new Get(partitionKey, clusteringKey);
    Optional<Result> result = tx.get(get);
    if ( !result.isPresent() ) {
      return false;
    }
    Put put = new Put(partitionKey,clusteringKey)
            .withValue(new TextValue(ID,user.getId()))
            .withValue(new TextValue(FIRSTNAME, user.getFirstname()))
            .withValue(new TextValue(LASTNAME, user.getLastname()))
            .withValue(new TextValue(EMAIL, user.getEmail()))
            .withValue(new TextValue(PASSWORD,user.getPassword()))
            .withValue(new TextValue(PHONE, user.getPhone()))
            .withValue(new IntValue(USERSTATUS,user.getUserstatus()));
    tx.put(put);
    tx.commit();
    return true;
  }

  public boolean delete(User user) throws CrudException, CommitException, UnknownTransactionStatusException {
    DistributedTransaction tx = transactionService.start();
    Key partitionKey = new Key(new TextValue(GROUP_ID, GROUP_IDV));
    Key clusteringKey = new Key(new TextValue(USERNAME, user.getUsername()));
    Get get = new Get(partitionKey, clusteringKey);
    Optional<Result> result = tx.get(get);
    if ( !result.isPresent() ) {
      return false;
    }
    Delete delete = new Delete(partitionKey, clusteringKey);
    tx.delete(delete);
    tx.commit();
    return true;
  }

  public ArrayList<User> list() throws ExecutionException {
    ArrayList<User> ret = new ArrayList<User>();
    Key partitionKey = new Key(new TextValue(GROUP_ID, GROUP_IDV));
    Scan scan = new Scan(partitionKey);
    Scanner scanner = storageService.scan(scan);
    scanner.forEach(r -> {
      User user = new User();
      r.getValue(ID).ifPresent(v -> user.setId(((TextValue) v).getString().get()));
      r.getValue(USERNAME).ifPresent(v -> user.setUsername(((TextValue) v).getString().get()));
      r.getValue(FIRSTNAME).ifPresent(v -> user.setFirstname(((TextValue) v).getString().get()));
      r.getValue(LASTNAME).ifPresent(v -> user.setLastname(((TextValue) v).getString().get()));
      r.getValue(EMAIL).ifPresent(v -> user.setEmail(((TextValue) v).getString().get()));
      r.getValue(PASSWORD).ifPresent(v -> user.setPassword(((TextValue) v).getString().get()));
      r.getValue(PHONE).ifPresent(v -> user.setPhone(((TextValue) v).getString().get()));
      r.getValue(USERSTATUS).ifPresent(v -> user.setUserstatus(((IntValue) v).get()));
      ret.add(user);
    });
    return ret;
  }

  public User get(String username) throws ExecutionException {
    Key partitionKey = new Key(new TextValue(GROUP_ID, GROUP_IDV));
    Key clusteringKey = new Key(new TextValue(USERNAME, username));
    Get get = new Get(partitionKey, clusteringKey);
    Optional<Result> res = storageService.get(get);
    if ( !res.isPresent() ) {
      return null;
    }
    Result r = res.get();
    User user = new User();
    user.setId( ((TextValue)r.getValue(ID).get()).getString().get());
    user.setUsername( ((TextValue)r.getValue(USERNAME).get()).getString().get());
    user.setFirstname(((TextValue)r.getValue(FIRSTNAME).get()).getString().get());
    user.setLastname(((TextValue)r.getValue(LASTNAME).get()).getString().get());
    user.setEmail( ((TextValue)r.getValue(EMAIL).get()).getString().get());
    user.setPassword(((TextValue)r.getValue(PASSWORD).get()).getString().get());
    user.setPhone(((TextValue)r.getValue(PHONE).get()).getString().get());
    user.setUserstatus( ((IntValue)r.getValue(USERSTATUS).get()).get() );
    return user;
  }

  public void close() {
    storageService.close();
    transactionService.close();
  }
}