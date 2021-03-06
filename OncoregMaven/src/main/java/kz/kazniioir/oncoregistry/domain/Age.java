package kz.kazniioir.oncoregistry.domain;
// Generated Apr 13, 2014 1:07:26 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Age generated by hbm2java
 */
@Entity
@Table(name="age"
    ,catalog="oncoregistry"
)
public class Age  implements java.io.Serializable {


     private int id;
     private String name;
     private Set<Morbidityolap> morbidityolaps = new HashSet<Morbidityolap>(0);
     private Set<Population> populations = new HashSet<Population>(0);

    public Age() {
    }

	
    public Age(int id) {
        this.id = id;
    }
    public Age(int id, String name, Set<Morbidityolap> morbidityolaps, Set<Population> populations) {
       this.id = id;
       this.name = name;
       this.morbidityolaps = morbidityolaps;
       this.populations = populations;
    }
   
     @Id 
    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="name", length=100)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="age")
    public Set<Morbidityolap> getMorbidityolaps() {
        return this.morbidityolaps;
    }
    
    public void setMorbidityolaps(Set<Morbidityolap> morbidityolaps) {
        this.morbidityolaps = morbidityolaps;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="age")
    public Set<Population> getPopulations() {
        return this.populations;
    }
    
    public void setPopulations(Set<Population> populations) {
        this.populations = populations;
    }




}


