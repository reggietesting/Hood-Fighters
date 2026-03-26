
public class ReplicatorCompiler {
    String ID;
    String lastPivot;

    public ReplicatorCompiler(String ID) {
        this.ID = ID;
    }

    public void SetPivot(String lastPivot) {
        this.lastPivot = lastPivot;
    }

    public String getPivot() {
        return this.lastPivot;
    }
}

class ReplicatorDecompiler {

}
