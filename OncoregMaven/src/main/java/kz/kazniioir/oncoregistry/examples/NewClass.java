package kz.kazniioir.oncoregistry.examples;

public class NewClass {

    private int integ;
    private String str;

    public NewClass() {
    }

    public NewClass(int integ, String str) {
        this.integ = integ;
        this.str = str;
    }
    public int getInteg() {
        return integ;
    }

    public void setInteg(int integ) {
        this.integ = integ;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "NewClass{" + "hash=" + hashCode() + ", integ=" + integ + ", str=" + str + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    

    
    
    
    
    public static void main(String[] args) throws CloneNotSupportedException {
        NewClass a1 = new NewClass(1, "ret");
        NewClass a2 = (NewClass) a1.clone();
        
        System.out.println("a1 = " + a1);
        System.out.println("a2 = " + a2);
        
        
    }
    
    
}

