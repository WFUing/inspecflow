package info.nemoworks.inspecflow.domain;

public class Inspection {
    private String id;

    public Inspection(){
        
    }

    public Inspection(String id){
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}
