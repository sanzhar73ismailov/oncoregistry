package kz.kazniioir.oncoregistry.db;

public class DictionaryOnco {
private int id;
private String name;

    public DictionaryOnco() {
    }

    public DictionaryOnco(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dictionary{" + "id=" + id + ", name=" + name + '}';
    }
    
    

}
