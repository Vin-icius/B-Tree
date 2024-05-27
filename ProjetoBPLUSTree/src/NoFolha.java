public class NoFolha extends No{
    private No ant,prox;

    public NoFolha(){
        this.ant=null;
        this.prox=null;
    }
    public NoFolha(No ant,No prox){
        this.ant = ant;
        this.prox = prox;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
