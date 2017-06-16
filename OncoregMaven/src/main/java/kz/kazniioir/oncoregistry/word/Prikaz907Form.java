package kz.kazniioir.oncoregistry.word;

public class Prikaz907Form {

    private int id;
    private int generalId;
    private String number;
    private String nameRus;
    private String nameKaz;
    private String documentType;
    private String format;
    private String organizations;
    private int pages;
    private int storageLife;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGeneralId() {
        return generalId;
    }

    public void setGeneralId(int generalId) {
        this.generalId = generalId;
    }
    
    

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getNameKaz() {
        return nameKaz;
    }

    public void setNameKaz(String nameKaz) {
        this.nameKaz = nameKaz;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOrganizations() {
        return organizations;
    }

    public void setOrganizations(String organizations) {
        this.organizations = organizations;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getStorageLife() {
        return storageLife;
    }

    public void setStorageLife(int storageLife) {
        this.storageLife = storageLife;
    }

    @Override
    public String toString() {
        return "Prikaz907Form{" + "id=" + id + ", generalId=" + generalId + ", number=" + number + ", \n   nameRus=" + nameRus + ", \n   nameKaz=" + nameKaz + ", documentType=" + documentType + ", format=" + format + ", organizations=" + organizations + ", pages=" + pages + ", storageLife=" + storageLife + '}';
    }

    
    
    
    
}
