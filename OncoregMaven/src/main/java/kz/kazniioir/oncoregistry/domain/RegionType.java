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
import javax.persistence.UniqueConstraint;

/**
 * RegionType generated by hbm2java
 */
@Entity
@Table(name="region_type"
    ,catalog="oncoregistry"
    , uniqueConstraints = @UniqueConstraint(columnNames="name") 
)
public class RegionType  implements java.io.Serializable {


     private int id;
     private String name;
     private Set<Population> populations = new HashSet<Population>(0);

    public RegionType() {
    }

	
    public RegionType(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public RegionType(int id, String name, Set<Population> populations) {
       this.id = id;
       this.name = name;
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
    
    @Column(name="name", unique=true, nullable=false, length=20)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="regionType")
    public Set<Population> getPopulations() {
        return this.populations;
    }
    
    public void setPopulations(Set<Population> populations) {
        this.populations = populations;
    }




}


