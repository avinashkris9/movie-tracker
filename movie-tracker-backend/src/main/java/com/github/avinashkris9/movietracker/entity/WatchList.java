package com.github.avinashkris9.movietracker.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
    name = "watchList",
    uniqueConstraints = {@UniqueConstraint(columnNames ={"name","showType"})})
public class WatchList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private String showType;
  private LocalDate dateAdded;
  private long externalId;




}
