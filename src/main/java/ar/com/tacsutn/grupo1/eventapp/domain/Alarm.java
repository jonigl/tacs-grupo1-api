package ar.com.tacsutn.grupo1.eventapp.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel
public class Alarm {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(example = "12345678")
  private Long id;

  @Column(nullable = false)
  @ApiModelProperty(example = "sample alarm name")
  private String name;

  @Column(nullable = false)
  @ApiModelProperty(example = "sample criteria to find")
  private String criteria;

  public Alarm(Long id, String name, String criteria) {
    this.id = id;
    this.name = name;
    this.criteria = criteria;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCriteria() {
    return criteria;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }
}
