public class No {
    public static int n=4;
    private int vInfo[];
    private No vLig[];
    private No ant,prox;
    private int tl;

    public No()
    {
        vInfo = new int[n-1];
        vLig = new No[n];
        tl=0;
    }

    public No(int info, int posArq)
    {
        this();
        vInfo[0] = info;
        tl = 1;
    }

    public int procurarPosicao(int info)
    {
        int pos=0;
        while(pos<tl && info>vInfo[pos])
            pos++;
        return pos;
    }

    public void remanejar(int pos)
    {
        vLig[tl] = vLig[tl-1];
        for(int i=tl; i>pos; i--)
        {
            vInfo[i] = vInfo[i-1];
            vLig[i] = vLig[i-1];
        }
    }

    public void remanejarExclusao(int pos) {
        for (int i = pos; i < this.tl - 1; i++) {
            this.vInfo[i] = this.vInfo[i + 1];
            this.vLig[i] = this.vLig[i + 1];
        }
        this.vLig[this.tl - 1] = this.vLig[this.tl];
    }

    public int getvInfo(int p) {
        return vInfo[p];
    }

    public void setvInfo(int p, int info) {
        vInfo[p] = info;
    }

    public No getvLig(int p) {
        return vLig[p];
    }

    public void setvLig(int p, No lig) {
        vLig[p] = lig;
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
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
